package demo.controller;

/**
 * Created by ashleyalmeida
 */

import com.amazonaws.util.CollectionUtils;
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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * GET - fetches and populates transactions into hash-map.
     * it then iterates against hash map with a list of users
     * and matching participating merchants.
     * Once a match is found; the database is updated under
     * user ID and merchant ID.
     *
     * @return updates database with transactions.
     */
    @GetMapping("/update/transactions")
    public String updateTransactions() {
        // searches for database link in services
        return userService.analyseAllUserTransaction();
    }

    /**
     * GET - all users from database with ID number, email, and password.
     *
     * @return list of all users in database.
     * @throws Exception
     */
    @GetMapping("/all")
    public CustomResponseObject<Users> getAllUsers() throws Exception {

        List<Users> users = userService.findAllUsers();

        if (!(CollectionUtils.isNullOrEmpty(users))) {

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(users);
        obj.setMessage("success.");
        obj.setStatusCode(200);

        return obj;

        } else throw new DatabaseException("No users to show.");
    }

    /**
     * GET - retrieves user by unique ID
     *
     * @param id unique id assigned to users
     * @return all user information in database
     * @throws Exception
     */
    @RequestMapping("/{id}")
    public CustomResponseObject<Users> findByID(@PathVariable("id") int id) throws Exception {

        Users user = userService.findUserByID(id);

        if (user != null) {
            CustomResponseObject obj = new CustomResponseObject();
            obj.setData(user);
            obj.setMessage("success.");
            obj.setStatusCode(200);

            return obj;

        } throw new DatabaseException("User ID doesn't exist. Please enter correct ID number or create new account.");
    }

    /**
     * POST - create new user in database
     *
     * @param user RequestBody - name, email and password
     * @return user creation confirmation
     * @throws Exception
     */
    @PostMapping()
    public CustomResponseObject<Users> createUser(@RequestBody Users user) throws Exception {

        userService.createUser(user);

        if (user == null) throw new DatabaseException("Does not exist.");

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(user);
        obj.setMessage("success. user created");
        obj.setStatusCode(200);

        return obj;
    }

    /**
     * DELETE - removes user from database by unique ID
     *
     * @param id unique ID removes user from database
     * @return boolean of successful removal of user
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public CustomResponseObject deleteByID(@PathVariable("id") int id) throws Exception {

        boolean success = userService.deleteUserByID(id);
        CustomResponseObject obj = new CustomResponseObject();

        if (success) {
            obj.setData("user removed");
            obj.setStatusCode(200);
            return obj;
        } else throw new DatabaseException("Unable to remove user. Please try again later.");

    }

    /**
     * PUT - updates user in database by unique ID
     *
     * @param id unique ID to update user in database
     * @param users RequestBody to fill out new information for user
     * @return returns information submitted for confirmation
     * @throws Exception
     */
    @PutMapping("/{id}")
    public CustomResponseObject<Users> updateUserByID(@PathVariable("id") int id,
                                                      @RequestBody Users users) throws Exception {

        userService.updateUserByID(id, users);

        CustomResponseObject obj = new CustomResponseObject();
        obj.setData(users);
        obj.setMessage("successfully updated.");
        obj.setStatusCode(200);

        return obj;
    }

}
