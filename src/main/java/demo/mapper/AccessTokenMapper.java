package demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccessTokenMapper {

    String GET_BY_ACCESSTOKEN = "SELECT accesstoken FROM `plaid`.users where id = #{id}";

    @Select(GET_BY_ACCESSTOKEN)
    public String getGetByAccesstoken(int id);

}
