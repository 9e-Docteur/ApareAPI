package be.ninedocteur.apare.utils;

public class MessageUtils {
    public static String tempString;
    private String name;

    public MessageUtils(String name){
        this.name = name;
    }


    public void send(String message){
        tempString = "[" + name + "] " + message;
        System.out.println("[" + name + "] " + message + ColorUtils.RESET);
    }

    public void sendSuccess(String message){
        tempString = "[" + name + "] " + ColorUtils.GREEN + message;
        System.out.println("[" + name + "] " + ColorUtils.GREEN + message + ColorUtils.RESET);
    }

    public void sendError(String message){
        tempString = "[" + name + "] " + ColorUtils.RED_BOLD +message + ColorUtils.RESET;
        System.out.println("[" + name + "] " + ColorUtils.RED_BOLD + message + ColorUtils.RESET);
    }

    public void sendWarn(String message){
        tempString = "[" + name + "] " + ColorUtils.YELLOW_BOLD + message;
        System.out.println("[" + name + "] " + ColorUtils.YELLOW_BOLD + message + ColorUtils.RESET);
    }

    public static String getTempString() {
        return tempString;
    }
}
