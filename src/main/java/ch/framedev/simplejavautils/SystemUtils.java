package ch.framedev.simplejavautils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * / This Plugin was Created by FrameDev
 * / Package : ch.framedev.simplejavautils
 * / ClassName SystemUtils
 * / Date: 16.07.21
 * / Project: JavaUtils
 * / Copyrighted by FrameDev
 */

public class SystemUtils {

    public enum JavaVersion {
        VERSION_1_7,
        VERSION_1_8,
        VERSION_1_9,
        VERSION_10,
        VERSION_11,
        VERSION_12,
        VERSION_13,
        VERSION_14,
        VERSION_15,
        VERSION_16,
        VERSION_17,
        VERSION_18,
        VERSION_19,
        VERSION_20,
        VERSION_21,
        VERSION_22,
        VERSION_23,
        VERSION_24;
    }

    public static class JavaVersionMapper {
        private static final Map<String, JavaVersion> JAVA_VERSION_MAP = new HashMap<>();

        static {
            JAVA_VERSION_MAP.put("1.8", JavaVersion.VERSION_1_8);
            JAVA_VERSION_MAP.put("1.9", JavaVersion.VERSION_1_9);
            JAVA_VERSION_MAP.put("10", JavaVersion.VERSION_10);
            JAVA_VERSION_MAP.put("11", JavaVersion.VERSION_11);
            JAVA_VERSION_MAP.put("12", JavaVersion.VERSION_12);
            JAVA_VERSION_MAP.put("13", JavaVersion.VERSION_13);
            JAVA_VERSION_MAP.put("14", JavaVersion.VERSION_14);
            JAVA_VERSION_MAP.put("15", JavaVersion.VERSION_15);
            JAVA_VERSION_MAP.put("16", JavaVersion.VERSION_16);
            JAVA_VERSION_MAP.put("17", JavaVersion.VERSION_17);
            JAVA_VERSION_MAP.put("18", JavaVersion.VERSION_18);
            JAVA_VERSION_MAP.put("19", JavaVersion.VERSION_19);
            JAVA_VERSION_MAP.put("20", JavaVersion.VERSION_20);
            JAVA_VERSION_MAP.put("21", JavaVersion.VERSION_21);
            JAVA_VERSION_MAP.put("22", JavaVersion.VERSION_22);
            JAVA_VERSION_MAP.put("23", JavaVersion.VERSION_23);
            JAVA_VERSION_MAP.put("24", JavaVersion.VERSION_24);
        }

        public static JavaVersion getJavaVersion() {
            String version = System.getProperty("java.version");
            String majorVersion = extractMajorVersion(version);
            return JAVA_VERSION_MAP.getOrDefault(majorVersion, JavaVersion.VERSION_1_7);
        }

        private static String extractMajorVersion(String version) {
            // Split the version string by dots and return the first segment
            String[] parts = version.split("\\.");
            if (parts[0].equals("1")) { // Handle legacy versions like "1.8"
                return parts[1];
            }
            return parts[0]; // Modern versions like "23.0.3"
        }
    }


    public enum OSType {
        WINDOWS, MACOS, LINUX, OTHER;
    }

    /**
     * Return the OsType from the Operating System
     *
     * @return Return the OsType from the Operating System
     */
    public OSType getOSType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase();
        if (OS.contains("win")) {
            return OSType.WINDOWS;
        } else if ((OS.contains("mac")) || (OS.contains("darwin"))) {
            return OSType.MACOS;
        } else if (OS.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public Thread getActiveThread() {
        return Thread.currentThread();
    }

    public int getActiveThreadCount() {
        return Thread.activeCount();
    }

    public String getArchitecture() {
        return ManagementFactory.getOperatingSystemMXBean().getArch();
    }

    public int getCores() {
        return ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    }

    public static enum DiskSizeType {
        MB(1000 * 1000),
        GB(1000 * 1000 * 1000),
        TB(1000L * 1000 * 1000 * 1000),
        PB(1000L * 1000 * 1000 * 1000 * 1000);

        private final long size;

        DiskSizeType(long size) {
            this.size = size;
        }

        public long getSize() {
            return size;
        }
    }

    /**
     * This Method returns the LocalAddress
     *
     * @return return the LocalAddress
     */
    public String getLocalAddress() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            //hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * This Method returns the Local Hostname
     *
     * @return return the Local Hostname
     */
    public String getLocalHostName() {
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

    public String getDriveDescription(File file) {
        return FileSystemView.getFileSystemView().getSystemTypeDescription(file);
    }

    public boolean isDrive(File file) {
        return FileSystemView.getFileSystemView().isDrive(file);
    }

    public double getAllTotalDiskSpace() {
        long gb = DiskSizeType.GB.getSize();
        long totalSpace = 0;
        for (Path root : FileSystems.getDefault().getRootDirectories()) {

            try {
                if (isDrive(root.toFile())) {
                    FileStore store = Files.getFileStore(root);
                    totalSpace += store.getTotalSpace() / gb;
                } else if (getOSType() == OSType.MACOS || getOSType() == OSType.LINUX) {
                    FileStore store = Files.getFileStore(root);
                    totalSpace += store.getTotalSpace() / gb;
                }
            } catch (IOException e) {
                System.out.println("error querying space: " + e.toString());
            }
        }
        return totalSpace;
    }

    public double getTotalDiskSpace(File file) {
        long gb = DiskSizeType.GB.getSize();
        long totalSpace = 0;
        try {
            if (isDrive(file)) {
                FileStore store = Files.getFileStore(file.toPath());
                totalSpace = store.getTotalSpace() / gb;
            } else if (getOSType() == OSType.MACOS || getOSType() == OSType.LINUX) {
                FileStore store = Files.getFileStore(file.toPath());
                totalSpace = store.getTotalSpace() / gb;
            }
        } catch (IOException e) {
            System.out.println("error querying space: " + e.toString());
        }
        return totalSpace;
    }

    public double getUsedDiskSpace(File file) {
        long gb = DiskSizeType.GB.getSize();
        long usedSpace = 0;
        long totalSpace = 0;
        try {
            if (isDrive(file)) {
                FileStore store = Files.getFileStore(file.toPath());
                totalSpace = store.getTotalSpace() / gb;
                usedSpace = totalSpace - (store.getUsableSpace() / gb);
            } else if (getOSType() == OSType.MACOS || getOSType() == OSType.LINUX) {
                FileStore store = Files.getFileStore(file.toPath());
                totalSpace = store.getTotalSpace() / gb;
                usedSpace = totalSpace - (store.getUsableSpace() / gb);
            }
        } catch (IOException e) {
            System.out.println("error querying space: " + e.toString());
        }
        return usedSpace;
    }

    public double getAllUsedDiskSpace() {
        long gb = DiskSizeType.GB.getSize();
        long usedSpace = 0;
        long totalSpace = 0;
        for (Path root : FileSystems.getDefault().getRootDirectories()) {
            try {
                if (isDrive(root.toFile())) {
                    FileStore store = Files.getFileStore(root);
                    totalSpace += store.getTotalSpace() / gb;
                    usedSpace += totalSpace - (store.getUsableSpace() / gb);
                } else if (getOSType() == OSType.MACOS || getOSType() == OSType.LINUX) {
                    FileStore store = Files.getFileStore(root);
                    totalSpace += store.getTotalSpace() / gb;
                    usedSpace += totalSpace - (store.getUsableSpace() / gb);
                }
            } catch (IOException e) {
                System.out.println("error querying space: " + e.toString());
            }
        }
        return usedSpace;
    }

    public double getAllFreeDiskSpace() {
        double used = getAllUsedDiskSpace();
        double total = getAllTotalDiskSpace();
        return total - used;
    }

    public double getFreeDiskSpace(File file) {
        double used = getUsedDiskSpace(file);
        double total = getTotalDiskSpace(file);
        return total - used;
    }
}
