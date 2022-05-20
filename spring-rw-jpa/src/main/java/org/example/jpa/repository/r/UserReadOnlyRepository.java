package org.example.jpa.repository.r;

import org.example.jpa.entity.User;
import org.example.jpa.entity.projections.UserFieldsAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReadOnlyRepository extends JpaRepository<User, String> {
    Iterable<UserFieldsAge> findByName(String name);
}
