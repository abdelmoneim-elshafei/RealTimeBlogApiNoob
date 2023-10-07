package noob.blogapi.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("abdo"));
        String pw_hash = BCrypt.hashpw("abdo",BCrypt.gensalt());
        System.out.println(pw_hash);
        System.out.println(BCrypt.checkpw("abdo",pw_hash));
    }
}
