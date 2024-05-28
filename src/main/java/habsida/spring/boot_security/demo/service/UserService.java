package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.model.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO loginUser(String username, String password);
    void saveOrUpdateUser(User user);

    void removeUserById(long id);

    User getUserById(long id);

    List<User> getAllUsers();

}
