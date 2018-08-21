package demo.controller;

import demo.model.Users;
import demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//calls the User service method to update the db

    @RestController
    @RequestMapping("/loyalty")
    public class UserController {

        @Autowired
        UserService userService;

        // calls DB for all users
        @GetMapping("/update/users")
        public String getAllUsers() {
            // searches for database link in services
            return userService.identifyUserMerchantTransactions();
    }
}
