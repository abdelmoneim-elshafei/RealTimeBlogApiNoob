package noob.blogapi.payload;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
}
