package org.example.app.api;

import org.example.commons.standard.RestResponse;
import org.example.jpa.entity.User;
import org.example.jpa.entity.projections.UserFieldsAge;

public interface Users {
    RestResponse<User> save(User user);
    Iterable<UserFieldsAge> findByName(String name);
}
