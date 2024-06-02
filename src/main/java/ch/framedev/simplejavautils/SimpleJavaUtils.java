package ch.framedev.simplejavautils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SimpleJavaUtils {

    /**
     * Create a String of a Base64 (encode)
     *
     * @param object the Object to encode to Base64
     * @return returns the encoded Base64 Byte Array
     */
    public <T extends Serializable> String objectToBase64(T object) {
        try {
            ByteArrayOutputStream is = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(is);
            os.writeObject(object);
            os.flush();
            os.close();
            return Base64.getEncoder().encodeToString(is.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decode a Base64 String
     *
     * @param base the encoded Base64
     * @return returns the decoded Object
     */
    @SuppressWarnings("unchecked")
    public <T> T objectFromBase64(String base) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(base));
            ObjectInputStream os = new ObjectInputStream(is);
            return (T) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save an object to a Base64 File
     *
     * @param file   the selected File for Save
     * @param object the Object to Save
     * @param <T>    need to extend Serializable
     */
    public <T extends Serializable> void saveObjectToBase64File(File file, T object) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(objectToBase64(object));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return an Object as (T) from a File
     *
     * @param file the Selected File where the Base64 File is stored
     * @param <T>  the Object
     * @return return an Object from a Base64 String File
     */
    public <T> T getObjectFromBase64File(File file) {
        T object = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            object = objectFromBase64(reader.readLine());
        } catch (Exception ignored) {

        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * Debug an Object
     *
     * @param object the Object for debugging
     */
    public void debug(Object object) {
        System.out.println(object);
    }

    /**
     * Print an Error with the Object
     * (System.err.println())
     * @param object the selected Object for printing an error
     */
    public void error(Object object) {
        System.err.println(object);
    }

    /**
     * @param name the Name of the new Logger
     * @return returns the new Created Logger
     */
    public Logger createLogger(String name) {
        return Logger.getLogger(name);
    }

    /**
     * @param text  the Text for Splitting
     * @param regex the char for splitting
     * @return return an Array of split Strings
     */
    public String[] stringSplitter(String text, @NotNull String regex) {
        if (text == null) return null;
        if (!text.contains(regex)) return null;
        return text.split(regex);
    }

    /**
     * This Method Creates a Logger from scratch
     *
     * @param name       the Name of the new Logger
     * @param timeFormat if you would like an TimeStamp in logger
     * @return return the new Logger
     */
    public Logger createEmptyLogger(String name, boolean timeFormat) {
        return new MyFormatter(timeFormat).createEmptyLogger(name);
    }

    /**
     * Round a double value to the giving Places
     *
     * @param value  The Value to Round
     * @param places the Places where the Comma will bee
     * @return return the Rounden Value of the giving Value to the Places
     */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Download a File from an Url and copying it to a new Folder
     *
     * @param fileUrl                the Download Url
     * @param location               the Location where the Downloaded file will be added
     * @param fileNameWithExtensions the FileName with the extension
     * @param newLocation            the new Location where the File will be
     */
    public void download(String fileUrl, String location, String fileNameWithExtensions, String newLocation) {
        File file;
        if (location != null) {
            file = new File(location, fileNameWithExtensions);
            if (file.getParentFile() != null && !file.getParentFile().exists()) file.getParentFile().mkdirs();
        } else {
            file = new File(fileNameWithExtensions);
        }
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            URL url = new URL(fileUrl);
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(new File(location, fileNameWithExtensions));
            final byte[] data = new byte[4096];
            int count;
            while ((count = in.read(data, 0, 4096)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        if (new File(newLocation, fileNameWithExtensions).getParentFile() != null && !new File(newLocation, fileNameWithExtensions).getParentFile().exists())
            new File(newLocation, fileNameWithExtensions).getParentFile().mkdirs();
        if (!file.renameTo(new File(newLocation, fileNameWithExtensions))) {
            Logger.getLogger("SimpleJavaUtils").log(Level.SEVERE, "File cannot be Renamed or Moved!");
        }
    }

    /**
     * Download a File from an Url
     *
     * @param fileUrl                Download Url for File to Download
     * @param location               the new Location of the Download
     * @param fileNameWithExtensions the FileName with the new extension
     */
    public void download(String fileUrl, String location, String fileNameWithExtensions) {
        File file;
        if (location != null) {
            file = new File(location, fileNameWithExtensions);
            if (file.getParentFile() != null && !file.getParentFile().exists()) file.getParentFile().mkdirs();
        } else {
            file = new File(fileNameWithExtensions);
        }
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            URL url = new URL(fileUrl);
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(file);
            final byte[] data = new byte[4096];
            int count;
            while ((count = in.read(data, 0, 4096)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get The Temp Directory
     *
     * @return return the Temp Directory
     */
    public String getTempDir() {
        String os = System.getProperty("os.name").toLowerCase();
        String tempDir = "";
        if (os.contains("mac")) {
            tempDir = System.getProperty("java.io.tmpdir") + "/";
        } else if (os.contains("windows")) {
            tempDir = System.getProperty("java.io.tmpdir") + "/";
        } else {
            tempDir = System.getProperty("java.io.tmpdir") + "/";
        }
        return tempDir;
    }

    /**
     * Get The User Home Directory
     *
     * @return return the User Home Directory
     */
    public String getUserHome() {
        String os = System.getProperty("os.name").toLowerCase();
        String userDir = "";
        if (os.contains("mac")) {
            userDir = System.getProperty("user.home");
        } else if (os.contains("windows")) {
            userDir = System.getProperty("user.home") + "/";
        } else {
            userDir = System.getProperty("user.home") + "/";
        }
        return userDir;
    }

    /**
     * Get the User's Directory
     *
     * @return return the User Directory
     */
    public String getUserDir() {
        String os = System.getProperty("os.name").toLowerCase();
        String userDir;
        if (os.contains("mac")) {
            userDir = System.getProperty("user.dir") + "/";
        } else if (os.contains("windows")) {
            userDir = System.getProperty("user.dir") + "/";
        } else {
            userDir = System.getProperty("user.dir") + "/";
        }
        return userDir;
    }

    /**
     * Get the user Applications Store Folder
     *
     * @return return the Applications Store Folder
     */
    public String getUserAppData() {
        String os = getOs();
        String userData;
        if (os.contains("mac")) {
            userData = System.getProperty("user.home") + "/Library/Application Support/";
        } else if (os.contains("windows")) {
            userData = System.getenv("APPDATA") + "/";
        } else {
            userData = System.getProperty("user.home") + "/";
        }
        return userData;
    }

    /**
     * Return the Operating System Name
     *
     * @return return the Os Name
     **/
    public String getOs() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * Return Os's Version
     *
     * @return return the Os Version
     */
    public String getOsVersion() {
        return System.getProperty("os.version").toLowerCase();
    }

    /**
     * @param in InputStream
     * @return the File from the InputStream
     */
    protected File streamToFile(InputStream in) {
        if (in == null) {
            return null;
        }
        FileOutputStream out = null;
        try {
            // Create a Temp File
            File f = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            f.deleteOnExit();

            out = new FileOutputStream(f);
            byte[] buffer = new byte[1024];

            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            // Return the Temp File
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFromResourceFile(String file) {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(file);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            return streamToFile(resource);
        }
    }

    public File getFromResourceFile(String file, Class<?> class_) {
        InputStream resource = class_.getClassLoader().getResourceAsStream(file);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            return streamToFile(resource);
        }
    }

    /**
     * Check if a File is existing
     *
     * @param fileName the FileName to check if it is exists or not
     * @return return a boolean if exists or not
     */
    public boolean existsFile(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * Check if the selected File is existing
     *
     * @param file the Selected File
     * @return return if exists or not
     */
    public boolean existsFile(File file) {
        if (file == null) return false;
        return file.exists();
    }

    /**
     * Copy a File to a new Location
     *
     * @param source the Selected File to Copy
     * @param target the Selected File where the File will be copied
     * @throws IOException throw an IOException if an error occurred
     */
    public void copyFileTo(File source, File target) throws IOException {
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Move a File to the new Location
     *
     * @param source the File to Move
     * @param target the File where the File will be moved
     * @throws IOException throw an IOException if an error occurred
     */
    public void moveFileTo(File source, File target) throws IOException {
        Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Zipping a Complete Directory
     *
     * @param directory the Selected Directory for zipping
     * @return return the Zipped Directory as .zip
     * @throws IOException throw an IOException if an Error occurred
     */
    public File zipDirectory(File directory, File outPut) throws IOException {
        FileOutputStream fos = new FileOutputStream(outPut);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = directory;

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        return outPut;
    }

    /**
     * Create a Zip Archive where the Files contains
     *
     * @param zipFile the Zip file where all Files contains
     * @param files   the Selected Files for Zipping
     * @return return the Zipped File
     * @throws IOException throw when unsuccessful
     */
    public File zipFiles(File zipFile, File... files) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (File srcFile : files) {
            FileInputStream fis = new FileInputStream(srcFile);
            ZipEntry zipEntry = new ZipEntry(srcFile.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();

        return zipFile;
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    /**
     * Check if Server with Port is Online or can connect
     *
     * @param server  Server Ip or HostName
     * @param port    port as example for MySQL 3306
     * @param timeout how long is it trying to connect
     * @return return if is Online or not
     */
    public static boolean isOnline(String server, int port, int timeout) {
        boolean b = true;
        try {
            InetSocketAddress sa = new InetSocketAddress(server, port);
            Socket ss = new Socket();
            ss.connect(sa, timeout);
            ss.close();
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     * Check if Server with Port is Online or can Connect
     * Timeout default is 2500 Milliseconds
     *
     * @param server Server Ip or HostName
     * @param port   port as example for MySQL 3306
     * @return return if is Online or not
     */
    public boolean isOnline(String server, int port) {
        return isOnline(server, port, 2500);
    }

    public Logger getLogger() {
        return createEmptyLogger("SimpleJavaUtils", true);
    }

    /**
     * Retrieves the file path of the application based on the operating system.
     * If the operating system is Windows, the method returns the file path with a backslash as the separator.
     * For other operating systems, the method returns the file path with a forward slash as the separator.
     * If an error occurs while getting the file path, a runtime exception is thrown with the original exception as the cause.
     *
     * @return the file path of the application
     */
    public String getFilePath() {
        try {
            if (new SystemUtils().getOSType() == SystemUtils.OSType.WINDOWS) {
                return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + "\\";
            } else {
                return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + "/";
            }
        } catch (URISyntaxException e) {
            getLogger().log(Level.SEVERE, "Error while getting File Path", e);
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves the file path of the application based on the operating system.
     * If the operating system is Windows, the method returns the file path with a backslash as the separator.
     * For other operating systems, the method returns the file path with a forward slash as the separator.
     * If an error occurs while getting the file path, a runtime exception is thrown with the original exception as the cause.
     *
     * @return the file path of the application
     */
    public String getFilePath(Class<?> clazz) {
        try {
            if (new SystemUtils().getOSType() == SystemUtils.OSType.WINDOWS) {
                return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + "\\";
            } else {
                return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + "/";
            }
        } catch (URISyntaxException e) {
            getLogger().log(Level.SEVERE, "Error while getting File Path", e);
            throw new RuntimeException(e);
        }
    }

}
