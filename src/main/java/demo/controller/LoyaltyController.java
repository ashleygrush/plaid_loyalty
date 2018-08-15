package demo.controller;

import demo.model.database.Merchants;
import demo.model.database.Users;
import demo.services.LoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;

import java.util.List;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {
//
//    @Autowired
//    LoyaltyService loyaltyService;
//
//    // calls DB for all Merchants
//    @GetMapping("/merchants")
//    public List<Merchants> getMerchants() {
//
//        // searches for database link in services
//        return loyaltyService.findAllMerchants();
//    }
//
//    // calls DB for all users
//    @GetMapping("/users")
//    public List<Users> getAllUsers() {
//        // searches for database link in services
//        return loyaltyService.findAllUsers();
//    }
//
//    // calls DB for Users by ID number
//    @GetMapping("/users/{id}")
//    public Users findByID(@RequestParam("id") int id) {
//        // searches for database link by ID
//        return loyaltyService.findUserByID(id);
//    }
//
//    //creates new user
//   @PostMapping("/users")
//   public Users createUser(@RequestBody Users user) {
//
//        return loyaltyService.createUser(user);
//   }

}