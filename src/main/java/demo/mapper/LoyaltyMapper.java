package demo.mapper;

import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
// links to Plaid - Loyalty lookup table
public interface LoyaltyMapper {

    // list of all points
    String LIST_ALL_POINTS = "Select id, merchant_id, user_id, deal_id, redeemed, points from plaid.loyalty";

    // find user by ID
    String FIND_POINTS_BY_USER_ID = "Select * from plaid.loyalty where user_id = #{user_id}";

    // find all deal ID's by Merchant ID
    String FIND_ALL_DEALS_BY_MERCHANT_ID = "Select id from plaid.loyalty where merchant_id = #{merchant_id}";

    //update points in DB per transaction
    String UPDATE_POINTS_BY_ID = "Update plaid.loyalty SET points = #{points} WHERE id = #{id}";

    // list points count
    String POINTS_COUNT = "Select points from plaid.loyalty WHERE id = #{id}";

    // switch redeem to active/true
    String ACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{true}" +
            "WHERE id = #{id}";

    // deactivate redeem - switch to false
    String DEACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{false}" +
            "WHERE id = #{id}";



    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // returns user by ID number
    @Select(FIND_POINTS_BY_USER_ID)
    Loyalty findPointsByUserID(int user_id);

    // returns all deals by Merchant ID
    @Select(FIND_ALL_DEALS_BY_MERCHANT_ID)
    int findAllDealsByMerchantID(int merchant_id);

    //update points by ID number
    @Update(UPDATE_POINTS_BY_ID)
    int updatePoints(int points, int id);

    @Select(POINTS_COUNT)
    int loyaltyCount(int id);

    @Update(ACTIVATE_REDEEMED)
    int activateRedeemed(int id);

    @Update(DEACTIVATE_REDEEMED)
    int deactivateRedeemed(int id);
}
