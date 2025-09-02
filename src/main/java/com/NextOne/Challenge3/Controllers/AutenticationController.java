package com.NextOne.Challenge3.Controllers;

import com.NextOne.Challenge3.infra.security.JwtRequestData;
import com.NextOne.Challenge3.infra.security.TokenService;
import com.NextOne.Challenge3.domain.users.User;
import com.NextOne.Challenge3.domain.users.UserRequestData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtRequestData> iniciarSesion(@RequestBody @Valid UserRequestData datos){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.username(), datos.password());
        var autenticacion = authenticationManager.authenticate(authenticationToken);
        var user = (User) autenticacion.getPrincipal();
        var jwtToken = tokenService.generarToken(user);
        return ResponseEntity.ok(new JwtRequestData(jwtToken));
    }
}
