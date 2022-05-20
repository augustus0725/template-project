package org.example.jpa.repository.rw;

import com.google.common.collect.Lists;
import org.example.config.ReadJpaConfiguration;
import org.example.config.ReadWriteJpaConfiguration;
import org.example.jpa.entity.User;
import org.example.jpa.entity.projections.UserFieldsAge;
import org.example.jpa.repository.r.UserReadOnlyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 4 需要, junit 5不需要
@DataJpaTest(properties = "spring.main.banner-mode=off")
// 下面的配置注释掉之后就会用嵌入的h2数据库
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 不使用嵌入的测试库，用真实的库
@Import({ReadWriteJpaConfiguration.class, ReadJpaConfiguration.class}) // 不使用嵌入的测试库，用真实的库的时候就需要导入这个配置
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private UserReadOnlyRepository userReadOnlyRepository;

    @Test
    @Rollback(value = false)
    public void testSave() {
        User user = userRepository.saveAndFlush(
//        User user = userRepository.save(
                User.builder()
                        .name("sabo")
                        .age(100)
                        .createdDate(new Timestamp(System.currentTimeMillis()))
                        .lastModifiedDate(System.currentTimeMillis()).build()
        );

        // 应该查不到, pg的从主机是根据日志同步数据的
        // List<User> users = userReadOnlyRepository.findAll();

//        assertEquals("sabo", user.getName());
    }


    @Test
    @Rollback(value = false)
    public void testBatchSave() {
        List<User> users = Lists.newArrayList();

        for (int i = 201; i < 210; ++i) {
            users.add(
                    User.builder().name("sabo" + i).age(i).build()
            );
        }

        List<User> savedUsers = userRepository.saveAll(users);

        assertFalse(savedUsers.isEmpty());
    }

    @Test
    public void testFind() {
        List<User> users = userReadOnlyRepository.findAll();
        assertEquals(1, users.size());
        User user = users.get(0);

        assertEquals("sabo", user.getName());

        Iterable<UserFieldsAge> ages = userReadOnlyRepository.findByName("sabo");
        for (UserFieldsAge e: ages
             ) {
            System.out.println(e.getAge());
        }
    }

    // 查看事务, 可以在日志里搜 transaction
    // 开始事务：  Began transaction
    // 结束事务:  Committed transaction

    @Test
    @Rollback(value = false)
    public void testBatchUpdateAndInsert() {
        List<User> users = Lists.newArrayList();

        for (int i = 201; i < 210; ++i) {
            users.add(
                    User.builder().name("sabo" + i).age(i).build()
            );
        }

        List<User> savedUsers = userRepository.saveAll(users);


        List<User> usersAll = userRepository.findAll();
//        assertEquals(1, users.size());
        for (User u : usersAll) {
            u.setAge(700);
        }
    }

}