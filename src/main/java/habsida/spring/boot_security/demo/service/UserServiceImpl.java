package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public User saveUser(User user) {
        user.setRoles(user.getRoles());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public User updateUser(Long id, User user) {
//        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found :("));
//        if (existingUser != null) {
//            existingUser.setFirstName(user.getFirstName());
//            existingUser.setLastName(user.getLastName());
//            existingUser.setEmail(user.getEmail());
//            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            existingUser.setRoles(user.getRoles());
//
//            return userRepository.save(existingUser);
//        }
//
//        return null;

        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAge(user.getAge());
            existingUser.setEmail(user.getEmail());
//            if (user.getPassword() != null) {
//                existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            }

            Set<Role> roles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role existingRole = roleRepository.findByRoleName(role.getRoleName());
                roles.add(Objects.requireNonNullElse(existingRole, role));
            }

            existingUser.setRoles(roles);

            return userRepository.save(existingUser);
        }).orElse(null);

    }

    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public List<User> getAllUsers() {
        try {
            logger.debug("retrieving all users from db");
            List<User> users = userRepository.findAll();
            logger.debug("retrieved all users from db: {}", users);
            return users;

        } catch (Exception e) {
            logger.error("error retrieving users!!!!!!!");
            throw e;
        }

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByUsername(email);

    }
}
