package demo.services;

import demo.mapper.PlaidDatabase;
import demo.model.database.Merchants;
import demo.model.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyService {

    @Autowired
    PlaidDatabase plaidDatabase;


    // GET - all Merchants
    public List <Merchants> findAllMerchants() {
        // import merchants
        return plaidDatabase.listAllMerchants();
   }

   // GET - all Users
    public List<Users> findAllUsers() {
        // import users
        return plaidDatabase.listAllUsers();
    }

    // GET - Users/Merchants by ID
    public Users findUserByID(int id) {

        return plaidDatabase.findUserByID(id);
    }


    // GET - points/Loyalty status: customers (all)

    // GET - merchant deals (all)

    // GET - all user transactions

    // for each transaction; search hashmap (merchant ID)

        // if merchant ID is found - - update if needed
        // check customer ID Loyalty points % for new point value (how many stars)
        // if Loyalty points reaches limit > email user notification
}
