package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User loginUser(String email, String password);
    void saveOrUpdateUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    List<Role> getRoles();

//    String createAcc(Long idRole, Long idUser);

}
