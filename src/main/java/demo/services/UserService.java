package demo.services;

import com.plaid.client.PlaidClient;
import demo.mapper.AccessTokenMapper;
import demo.mapper.MerchantMapper;
import demo.mapper.UpdateUserPointsMapper;
import org.springframework.core.env.Environment;
import com.plaid.client.response.TransactionsGetResponse;
import demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import demo.model.database.Users;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    MerchantService merchantService;

    @Autowired
    PlaidCallService plaidCallService;

    @Autowired
    AccessTokenMapper accessTokenMapper;

    @Autowired
    UpdateUserPointsMapper updateUserPointsMapper;


    private final Environment env;
    private PlaidClient plaidClient;  //this includes the configured info (secret, id and key)
    private final PlaidAuthService authService;

    @Autowired
    public UserService(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.authService = authService;
        this.plaidClient = plaidClient;

    }

    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        data.put("message", message);
        return data;
    }


    // GET all Users- call plaid API, iterate against hashmap, update database if required
    public String analyseAllUserTransaction() {
        List<Users> userList = mapper.listAllUsers();
        ArrayList<String> arrayListMerchants = new ArrayList<>();
        for (Users user : userList) {
            try {
//                PlaidAuthService.PlaidAccessInfo plaidAccessInfo = null;
//                plaidAccessInfo = new PlaidAuthService.PlaidAccessInfo(user.getAccessToken(), user.getItemId())
                int userID = user.getId();
                TransactionsGetResponse transactionList = (TransactionsGetResponse) plaidCallService.
                        getTransactionsLoop(userID).getBody();


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

            //now need to iterate through the resulting array list for merchants within the hashmap
            for (String transactionElement : arrayListMerchants) {
                HashMap<String, Integer> merchantsHM = merchantService.merchantsList();

                if(merchantsHM.containsKey(transactionElement) == true){
                    //update db for user loyalty for a specific merchant

                    //get the database id for the merchant
                    int merchantDatabaseId = merchantsHM.get(transactionElement);

                    // update the loyalty db for an additional point (on the basis the timestamp is not the same
                    int userID = user.getId();

                    //call the update loyalty point program method in mapper for DB passing the merchant and the user
                    updateUserPointsMapper.UpdateUserPointsByMerchant(merchantDatabaseId, userID);
                }
            }
        }
        return "Success";
    }


    //USER CRUD

    // GET - all Users
    public List<Users> findAllUsers() {
        return mapper.listAllUsers();
    }


    // GET - find user by ID
    public List<Users> findUserByID(int id) {
        return mapper.findUserByID(id);
    }


    // POST - create new user
    public Users createUser(Users data) {

        Users newUser = new Users();

        newUser.setName(data.getName());
        newUser.setPassword(data.getPassword());
        newUser.setEmail(data.getEmail());

        try {
            mapper.createUser(newUser);
        } catch (Exception e) {
            System.out.println("User already exists. Please log in : " + data.getEmail());
        }
        return newUser;
    }

    // DELETE - delete existing user by ID
    public String deleteUserByID(int id) {
        mapper.deleteUserByID(id);
        return "User successfully removed with ID : " + id + ".";
    }

    // PUT - update existing user by ID
    public Users updateUserByID(int id, Users data) {

        Users updateUser = new Users();

        updateUser.setName(data.getName());
        updateUser.setPassword(data.getPassword());
        updateUser.setEmail(data.getEmail());
        updateUser.setId(id);

        mapper.updateUserByID(updateUser);

        return updateUser;
    }

    // gets user email by ID
    public String userEmail(int id) {
        return mapper.userEmailByID(id);
    }
}
