package org.lightPoke.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class LogManagement {
    private static LogManagement instance;
    File logFile = null;

    private LogManagement() {
        logFile = new File("./src/main/java/org/lightPoke/log", "log.txt");
        try {
            logFile.createNewFile();
        } catch (IOException e) {
            // Failed when creating new file
        }
    }

    public static LogManagement getInstance() {
        return instance == null ? instance = new LogManagement() : instance;
    }

    public void writeLog(final String message) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write("[" + localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear() + " | " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() + "] -- " + message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // File not found
        }
    }
}