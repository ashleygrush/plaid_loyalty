package demo.controller;

import demo.service.PlaidAuthService;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.ItemGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.ErrorResponse;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.ItemGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.ItemStatus;
import com.plaid.client.response.TransactionsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class HomeController {

    private final Environment env;
    private PlaidClient plaidClient;
    private final PlaidAuthService authService;


    @Autowired
    public HomeController(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.plaidClient = plaidClient;
        this.authService = authService;
    }




    /**
     * Home page.
     */
    @GetMapping(value="/")
    public String index(Model model) {
        model.addAttribute("PLAID_PUBLIC_KEY", env.getProperty("PLAID_PUBLIC_KEY"));
        model.addAttribute("PLAID_ENV", env.getProperty("PLAID_ENV"));
        return "index";
    }





    /**
     * Exchange link public token for access token.
     */

    @PostMapping(value="/get_access_token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(@RequestParam("public_token") String publicToken) throws Exception {

        String accessToken;

        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("5b51290f4ca9fb0011c5bffe", "846f197e0e89aac5d4e8dcf484c484")
                .publicKey("3b6e5c84bf8feb3dda6cfdd2f9ff72") // optional. only needed to call endpoints that require a public key
                .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
                .build();

        // Synchronously exchange a Link public_token for an API access_token
        // Required request parameters are always Request object constructor arguments
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken)).execute();

        if (response.isSuccessful()) {
            accessToken = response.body().getAccessToken();
            System.out.println(accessToken);
            this.authService.setAccessToken(response.body().getAccessToken());
            this.authService.setItemId(response.body().getItemId());


            Map<String, Object> data = new HashMap<>();
            data.put("error", false);

            return ResponseEntity.ok(data);
        } else {
            System.out.println(response.errorBody().string());
            return ResponseEntity.status(500).body(getErrorResponseData(response.errorBody().string()));
        }
    }





    /**
     * Retrieve high-level account information and account and routing numbers
     * for each account associated with the Item.
     */

    @GetMapping(value="/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getAccount() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<AuthGetResponse> response = this.plaidClient.service()
                .authGet(new AuthGetRequest(this.authService.getAccessToken())).execute();

        if (response.isSuccessful()) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("accounts", response.body().getAccounts());
            data.put("numbers", response.body().getNumbers());

            return ResponseEntity.ok(data);
        } else {
            System.out.println(response.errorBody().string());
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Unable to pull accounts from the Plaid API.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }






    /**
     * Pull the Item - this includes information about available products,
     * billed products, webhook information, and more.
     */
    @PostMapping(value="/item", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getItem() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<ItemGetResponse> itemResponse = this.plaidClient.service()
                .itemGet(new ItemGetRequest(this.authService.getAccessToken()))
                .execute();

        if (!itemResponse.isSuccessful()) {
            System.out.println(itemResponse.errorBody().string());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull item information from the Plaid API."));
        } else {
            ItemStatus item = itemResponse.body().getItem();

            Response<InstitutionsGetByIdResponse> institutionsResponse = this.plaidClient.service()
                    .institutionsGetById(new InstitutionsGetByIdRequest(item.getInstitutionId()))
                    .execute();

            if (!institutionsResponse.isSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(getErrorResponseData("Unable to pull institution information from the Plaid API."));
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("error", false);
                data.put("item", item);
                data.put("institution", institutionsResponse.body().getInstitution());
                return ResponseEntity.ok(data);
            }
        }
    }




    /**
     * Pull transactions for the Item for the last 30 days.
     */
    @PostMapping(value="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(this.authService.getAccessToken(), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
        if (response.isSuccessful()) {
            return ResponseEntity.ok(response.body());
        } else {
            System.out.println(response.errorBody().string());
            ErrorResponse error = this.plaidClient.parseError(response);
            Map<String, Object> data = new HashMap<>();
            data.put("error", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }

    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        data.put("message", message);
        return data;
    }
}
