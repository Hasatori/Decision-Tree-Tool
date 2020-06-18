package com.github.hasatori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import org.slf4j.event.Level;

public final class ActionLibrary {
    private static final Logger log = LoggerFactory.getLogger(ActionLibrary.class);

    private ActionLibrary() {
    }

    public static IAction doNothing() {
        return () -> {
        };
    }

    public static IAction joinActions(IAction action1, IAction action2, IAction... actions) {
        return () -> {
            action1.execute();
            action2.execute();
            Arrays.stream(actions).forEach(IAction::execute);
        };
    }

    public static IAction log(String message, Level level) {
        return () -> {
            switch (level) {
                case DEBUG:
                    log.debug(message);
                    break;
                case ERROR:
                    log.error(message);
                    break;
                case WARN:
                    log.warn(message);
                    break;
                case INFO:
                    log.info(message);
                    break;
                case TRACE:
                    log.trace(message);
                    break;
            }
        };
    }
}
