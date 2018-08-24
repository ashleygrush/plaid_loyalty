package demo.mapper;

import demo.model.database.Deals;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DealsMapper {

    // list of all deals from merchant
    String LIST_ALL_DEALS = "Select id, merchant_id, deal_description, deal_points, deal_instructions from plaid.deals where merchant_id = #{merchant_id} ";

    // find deal by ID
    String FIND_DEAL_BY_ID = "Select * from plaid.deals where " +
            "merchant_id = #{merchant_id} and id = #{id}";

    // create new deal
    String CREATE_DEAL = "Insert into plaid.deals " +
            " (deal_description, deal_points, deal_instructions) " +
            " VALUES (#{deal_description}, #{deal_points}, #{deal_instructions})";

    // delete's existing deal from database
    String DELETE_DEAL= "Delete from plaid.deals where id = #{id}";

    // update existing deal by ID
    String UPDATE_DEAL_BY_ID = "UPDATE plaid.deals "+
            "SET deal_description = #{deal_description}, " +
            "deal_points = #{deal_points} " +
            "WHERE merchant_id = #{merchant_id}" +
            "and id = #{id}";



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
}
