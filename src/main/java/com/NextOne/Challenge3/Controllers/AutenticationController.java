package com.NextOne.Challenge3.Controllers;

import com.NextOne.Challenge3.Services.security.JwtRequestData;
import com.NextOne.Challenge3.Services.security.TokenService;
import com.NextOne.Challenge3.domain.users.User;
import com.NextOne.Challenge3.domain.users.UserRequestData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<JwtRequestData> autenticarUsuario(@RequestBody @Valid UserRequestData userRequestData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userRequestData.login(),
                userRequestData.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((User) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JwtRequestData(JWTtoken));
    }
}
