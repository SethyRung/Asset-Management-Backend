package com.asset_management.repositories;

import com.asset_management.enums.RoleEnum;
import com.asset_management.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordId(String resetId);

    Page<User> findByUsernameContainingIgnoreCase(String search, Pageable pageable);

    @Query(
            value = "SELECT * FROM \"user\" u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail",
            nativeQuery = true
    )
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    @Query(
            value = "SELECT * FROM \"user\" u WHERE u.role = :role OR :role = ''",
            nativeQuery = true
    )
    List<User> findAllByRole(@Param("role") String role);

    @Query(
            value = "SELECT * FROM \"user\" u WHERE u.status = 'TRUE'",
            nativeQuery = true
    )
    List<User> findAllActiveUser();
}
