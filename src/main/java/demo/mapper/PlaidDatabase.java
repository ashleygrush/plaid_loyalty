package demo.mapper;

import demo.model.database.Merchants;
import demo.model.database.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// maps to PlaidDatabase
@Mapper
public interface PlaidDatabase {

    // list of all Merchants
    String LIST_ALL_MERCHANTS = "Select id, name, email from plaid.merchants";

    // list of all customers
    String LIST_ALL_USERS = "Select id, name, email from plaid.users";

    // find user by ID
    String FIND_USER_BY_ID = "Select * from plaid.users where id = #{id}";

    // creates new User (name, email, password)
    String CREATE_USER = "Insert into plaid.users " +
            "(name, password, email) " +
            "VALUES (#{name}, #{password}, #{email})";

    // delete's existing user from database
    String DELETE_USER = "Delete from plaid.users where id = #{id}";


    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_MERCHANTS)
    List<Merchants> listAllMerchants();

    // returns list of all Users from Users table
    @Select(LIST_ALL_USERS)
    List<Users> listAllUsers();

    // finds user by ID number
    @Select(FIND_USER_BY_ID)
    Users findUserByID(int id);

    // creates new user
    @Insert(CREATE_USER)
    int createUser(Users user);

    // delete existing user
    @Delete(DELETE_USER)
    int deleteUserByID(int id);
}
