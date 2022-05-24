package org.example.app.v1;

import org.example.app.api.Hello;
import org.example.app.api.vo.i.HelloVoIn;
import org.example.app.api.vo.o.HelloVoOut;
import org.springframework.stereotype.Service;

@Service
public class HelloV1 implements Hello {
    @Override
    public HelloVoOut say(HelloVoIn c) {
        return HelloVoOut.builder().content(c.getContent()).build();
    }
}
