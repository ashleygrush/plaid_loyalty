package demo.controller;

import demo.model.database.DBSearch;
import demo.model.database.Loyalty;
import demo.services.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loyalty_points")
public class LoyaltyController {

    @Autowired
    LoyaltyService service;

    // calls DB for all loyalty points
    @GetMapping("/all")
    public List<Loyalty> getAllPoints() {
        return service.findAllPoints();
    }

    // calls DB for Users by ID number
    @RequestMapping("/{user_id}")
    public DBSearch findByUserID(@PathVariable("user_id") int user_id) {
        return service.findByUserID(user_id);
    }


}