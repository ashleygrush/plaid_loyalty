package demo.mapper;

import demo.model.database.Merchants;
import demo.model.database.Users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// maps to PlaidDatabase
@Mapper
public interface PlaidDatabase {

    // list of all Merchants
    String LIST_ALL_MERCHANTS = "Select id, name, email from plaid.merchants";

    // list of all customers
    String LIST_ALL_USERS = "Select id, name, email * from plaid.users";

    // find user by ID
    String FIND_USER_BY_ID = "Select * from plaid.users where id = {#id}";

    // fill hashmap with merchant ID




    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_MERCHANTS)
    List<Merchants> listAllMerchants();


    @Select(LIST_ALL_USERS)
    List<Users> listAllUsers();

    @Select(FIND_USER_BY_ID)
    Users findUserByID(int id);

}
