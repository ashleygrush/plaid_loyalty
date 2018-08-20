package demo.mapper;

import demo.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// maps to Plaid database - Users
@Mapper
public interface UserMapper {

    // list of all customers
    String LIST_ALL_USERS = "Select id, name, email from plaid.users";

    String INSERT_ACCESSTOKEN = "UPDATE plaid.users set accesstoken = #{accesstoken} where id = #{id}";

    // returns list of all Users from Users table
    @Select(LIST_ALL_USERS)
    List<Users> listAllUsers();

    @Update(INSERT_ACCESSTOKEN)
    public int insertAccesstoken (Users user);}
