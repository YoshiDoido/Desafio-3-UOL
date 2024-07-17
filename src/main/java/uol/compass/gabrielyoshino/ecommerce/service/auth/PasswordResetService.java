package uol.compass.gabrielyoshino.ecommerce.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uol.compass.gabrielyoshino.ecommerce.entity.user.TempToken;
import uol.compass.gabrielyoshino.ecommerce.entity.user.User;
import uol.compass.gabrielyoshino.ecommerce.exception.user.UsuarioNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.user.TempTokenRepository;
import uol.compass.gabrielyoshino.ecommerce.repository.user.UserRepository;
import uol.compass.gabrielyoshino.ecommerce.security.TokenService;

import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempTokenRepository tempTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        String token = tokenService.generateToken(user);

        //String resetLink = "http://localhost:8080/reset-password?token=" + token;
        TempToken tempToken = new TempToken();
        tempToken.setToken(token);

        tempTokenRepository.save(tempToken);
        user.setTempToken(tempToken);

        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Foi solicitada a redefinição de senha", "Use esse token para acessar: " + token);
    }

    public void updatePassword(String token, String newPassword) {
        TempToken tempToken = tempTokenRepository.findByToken(token);

        if (tempToken == null)
            throw new RuntimeException("Token inválido");

        User user = userRepository.findByTempToken(tempToken).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTempToken(null);
        userRepository.save(user);
        tempTokenRepository.delete(tempToken);
    }
}
