package com.joshua.ransom.service;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Joshua Ransom on 11/1/2020.
 */
@Service
@Component
public class MixPanelService {

    private ClientDelivery delivery = new ClientDelivery();
    private final MixpanelAPI mixpanel = new MixpanelAPI();
    private final MessageBuilder messageBuilder = new MessageBuilder("5e637f6aeaf35f2af8c1fa7298179179");
    private String token;

    public void event(String event, String ip) {
        try {
            JSONObject props = new JSONObject();
            JSONObject update = messageBuilder.event(ip, event, props);
            deliver(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 60_000)
    private void submitMessages() {
        ClientDelivery d_ = delivery;
        delivery = new ClientDelivery();

        try {
            mixpanel.deliver(d_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deliver(JSONObject message) {
        delivery.addMessage(message);
    }

}
