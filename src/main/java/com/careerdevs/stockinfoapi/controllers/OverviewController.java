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

import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;


    @GetMapping("{symbol}")
    public ResponseEntity<?> dynamicOverview (RestTemplate restTemplate, @PathVariable("symbol") String symbol) {

        try {
            String apiKey = env.getProperty("ALPHA_VANTAGE_KEY");
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            Overview requestData = restTemplate.getForObject(url, Overview.class);
            if(requestData == null){
                ApiErrorHandling.throwErr(500,"Did not receive response from AV");

            } else if(requestData.getSymbol() == null){
                ApiErrorHandling.throwErr(404,"Invalid Stock Symbol"+symbol);

            }
            return ResponseEntity.ok(requestData);

        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(),e.getStatusCode().value());
        }

        catch (Exception e){
           return ApiErrorHandling.genericApiError(e);
        }
    }


    @PostMapping("{symbol}")
    public ResponseEntity<?> uploadOverview (RestTemplate restTemplate, @PathVariable("symbol") String symbol) {

        try {
            String apiKey = env.getProperty("ALPHA_VANTAGE_KEY");
            String url = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
            Overview requestData = restTemplate.getForObject(url, Overview.class);
            if(requestData == null){
                ApiErrorHandling.throwErr(500,"Did not receive response from AV");

            } else if(requestData.getSymbol() == null){
                ApiErrorHandling.throwErr(404,"Invalid Stock Symbol: " + symbol);
            }

            Overview savedOverview = overviewRepository.save(requestData);



            return ResponseEntity.ok(savedOverview);

        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch (DataIntegrityViolationException e){
            return ApiErrorHandling.customApiError(
                    "Can not upload duplicate Stock data",
                    404);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    //GET ALL from SQL
    @GetMapping("all")
    public ResponseEntity<?> getAllOverviews (){
        try{

            Iterable<Overview> allOverviews = overviewRepository.findAll();

            return ResponseEntity.ok(allOverviews);



        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    //DELETE ALL FROM SQL
    @DeleteMapping("deleteall")
    public ResponseEntity<?> deleteAllOverviews (){
        try{

            long totalOverviews = overviewRepository.count();

            if(totalOverviews == 0){
                ApiErrorHandling.throwErr(404,"No Overviews.");
            }

            overviewRepository.deleteAll();

            return new ResponseEntity<>("Overviews deleted: " + totalOverviews,HttpStatus.OK);

        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }

    //GET ONE FROM SQL
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getOverviewById (@PathVariable("id") String id) {
        try{
            long uID = Integer.parseInt(id);
            Optional<Overview> foundOverview = overviewRepository.findById(uID);

            if(foundOverview.isEmpty()){
                ApiErrorHandling.throwErr(404,id+"did not match any overview");
            }

            return ResponseEntity.ok(foundOverview);
        }catch (NumberFormatException e){
            return ApiErrorHandling.customApiError("ID Must be a number.",404);
        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    //DELETE ONE FROM SQL
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteOverviewById (@PathVariable("id") String id) {
        try{
            long uID = Integer.parseInt(id);
            Optional<Overview> foundOverview = overviewRepository.findById(uID);

            if(foundOverview.isEmpty()){
                ApiErrorHandling.throwErr(404,id + " did not match any overview");
            }

            overviewRepository.deleteById(uID);
            return new ResponseEntity<>(foundOverview, HttpStatus.OK);
        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch(NumberFormatException e){
            return ApiErrorHandling.customApiError("ID Must be a number: " + id,404);
        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

}
