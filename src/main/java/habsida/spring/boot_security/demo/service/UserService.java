package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    User getUserById(Long id);

    List<Role> getRoles();


}
