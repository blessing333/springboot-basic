package com.blessing333.springbasic.console;

import com.blessing333.springbasic.console.ui.UserInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("console")
@RequiredArgsConstructor
@Slf4j
public class App implements ApplicationRunner {
    private static final String PROGRAM_EXIT = "exit";
    private final UserInterface userInterface = new MainAppInterface();
    private final ControllerMapper controllerMapper;

    @Override
    public void run(ApplicationArguments args) {
        while (true) {
            userInterface.printGuide();
            String command = userInterface.requestMessage();
            if (PROGRAM_EXIT.equals(command)) break;
            else doService(command);
        }
    }

    private void doService(String command) {
        try {
            ServiceStrategy strategy = ServiceStrategy.fromString(command);
            RunnableController controller = controllerMapper.getRunnableController(strategy);
            controller.startService();
        } catch (NotSupportedStrategyException e) {
            log.error(e.getMessage(), e);
            userInterface.printMessage(e.getMessage());
        }
    }

}
