package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.model.User;

import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private final UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));

    }

    @PutMapping("/admins/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody @Valid User user) {
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
