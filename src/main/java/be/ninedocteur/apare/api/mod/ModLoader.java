package be.ninedocteur.apare.api.mod;

import be.ninedocteur.apare.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {
    private static String fileDir;
    public ModLoader() {
        //ApareAPI.getLogger().send("Loading Apare Mods...", Logger.Type.NORMAL);
        loadMods();
    }

    public static void loadMods() {
        File folder = new File(fileDir);
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

                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                        try {
                            Class<?> clazz = loader.loadClass(className);
                            if (clazz.isAnnotationPresent(Mod.class)) {
                                Object instance = clazz.getAnnotation(Mod.class).value().newInstance();
                                if (instance instanceof ApareMod) {
                                    ((ApareMod) instance).loadMod();
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
    }


    public static File getOrCreateModDir() {
        File imagesDir = new File("C:\\ApareProject\\Mods\\");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static File getOrCreateModDir(String fileDir) {
        File imagesDir = new File(fileDir);
        setModFileDir(fileDir);
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        return imagesDir;
    }

    public static String getModFileDir() {
        return fileDir;
    }

    public static void setModFileDir(String fileDir) {
        ModLoader.fileDir = fileDir;
    }
}

