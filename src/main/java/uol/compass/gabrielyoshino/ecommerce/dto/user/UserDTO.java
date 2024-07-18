package uol.compass.gabrielyoshino.ecommerce.dto.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserDTO {

    private String id;
    private String name;
    private String email;
    private List<SimpleGrantedAuthority> roles;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email, List<SimpleGrantedAuthority> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<SimpleGrantedAuthority> roles) {
        this.roles = roles;
    }
}
