package uol.compass.gabrielyoshino.ecommerce.service.user;

import org.springframework.beans.factory.annotation.Autowired;
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
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRoles())).collect(Collectors.toList());
    }

    public UserDTO addRolesToUser(String userId, List<String> roles) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        user.getRoles().addAll(roles);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getRoles());
    }
}
