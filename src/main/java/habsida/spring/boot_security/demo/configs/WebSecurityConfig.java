package habsida.spring.boot_security.demo.configs;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public SuccessUserHandler getSuccessUserHandler() {
        return new SuccessUserHandler();
    }

    @PostConstruct
    public void init() {
        createDbUsers("user", "user", "USER");
        createDbUsers("admin", "admin", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/register").permitAll()
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .successHandler(getSuccessUserHandler())
                .and().logout()
                .deleteCookies();

    }

//     аутентификация inMemory
    private void createDbUsers(String username, String password, String... roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder().encode(password));

        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            Role r = roleRepository.findByName(role);
            if (r == null) {
                r = new Role();
                r.setRoleName(role);
            }

            System.out.println("Creating role: " + r.getRoleName());

            roleRepository.save(r);
            roleSet.add(r);
        }

        user.setRoles(roleSet);

        userRepository.save(user);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);

    }

    @Lazy
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}