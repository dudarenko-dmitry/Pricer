package pl.senla.pricer.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    REGULAR(Set.of(
            Permission.WRITE,
            Permission.READ)),
    ADMIN(Set.of(
            Permission.ADMIN_WRITE,
            Permission.WRITE,
            Permission.READ,
            Permission.UPDATE,
            Permission.DELETE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
