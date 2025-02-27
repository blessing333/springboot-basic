package com.blessing333.springbasic.console;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("console")
public interface RunnableController {
    void startService();
}
