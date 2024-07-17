package uol.compass.gabrielyoshino.ecommerce.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uol.compass.gabrielyoshino.ecommerce.entity.user.User;
import uol.compass.gabrielyoshino.ecommerce.exception.user.UsuarioNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.user.UserRepository;
import uol.compass.gabrielyoshino.ecommerce.security.TokenService;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        String token = tokenService.generateToken(user);
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        emailService.sendEmail(user.getEmail(), "Foi solicitada a redefinição de senha", "Clique no link para redefinir sua senha: " + resetLink);
    }
}
