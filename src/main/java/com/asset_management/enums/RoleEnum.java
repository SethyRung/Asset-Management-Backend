package com.asset_management.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.asset_management.enums.PermissionEnum.*;


@Getter
@RequiredArgsConstructor
public enum RoleEnum {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE
          )
  ),
  Manager(
          Set.of(
                  Manager_READ,
                  Manager_UPDATE,
                  Manager_DELETE,
                  Manager_CREATE
          )
  ),
  Employee(
          Set.of(
                  Employee_READ,
                  Employee_UPDATE,
                  Employee_DELETE,
                  Employee_CREATE
          )
  ),
  Auditor(
          Set.of(
                  Auditor_READ,
                  Auditor_UPDATE,
                  Auditor_DELETE,
                  Auditor_CREATE
          )
  );

  private final Set<PermissionEnum> permissionEnums;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissionEnums()
            .stream()
            .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
