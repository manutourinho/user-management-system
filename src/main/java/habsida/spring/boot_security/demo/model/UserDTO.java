package habsida.spring.boot_security.demo.model;

public class UserDTO {

    private Long idUser;
    private String firstName;
    private String lastName;
    private Byte age;
    private String email;
    private Long idAccount;
    private String username;
    private String password;
    private Role role;

    public UserDTO() {
    }

    public UserDTO(User user, UserAccount userAccount) {
        this.idUser = user.getIdUser();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.idAccount = userAccount.getIdAccount();
        this.username = userAccount.getUsername();
        this.password = userAccount.getPassword();
        this.role = userAccount.getRole();

    }

    //    ✿✿✿ getters & setters ✿✿✿
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
