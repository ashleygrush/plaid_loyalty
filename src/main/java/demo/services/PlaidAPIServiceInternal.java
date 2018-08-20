package demo.services;

import com.plaid.client.request.*;
import com.plaid.client.response.*;
import com.plaid.client.PlaidClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;



//THIS IS FOR THE PLAID CALL- TESTING PURPOSES ONLY. NOT REQUIRED FOR THE APPLICATION TO RUN



@Service
public class PlaidAPIServiceInternal {

    private final Environment env;
    private PlaidClient plaidClient;
    private final PlaidAuthService authService;

    @Autowired
    public PlaidAPIServiceInternal(Environment env, PlaidClient plaidClient,PlaidAuthService authService) {
        this.env = env;
        this.authService = authService;
        this.plaidClient = plaidClient;


//        // this probably needs regular refreshing
        // NOPE IT DOES NOT - "By default, an access_token never expires", from Plaid docs

//        try {
//            getAccessInfo(publicToken);
//        } catch (IOException e) {
//            System.out.println("GET ACCESS TOKEN FAILED - API");
//            e.printStackTrace();
//        }
    }


    public PlaidAuthService.PlaidAccessInfo getAccessInfo(String publicToken) throws IOException {
        String accessToken;

        // For sandbox - create a test public token
//        Response<SandboxPublicTokenCreateResponse> createResponse = plaidClient.service()
//                .sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest("ins_109511", Arrays.asList(Product.AUTH)))
//                .execute();

        System.out.println("Obtaining access token from public token: " + publicToken);

        // Synchronously exchange a Link public_token for an API access_token
        // Required request parameters are always Request object constructor arguments
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken))
                .execute();

        if (response.isSuccessful()) {
            accessToken = response.body().getAccessToken();
            System.out.println(accessToken);
            return new PlaidAuthService.PlaidAccessInfo(response.body().getAccessToken(), response.body().getItemId());

        } else {
            System.out.println(response.errorBody().string());
            // It's easier to throw an error in your internal code and let the web framework convert it into a
            // HTTP response.
            throw new RuntimeException(response.errorBody().string());
        }
    }

    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false); // Why is this false?
        data.put("message", message);
        return data;
    }

    public ResponseEntity getAccounts(PlaidAuthService.PlaidAccessInfo accessInfo) throws Exception {
        if (accessInfo.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<AccountsGetResponse> response = this.plaidClient.service()
                .accountsGet(new AccountsGetRequest(accessInfo.accessToken)).execute();

        if (response.isSuccessful()) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("accounts", response.body().getAccounts());
            data.put("numbers", response.body().getItem());

            return ResponseEntity.ok(data);
        } else {
            System.out.println(response.errorBody().string());
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Unable to pull accounts from the Plaid API.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }



    public ResponseEntity getItem(PlaidAuthService.PlaidAccessInfo accessInfo) throws Exception {
        if (accessInfo.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        //calls the ItemGetRequest which takes in an access token and returns a response of same type
        Response<ItemGetResponse> itemResponse = this.plaidClient.service()
                .itemGet(new ItemGetRequest(accessInfo.accessToken))
                .execute();

        //if the reponse doesnt work then print out an error message
        //otherwise if all good then set object item to a body of the response class which is a URL
        if (!itemResponse.isSuccessful()) {
            System.out.println(itemResponse.errorBody().string());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull item information from the Plaid API."));
        } else {
            ItemStatus item = itemResponse.body().getItem();

            Response<InstitutionsGetByIdResponse> institutionsResponse = this.plaidClient.service()
                    .institutionsGetById(new InstitutionsGetByIdRequest(item.getInstitutionId()))
                    .execute();

                Map<String, Object> data = new HashMap<>();
                data.put("error", false);
                data.put("item", item);
                data.put("institution", institutionsResponse.body().getInstitution());
                return ResponseEntity.ok(data);
            }
        }



    @GetMapping(value = "/accounts")
    public @ResponseBody
    ResponseEntity getAccount(PlaidAuthService.PlaidAccessInfo accessInfo) throws Exception {
        if (accessInfo.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<AccountsGetResponse> response = this.plaidClient.service()
                .accountsGet(new AccountsGetRequest(accessInfo.accessToken)).execute();

        if (response.isSuccessful()) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("accounts", response.body().getAccounts());
            data.put("numbers", response.body().getItem());

            return ResponseEntity.ok(data);
        } else {
            System.out.println(response.errorBody().string());
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Unable to pull accounts from the Plaid API.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }

    public ResponseEntity getTransactionsLoop(PlaidAuthService.PlaidAccessInfo accessInfo) throws Exception{
        ResponseEntity response = null;
        boolean success = false;
        int count = 0;
        do {
            System.out.println("transaction attempt: " + count);
            try {
                response = getTransactions(accessInfo);
                success = true;
            } catch (Exception e){
//                System.out.println("Attempt1");
                System.out.println(e.getMessage());
                success = false;
            }
            count++;
            Thread.sleep(1000);
        }while ( !success && count < 5);

        return response;
    }
    public ResponseEntity getTransactions(PlaidAuthService.PlaidAccessInfo accessInfo) throws Exception {
        if (accessInfo.accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        //setting the timescale
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();


        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(accessInfo.accessToken, startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
        if (response.isSuccessful()) {
            return ResponseEntity.ok(response.body());
        } else {

            System.out.println(response.errorBody().string());
//            System.out.println("ignore");
            ErrorResponse error = this.plaidClient.parseError(response);
            Map<String, Object> data = new HashMap<>();
            data.put("error", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }


    public Environment getEnv() {
        return env;
    }

    public PlaidClient getPlaidClient() {
        return plaidClient;
    }

    public void setPlaidClient(PlaidClient plaidClient) {
        this.plaidClient = plaidClient;
    }
    public PlaidAuthService getAuthService() {
        return authService;
    }
}

