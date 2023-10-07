package noob.blogapi.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {
    private String accessToken;
    private final String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }

}
