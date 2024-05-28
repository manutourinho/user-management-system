package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.model.UserAccount;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserAccountRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<UserAccount> getAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public String createAcc(Long idRole, Long idUser) {
        User user = userRepository.findById(idUser).get();
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setRole(roleRepository.findById(idRole).get());
        String generatedPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(generatedPassword);

        UserAccount tempUserAccount = userAccountRepository.findByUsername(user.getEmail());
        if (tempUserAccount == null) {
            userAccount.setUsername(user.getEmail());
            userAccountRepository.save(userAccount);
            return generatedPassword;

        }

        int m = 0;
        String login = "";
        while (true) {
            login = user.getEmail() + m;
            tempUserAccount = userAccountRepository.findByUsername(login);
            if (tempUserAccount == null) {
                userAccount.setUsername(login);
                break;
            }

            m++;
        }

        userAccountRepository.save(userAccount);
        return generatedPassword;
    }
}
