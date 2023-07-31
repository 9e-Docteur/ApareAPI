package be.ninedocteur.apare.utils;

public class MessageUtils {
    public static String tempString;


    public static void send(String message){
        tempString = "[ApareProject] " + message;
        System.out.println("[ApareProject] " + message + ColorUtils.RESET);
    }

    public static void sendSuccess(String message){
        tempString = "[ApareProject] " + ColorUtils.GREEN + message;
        System.out.println("[ApareProject] " + ColorUtils.GREEN + message + ColorUtils.RESET);
    }

    public static void sendError(String message){
        tempString = "[ApareProject] " + ColorUtils.RED_BOLD +message + ColorUtils.RESET;
        System.out.println("[ApareProject] " + ColorUtils.RED_BOLD + message + ColorUtils.RESET);
    }

    public static void sendWarn(String message){
        tempString = "[ApareProject] " + ColorUtils.YELLOW_BOLD + message;
        System.out.println("[ApareProject] " + ColorUtils.YELLOW_BOLD + message + ColorUtils.RESET);
    }

    public static String getTempString() {
        return tempString;
    }
}
