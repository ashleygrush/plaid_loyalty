package demo.controller;

import demo.model.database.DBSearch;
import demo.model.database.Users;
import demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//calls the User service method to update the db

    @RestController
    @RequestMapping("/users")
    public class UserController {

        @Autowired
        UserService userService;

        // calls DB for all users
        @GetMapping("/update/users")
        public String updateAllUsers() {
            // searches for database link in services
            return userService.analyseAllUserTransaction();
    }


    // calls DB for all users
    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return userService.findAllUsers();
    }

    // calls DB for Users by ID number
    @RequestMapping("/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        return userService.findUserByID(id);
    }

    // creates new user
    @PostMapping()
    public Users createUser(@RequestBody Users user) {
        return userService.createUser(user);
    }

    //delete existing user by ID
    @DeleteMapping("/{id}")
    public DBSearch deleteByID(@PathVariable("id") int id) {
        return userService.deleteUserByID(id);
    }

    // update existing user by ID
    @PutMapping("/{id}")
    public Users updateUserByID(@PathVariable("id") int id,
                                @RequestBody Users users) {
        return userService.updateUserByID(id, users);
    }
    }
