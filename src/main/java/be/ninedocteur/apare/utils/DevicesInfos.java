package be.ninedocteur.apare.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

public class DevicesInfos {
    private static MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private static MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
    private static MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();
    private static OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    public static int getOccupiedMemory() {
        return (int) (heapMemoryUsage.getUsed() / (1024 * 1024));
    }

    public static int getFreeMemory() {
        return (int) (heapMemoryUsage.getMax() / (1024 * 1024));
    }

    public static String getOSName() {
        return osBean.getName();
    }

    public static String getProcessorArch() {
        return osBean.getArch();
    }

    public static double getProcessorLoad() {
        return osBean.getSystemLoadAverage();
    }

    public static int getCPUCount() {
        return osBean.getAvailableProcessors();
    }
}
