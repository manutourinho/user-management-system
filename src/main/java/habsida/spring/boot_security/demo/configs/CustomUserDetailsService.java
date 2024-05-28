package habsida.spring.boot_security.demo.configs;

import habsida.spring.boot_security.demo.model.UserAccount;
import habsida.spring.boot_security.demo.model.UserPrincipal;
import habsida.spring.boot_security.demo.repository.UserAccountRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    private Logger LOGGER = Logger.getLogger(getClass().getName());


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = null;
        userAccount = userAccountRepository.findByUsername(username);

        if ((userAccount == null) || (userAccount.getRole() == null)) {
            String message = "Username " + username + " not found";
            LOGGER.warn(message);
            throw new UsernameNotFoundException(message);
        }
//
//        if (username.equals("admin")) {
//            return new User(
//                    "admin",
//                    "{noop}password",
//                    true,
//                    true,
//                    true,
//                    true,
//                    getAuthorities("ROLE_ADMIN")
//            );
//        } else if (username.equals("user")) {
//            return new User(
//                    "user",
//                    "{noop}password",
//                    true,
//                    true,
//                    true,
//                    true,
//                    getAuthorities("ROLE_USER")
//            );
//        } else {
//            throw new UsernameNotFoundException("User not found: " + username + " :(");
//        }

        return new UserPrincipal(userAccount);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String... roles) {
        return Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
