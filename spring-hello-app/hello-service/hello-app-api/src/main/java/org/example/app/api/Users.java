package org.example.app.api;

import org.example.commons.standard.RestResponse;
import org.example.jpa.entity.User;
import org.example.jpa.entity.base.Page;
import org.example.jpa.entity.projections.UserFieldsAge;

public interface Users {
    RestResponse<User> save(User user);
    RestResponse<Page<UserFieldsAge>> findByName(String name, int currentPage, int pageSize);
}
