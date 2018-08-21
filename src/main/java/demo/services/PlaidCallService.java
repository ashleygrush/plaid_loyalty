package demo.services;

import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.ItemGetRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.*;
import demo.mapper.AccessTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PlaidCallService {

@Autowired
AccessToken accessToken;

@Autowired
AccessTokenMapper accessTokenMapper;


    public ResponseEntity getAccounts(int id) throws Exception {
//        if (accessInfo.accessToken == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(accessToken.getErrorResponseData("Not authorized"));
//        }

        Response<AccountsGetResponse> response = accessToken.getPlaidClient().service()
                .accountsGet(new AccountsGetRequest(accessTokenMapper.getGetByAccesstoken(id))).execute();

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
                    .body(accessToken.getErrorResponseData("Not authorized"));
        }

        //calls the ItemGetRequest which takes in an access token and returns a response of same type
        Response<ItemGetResponse> itemResponse = this.accessToken.getPlaidClient().service()
                .itemGet(new ItemGetRequest(accessInfo.accessToken))
                .execute();

        //if the reponse doesnt work then print out an error message
        //otherwise if all good then set object item to a body of the response class which is a URL
        if (!itemResponse.isSuccessful()) {
            System.out.println(itemResponse.errorBody().string());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(accessToken.getErrorResponseData("Unable to pull item information from the Plaid API."));
        } else {
            ItemStatus item = itemResponse.body().getItem();

            Response<InstitutionsGetByIdResponse> institutionsResponse = this.accessToken.getPlaidClient().service()
                    .institutionsGetById(new InstitutionsGetByIdRequest(item.getInstitutionId()))
                    .execute();

            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("item", item);
            data.put("institution", institutionsResponse.body().getInstitution());
            return ResponseEntity.ok(data);
        }
    }


    public ResponseEntity getTransactionsLoop(int id) throws Exception{
        ResponseEntity response = null;
        boolean success = false;
        int count = 0;
        do {
            System.out.println("transaction attempt: " + count);
            try {
                response = getTransactions(id);
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



    public ResponseEntity getTransactions(int id) throws Exception {
//        if (accessInfo.accessToken == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(accessToken.getErrorResponseData("Not authorized"));
//        }

        //setting the timescale
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        Response<TransactionsGetResponse> response = this.accessToken.getPlaidClient().service()
                .transactionsGet(new TransactionsGetRequest(accessTokenMapper.getGetByAccesstoken(id), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
        if (response.isSuccessful()) {
            return ResponseEntity.ok(response.body());
        } else {

            System.out.println(response.errorBody().string());
            ErrorResponse error = this.accessToken.getPlaidClient().parseError(response);
            Map<String, Object> data = new HashMap<>();
            data.put("error", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }
}
