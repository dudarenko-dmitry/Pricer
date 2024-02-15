package pl.senla.pricer.entity;

import lombok.Getter;

@Getter
public enum Permission {
    READ("read"),
    WRITE("write"),
    UPDATE("update"),
    DELETE("delete"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    Permission(String permission) {
        this.permission=permission;
    }

}
