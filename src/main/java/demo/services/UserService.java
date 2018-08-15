package demo.services;
import demo.mapper.UserMapper;
import demo.model.database.DBSearch;
import demo.model.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    // GET - all Users
    public List<Users> findAllUsers() {
        // import users
        return mapper.listAllUsers();
    }


    // GET - find user by ID
    public DBSearch findUserByID(int id) {

        DBSearch searchID = new DBSearch();

        searchID.setId(id);

        searchID.setUsers(mapper.findUserByID(id));

        return searchID;
    }


    // POST - create new user
    public Users createUser(Users data) {

        Users newUser = new Users();

        newUser.setName(data.getName());
        newUser.setPassword(data.getPassword());
        newUser.setEmail(data.getEmail());

        try {
            mapper.createUser(newUser);
        } catch (Exception e) {
            System.out.println("User already exists. Please log in : " + data.getEmail());
        }
        return newUser;
    }

    // DELETE - delete existing user by ID
    public DBSearch deleteUserByID(int id) {

        DBSearch removeID = new DBSearch();

        // compare DB ID (removeID) to searched "id" (id)
        if (removeID.getId() == id) ;
        {
            // remove ID from DB
            removeID.setId(mapper.deleteUserByID(id));
        }
        // if no results; return DB ID (remove ID)
        return removeID;
    }

    // PUT - update existing user by ID
    public Users updateUserByID(int id, Users data) {

        Users updateUser = new Users();

        updateUser.setName(data.getName());
        updateUser.setPassword(data.getPassword());
        updateUser.setEmail(data.getEmail());
        updateUser.setId(id);

        mapper.updateUserByID(updateUser);

        return updateUser;
    }
}
