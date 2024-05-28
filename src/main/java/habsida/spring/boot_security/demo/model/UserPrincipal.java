package habsida.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private UserAccount userAccount;

    public UserPrincipal(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> arrAuths = new ArrayList<>();

        if (userAccount.getRole() != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userAccount.getRole().getRoleName());
            arrAuths.add(authority);
        }

        return arrAuths;
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userAccount.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userAccount.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userAccount.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userAccount.isEnabled();
    }

    //    ✿✿✿ getters & setters ✿✿✿
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
