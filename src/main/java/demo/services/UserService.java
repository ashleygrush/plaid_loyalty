package demo.services;

import com.plaid.client.PlaidClient;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.common.Product;
import demo.mapper.MerchantMapper;
import demo.model.Merchants;
import org.springframework.core.env.Environment;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.SandboxPublicTokenCreateResponse;
import com.plaid.client.response.TransactionsGetResponse;
import demo.mapper.UserMapper;
import demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    MerchantService merchantService;

    @Autowired
    PlaidAPIServiceInternal plaidAPIServiceInternal;


//    @Autowired
//    RequiredData requiredData;


    private final Environment env;
    private PlaidClient plaidClient;        //this includes the configued info (secret, id and key)
    private final PlaidAuthService authService;

    @Autowired
    public UserService(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.authService = authService;
        this.plaidClient = plaidClient;


        // each access tocken lasts 30 mins. the "getAccessToken" methods recieves a unique time when called
        try {
            getAccessToken();
        } catch (IOException e) {
            System.out.println("GET ACCESS TOKEN FAILED - API");
            e.printStackTrace();
        }
    }

////    String bankId = requiredData.getBankId();
//

    public ResponseEntity getAccessToken() throws IOException {

        //create public tocken
        Response<SandboxPublicTokenCreateResponse> createResponse = plaidClient.service()
                .sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest("ins_109511", Arrays.asList(Product.AUTH)))
                .execute();

        // Synchronously exchange a Link public_token for an API access_token
        // Required request parameters are always Request object constructor arguments
        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(createResponse.body().getPublicToken()))
                .execute();
        //if the reponse is successful, print access token and set the access token and id in the authService method
        if (response.isSuccessful()) {
            String accessToken = response.body().getAccessToken();
            System.out.println(accessToken);
            this.authService.setAccessToken(response.body().getAccessToken());
            this.authService.setItemId(response.body().getItemId());

            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            return ResponseEntity.ok(data);
            //if the response fails then return an error message
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





    // GET all Users- call plaid API, iterate against hashmap, update database if required
    public String identifyUserMerchantTransactions() {
        List<Users> userList = mapper.listAllUsers();
        ArrayList<String> arrayListMerchants = new ArrayList<>();
        for (Users user : userList) {
            try {
                TransactionsGetResponse transactionList =
                        (TransactionsGetResponse) plaidAPIServiceInternal.getTransactionsLoop().getBody();

                for (TransactionsGetResponse.Transaction t : transactionList.getTransactions()) {
                    if (t != null) {
                        arrayListMerchants = new ArrayList<>();
                        arrayListMerchants.add(t.getName());
                        arrayListMerchants.get(0);
                        System.out.println(arrayListMerchants);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //now need to itterate through the resulting array list for merchants within the hashmap
            for (String transactionElement : arrayListMerchants) {
                HashMap<String, Integer> merchantsHM = merchantService.merchantsList();
                if(merchantsHM.containsKey(transactionElement) == true){
                    int databaseId = merchantsHM.get(transactionElement);
                    Merchants merchants = new Merchants();
                    merchants.setId(databaseId);
                    merchantMapper.updateMerchantByID(merchants);
                }

            }
        }
        return "Success";
    }
}

