package demo.services;

import demo.mapper.UserMapper;
import demo.model.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

//should this be moved to the model package?

@Service
public class PlaidAuthService {

    @Autowired
    UserMapper userMapper;

    public PlaidAccessInfo getAccessToken(HttpSession session) {
        System.out.println("Loading accessToken from session: " + session.getAttribute("accessToken"));
        return new PlaidAccessInfo(
                (String) session.getAttribute("accessToken"),
                (String) session.getAttribute("itemId"));
    }



    public void setAccessToken(HttpSession session, PlaidAccessInfo accessInfo) {
        System.out.println("Storing accessToken in session: " + accessInfo.accessToken);
        Users userobj = new Users();
        userobj.setAccesstoken(accessInfo.accessToken);
        userobj.setId(7);
        userMapper.insertAccesstoken(userobj);

//        session.setAttribute("accessToken", accessInfo.accessToken);
//        session.setAttribute("itemId", accessInfo.itemId);
    }

    public static class PlaidAccessInfo {
        public String accessToken;
        public String itemId;

        public PlaidAccessInfo(String accessToken, String itemId) {
            this.accessToken = accessToken;
            this.itemId = itemId;
        }
    }

}
