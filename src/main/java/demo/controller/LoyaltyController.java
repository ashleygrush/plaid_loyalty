package demo.controller;

import demo.model.database.Merchants;
import demo.services.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
        @RequestMapping("/loyalty")
public class LoyaltyController {

    @Autowired
    LoyaltyService loyaltyService;

    // calls DB for all Merchants
    @GetMapping("/merchants")
    public List<Merchants> getMerchants() {

      // searches for database link in services
      return loyaltyService.findAllMerchants();
    }


}
