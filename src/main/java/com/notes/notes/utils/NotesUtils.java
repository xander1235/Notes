package com.notes.notes.utils;

import com.notes.notes.constants.NotesConstants;
import com.notes.notes.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
public class NotesUtils {

    public static String getDateAsString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(NotesConstants.NOTES_DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String getDateWithTimeAsString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(NotesConstants.NOTES_DATE_FORMAT_WITH_TIME);
        return dateFormat.format(date);
    }

    public static String generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat(NotesConstants.NOTES_DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            log.error("Exception occurred while parsing the date: ", e);
            return null;
        }
    }


    public static <T> T checkIfNull(T t, String message) {
        if (t == null) {
            log.error(message);
            throw new BadRequestException(message);
        }
        return t;
    }

    public static <T> void checkIfNotNull(T t, String message) {
        if (t != null) {
            log.error(message);
            throw new BadRequestException(message);
        }
    }

    public static <T> void checkIfListNull(List<T> t, String message) {
        if (t == null || t.isEmpty()) {
            log.error(message);
            throw new BadRequestException(message);
        }
    }
}
