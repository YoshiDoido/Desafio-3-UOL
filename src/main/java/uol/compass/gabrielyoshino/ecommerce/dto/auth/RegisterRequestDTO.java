package uol.compass.gabrielyoshino.ecommerce.dto.auth;

import uol.compass.gabrielyoshino.ecommerce.entity.user.Role;

public record RegisterRequestDTO(String name, String email, String password, Role role) {
}
