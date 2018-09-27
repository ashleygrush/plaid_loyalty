package demo.mapper;

/**
 * Created by ashleyalmeida
 */

import demo.model.MerchantsForHashMap;
import demo.model.database.Merchants;
import demo.model.database.Users;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
// maps to Plaid Database - Merchant table
public interface MerchantMapper {

    // list of all Merchants
    String LIST_ALL_MERCHANTS = "Select id, name, email from plaid.merchants";

    // find user by ID
    String FIND_MERCHANT_BY_ID = "Select id, name, email from plaid.merchants where id = #{id}";

    // creates new User (name, email, password)
    String CREATE_MERCHANT = "Insert into plaid.merchants " +
            "(name, password, email) " +
            "VALUES (#{name}, #{password}, #{email})";

    // delete's existing user from database
    String DELETE_MERCHANT = "Delete from plaid.merchants where id = #{id}";

    // update existing user by ID
    String UPDATE_MERCHANT_BY_ID = "UPDATE plaid.merchants " +
            "SET name = #{name}, " +
            "password = #{password}, " +
            "email = #{email} " +
            "WHERE id = #{id}";

    // merchant name by ID
    String MERCHANT_NAME_BY_ID = "Select name from plaid.merchants where id = #{id}";


    // returns list of all MerchantsForHashMap from MerchantsForHashMap table
    @Select(LIST_ALL_MERCHANTS)
    List<MerchantsForHashMap> listAllMerchantsForHM();

    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_MERCHANTS)
    List<Merchants> listAllMerchants();

    // finds user by ID number
    @Select(FIND_MERCHANT_BY_ID)
    Merchants findMerchantByID(int id);

    // creates new user
    @Insert(CREATE_MERCHANT)
    int createMerchant(Merchants merchants);

    // delete existing user
    @Delete(DELETE_MERCHANT)
    int deleteMerchantByID(int id);

    // update existing user by ID
    @Insert(UPDATE_MERCHANT_BY_ID)
    int updateMerchantByID(Merchants merchants);

    // gets merchant name by ID - primary use for email/points update
    @Select(MERCHANT_NAME_BY_ID)
    String getMerchantNameById(int id);
}
