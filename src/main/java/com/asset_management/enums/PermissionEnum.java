package com.asset_management.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionEnum {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    Manager_READ("manager:read"),
    Manager_UPDATE("manager:update"),
    Manager_CREATE("manager:create"),
    Manager_DELETE("manager:delete"),
    Employee_READ("employee:read"),
    Employee_UPDATE("employee:update"),
    Employee_CREATE("employee:create"),
    Employee_DELETE("employee:delete"),
    Auditor_READ("auditor:read"),
    Auditor_UPDATE("auditor:update"),
    Auditor_CREATE("auditor:create"),
    Auditor_DELETE("auditor:delete");

    private final String permission;
}
