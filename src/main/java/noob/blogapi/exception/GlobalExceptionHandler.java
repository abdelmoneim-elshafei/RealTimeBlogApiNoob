package noob.blogapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception ,
                                                             WebRequest request){
        return new ResponseEntity<>(request.getDescription(false) + exception.getMessage()
                , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<String> handleBlogApiException(BlogApiException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        HashMap<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldError = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldError,message);
        });
        return new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
    }

}