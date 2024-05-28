package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.model.UserAccount;
import habsida.spring.boot_security.demo.model.UserDTO;
import habsida.spring.boot_security.demo.repository.UserAccountRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserServiceImpl(UserRepository userRepository, UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDTO loginUser(String username, String password) {
        User user = userRepository.findByEmail(username).getUser();

        if (user == null) {
            UserAccount userAccount = userAccountRepository.findByUsername(username);
            return new UserDTO(user, userAccount);

        } else {
            return null;
        }

    }

    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);

    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User getUserById(long id) {
        User user = null;
        if (Objects.nonNull(id)) {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                user = userOptional.get();
            } else {
                throw new RuntimeException("user not found with the id " + id + " :(");
            }
        }
        return user;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
