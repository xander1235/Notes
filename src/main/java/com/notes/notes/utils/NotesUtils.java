package com.notes.notes.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public class NotesUtils {

    public static <T> T checkIfNull(T t, String message) {
        if (t == null) {
            log.error(message);
            throw new RuntimeException(message);
        }
        return t;
    }

    public static <T> void checkIfNotNull(T t, String message) {
        if (t != null) {
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    public static <T> void checkIfListNull(List<T> t, String message) {
        if (t == null || t.isEmpty()) {
            log.error(message);
            throw new RuntimeException(message);
        }
    }
}
