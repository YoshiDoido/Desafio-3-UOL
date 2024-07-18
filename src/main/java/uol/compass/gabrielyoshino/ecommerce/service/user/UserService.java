package uol.compass.gabrielyoshino.ecommerce.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uol.compass.gabrielyoshino.ecommerce.dto.user.UserDTO;
import uol.compass.gabrielyoshino.ecommerce.entity.user.User;
import uol.compass.gabrielyoshino.ecommerce.exception.user.UsuarioNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), (List<SimpleGrantedAuthority>) user.getAuthorities())).collect(Collectors.toList());
    }
}
