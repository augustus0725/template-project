package com.sabo.service;

import com.sabo.apis.Hello;
import com.sabo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author crazy
 * date: 2020/8/15
 */
@Service
public class HelloService implements Hello {
    @Autowired
    private StudentRepository studentRepository;

    private void m0() {
        studentRepository.updateByIdWithFirstname(2L, "m0");
    }

    private void m1() {
        studentRepository.updateByIdWithFirstname(2L, "m1");
    }

    @Transactional(rollbackFor = Exception.class) // 只有public方法才有效，被spring自动实例化的public方法才有效
    public void m10(int num) {
        m1();
        if (num > 0) {
            throw new RuntimeException("xxxxxxxx");
        }
        m0();
    }
}
