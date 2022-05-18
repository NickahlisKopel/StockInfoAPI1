package com.careerdevs.stockinfoapi.controllers;

import com.careerdevs.stockinfoapi.models.Overview;
import com.careerdevs.stockinfoapi.repositories.OverviewRepository;
import com.careerdevs.stockinfoapi.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/stocks")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;


    @GetMapping("/overview/{symbol}")
    public ResponseEntity<?> dynamicOverview (RestTemplate restTemplate, @PathVariable("symbol") String symbol) {

        try {
            String apiKey = env.getProperty("ALPHA_VANTAGE_KEY");
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            Overview requestData = restTemplate.getForObject(url, Overview.class);
            if(requestData == null){
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            } else if(requestData.getSymbol() == null){
                return ApiErrorHandling.customApiError("Invalid Stock Symbol: " + symbol,
                        HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(requestData);

        }catch (Exception e){
           return ApiErrorHandling.genericApiError(e);
        }
    }


    @PostMapping("/overview/{symbol}")
    public ResponseEntity<?> uploadOverview (RestTemplate restTemplate, @PathVariable("symbol") String symbol) {

        try {
            String apiKey = env.getProperty("ALPHA_VANTAGE_KEY");
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            Overview requestData = restTemplate.getForObject(url, Overview.class);
            if(requestData == null){
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            } else if(requestData.getSymbol() == null){
                return ApiErrorHandling.customApiError("Invalid Stock Symbol: " + symbol,
                        HttpStatus.NOT_FOUND);
            }

            Overview savedOverview = overviewRepository.save(requestData);



            return ResponseEntity.ok(savedOverview);

        }catch (DataIntegrityViolationException e){
            return ApiErrorHandling.customApiError(
                    "Can not upload duplicate Stock data",
                    HttpStatus.BAD_REQUEST);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    //GET ALL from SQL
    @GetMapping("/overview/all")
    public ResponseEntity<?> getAllOverviews (){
        try{

            Iterable<Overview> allOverviews = overviewRepository.findAll();

            return new ResponseEntity<>(allOverviews, HttpStatus.OK);



        }catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    //DELETE ALL FROM SQL
    @DeleteMapping("/overview/deleteall")
    public ResponseEntity<?> deleteAllOverviews (){
        try{

            long totalOverviews = overviewRepository.count();
            overviewRepository.deleteAll();

            return new ResponseEntity<>("Overviews deleted: " + totalOverviews,HttpStatus.OK);

        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }


}
