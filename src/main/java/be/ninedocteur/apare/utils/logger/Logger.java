package be.ninedocteur.apare.utils.logger;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.utils.MessageUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {
    private static String tempString;
    private static String message;
    private String name;
    static Date date = new Date();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE_d_MMMM_yyyy___HH-mm-ss ", Locale.getDefault());
    private static String logFile = simpleDateFormat.format(date);
    private MessageUtils msg;

    public Logger(String name){
        this.name = name;
        msg = new MessageUtils(name);
    }

    public void send(String message, Type type) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("[EEEE d MMMM yyyy | HH:mm:ss] ", Locale.getDefault());
        String dateFormatted = sdf.format(date);


        /* NOT WORKING
        switch (type){
            case NORMAL:
                MessageUtils.send("("+ date.getHours() + ":" + date.getMinutes() +") " + message);
            case WARN:
                MessageUtils.sendWarn("("+ date.getHours() + ":" + date.getMinutes() +") " + message);
            case ERROR:
                MessageUtils.sendError("("+ date.getHours() + ":" + date.getMinutes() +") " + message);
            case SUCCESS:
                MessageUtils.sendSuccess("("+ date.getHours() + ":" + date.getMinutes() +") " + message);
        }
         */

        if (type == Type.NORMAL && message != tempString) {
            msg.send(dateFormatted + message);
            tempString = dateFormatted + message;
            Logger.message = tempString;
            addLineToLog(tempString);
        } else if (type == Type.WARN && message != tempString) {
            msg.sendWarn(dateFormatted + message);
            tempString = dateFormatted + message;
            Logger.message = tempString;
            addLineToLog(tempString);
        } else if (type == Type.ERROR && message != tempString) {
            msg.sendError(dateFormatted + message);
            tempString = dateFormatted + message;
            Logger.message = tempString;
            addLineToLog(tempString);
        } else {
            msg.sendSuccess(dateFormatted + message);
            tempString = dateFormatted + message;
            Logger.message = tempString;
            addLineToLog(tempString);
        }
    }

    public String getMessage() {
        return message;
    }

    public static File getOrCreateModDir() {
        String userDir = System.getProperty("user.home");
        String modInstanceFolder = userDir + "/ApareAPI/Logs/";
        File imagesDir = new File(modInstanceFolder);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static File getOrCreateLogsFile() {
        getOrCreateModDir();
        String userDir = System.getProperty("user.home");
        String modInstanceFolder = userDir + "/ApareAPI/Logs/";
        File imagesDir = new File(modInstanceFolder + logFile + ".txt");
        if (!imagesDir.exists()) {
            try{
                imagesDir.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imagesDir;
    }

    public static void addLineToLog(String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getOrCreateLogsFile(), true));
            writer.write(line);
            writer.newLine();
            writer.close();;
        } catch (IOException e) {
            ApareAPI.getLogger().send("Error when adding line in log : " + e.getMessage(), Type.ERROR);
        }
    }

    public enum Type {
        NORMAL,
        SUCCESS,
        WARN,
        ERROR
    }
}
