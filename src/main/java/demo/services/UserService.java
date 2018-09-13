package demo.services;

import com.plaid.client.PlaidClient;
import demo.exceptions.DatabaseException;
import demo.mapper.AccessTokenMapper;
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


    // GET all Users- call plaid API, iterate against hash map, update database if required
    public String analyseAllUserTransaction() {

        // fills list with all users
        List<Users> userList = mapper.listAllUsers();
        ArrayList<String> arrayListMerchants = new ArrayList<>();

        for (Users user : userList) {
            try {
//                PlaidAuthService.PlaidAccessInfo plaidAccessInfo = null;
//                plaidAccessInfo = new PlaidAuthService.PlaidAccessInfo(user.getAccessToken(), user.getItemId())

                // get the id of each user in the list
                int userID = user.getId();

                // gets all transactions for each user ID
                TransactionsGetResponse transactionList = (TransactionsGetResponse) plaidCallService.
                        getTransactionsLoop(userID).getBody();

                // checks for new transactions: if new; add merchant name + ID??
                for (TransactionsGetResponse.Transaction t : transactionList.getTransactions()) {
                    if (t != null) {

                        arrayListMerchants = new ArrayList<>();
                        arrayListMerchants.add(t.getName());  // transaction's merchant name
                        arrayListMerchants.get(0);
                        System.out.println(arrayListMerchants);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // now need to iterate through the resulting array list for merchants within the hash map
            for (String transactionElement : arrayListMerchants) {
                HashMap<String, Integer> merchantsHM = merchantService.merchantsList();

                // arrayList of Merchants contains Name/ID in transaction:
                if (merchantsHM.containsKey(transactionElement) == true) {

                    //update db for user loyalty for a specific merchant

                    //get merchant ID from hash map
                    int merchant_id = merchantsHM.get(transactionElement);

                    // get user ID from hash map
                    int user_id = user.getId();

                    //call the update loyalty point program method in mapper for DB passing the merchant and the user
                    updateUserPointsMapper.UpdateUserPointsByMerchant(merchant_id, user_id);
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
    public Users findUserByID(int id) throws Exception {

        Users user;

        try {
            user = mapper.findUserByID(id);

        } catch (Exception e) {
            throw e;
        }
        return user;
    }


    // POST - create new user
    public Users createUser(Users data) throws Exception {
        try {
            Users newUser = new Users();

            newUser.setName(data.getName());
            newUser.setPassword(data.getPassword());
            newUser.setEmail(data.getEmail());

            mapper.createUser(newUser);
            return newUser;

        } catch (Exception e) {
            throw e;
        }
    }

    // DELETE - delete existing user by ID
    public boolean deleteUserByID(int id) throws Exception {
        try {
            mapper.deleteUserByID(id);
            return true;
        } catch (Exception e) {
            throw e;
        }

    }

    // PUT - update existing user by ID
    public Users updateUserByID(int id, Users data) throws Exception, DatabaseException {

        Users updateUser = new Users();

        updateUser.setName(data.getName());
        updateUser.setPassword(data.getPassword());
        updateUser.setEmail(data.getEmail());
        updateUser.setId(id);
        try {
            mapper.updateUserByID(updateUser);

            if (id < 1) {
                throw new DatabaseException("Unable to update user with ID : " + id);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return updateUser;
    }


    // gets user email by ID
    public String userEmail(int id) {
        return mapper.userEmailByID(id);
    }
}
