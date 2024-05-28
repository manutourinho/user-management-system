package habsida.spring.boot_security.demo.repository;

import habsida.spring.boot_security.demo.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select u from UserAccount u where u.username = :username")
    UserAccount findByUsername(@Param("username") String username);
}
