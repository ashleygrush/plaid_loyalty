package demo.mapper;

import demo.model.database.Merchants;
import demo.model.database.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaidDatabase {

    // list of all Merchants
    String LIST_ALL_MERCHANTS = "Select * from plaid.merchants";

    // list of all customers
    String LIST_ALL_USERS = "Select * from plaid.users";

    // fill hashmap with merchant ID



    // returns list of all Merchants from Merchants table
    @Insert(LIST_ALL_MERCHANTS)
    String listAllMerchants(Merchants merchants);

    @Insert(LIST_ALL_USERS)
    String listAllUsers(Users users);



}
