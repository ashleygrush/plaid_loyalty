package Test;

import demo.services.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class Test {

    // LOYALTY TEST AREA - completed tested code is moved to LoyaltyController after.
    @Autowired
    LoyaltyService service;

    // tests hash map search
    // too many results error
    @RequestMapping("/test/user_id={user_id}/merchant_id={merchant_id}")
    public String HashMapSearch(@PathVariable("merchant_id") int merchant_id,
                                    @PathVariable("user_id") int user_id) {
        return service.searchHashMapForMerchantID(merchant_id, user_id);
    }

    // tests modulus for reward or not
    @RequestMapping("test/update_points/id={id}")
    public String UpdatePoints(@PathVariable("id") int id) {
        return service.updatePoints(id);
    }
}
