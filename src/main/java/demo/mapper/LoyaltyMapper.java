package demo.mapper;

import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.websocket.server.ServerEndpoint;
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

    // switch redeem to active/true
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

    // find email by user ID.
    String USER_EMAIL_BY_ID = "Select email from plaid.users WHERE id = #{id}";

    // get points_collected by user_id
    String GET_POINTS_COLLECTED = "Select points_collected from plaid.loyalty where user_id = #{user_id}";

    // get deal_id by user_id
    String GET_DEALS_ID = "Select deal_id from plaid.loyalty where user_id = #{user_id}";

    // get id by user_id
    String GET_POINTS_ID = "Select id from plaid.loyalty where user_id = #{user_id}";

    // get id by user_id AND if active
    String GET_ALL_ACTIVE_REWARDS = "Select id from plaid.loyalty where user_id = #{user_id} and active = true";

    // reset points
    String RESET_POINTS = "Update plaid.loyalty " +
            "SET points_collected = 0 " +
            "WHERE id = #{id}";

    // set merchant by id
    String MERCHANT_ID = "Select merchant_id from plaid.loyalty WHERE id = #{id}";

    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // returns user by ID number
    @Select(FIND_POINTS_BY_USER_ID)
    List<Loyalty> findPointsByUserID(int id);

    @Select(GET_USER_ID_BY_LOYALTY_ID)
    int userIdByLoyaltyID(int id);

    // USED IN LOYALTY SERVICE FOR EMAIL PURPOSES
    @Select(USER_EMAIL_BY_ID)
    String userEmailByID(int id);

    @Update(ACTIVATE_REWARD)
    boolean activateReward(int id);

    @Update(DEACTIVATE_REWARD)
    boolean deactivateAward(int id);

    @Update(ACTIVATE_REDEEMED)
    boolean activateRedeemed(int id);

    @Update(DEACTIVATE_REDEEMED)
    boolean deactivateRedeemed(int id);

    @Select(GET_POINTS_COLLECTED)
    int getPointsCollected(int user_id);

    @Select(GET_DEALS_ID)
    int getDealsID(int user_id);

    @Select(GET_POINTS_ID)
    int getPointsID(int user_id);

    @Select(GET_ALL_ACTIVE_REWARDS)
    int getAllActiveRewards(int user_id);

    @Update(RESET_POINTS)
    int resetPoints(int activePoint);

    @Select(MERCHANT_ID)
    int merchantIDbyLoyaltyID(int id);
}
