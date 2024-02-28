package be.ninedocteur.apare.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApareAPIJVMArgs {
    private String[] args;
    private List<String> ARGUMENTS = new ArrayList<>(); //TO MADE IT EASY

    public ApareAPIJVMArgs(String[] args){
        this.args = args;
        this.ARGUMENTS.addAll(Arrays.asList(args));
    }
    public boolean containsArg(String arg){
        return this.ARGUMENTS.contains("--" + arg);
    }

    public List<String> getArgs() {
        return ARGUMENTS;
    }
}
