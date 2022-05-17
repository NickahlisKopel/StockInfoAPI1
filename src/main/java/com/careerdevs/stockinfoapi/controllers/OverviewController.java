package com.careerdevs.stockinfoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/stocks")
public class OverviewController {

    @Autowired
    private Environment env;



    @GetMapping("/overview/{symbol}")
    public ResponseEntity<?> dynamicOverview (RestTemplate restTemplate, @PathVariable("symbol") String symbol) {

        try {
            String apiKey = env.getProperty("ALPHA_VANTAGE_KEY");
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            Object requestData = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(requestData);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
