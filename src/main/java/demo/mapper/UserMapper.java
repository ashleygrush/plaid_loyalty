package demo.mapper;

import demo.model.database.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// maps to Plaid database - Users
@Mapper
public interface UserMapper {

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

    // update existing user by ID
    String UPDATE_USER_BY_ID = "UPDATE plaid.users "+
            "SET name = #{name}, " +
            "password = #{password}, " +
            "email = #{email} " +
            "WHERE id = #{id}";


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

    // update existing user by ID
    @Insert(UPDATE_USER_BY_ID)
    int updateUserByID(Users users);
}
