package demo.services;

import demo.mapper.PlaidDatabase;
import demo.model.database.DBSearch;
import demo.model.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    PlaidDatabase plaidDatabase;

    // GET - all Users
    public List<Users> findAllUsers() {
        // import users
        return plaidDatabase.listAllUsers();
    }


    // GET - Users by ID
    public DBSearch findUserByID(int id) {

        DBSearch searchID = new DBSearch();

        searchID.setId(id);

        searchID.setUsers(plaidDatabase.findUserByID(id));

        return searchID;
    }


    // POST - create new user
    public Users createUser(Users data) {

        Users newUser = new Users();

        newUser.setName(data.getName());
        newUser.setPassword(data.getPassword());
        newUser.setEmail(data.getEmail());

        plaidDatabase.createUser(newUser);

        return newUser;
    }




}
