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

@Service
public class AccessToken {


    private final Environment env;
    private PlaidClient plaidClient;
    private final PlaidAuthService authService;


    @Autowired
    public AccessToken(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.authService = authService;
        this.plaidClient = plaidClient;

    }

    public PlaidAuthService.PlaidAccessInfo getAccessInfo(String publicToken) throws IOException {
        String accessToken;
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
    public Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false); // Why is this false?
        data.put("message", message);
        return data;
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

