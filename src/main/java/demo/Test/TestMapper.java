package demo.Test;

import demo.model.database.Loyalty;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TestMapper {



    //update points in DB per transaction
    String UPDATE_POINTS_BY_ID = "Update plaid.loyalty SET points = #{points} WHERE id = #{id}";



    //update points by ID number
    @Update(UPDATE_POINTS_BY_ID)
    int updatePoints(Loyalty loyalty);
}
