package com.learn.SidClasses.Customs_Constants;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomMesssage {
    private String Message;
    private boolean Success;
}
