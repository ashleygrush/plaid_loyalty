package demo.controller;

import com.plaid.client.request.*;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.*;
import demo.service.PlaidAuthService;
import com.plaid.client.PlaidClient;
import demo.services.PlaidAPIServiceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.util.*;

/**
 * Created by ryandesmond on 8/1/18.
 */

@RestController
@RequestMapping("/test")
public class PlaidApiController {

    @Autowired
    PlaidAPIServiceInternal plaidAPIService;

    @RequestMapping("/item")
    public @ResponseBody ResponseEntity getItem() throws Exception {
        return plaidAPIService.getItem();
    }

    @GetMapping(value="/accounts")
    public @ResponseBody ResponseEntity getAccount() throws Exception {
        return plaidAPIService.getAccounts();
    }


    @GetMapping(value="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions() throws Exception {
        return plaidAPIService.getTransactionsLoop();
    }

    @PostMapping(value="/get_access_token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(@RequestParam("public_token") String publicToken) throws Exception {

        return plaidAPIService.getAccessToken();
    }

}
