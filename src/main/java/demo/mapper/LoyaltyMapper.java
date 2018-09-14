package demo.mapper;

import demo.model.PointsHashMap;
import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.ArrayList;
import java.util.List;

@Mapper
// links to Plaid - Loyalty lookup table
public interface LoyaltyMapper {

    // list of all points
    String LIST_ALL_POINTS = "Select * from plaid.loyalty";

    // find user by ID
    String FIND_POINTS_BY_USER_ID = "Select * from plaid.loyalty where user_id = #{user_id}";


    // Get user ID by Loyalty ID
    String GET_USER_ID_BY_LOYALTY_ID = "Select user_id from plaid.loyalty WHERE id = #{id}";

    // switch redeem to active/true by ID
    String ACTIVATE_REWARD = "Update plaid.loyalty " +
            "SET active = #{true} " +
            "WHERE id = #{id}";

    // deactivate redeem - switch to false
    String DEACTIVATE_REWARD  = "Update plaid.loyalty " +
            "SET activate = #{false}" +
            "WHERE id = #{id}";

    // switch redeem to active/true
    String ACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{true} " +
            "WHERE id = #{id}";

    // deactivate redeem - switch to false
    String DEACTIVATE_REDEEMED = "Update plaid.loyalty " +
            "SET redeemed = #{false}" +
            "WHERE id = #{id}";

    // get points_collected by user_id
    String GET_POINTS_COLLECTED = "Select id, points_collected from plaid.loyalty where user_id = #{user_id}";

    // get deal_id by user_id - for Reward Comparison
    String GET_DEALS_ID_BY_USER = "Select deal_id from plaid.loyalty where user_id = #{user_id}";

    // get deal_id by id
    String GET_DEALS_ID = "Select deal_id from plaid.loyalty where id = #{id}";

    // get id by user_id
    String GET_POINTS_ID = "Select id from plaid.loyalty where user_id = #{user_id}";

    // get id by user_id AND if active
    String GET_ALL_ACTIVE_REWARDS = "Select id from plaid.loyalty where user_id = #{user_id} and active = true";

    // reset points - only used when point is activated first time.
    String RESET_POINTS = "Update plaid.loyalty " +
            "SET points_collected = 0 " +
            "WHERE id = #{id}";

    // set merchant by id
    String MERCHANT_ID_BY_LOYALTY_ID = "Select merchant_id from plaid.loyalty WHERE id = #{id}";

    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // returns user by ID number
    @Select(FIND_POINTS_BY_USER_ID)
    List<Loyalty> findPointsByUserID(int id);

    // returns user id by Loyalty ID - used for email purposes
    @Select(GET_USER_ID_BY_LOYALTY_ID)
    int userIdByLoyaltyID(int id);

    // activate's reward status
    @Update(ACTIVATE_REWARD)
    boolean activateReward(int id);

    // deactivate's reward status
    @Update(DEACTIVATE_REWARD)
    boolean deactivateAward(int id);

    // activate's reward redeemed status
    @Update(ACTIVATE_REDEEMED)
    boolean activateRedeemed(int id);

    // activate's reward redeemed status
    @Update(DEACTIVATE_REDEEMED)
    boolean deactivateRedeemed(int id);

    // gets points_collected by user ID
    @Select(GET_POINTS_COLLECTED)
    ArrayList<PointsHashMap> getPointsCollected(int user_id);

    // gets Deal ID by user ID
    @Select(GET_DEALS_ID_BY_USER)
    int getDealsIDByUser(int user_id);

    // gets Deal ID by Loyalty ID
    @Select(GET_DEALS_ID)
    int getDealsID(int id);

    // gets Loyalty_ID by user ID
    @Select(GET_POINTS_ID)
    List<Integer> getPointsID(int user_id);

    // returns all active rewards by user ID
    @Select(GET_ALL_ACTIVE_REWARDS)
    int getAllActiveRewards(@Param("user_id") int user_id);

    // Resets all collected_points to 0 if Point is Active
    // this is separated due to only being used at time of activation
    @Update(RESET_POINTS)
    int resetPoints(int activePoint);

    // returns merchant ID by Loyalty ID - used for email purposes
    @Select(MERCHANT_ID_BY_LOYALTY_ID)
    int merchantIDbyLoyaltyID(int id);
}
