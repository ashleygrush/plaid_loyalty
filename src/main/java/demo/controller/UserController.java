package demo.controller;

import demo.model.database.DBSearch;
import demo.model.database.Users;
import demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loyalty")
public class UserController {

    @Autowired
    UserService userService;

    // calls DB for all users
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        // searches for database link in services
        return userService.findAllUsers();
    }

    // calls DB for Users by ID number
    @RequestMapping("/users/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        // searches for database link by ID
        return userService.findUserByID(id);
    }

    //creates new user
    @PostMapping("/users")
    public Users createUser(@RequestBody Users user) {

        return userService.createUser(user);
    }

}
