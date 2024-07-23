package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import habsida.spring.boot_security.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
//@RequestMapping("/")
public class WebAppController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public WebAppController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    public String home() {
        return "index";

    }

    @GetMapping("/users")
    public String userHomePage(Model model, Principal principal) {
        User loggedUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("roles", roleRepository.findAll());

        return "user/user-home";

    }

    @GetMapping("/admins")
    public String adminHomePage(Model model, Principal principal) {
        User loggedUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("roles", roleRepository.findAll());

        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("users", null);
        }

        model.addAttribute("user", new User());

        return "admin/admin-home";

    }

//    @PostMapping("/admins/add")
//    public String add(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("roles") Set<Role> roles, @RequestParam("password") String password) {
//        if (bindingResult.hasErrors()) {
//            logger.error("Error creating new user {}", user);
//        }
//
//
//        user.setRoles(roles);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
//        userService.saveUser(user);
//        logger.info("new user was created {}", user);
//        return "redirect:/admins";
//
//    }
//
//    @PostMapping(value = "/admins/update/{id}")
//    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            logger.error("Error updating new user {}", user);
//            return "redirect:/admins";
//        }
//
//        userService.updateUser(id, user);
//        logger.info("User was updated {}", user);
//        return "redirect:/admins";
//
//    }
//
//    @PostMapping(value = "admins/delete/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        userService.removeUserById(id);
//        logger.info("user has been deleted. User id {}", id);
//        return "redirect:/admins";
//
//    }


}