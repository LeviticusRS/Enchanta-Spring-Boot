package com.joshua.ransom.controller;


import club.minnced.discord.webhook.WebhookClient;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.serverside.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joshua.ransom.Website;
import com.joshua.ransom.model.*;
import com.joshua.ransom.service.MixPanelService;
import com.joshua.ransom.service.VoteService;
import com.joshua.ransom.util.Sessions;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventData;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static com.joshua.ransom.Website.PIXEL_ID;
import static com.joshua.ransom.Website.getContext;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {
    private final VoteService voteService;
    private final MixPanelService mixPanelService;
    private final JdbcTemplate db;

    public ApiController(VoteService voteService, MixPanelService mixPanelService, JdbcTemplate db) {
        this.voteService = voteService;
        this.mixPanelService = mixPanelService;
        this.db = db;
    }

    @GetMapping("/vote/callback/runelocus")
    public ResponseEntity runelocusCallBack(@RequestParam String usr) {
        log.info("runelocus callback controller: " + usr);

        voteService.callback(usr);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        log.info("Exiting callback controller");
        return responseEntity;
    }

    @GetMapping("/vote/callback/topg")
    public ResponseEntity topGCallBack(@RequestParam String p_resp, @RequestParam String ip) {
        log.info("topg callback controller: " + p_resp + " : " + ip);

        voteService.callback(p_resp);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        log.info("Exiting callback controller");
        return responseEntity;
    }

    @GetMapping("/vote/callback/top100arena")
    public ResponseEntity top100ArenaCallBack(@RequestParam String postback) {
        log.info("top100Arena callback controller: " + postback);

        voteService.callback(postback);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        log.info("Exiting callback controller");
        return responseEntity;
    }

    @PostMapping("/vote/callback/rspslist")
    public ResponseEntity rspsListCallBack(@RequestParam String userid, @RequestParam String userip, @RequestParam int voted) {
        log.info("rspslist callback controller: " + userid + " : " + userip + " : " + voted);
        String[] split = userid.split(",");

        if (voted == 1)
            voteService.callback(split[0]);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        log.info("Exiting callback controller");
        return responseEntity;
    }

    @PostMapping("/vote/callback/runelist")
    public ResponseEntity runeListCallBack(@RequestParam String postback) {
        log.info("runelist callback controller: " + postback);

        voteService.callback(postback);

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        log.info("Exiting callback controller");
        return responseEntity;
    }

    @SneakyThrows
    @GetMapping(value = "/vote")
    public void doVote(HttpServletResponse response, HttpServletRequest request, @RequestParam int siteId, @RequestParam int id) {
        String ip = Sessions.getClientIp(request);
        log.info("Attempting vote service");

        Vote vote = new Vote();
        vote.setUserId(id);
        String voteKey = voteService.generateVoteKey();
        vote.setVoteKey(voteKey);
        vote.setIpAddress(ip);
        vote.setStartedOn(Timestamp.from(Instant.now()));
        vote.setSiteId(siteId);

        Optional<Site> site = Optional.ofNullable(Site.findBySiteId(db, siteId));
        if (!site.isPresent()) {
            log.error("Invalid site id attempted");
            response.sendRedirect("/vote");
            return;
        }

        mixPanelService.event("Vote Attempt", Sessions.getClientIp(request));

        Optional<Vote> lastVoted = Optional.ofNullable(Vote.getMostRecentVote(db, id, ip, siteId));
        if (lastVoted.isPresent()) {
            log.error("Found earlier uncompleted vote");
            vote = lastVoted.get();
        } else {
            db.update("INSERT INTO votes (player_id, ip_address, started_on, vote_key, site_id) VALUES (?, ?, ?, ?, ?)", vote.getUserId(), vote.getIpAddress(), vote.getStartedOn(), vote.getVoteKey(), vote.getSiteId());
            log.info("Vote attempt saved in DB");
        }
        log.info("Exiting vote attempt service");

        String urlToVisit =  site.get().getUrl();
        urlToVisit = urlToVisit.replace("{sid}", site.get().getVoteId());
        urlToVisit = urlToVisit.replace("{incentive}", vote.getVoteKey());
        response.sendRedirect(urlToVisit);
    }

    @PostMapping("/checkout-session")
    public String checkout(HttpServletRequest request, @RequestParam int productId, @RequestParam int userId) throws StripeException {
        Optional<CreditPackage> packageOptional = CreditPackage.forProduct(productId);
        Optional<BondPackage> packageBondOptional = BondPackage.forProduct(productId);
        if (!packageOptional.isPresent() && !packageBondOptional.isPresent()) {
            return "INVALID_PACKAGE";
        }

        Account account = Account.findByAccountId(db, userId);
        if (account == null) {
            return "INVALID_ACCOUNT";
        }

        mixPanelService.event("Stripe Checkout Attempt", Sessions.getClientIp(request));

        int generatedInvoice = db.queryForObject("INSERT INTO invoices (account_id, ip, country, agent, product_id) VALUES (?, ?, ?, ?, ?) RETURNING id",
                Integer.class, account.id(), Sessions.getClientIp(request), Sessions.getCountry(request), Sessions.getUserAgent(request),
                packageOptional.isPresent() ? packageOptional.get().productId() : packageBondOptional.get().productId());

        Map<String, String> metaData = new HashMap();
        metaData.put("invoiceId", ""+generatedInvoice);
        Gson gson = new Gson();
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCancelUrl("https://enchanta.net/credits/stripe/cancel")
                        .setSuccessUrl("https://enchanta.net/credits/stripe/success")
                        .putAllMetadata(metaData)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount((long) ((packageOptional.isPresent() ? packageOptional.get().price() : packageBondOptional.get().price()) * 100))
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName(packageOptional.isPresent() ? packageOptional.get().packageName() : packageBondOptional.get().packageName())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap();
        responseData.put("id", session.getId());

        return gson.toJson(responseData);
    }

    @RequestMapping(value = "/stripe/complete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void stripeWebhookEndpoint(@RequestBody String json, HttpServletRequest request) {
        String header = request.getHeader("Stripe-Signature");
        String endpointSecret = "whsec_KALJ9TURqc1QPittOYUnuyFukneLImjc";
        Event event;
        try {
            event = Webhook.constructEvent(json, header, endpointSecret);
            EventData data = event.getData();
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(data.toJson());
            JsonObject metaData = jsonTree.getAsJsonObject().getAsJsonObject("object").getAsJsonObject("metadata");
            JsonObject customerDetails = jsonTree.getAsJsonObject().getAsJsonObject("object").getAsJsonObject("customer_details");
            String email = DigestUtils.sha256Hex(customerDetails.get("email").getAsString());
            int invoiceId = Integer.parseInt(metaData.get("invoiceId").getAsString());
            if (event.getType().equals("checkout.session.completed")) {
                db.update("INSERT INTO stripe_log (invoice_id, jsondata) VALUES (?, ?)", invoiceId, json);

                Map<String, Object> invoiceInfo = db.queryForMap("UPDATE invoices SET paid = TRUE, paid_on = now(), payment_method = ?" +
                        " WHERE id = ? AND paid = FALSE RETURNING account_id, product_id", "stripe", invoiceId);
                    int accountId = (int) invoiceInfo.get("account_id");
                    int productId = (int) invoiceInfo.get("product_id");
                    String ip = (String) invoiceInfo.get("ip");
                    String country = (String) invoiceInfo.get("country");
                    String agent = (String) invoiceInfo.get("agent");
                Optional<CreditPackage> packageOptional = CreditPackage.forProduct(productId);
                Optional<BondPackage> packageBondOptional = BondPackage.forProduct(productId);
                if (packageOptional.isPresent()) {
                    CreditPackage creditPackage = packageOptional.get();
                    Account account = Account.findByAccountId(db, accountId);
                    int worked = db.update("UPDATE accounts SET credits = credits + ? WHERE id = ?", creditPackage.totalCredits("STRIPE"),  accountId);
                    if (worked > 0) {
                        log.info("Completed Stripe Credits Donation");
                        Website.donationDiscord().send("Donation By: [" + account.displayName() + "] " + creditPackage);
                        List<com.facebook.ads.sdk.serverside.Event> events = new ArrayList<>();
                        long unixTime = System.currentTimeMillis() / 1000L;
                        UserData userData_0 = new UserData()
                                .emails(Collections.singletonList(email))
                                .clientIpAddress(ip)
                                .countryCode(country)
                                .clientUserAgent(agent);

                        CustomData customData_0 = new CustomData()
                                .orderId("Order[" + invoiceId + "]")
                                .itemNumber("Prod[" + productId + "]")
                                .value((float) creditPackage.price())
                                .currency("USD");

                        com.facebook.ads.sdk.serverside.Event event_0 = new com.facebook.ads.sdk.serverside.Event()
                                .eventName("Purchase")
                                .eventTime(unixTime)
                                .userData(userData_0)
                                .customData(customData_0)
                                .actionSource(ActionSource.website)
                                .eventSourceUrl("https://enchanta.net/credits/stripe/complete");
                        events.add(event_0);

                        EventRequest eventRequest = new EventRequest(PIXEL_ID, getContext())
                                .data(events);

                        try {
                           eventRequest.execute();
                        } catch (APIException e) {
                            e.printStackTrace();
                        }
                    } else {
                        log.info("Couldn't Complete Stripe Credits Donation: " + productId + " - " + accountId);
                    }
                    db.update("UPDATE accounts SET donated = donated + ? WHERE id = ?", creditPackage.price(), accountId);
                } else if (packageBondOptional.isPresent()) {
                    Account account = Account.findByAccountId(db, accountId);
                    BondPackage bondPackage = packageBondOptional.get();
                    int worked =  db.update("INSERT INTO credit_transactions (ip, account_id, product_id, quantity) " +
                                    "VALUES (?, ?, ?, ?)",
                            "API", account.id(), bondPackage.productId(), 1);
                    if (worked > 0) {
                        log.info("Completed Stripe Bond Donation");
                        Website.donationDiscord().send("Donation By: [" + account.displayName() + "] " + bondPackage);
                        List<com.facebook.ads.sdk.serverside.Event> events = new ArrayList<>();
                        long unixTime = System.currentTimeMillis() / 1000L;
                        UserData userData_0 = new UserData()
                                .emails(Collections.singletonList(email))
                                .clientIpAddress(ip)
                                .countryCode(country)
                                .clientUserAgent(agent);

                        CustomData customData_0 = new CustomData()
                                .value((float) bondPackage.price())
                                .orderId("Order[" + invoiceId + "]")
                                .itemNumber("Prod[" + productId + "]")
                                .currency("USD");

                        com.facebook.ads.sdk.serverside.Event event_0 = new com.facebook.ads.sdk.serverside.Event()
                                .eventName("Purchase")
                                .eventTime(unixTime)
                                .userData(userData_0)
                                .customData(customData_0)
                                .actionSource(ActionSource.website)
                                .eventSourceUrl("https://enchanta.net/credits/stripe/complete");
                        events.add(event_0);

                        EventRequest eventRequest = new EventRequest(PIXEL_ID, getContext())
                                .data(events);

                        try {
                            eventRequest.execute();
                        } catch (APIException e) {
                            e.printStackTrace();
                        }
                    } else {
                        log.info("Couldn't Complete Stripe Bond Donation: " + productId + " - " + accountId);
                    }
                } else {
                    log.info("Invalid Stripe Donation Package");
                }

            }
        } catch (SignatureVerificationException | DataAccessException e) {
            e.printStackTrace();
        }
    }
}
