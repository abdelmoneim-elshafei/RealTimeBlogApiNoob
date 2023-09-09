package noob.blogapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class BlogApiException extends  RuntimeException{
    HttpStatus httpStatus;
    String massage;

    public BlogApiException(HttpStatus httpStatus, String massage) {
        super(massage);
        this.httpStatus = httpStatus;
        this.massage = massage;
    }



}
