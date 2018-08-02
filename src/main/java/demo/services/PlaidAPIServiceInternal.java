package demo.services;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.*;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.*;
import demo.service.PlaidAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;

/**
 * Created by ryandesmond on 8/2/18.
 */

@Service
public class PlaidAPIServiceInternal {

    private final Environment env;
    private PlaidClient plaidClient;
    private final PlaidAuthService authService;

    @Autowired
    public PlaidAPIServiceInternal(Environment env, PlaidAuthService authService) {
        this.env = env;
        this.plaidClient = plaidClient;
        this.authService = authService;

        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("5b51290f4ca9fb0011c5bffe", "846f197e0e89aac5d4e8dcf484c484")
                .publicKey("3b6e5c84bf8feb3dda6cfdd2f9ff72") // optional. only needed to call endpoints that require a public key
                .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
                .build();

        // this probably needs regular refreshing
        try {
            getAccessToken();
        } catch (IOException e) {
            System.out.println("GET ACCESS TOKEN FAILED - API");
            e.printStackTrace();
        }
    }

    public ResponseEntity getAccessToken() throws IOException {
        String accessToken;

        plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret("5b51290f4ca9fb0011c5bffe", "846f197e0e89aac5d4e8dcf484c484")
                .publicKey("3b6e5c84bf8feb3dda6cfdd2f9ff72") // optional. only needed to call endpoints that require a public key
                .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
                .build();

        Response<SandboxPublicTokenCreateResponse> createResponse = plaidClient.service()
                .sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest("ins_109511", Arrays.asList(Product.AUTH)))
                .execute();


        // Synchronously exchange a Link public_token for an API access_token
        // Required request parameters are always Request object constructor arguments
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(createResponse.body().getPublicToken()))
                .execute();

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

    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        data.put("message", message);
        return data;
    }

    public ResponseEntity getAccounts() throws Exception {
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

    public ResponseEntity getItem() throws Exception {
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

    public ResponseEntity getTransactions() throws Exception {
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
