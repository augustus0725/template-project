package org.example.jpa.repository.rw.pg;

import org.example.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PgUserRepository extends JpaRepository<User, String> {
}
