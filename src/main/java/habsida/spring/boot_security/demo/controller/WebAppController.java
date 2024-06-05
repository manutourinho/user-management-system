package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class WebAppController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public WebAppController(UserService userService,
                            RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;

        this.roleRepository = roleRepository;
    }

//    @GetMapping("/register")
//    public String showRegistration() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerAcc(@ModelAttribute("user") @Valid User user, @ModelAttribute("role") Role role, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "register";
//        }
//
//        user.setRole(role);
//        userRepository.save(user);
//        userService.createAcc(user.getIdUser(), role.getIdRole());
//        return "redirect:/login";
//
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        User user = userService.loginUser(username, password);
        if (user == null) {
            session.setAttribute("currentUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/user")
    public String showUsersPage(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("users", null);
        }
        return "list";

    }

    @GetMapping("/admin")
    public String adminHomePage() {
        return "admin";
    }

    @GetMapping("/admin/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "form";

    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roleId") List<Long> roleId) {

        Set<Role> roles = roleId.stream()
                        .map(roleRepository::findById)
                                .filter(Optional::isPresent)
                                        .map(Optional::get)
                                                .collect(Collectors.toSet());
        user.setRoles(roles);
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "admin/update/{id}")
    public String showUserUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "form";

    }

    @PostMapping("admin/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "admin/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";

    }

    @GetMapping("/admin/list")
    public String adminListView(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("users", null);
        }

        return "admin_list_view";

    }

}
