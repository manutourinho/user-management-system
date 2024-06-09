package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User loginUser(String username, String password) {
        return userRepository.findByUsername(username);

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
    public Optional<User> findById(long id) {
        return userRepository.findById(id);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();

    }

//    @Override
//    public String createAcc(Long idRole, Long idUser) {
//        Optional<User> userOptional = userRepository.findById(idUser);
//        Optional<Role> roleOptional = roleRepository.findById(idRole);
//
//        if (userOptional.isEmpty()) {
//            throw new EntityNotFoundException("Username not found :(");
//
//        }
//
//        User user = userOptional.get();
//        Role role = roleOptional.orElseThrow(() -> new EntityNotFoundException("Role not found :("));
//
//        user.getRoles().add(role);
//
//        String generatedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(generatedPassword);
//        user.setUsername(generateUsername(user.getEmail()));
//
//        userRepository.save(user);
//
//        return generatedPassword;
//    }

    private String generateUsername(String email) {
        int m = 0;
        String username = "";
        do {
            username = email + (m > 0 ? m : "");
            m++;

        } while (userRepository.findByUsername(username) != null);

        return username;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found :(");

        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());

    }
}
