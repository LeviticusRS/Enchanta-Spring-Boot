package com.joshua.ransom.controller.store.stripe;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StripeResultsController {

    private final JdbcTemplate db;

    public StripeResultsController(JdbcTemplate db) {
        this.db = db;
    }

    @RequestMapping("/credits/stripe/success")
    public String success(Model model) {
        return "success";
    }

    @RequestMapping("/credits/stripe/cancel")
    public String cancel(Model model) {
        return "cancel";
    }
}