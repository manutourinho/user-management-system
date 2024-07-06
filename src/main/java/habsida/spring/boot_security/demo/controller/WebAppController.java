package habsida.spring.boot_security.demo.controller;

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
@RequestMapping("/")
public class WebAppController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(WebAppController.class);

    @Autowired
    public WebAppController(UserService userService, RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @GetMapping("/user")
    public String userHomePage(Model model, Principal principal) {
        User loggedUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("roles", roleRepository.findAll());

        return "user/user-home";

    }

    @GetMapping("/admin")
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

    @PostMapping("/admin/add")
    public String add(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("roles") Set<Role> roles, @RequestParam("password") String password) {
        if (bindingResult.hasErrors()) {
            logger.error("Error creating new user {}", user);
        }


        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userService.saveUser(user);
        logger.info("new user was created {}", user);
        return "redirect:/admin";

    }

    @PostMapping(value = "/admin/update/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Error updating new user {}", user);
            return "redirect:/admin";
        }

        User userToUpdate = userRepository.findUserById(id);
        userService.updateUser(userToUpdate);
        logger.info("User was updated {}", user);
        return "redirect:/admin";

    }

    @PostMapping(value = "admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        logger.info("user has been deleted. User id {}", id);
        return "redirect:/admin";

    }


}
