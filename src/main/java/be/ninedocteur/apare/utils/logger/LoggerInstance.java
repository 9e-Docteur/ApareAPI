package be.ninedocteur.apare.utils.logger;

public class LoggerInstance {
    private Logger logger;

    public Logger create(String name){
       if(logger == null){
           this.logger = new Logger(name);
       }
       return logger;
    }
}
