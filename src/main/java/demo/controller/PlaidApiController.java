package demo.controller;

import com.google.common.collect.ImmutableMap;
import demo.services.PlaidAPIServiceInternal;
import demo.services.PlaidAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class PlaidApiController {

    @Autowired
    PlaidAPIServiceInternal plaidAPIService;
    @Autowired
    PlaidAuthService plaidAuthService;


    @RequestMapping("/item")
    public @ResponseBody ResponseEntity getItem(HttpSession session) throws Exception {
        return plaidAPIService.getItem(plaidAuthService.getAccessToken(session));
    }

    @GetMapping(value="/accounts")
    public @ResponseBody ResponseEntity getAccount(HttpSession session) throws Exception {
        return plaidAPIService.getAccounts(plaidAuthService.getAccessToken(session));
    }

    @PostMapping(value="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions(HttpSession session) throws Exception {
        return plaidAPIService.getTransactionsLoop(plaidAuthService.getAccessToken(session));
    }

    @PostMapping(value="/get_access_token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(
            HttpSession session,
            @RequestParam("public_token") String publicToken) throws Exception {

        PlaidAuthService.PlaidAccessInfo accessInfo = plaidAPIService.getAccessInfo(publicToken);
        plaidAuthService.setAccessToken(session, accessInfo);

        Map<String, Object> data = ImmutableMap.of("error", "false");
        return ResponseEntity.ok(data);
    }



}
