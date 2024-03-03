package be.ninedocteur.apare.api.mod;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.events.ModLoadedEvent;
import be.ninedocteur.apare.utils.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {
    private static String userDir = System.getProperty("user.home");
    private static String commonFolder = userDir + "/ApareAPI/Mods/common/";
    private static boolean isLoaded;
    private static List<String> REQUIRED_FIELD = new ArrayList<>();
    public static HashMap<String, Boolean> MOD_LOADED = new HashMap<>();
    private static ModSide modSide;

    public ModLoader(ModSide modSide){
        if(!isLoaded){
            ApareAPI.getLogger().send("Starting mod loader...", Logger.Type.WARN);
            ModLoader.modSide = modSide;
            REQUIRED_FIELD.add("mod_name");
            REQUIRED_FIELD.add("mod_version");
            REQUIRED_FIELD.add("authors");
            REQUIRED_FIELD.add("credits");
            REQUIRED_FIELD.add("description");
        }
    }

    public void loadMods() {
        if(!isLoaded){
            ApareAPI.getLogger().send("Looking for mods...", Logger.Type.WARN);
            File folder = new File(commonFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));

            if (files == null) {
                System.out.println("No mods found");
                return;
            }

            for (File file : files) {
                try {
                    URL url = file.toURI().toURL();
                    URLClassLoader loader = new URLClassLoader(new URL[] { url });

                    ObjectMapper mapper = new ObjectMapper();

                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().equals("mods.json")) {
                            JsonNode modNode = mapper.readTree(jarFile.getInputStream(entry));
                            validateModJson(file.getName() , modNode);
                        }
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                            try {
                                Class<?> clazz = loader.loadClass(className);
                                if (clazz.isAnnotationPresent(Mod.class)) {
                                    Object instance = clazz.getAnnotation(Mod.class).value().newInstance();
                                    Mod modAnnonation = clazz.getAnnotation(Mod.class);
                                    if (instance instanceof ApareMod) {
                                        if(modAnnonation.modSide() == ModSide.BOTH || modAnnonation.modSide() == modSide){
                                            ApareAPI.getLogger().send("Finded mod: " + ((ApareMod) instance).getModName(), Logger.Type.WARN);
                                            ((ApareMod) instance).init();
                                            ModLoadedEvent modLoadedEvent = new ModLoadedEvent((ApareMod) instance);
                                            ApareAPI.getEventFactory().fireEvent(modLoadedEvent);
                                        } else {
                                            ApareAPI.getLogger().send("Cannot load mod: " + ((ApareMod) instance).getModName() + "\n--> This mod is for " + modAnnonation.modSide().name() + " only!", Logger.Type.ERROR);
                                        }
                                    }
                                }
                            } catch (ClassNotFoundException e) {
                                System.out.println("Failed to load class " + className + " from " + file.getName());
                            }
                        }
                    }

                    jarFile.close();
                    loader.close();

                } catch (IOException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                }
            }
            isLoaded = true;
        }
    }

    public void loadModsForModInstance(String modInstance) {
        ApareAPI.getLogger().send("Looking for mod for modInstance: " + modInstance, Logger.Type.WARN);
        String modInstanceFolder = userDir + "/ApareAPI/Mods/" + modInstance + "/";
        if (!MOD_LOADED.get(modInstance)) {
            File folder = new File(modInstanceFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));

            if (files == null) {
                System.out.println("No mods found");
                return;
            }

            for (File file : files) {
                try {
                    URL url = file.toURI().toURL();
                    URLClassLoader loader = new URLClassLoader(new URL[]{url});

                    ObjectMapper mapper = new ObjectMapper();

                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().equals("mods.json")) {
                            JsonNode modNode = mapper.readTree(jarFile.getInputStream(entry));
                            validateModJson(file.getName(), modNode);
                        }
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                            try {
                                Class<?> clazz = loader.loadClass(className);
                                if (clazz.isAnnotationPresent(Mod.class)) {
                                    Object instance = clazz.getAnnotation(Mod.class).value().newInstance();
                                    Mod modAnnonation = clazz.getAnnotation(Mod.class);
                                    if (instance instanceof ApareMod) {
                                        if(modAnnonation.modSide() == ModSide.BOTH || modAnnonation.modSide() == modSide){
                                            ((ApareMod) instance).init();
                                            ApareAPI.getLogger().send("Finded mod: " + ((ApareMod) instance).getModName(), Logger.Type.WARN);
                                        } else {
                                            ApareAPI.getLogger().send("Cannot load mod: " + ((ApareMod) instance).getModName() + "\n--> This mod is for " + modAnnonation.modSide().name() + " only!", Logger.Type.ERROR);
                                        }
                                    }
                                }
                            } catch (ClassNotFoundException e) {
                                System.out.println("Failed to load class " + className + " from " + file.getName());
                            }
                        }
                    }

                    jarFile.close();
                    loader.close();

                } catch (IOException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                } catch (InstantiationException | IllegalAccessException e) {
                    System.out.println("Failed to load mod from " + file.getName() + ": " + e.getMessage());
                }
            }
            isLoaded = true;
        }
    }


    public static File getOrCreateModDir() {
        File imagesDir = new File(commonFolder);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static File getOrCreateModDir(String modInstance) {
        String modInstanceFolder = userDir + "/ApareAPI/Mods/";
        File imagesDir = new File(modInstanceFolder + modInstance + "/");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static String getModFileDir() {
        return commonFolder;
    }

    public static void setModFileDir(String fileDir) {
        ModLoader.commonFolder = fileDir;
    }

    private static boolean validateModJson(String modName, JsonNode modNode) {
        for(String requiredString : REQUIRED_FIELD){
            if(modNode.findParent(requiredString) == null){
                throw new RuntimeException("Missing a value: " + requiredString + " in mods.json from mod " + modName);
            }
        }
        return true;
    }
}

