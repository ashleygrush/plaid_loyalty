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
        return userService.findAllUsers();
    }

    // calls DB for Users by ID number
    @RequestMapping("/users/{id}")
    public DBSearch findByID(@PathVariable("id") int id) {
        return userService.findUserByID(id);
    }

    // creates new user
    @PostMapping("/users")
    public Users createUser(@RequestBody Users user) {
        return userService.createUser(user);
    }

    //delete existing user by ID
    @DeleteMapping("/users/{id}")
    public DBSearch deleteByID(@PathVariable("id") int id) {
        return userService.deleteUserByID(id);
    }

    // update existing user by ID
    @PutMapping("/users/{id}")
    public Users updateUserByID(@PathVariable("id") int id,
                                @RequestBody Users users) {
        return userService.updateUserByID(id, users);
    }

}
