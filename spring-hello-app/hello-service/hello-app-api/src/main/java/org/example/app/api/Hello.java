package org.example.app.api;

import org.example.app.api.vo.i.HelloVoIn;
import org.example.app.api.vo.o.HelloVoOut;

public interface Hello {
    HelloVoOut say(HelloVoIn c);
}
