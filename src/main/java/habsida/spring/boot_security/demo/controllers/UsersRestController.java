package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;

import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UsersRestController.class);

    @Autowired
    public UsersRestController(UserService userService,
                               RoleRepository roleRepository) {
        this.userService = userService;

        this.roleRepository = roleRepository;
    }

    @GetMapping("/admins")
    public ResponseEntity<Map<String, Object>> getAllUsers() {

            logger.debug("fetching all users");
            List<User> users = userService.getAllUsers();
            List<Role> roles = roleRepository.findAll();
            logger.debug("fetched users: {}", users);
            logger.debug("fetched roles: {}", roles);

            Map<String,Object> resp = new HashMap<>();
            resp.put("users", users);
            resp.put("roles", roles);

            return ResponseEntity.ok(resp);


    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.getUserById(id);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);
//
//    }

    @PostMapping("/admins/add")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {

        System.out.println("resolved roles: " + user.getRoles());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));

    }

    @PutMapping("/admins/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody @Valid User user) {
        System.out.println("receivd user: " + user);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/admins/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return ResponseEntity.noContent().build();

    }

}
