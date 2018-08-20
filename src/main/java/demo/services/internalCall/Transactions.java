//package demo.services.internalCall;
//
//import com.plaid.client.PlaidClient;
//import com.plaid.client.request.ItemPublicTokenExchangeRequest;
//import com.plaid.client.request.SandboxPublicTokenCreateRequest;
//import com.plaid.client.request.common.Product;
//import com.plaid.client.response.ItemPublicTokenExchangeResponse;
//import com.plaid.client.response.SandboxPublicTokenCreateResponse;
//import demo.model.RequiredData;
//import demo.services.PlaidAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import retrofit2.Response;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Service
//public class Transactions {
//
//    @Autowired
//    RequiredData requiredData;
//
//        Environment env = requiredData.getEnv();
//        PlaidClient plaidClient = requiredData.getPlaidClient();
//        PlaidAuthService authService = requiredData.getAuthService();
//
//
//    public ResponseEntity getAccessInfo() throws IOException {
//        String accessToken;
//
//        Response<SandboxPublicTokenCreateResponse> createResponse = plaidClient.service()
//                .sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest("ins_109511", Arrays.asList(Product.AUTH)))
//                .execute();
//
//
//        // Synchronously exchange a Link public_token for an API access_token
//        // Required request parameters are always Request object constructor arguments
//        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
//                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(createResponse.body().getPublicToken()))
//                .execute();
//
//        if (response.isSuccessful()) {
//            accessToken = response.body().getAccessInfo();
//            System.out.println(accessToken);
//            this.authService.setAccessToken(response.body().getAccessInfo());
//            this.authService.setItemId(response.body().getItemId());
//
//
//            Map<String, Object> data = new HashMap<>();
//            data.put("error", false);
//
//            return ResponseEntity.ok(data);
//        } else {
//            System.out.println(response.errorBody().string());
//            return ResponseEntity.status(500).body(getErrorResponseData(response.errorBody().string()));
//        }
//    }
//
//    private Map<String, Object> getErrorResponseData(String message) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("error", false);
//        data.put("message", message);
//        return data;
//    }
//}
