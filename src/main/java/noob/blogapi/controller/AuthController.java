package noob.blogapi.controller;

import lombok.RequiredArgsConstructor;
import noob.blogapi.entity.Role;
import noob.blogapi.entity.User;
import noob.blogapi.payload.JwtAuthResponse;
import noob.blogapi.payload.LoginDTO;
import noob.blogapi.payload.SignUpDTO;
import noob.blogapi.repository.RoleRepository;
import noob.blogapi.repository.UserRepository;
import noob.blogapi.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider provider;

    @PostMapping("/signin")
    public ResponseEntity<?> singIn(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername()
                , loginDTO.getPassword()));
        String token = provider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO sign) {
        if (userRepository.existsByUsername(sign.getUsername())) {
            return new ResponseEntity<>("Username Not Available", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(sign.getUsername())) {
            return new ResponseEntity<>("Email Not Available", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        User user = User.builder()
                .name(sign.getName())
                .email(sign.getEmail())
                .password(passwordEncoder.encode(sign.getPassword()))
                .username(sign.getUsername())
                .roles(Collections.singleton(role))
                .build();
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }


}
