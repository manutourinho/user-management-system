package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.model.UserAccount;
import habsida.spring.boot_security.demo.model.UserDTO;
import habsida.spring.boot_security.demo.repository.UserAccountRepository;
import habsida.spring.boot_security.demo.service.UserAccountService;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class WebAppController {

    private final UserService userService;
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public WebAppController(UserService userService, UserAccountService userAccountService,
                            UserAccountRepository userAccountRepository) {
        this.userService = userService;
        this.userAccountService = userAccountService;
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping("/register")
    public String showRegistration() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAcc(@ModelAttribute("accounts") @Valid UserAccount userAccount, @ModelAttribute("role") Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userAccount.setRole(role);
        userAccountRepository.save(userAccount);
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        UserDTO userDTO = userService.loginUser(username, password);
        if (userDTO == null) {
            session.setAttribute("currentUser", userDTO);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

    }

    @GetMapping("/admin")
    public String adminHomePage() {
        return "index";
    }

    @GetMapping("/user")
    public String showUsersPage(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("users", null);
        }
        return "user";
    }

    @GetMapping("/admin/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "form";

    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "admin/update/{id}")
    public String showUserUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
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

}
