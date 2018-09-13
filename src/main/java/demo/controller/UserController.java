package demo.controller;

import demo.exceptions.DatabaseException;
import demo.exceptions.GlobalExceptionHandler;
import demo.model.CustomResponseObject;
import demo.model.database.Users;
import demo.services.UserService;
import org.apache.catalina.User;
import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//calls the User service method to update the db
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    // NEEDS EXCEPTION HANDLING!
    // calls DB for all users
    @GetMapping("/update/transactions")
    public String updateTransactions() {
        // searches for database link in services
        return userService.analyseAllUserTransaction();
    }

    // calls DB for all users
    @GetMapping("/all")
    public CustomResponseObject<Users> getAllUsers() {

        List<Users> users = userService.findAllUsers();

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(users);
        obj.setError("success.");
        obj.setStatusCode(200);

        return obj;
    }

    // calls DB for Users by ID number
    @RequestMapping("/id={id}")
    public CustomResponseObject<Users> findByID(@PathVariable("id") int id) throws Exception {

        Users user = userService.findUserByID(id);

        if (user != null) {
            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(user);
            obj.setError("success.");
            obj.setStatusCode(200);

            return obj;

        } throw new DatabaseException("User ID doesn't exist.");
    }

    // creates new user
    @PostMapping()
    public CustomResponseObject<Users> createUser(@RequestBody Users user) throws Exception {

        userService.createUser(user);

        if (user == null) throw new DatabaseException("Does not exist.");

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(user);
        obj.setError("success. user created");
        obj.setStatusCode(200);

        return obj;
    }

    //delete existing user by ID
    @DeleteMapping("/id={id}")
    public CustomResponseObject deleteByID(@PathVariable("id") int id) throws Exception {

        boolean success = userService.deleteUserByID(id);
        CustomResponseObject obj = new CustomResponseObject();

        if (success) {
            obj.setData("user removed");
            obj.setStatusCode(200);
            return obj;
        } else throw new DatabaseException("Unable to remove user. Please try again later.");

    }

    // update existing user by ID
    @PutMapping("/id={id}")
    public CustomResponseObject<Users> updateUserByID(@PathVariable("id") int id,
                                                      @RequestBody Users users) throws Exception {

        userService.updateUserByID(id, users);

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(users);
        obj.setError("successfully updated.");
        obj.setStatusCode(200);

        return obj;
    }

}
