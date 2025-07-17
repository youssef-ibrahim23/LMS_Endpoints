package com.example.sms.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
  @Query(value = """
      SELECT a.*
      FROM accounts a
      JOIN users u ON a.user_id = u.user_id
      JOIN roles r ON u.role_id = r.role_id
      WHERE a.email = :email
        AND a.status = 'active'
        AND u.is_deleted = false
        AND a.password = :password
      """, nativeQuery = true)
  Optional<Account> checkIfUserExistsAndTheirRoleAndStatus(@Param("email") String email,
      @Param("password") String password);

  boolean existsByUserId(Integer userId);
  @Query(value="""
      UPDATE accounts
      SET password = :pass, last_reset = CURRENT_DATE
      WHERE user_id = :user
      """, nativeQuery=true)
      int changePassword(@Param("pass") String pass, @Param("user") Integer user);
}
