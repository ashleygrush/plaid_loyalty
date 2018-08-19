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

    }

////    String bankId = requiredData.getBankId();
//


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
                // NOT IMPLEMENTED YET:
                // you are trying to request transactions on behalf of the user
                // normally, when you are doing something "on behalf of the user", you use the credentials from the
                // session - since the request was initiated by the user, you have these
                // however, this request is NOT initiated by the user in question
                // that means, you need to store users' access tokens (and probably item_id's) in the database
                // i.e. add it to the field as model/Users

                // that also means you won't be able to read transactions for a user that has never logged into Plaid Link,
                // since they won't have an access token recorded

                PlaidAuthService.PlaidAccessInfo plaidAccessInfo = null;
//                plaidAccessInfo = new PlaidAuthService.PlaidAccessInfo(user.getAccessToken(), user.getItemId())

                TransactionsGetResponse transactionList = (TransactionsGetResponse) plaidAPIServiceInternal
                        .getTransactionsLoop(plaidAccessInfo).getBody();

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

