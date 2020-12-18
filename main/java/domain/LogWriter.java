package domain;

import domain.CustomExceptions;

import java.io.*;
import java.util.Date;

public class LogWriter {

    public void addLogMessageToFile(String message) {

        PrintWriter writer = null;
        String fileName = "log.txt";
        Date timeStamp = new Date();

        try {
            writer = new PrintWriter(new BufferedWriter((new FileWriter(fileName, true))));
            writer.println(timeStamp);
            writer.println(message);
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
