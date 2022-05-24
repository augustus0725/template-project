package org.example.app.v1;

import lombok.RequiredArgsConstructor;
import org.example.app.api.Users;
import org.example.commons.standard.RestResponse;
import org.example.jpa.entity.User;
import org.example.jpa.entity.base.Page;
import org.example.jpa.entity.projections.UserFieldsAge;
import org.example.jpa.repository.r.UserReadOnlyRepository;
import org.example.jpa.repository.rw.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor__ = {@Autowired})
public class UsersV1 implements Users {
    private final UserRepository userRepository;
    private final UserReadOnlyRepository userReadOnlyRepository;

    @Override
    public RestResponse<User> save(User user) {
        return RestResponse.ok(userRepository.save(user));
    }

    @Override
    public RestResponse<Page<UserFieldsAge>> findByName(String name, int currentPage, int pageSize) {
        org.springframework.data.domain.Page<UserFieldsAge> pageData =
                userReadOnlyRepository.findByName(name, PageRequest.of(currentPage, pageSize));

        return RestResponse.ok(Page.of(pageData, currentPage, pageSize));
    }
}