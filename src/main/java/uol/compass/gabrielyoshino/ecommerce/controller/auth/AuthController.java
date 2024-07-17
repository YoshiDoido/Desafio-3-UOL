package uol.compass.gabrielyoshino.ecommerce.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uol.compass.gabrielyoshino.ecommerce.dto.auth.LoginRequestDTO;
import uol.compass.gabrielyoshino.ecommerce.dto.auth.RegisterRequestDTO;
import uol.compass.gabrielyoshino.ecommerce.dto.auth.ResetPasswordDTO;
import uol.compass.gabrielyoshino.ecommerce.dto.auth.ResponseDTO;
import uol.compass.gabrielyoshino.ecommerce.entity.user.User;
import uol.compass.gabrielyoshino.ecommerce.exception.user.UsuarioNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.user.UserRepository;
import uol.compass.gabrielyoshino.ecommerce.security.TokenService;
import uol.compass.gabrielyoshino.ecommerce.service.auth.PasswordResetService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PasswordResetService passwordResetService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, PasswordResetService passwordResetService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        if (passwordEncoder.matches( body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.userRepository.findByEmail(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam String email) {
        passwordResetService.resetPassword(email);
        return ResponseEntity.ok().build();
    }

    // Método para gerar nova senha com base no token recebido pelo email
    @PostMapping("/reset-password")
    public ResponseEntity<Void> newPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        passwordResetService.updatePassword(resetPasswordDTO.token(), resetPasswordDTO.password());
        return ResponseEntity.ok().build();
    }


}
