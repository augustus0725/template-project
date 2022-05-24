package org.example.app.controller;

import lombok.SneakyThrows;
import org.example.app.config.ReadJpaConfiguration;
import org.example.app.config.ReadWriteJpaConfiguration;
import org.example.jpa.entity.User;
import org.example.jpa.entity.projections.UserFieldsAge;
import org.example.jpa.repository.r.UserReadOnlyRepository;
import org.example.jpa.repository.rw.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 4 需要, junit 5不需要
@DataJpaTest(properties = "spring.main.banner-mode=off")
// 下面的配置注释掉之后就会用嵌入的h2数据库
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 不使用嵌入的测试库，用真实的库
@Import({ReadWriteJpaConfiguration.class, ReadJpaConfiguration.class}) // 不使用嵌入的测试库，用真实的库的时候就需要导入这个配置
@Ignore
public class UsersControllerTest {
    @Autowired private UserReadOnlyRepository userReadOnlyRepository;
    @Autowired private UserRepository userRepository;

    @SneakyThrows
    @Test
    @Rollback(value = false)
    public void testSave() {
        // 测试主库写
        User u = userRepository.save(User.builder().name("name001").age(200).build());

        assertEquals("name001", u.getName());
    }

    @SneakyThrows
    @Test
    public void testFind() {
        // 测试从库读
        Page<UserFieldsAge> page = userReadOnlyRepository.findByName("name001", PageRequest.of(0, 10));
        assertEquals(1, page.getTotalElements());
    }
}