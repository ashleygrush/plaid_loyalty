package demo.mapper;

import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
// links to Plaid - Loyalty lookup table
public interface LoyaltyMapper {

    // list of all points
    String LIST_ALL_POINTS = "Select id, merchant_id, user_id, deal_id, active from plaid.loyalty";

    // find user by ID
    String FIND_POINTS_BY_ID = "Select * from plaid.loyalty where user_id = #{user_id}";


    // returns list of all Merchants from Merchants table
    @Select(LIST_ALL_POINTS)
    List<Loyalty> listAllPoints();

    // finds user by ID number
    @Select(FIND_POINTS_BY_ID)
    Loyalty findPointsByID(int id);

}
