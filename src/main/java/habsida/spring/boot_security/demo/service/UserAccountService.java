package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.UserAccount;

import java.util.List;

public interface UserAccountService {

    public List<Role> getRoles();
    public List<UserAccount> getAccounts();
    public String createAcc(Long idRole, Long idUser);
}
