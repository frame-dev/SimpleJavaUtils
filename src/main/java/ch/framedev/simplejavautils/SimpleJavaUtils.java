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

@SuppressWarnings("unused")
public class SimpleJavaUtils {

    public static Logger logger = Logger.getLogger("SimpleJavaUtils");

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
            logger.log(Level.SEVERE, "Error while encoding Object to Base64", e);
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
            logger.log(Level.SEVERE, "Error while decoding Object from Base64", e);
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
            logger.log(Level.SEVERE, "Error while saving Object to Base64 File", e);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            try {
                object = objectFromBase64(reader.readLine());
            } catch (Exception ignored) {

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while closing Reader", e);
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
     *
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
     * @return return the Rounded Value of the giving Value to the Places
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
            if (file.getParentFile() != null && !file.getParentFile().exists()) if(!file.getParentFile().mkdirs()) {
                throw new IllegalStateException("Could not create directory : " + file.getParentFile().getAbsolutePath());
            }
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
            logger.log(Level.SEVERE, "Error while Downloading File", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                logger.log(Level.SEVERE, "Error while closing InputStream", e);
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException e) {
                logger.log(Level.SEVERE, "Error while closing FileOutputStream", e);
            }
        }
        if (new File(newLocation, fileNameWithExtensions).getParentFile() != null && !new File(newLocation, fileNameWithExtensions).getParentFile().exists())
            if(!new File(newLocation, fileNameWithExtensions).getParentFile().mkdirs())
                throw new IllegalStateException("Could not create directory : " + new File(newLocation, fileNameWithExtensions).getParentFile().getAbsolutePath());
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
            if (file.getParentFile() != null && !file.getParentFile().exists()) if(!file.getParentFile().mkdirs())
                throw new IllegalStateException("Could not create directory : " + file.getParentFile().getAbsolutePath());
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
            logger.log(Level.SEVERE, "Error while Downloading File", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                logger.log(Level.SEVERE, "Error while closing InputStream", e);
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException e) {
                logger.log(Level.SEVERE, "Error while closing FileOutputStream", e);
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
        String tempDir;
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
        String userDir;
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
            logger.log(Level.SEVERE, "Error while creating Temp File", e);
            return null;
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while closing FileOutputStream", e);
            }
        }
    }

    /**
     * Get a File from the Resource Folder
     *
     * @param file the File to get from the Resource Folder
     * @return return the File from the Resource Folder
     */
    public File getFromResourceFile(String file) {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(file);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            return streamToFile(resource);
        }
    }

    /**
     * Get a File from the Resource Folder
     *
     * @param file    the File to get from the Resource Folder
     * @param class_  the Class where the Resource is located
     * @return return the File from the Resource Folder
     */
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
     * @param fileName the FileName to check if it is existing or not
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

        zipFile(directory, directory.getName(), zipOut);
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

    /**
     * Zips a file or directory into a ZipOutputStream.
     * @param fileToZip the file or directory to zip
     * @param fileName the name of the file in the zip archive
     * @param zipOut the ZipOutputStream to write the zip entry to
     * @throws IOException if an I/O error occurs while zipping the file
     */
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
            if (children == null) throw new IOException("Error while zipping File : " + fileToZip.getName());
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

    /**
     * Get the Logger for SimpleJavaUtils
     *
     * @return return the Logger for SimpleJavaUtils
     */
    protected Logger getLogger() {
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

    /**
     * Encodes a file to a Base64 binary array.
     *
     * @param file the file to encode
     * @return the Base64 encoded byte array of the file, or null if an error occurs
     */
    public byte[] encodeFileToBase64BinaryArray(File file) {
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            //noinspection ResultOfMethodCallIgnored
            fileInputStreamReader.read(bytes);
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while encoding File to Base64", e);
            return null;
        }
    }

    /**
     * Decodes a Base64 binary file and saves it to the specified location with the given file name and extension.
     *
     * @param decodedFile the Base64 encoded byte array of the file
     * @param extension   the file extension (e.g., "txt", "jpg"), can be null
     * @param fileName    the name of the file to be created
     * @param location    the directory where the file will be saved
     * @return the newly created file from the decoded Base64 data
     */
    public File decodeFileFromBase64Binary(byte[] decodedFile, String extension, String fileName, String location) {

        // Construct the file path
        File newFileName = null;
        try {
            if (extension == null) {
                newFileName = new File(location, fileName);
            } else {
                newFileName = File.createTempFile(location, fileName + "." + extension);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error while creating new File", ex);
        }

        // Write the decoded file to disk
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(decodedFile);
            FileOutputStream fos;
            if (newFileName != null) {
                fos = new FileOutputStream(newFileName);
                fos.write(decodedBytes);
                fos.close();
                System.out.println("Decoding completed. File saved as " + newFileName);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while decoding File from Base64", e);
        }

        // Return the newly created file
        return newFileName;
    }

    /**
     * Decodes a Base64 binary file and saves it as a temporary file with the specified extension.
     *
     * @param decodedFile the Base64 encoded byte array of the file
     * @param extension   the file extension (e.g., "txt", "jpg"), can be null
     * @return the temporary file created from the decoded Base64 data
     */
    public File decodeFileFromBase64BinaryTmp(byte[] decodedFile, String extension) {
        File file;
        try {
            if (extension != null) {
                file = File.createTempFile("simplejavautils", "." + extension);
            } else
                file = File.createTempFile("simplejavautils", "");
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(decodedFile);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(decodedBytes);
                fos.close();
                System.out.println("Decoding completed. File saved as " + file.toPath());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while decoding File from Base64", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Return the newly created file
        file.deleteOnExit();
        return file;
    }

    /**
     * Capitalizes the first letter of the given string.
     *
     * @param str the string to capitalize
     * @return the string with the first letter capitalized, or the original string if it is null or empty
     */
    @Deprecated
    public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Capitalizes the first letter of the given string.
     *
     * @param str the string to capitalize
     * @return the string with the first letter capitalized, or the original string if it is null or empty
     */
    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        if(str.contains(" ")) {
            String[] parts = str.split(" ");
            StringBuilder capitalizedString = new StringBuilder();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    capitalizedString.append(part.substring(0, 1).toUpperCase())
                            .append(part.substring(1)).append(" ");
                } else {
                    capitalizedString.append(" ");
                }
            }
            return capitalizedString.toString();
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Uncapitalizes the first letter of the given string.
     *
     * @param str the string to uncapitalize
     * @return the string with the first letter uncapitalized, or the original string if it is null or empty
     */
    public String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * Converts a string to camel case, where the first letter of the first word is lowercase and the first letter of each subsequent word is capitalized.
     *
     * @param str the string to convert
     * @return the string in camel case, or the original string if it is null or empty
     */
    public String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] parts = str.split(" ");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts) {
            camelCaseString.append(capitalize(part));
        }
        return camelCaseString.toString();
    }

    /**
     * Converts a string to Pascal case, where the first letter of each word is capitalized and no spaces are present.
     *
     * @param str the string to convert
     * @return the string in Pascal case, or the original string if it is null or empty
     */
    public String toPascalCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] parts = str.split(" ");
        StringBuilder pascalCaseString = new StringBuilder();
        for (String part : parts) {
            pascalCaseString.append(capitalize(part));
        }
        return pascalCaseString.toString();
    }

    /**
     * Converts a string to kebab case, where words are separated by hyphens and all letters are lowercase.
     *
     * @param str the string to convert
     * @return the string in kebab case, or the original string if it is null or empty
     */
    public String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Converts a string to kebab case, where words are separated by hyphens and all letters are lowercase.
     *
     * @param str the string to convert
     * @return the string in kebab case, or the original string if it is null or empty
     */
    public String toKebabCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    /**
     * Converts a string to title case, where the first letter of each word is capitalized.
     *
     * @param str the string to convert
     * @return the string in title case, or the original string if it is null or empty
     */
    public String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] parts = str.split(" ");
        StringBuilder titleCaseString = new StringBuilder();
        for (String part : parts) {
            titleCaseString.append(capitalize(part)).append(" ");
        }
        return titleCaseString.toString().trim();
    }
}
