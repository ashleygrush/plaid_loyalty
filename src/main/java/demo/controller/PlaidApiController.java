package demo.controller;

import demo.services.PlaidAPIServiceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
