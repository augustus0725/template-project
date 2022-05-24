package org.example.jpa.repository.r;

import org.example.jpa.entity.User;
import org.example.jpa.entity.projections.UserFieldsAge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadOnlyRepository extends JpaRepository<User, String> {
    Page<UserFieldsAge> findByName(String name, PageRequest request);
}
