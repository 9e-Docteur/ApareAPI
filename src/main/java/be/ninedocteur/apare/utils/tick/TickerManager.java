package be.ninedocteur.apare.utils.tick;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static be.ninedocteur.apare.ApareAPI.CLASSES_TO_TICK;

public class TickerManager {
    private static boolean isStarted;
    public static void start() {
            List<String> packages = getPackages();


            for (String packageName : packages) {
                List<Class<?>> classes = getClasses(packageName);

                for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(Ticker.class) && ITicker.class.isAssignableFrom(clazz)) {
                        try {
                            ITicker ticker = (ITicker) clazz.getDeclaredConstructor().newInstance();
                            CLASSES_TO_TICK.add(ticker);
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                                 InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    private static List<String> getPackages() {
        List<String> packages = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String classpathEntry : classpathEntries) {
            File entry = new File(classpathEntry);
            if (entry.isDirectory()) {
                exploreDirectory(entry, "", packages);
            }
        }
        return packages;
    }

    private static void exploreDirectory(File directory, String parentPackage, List<String> packages) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String packageName = parentPackage.isEmpty() ? file.getName() : parentPackage + "." + file.getName();
                    packages.add(packageName);
                    exploreDirectory(file, packageName, packages);
                }
            }
        }
    }

    private static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    String[] files = file.list();
                    for (String fileName : files) {
                        if (fileName.endsWith(".class")) {
                            String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
