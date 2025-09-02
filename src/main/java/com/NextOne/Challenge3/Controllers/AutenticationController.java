package com.NextOne.Challenge3.Controllers;

import com.NextOne.Challenge3.domain.users.DTO.UserDetailsResponse;
import com.NextOne.Challenge3.domain.users.DTO.UserRegisterRequest;
import com.NextOne.Challenge3.domain.users.UserRepository;
import com.NextOne.Challenge3.infra.security.JwtRequestData;
import com.NextOne.Challenge3.infra.security.TokenService;
import com.NextOne.Challenge3.domain.users.User;
import com.NextOne.Challenge3.domain.users.DTO.UserLoginRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtRequestData> login(@RequestBody @Valid UserLoginRequest datos){
        var email = datos.username().toLowerCase();
        var authenticationToken = new UsernamePasswordAuthenticationToken(email, datos.password());
        var autenticacion = authenticationManager.authenticate(authenticationToken);
        var user = (User) autenticacion.getPrincipal();
        var jwtToken = tokenService.generarToken(user);
        return ResponseEntity.ok(new JwtRequestData(jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDetailsResponse> register(@RequestBody @Valid UserRegisterRequest datos) {
        var emailLower = datos.username().toLowerCase();
        var encodedPass = passwordEncoder.encode(datos.password());

        if (userRepository.findByUsername(emailLower) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        User user = new User(
                null,
                emailLower,
                encodedPass
        );
        userRepository.save(user);
        String jwtToken = tokenService.generarToken(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserDetailsResponse(user.getUsername(), jwtToken));
    }
}
