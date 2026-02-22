package com.wbandara.enterprise.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Centralized logging utility wrapper around Log4j2.
 * Decouples framework code from direct Log4j2 API dependency.
 */
public final class LoggerUtils {

    private LoggerUtils() {
        throw new UnsupportedOperationException("LoggerUtils is a utility class and cannot be instantiated");
    }

    private static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static void info(Class<?> clazz, String message) {
        getLogger(clazz).info(message);
    }

    public static void debug(Class<?> clazz, String message) {
        getLogger(clazz).debug(message);
    }

    public static void warn(Class<?> clazz, String message) {
        getLogger(clazz).warn(message);
    }

    public static void error(Class<?> clazz, String message) {
        getLogger(clazz).error(message);
    }

    public static void error(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).error(message, throwable);
    }

    public static void trace(Class<?> clazz, String message) {
        getLogger(clazz).trace(message);
    }

    public static void fatal(Class<?> clazz, String message) {
        getLogger(clazz).fatal(message);
    }

    public static void fatal(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).fatal(message, throwable);
    }
}

