package demo.controller;

import com.google.common.collect.ImmutableMap;
import demo.services.AccessToken;
import demo.services.PlaidAuthService;
import demo.services.PlaidCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class PlaidApiController {

    @Autowired
    PlaidCallService plaidCallService;

    @Autowired
    PlaidAuthService plaidAuthService;

    @Autowired
    AccessToken accessToken;


    @RequestMapping("/item")
    public @ResponseBody ResponseEntity getItem(HttpSession session) throws Exception {
        return plaidCallService.getItem(plaidAuthService.getAccessToken(session));
    }

    @GetMapping(value="/accounts/{id}")
    public @ResponseBody ResponseEntity getAccount(@PathVariable ("id") int id) throws Exception {
        return plaidCallService.getAccounts(id);
    }

    @GetMapping(value="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions(HttpSession session) throws Exception {
        return plaidCallService.getTransactionsLoop(plaidAuthService.getAccessToken(session));
    }


    @PostMapping(value="/get_access_token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(
            HttpSession session,
            @RequestParam("public_token") String publicToken) throws Exception {

        PlaidAuthService.PlaidAccessInfo accessInfo = accessToken.getAccessInfo(publicToken);
        plaidAuthService.setAccessToken(session, accessInfo);

        Map<String, Object> data = ImmutableMap.of("error", "false");
        return ResponseEntity.ok(data);
    }



}
