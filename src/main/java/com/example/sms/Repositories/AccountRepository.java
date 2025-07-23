package com.example.sms.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
@Query("""
    SELECT a 
    FROM Account a
    JOIN FETCH a.user u
    JOIN FETCH u.role r
    WHERE a.email = :email
    AND lower(a.status) = 'active'
    AND u.isDeleted = false
    AND a.password = :password
    """)
Optional<Account> findActiveAccountByCredentials(
    @Param("email") String email,
    @Param("password") String password);

  boolean existsByUserUserId(Integer userId);
  @Query(value="""
      UPDATE accounts
      SET password = :pass, last_reset = CURRENT_DATE
      WHERE user_id = :user
      """, nativeQuery=true)
      int changePassword(@Param("pass") String pass, @Param("user") Integer user);
      
      Optional<Account> findByEmail(String email);
}
