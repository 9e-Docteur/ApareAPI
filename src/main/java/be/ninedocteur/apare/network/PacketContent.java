package be.ninedocteur.apare.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PacketContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Object> SAVED_CONTENT = new ArrayList<>();

    public void saveInt(int integer){
        SAVED_CONTENT.add(integer);
    }

    public void saveString(String string){
        SAVED_CONTENT.add(string);
    }

    public void saveList(List<Object> objectList){ SAVED_CONTENT.add(objectList); }

    public int getInt(){
        int integer = 0;
        for(Object object : SAVED_CONTENT){
            if(object instanceof Integer){
                integer = ((Integer) object).intValue();
                SAVED_CONTENT.remove(object);
                break;
            }
        }
        return integer;
    }

    public String getString(){
        String string = null;
        for(Object object : SAVED_CONTENT){
            if(object instanceof String){
                string = (String) object;
                SAVED_CONTENT.remove(object);
                break;
            }
        }
        return string;
    }

    public List<Object> getList(){
        List<Object> list = null;
        for(Object object : SAVED_CONTENT){
            if(object instanceof List){
                list = (List<Object>) object;
                SAVED_CONTENT.remove(object);
                break;
            }
        }
        return list;
    }

    public void saveFloat(float floatValue) {
        SAVED_CONTENT.add(floatValue);
    }

    public void saveDouble(double doubleValue) {
        SAVED_CONTENT.add(doubleValue);
    }

    public float getFloat() {
        float floatValue = 0.0f;
        for (Object object : SAVED_CONTENT) {
            if (object instanceof Float) {
                floatValue = ((Float) object).floatValue();
                SAVED_CONTENT.remove(object);
                break;
            }
        }
        return floatValue;
    }

    public double getDouble() {
        double doubleValue = 0.0;
        for (Object object : SAVED_CONTENT) {
            if (object instanceof Double) {
                doubleValue = ((Double) object).doubleValue();
                SAVED_CONTENT.remove(object);
                break;
            }
        }
        return doubleValue;
    }


    public List<Object> getContent() {
        return SAVED_CONTENT;
    }
}
