package com.payeshgaran.atm_erfan_p2.entity.permission;


public enum Permission {
    READ_USER("user:read"),
    WRITE_USER("user:write"),
    READ_ACCOUNT("account:read"),
    WRITE_ACCOUNT("account:write"),
    READ_TRANSACTION("transaction:read"),
    WRITE_TRANSACTION("transaction:write");
    private final String permissionName;

    Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }
}
