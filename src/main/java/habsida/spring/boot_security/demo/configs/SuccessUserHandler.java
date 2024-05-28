package habsida.spring.boot_security.demo.configs;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
//        Collection<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_USER")) {
//            httpServletResponse.sendRedirect("/user");
//        } else if (roles.contains("ROLE_ADMIN")) {
//            httpServletResponse.sendRedirect("/admin");
//        }
//
//        httpServletResponse.sendRedirect("/home");
        String homePage = "";
        Map<String, String> homePageByRole = new HashMap<>();
        homePageByRole.put("ROLE_USER", "/user");
        homePageByRole.put("ROLE_ADMIN", "/admin");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();

            if (homePageByRole.containsKey(authorityName)) {
                homePage = homePageByRole.get(authorityName);
                break;
            }
        }

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, homePage);

    }
}