package demo.mapper;

import demo.model.MerchantsForHashMap;
import demo.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper

public interface UpdateUserPointsMapper {

    String UPDATE_USER_POINTS_BY_MERCHANT = "UPDATE plaid.loyalty SET points = (points + 1) WHERE " +
            "merchant_id = {merchant_id} and " +
            "user_id = {user_id}";


    @Update(UPDATE_USER_POINTS_BY_MERCHANT)
    String UpdateUserPointsByMerchant(int merchant_id, int user_id);

}
