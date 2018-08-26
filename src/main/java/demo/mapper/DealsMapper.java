package demo.mapper;

import demo.model.database.Deals;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DealsMapper {

    // list of all deals from merchant
    String LIST_ALL_DEALS = "Select id, merchant_id, deal_description, deal_points, deal_instructions from plaid.deals";

    // find deal by ID
    String FIND_DEAL_BY_ID = "Select * from plaid.deals where id = #{id}";

    // create new deal
    String CREATE_DEAL = "Insert into plaid.deals " +
            " (merchant_id, deal_description, deal_points, deal_instructions) " +
            " VALUES (#{merchant_id}, #{deal_description}, #{deal_points}, #{deal_instructions})";

    // delete's existing deal from database
    String DELETE_DEAL= "Delete from plaid.deals where id = #{id}";

    // update existing deal by merchant_id and ID.
    String UPDATE_DEAL_BY_ID = "UPDATE plaid.deals "+
            "SET deal_description = #{deal_description}, " +
            "deal_points = #{deal_points}, " +
            "deal_instructions = #{deal_instructions} " +
            "WHERE merchant_id = #{merchant_id} " +
            "AND id = #{id}";

    // find all deals by merchant ID
    String FIND_ALL_DEALS_BY_MERCHANT_ID = "Select * from plaid.deals where merchant_id = #{merchant_id}";


    // returns list of all Deals
    @Select(LIST_ALL_DEALS)
    List<Deals> listAllDeals();

    // finds deal by ID number
    @Select(FIND_DEAL_BY_ID)
    Deals findDealByID(int id);

    // creates new deal
    @Insert(CREATE_DEAL)
    void createDeal(Deals newDeal);

    // delete existing deal
    @Delete(DELETE_DEAL)
    int deleteDealByID(int id);

    // update existing deal by ID
    @Insert(UPDATE_DEAL_BY_ID)
    int updateDealByID(Deals deals);

    @Select(FIND_ALL_DEALS_BY_MERCHANT_ID)
    List<Deals> listAllDealsByMerchant(int merchant_id);
}
