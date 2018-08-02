package demo.controller;

import demo.services.PlaidAPIServiceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PlaidMVCController {

    @Autowired
    PlaidAPIServiceInternal plaidApiService;

    /**
     * Home page.
     */
    @GetMapping(value="/")
    public String index(Model model) {
        model.addAttribute("PLAID_PUBLIC_KEY", plaidApiService.getEnv().getProperty("PLAID_PUBLIC_KEY"));
        model.addAttribute("PLAID_ENV", plaidApiService.getEnv().getProperty("PLAID_ENV"));
        return "index";
    }

    /**
     * Exchange link public token for access token.
     */
    @PostMapping(value="/get_access_token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(@RequestParam("public_token") String publicToken) throws Exception {

        return plaidApiService.getAccessToken();
    }

    /**
     * Retrieve high-level account information and account and routing numbers
     * for each account associated with the Item.
     */
    @GetMapping(value="/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getAccount() throws Exception {
        return plaidApiService.getAccounts();
    }

    /**
     * Pull the Item - this includes information about available products,
     * billed products, webhook information, and more.
     */
    @PostMapping(value="/item", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getItem() throws Exception {
        return plaidApiService.getItem();
    }

    /**
     * Pull transactions for the Item for the last 30 days.
     */
    @PostMapping(value="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions() throws Exception {
        return plaidApiService.getTransactions();
    }

}
