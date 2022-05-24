package org.example.app.api.vo.o;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class HelloVoOut {
    private String content;
}
