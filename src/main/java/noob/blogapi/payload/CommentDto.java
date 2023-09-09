package noob.blogapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty
    @Size(min = 3 , message = "name should at least 2 ")
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 5 , message = "Comment should at least 5 ")
    private String body;

}
