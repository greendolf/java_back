package infrastructure.dtos;

public class UserDTO {
    public String login;
    public String password;

    public UserDTO() {
    }

    public UserDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
