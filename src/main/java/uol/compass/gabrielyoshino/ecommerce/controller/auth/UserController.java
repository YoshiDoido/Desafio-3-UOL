package uol.compass.gabrielyoshino.ecommerce.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.gabrielyoshino.ecommerce.dto.user.UserDTO;
import uol.compass.gabrielyoshino.ecommerce.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("{userId}/roles")
    public ResponseEntity<UserDTO> addRoleToUser(@PathVariable String userId, @RequestBody List<String> roles) {
        UserDTO updatedUser = userService.addRolesToUser(userId, roles);
        return ResponseEntity.ok(updatedUser);
    }
}
