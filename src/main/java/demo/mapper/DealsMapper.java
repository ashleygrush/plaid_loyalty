package demo.mapper;

/**
 * Created by ashleyalmeida
 */

import demo.model.database.Deals;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DealsMapper {

    // list of all deals from merchant
    String LIST_ALL_DEALS = "Select id, merchant_id, deal_description, deal_points_cap, deal_instructions from plaid.deals";

    // find deal by ID
    String FIND_DEAL_BY_ID = "Select * from plaid.deals where merchant_id = #{merchant_id} and id = #{id}";

    // create new deal
    String CREATE_DEAL = "Insert into plaid.deals " +
            " (merchant_id, deal_description, deal_points_cap, deal_instructions) " +
            " VALUES (#{merchant_id}, #{deal_description}, #{deal_points_cap}, #{deal_instructions})";

    // delete's existing deal from database
    String DELETE_DEAL= "Delete from plaid.deals where merchant_id = #{merchant_id} and id = #{id}";

    // update existing deal by merchant_id and ID.
    String UPDATE_DEAL_BY_ID = "UPDATE plaid.deals "+
            "SET deal_description = #{deal_description}, " +
            "deal_points_cap = #{deal_points_cap}, " +
            "deal_instructions = #{deal_instructions} " +
            "WHERE merchant_id = #{merchant_id} " +
            "AND id = #{id}";

    // find all deals by merchant ID
    String FIND_ALL_DEALS_BY_MERCHANT_ID = "Select * from plaid.deals where merchant_id = #{merchant_id}";

    // gets points cap by ID
    String GET_POINTS_CAP_BY_ID = "Select deal_points_cap from plaid.deals Where id = #{id}";

    // gets deal_instructions by ID
    String GET_DEAL_INSTRUCTIONS_BY_ID = "Select deal_instructions from plaid.deals Where id = #{id}";

    // returns list of all Deals
    @Select(LIST_ALL_DEALS)
    List<Deals> listAllDeals();

    // finds deal by ID number
    @Select(FIND_DEAL_BY_ID)
    Deals findDealByID(@Param("merchant_id") int merchant_id,
                       @Param("id") int id);

    // creates new deal
    @Insert(CREATE_DEAL)
    void createDeal(Deals newDeal);

    // delete existing deal
    @Delete(DELETE_DEAL)
    int deleteDealByID(@Param("merchant_id") int merchant_id,
                       @Param("id") int id);

    // update existing deal by ID
    @Insert(UPDATE_DEAL_BY_ID)
    int updateDealByID(Deals deals);

    //
    @Select(FIND_ALL_DEALS_BY_MERCHANT_ID)
    List<Deals> listAllDealsByMerchant(int merchant_id);

    @Select(GET_POINTS_CAP_BY_ID)
    int pointsCap(int id);

    @Select(GET_DEAL_INSTRUCTIONS_BY_ID)
    String getDealInstructions(int id);
}
