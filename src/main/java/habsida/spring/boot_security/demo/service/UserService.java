package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    Set<Role> rolesToAssign(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    User getUserById(Long id);

    List<Role> getRoles();


}
