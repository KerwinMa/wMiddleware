package com.witbooking.middleware.utils;

import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.OTA_HotelResNotifRQ;
import org.apache.commons.codec.CharEncoding;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public final class FileUtils {

    public static final boolean NEW = false, EXIST = true;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileUtils.class);

    public static void writeStringToFile(String item, String dir, boolean append) throws IOException {
        final File file = new File(dir);
        if (!file.isFile()) {
            file.createNewFile();
        }
        logger.debug("STORE IN FILE '" + file.getAbsolutePath() + "' " + item);
        FileWriter fstream = null;
        ;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(dir, append);
            out = new BufferedWriter(fstream);
            out.write(item);
        } finally {
            if (out != null)
                out.close();
            if (fstream != null)
                fstream.close();
        }
    }

    public static void writeToFile(OTA_HotelResNotifRQ item, String dir, boolean append, boolean formattedOutput) throws IOException, JAXBException {
        FileUtils.writeStringToFile(XMLUtils.marshalFromObject(item, formattedOutput), dir, append);
    }

    public static String readFile(String path)
            throws IOException {
        return new Scanner(new File(path), CharEncoding.UTF_8).useDelimiter("\\A").next();
    }

    //    public static List<String> listFilesInFolder(File folder) {
    public static List<String> listFilesInFolder(final String fileDir) {
        final File folder = new File(fileDir);
        List<String> names = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesInFolder(fileEntry.getName());
            } else {
//                System.out.println(fileEntry.getName());
                names.add(fileEntry.getName());
            }
        }
        return names;
    }

    public static List<String> listFilesWithPathInFolder(final String fileDir) {
        final File folder = new File(fileDir);
        List<String> names = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesInFolder(fileEntry.getName());
            } else {
//                System.out.println(fileEntry.getName());
                names.add(fileDir + fileEntry.getName());
            }
        }
        return names;
    }

    public static String getNewRandomFile(String fileDir, final String fileName) {
        if (fileDir == null) {
            fileDir = Constants.DIR_FOLDER_RANDOMLY_GENERATED_FILES;
        }
        final int LIMIT = 9999;
        Random randomGenerator = new Random();
        File file = new File(fileDir + randomGenerator.nextInt(LIMIT) + fileName);
        while (file.isFile()) {
            file = new File(fileDir + randomGenerator.nextInt(LIMIT) + fileName);
        }
        return file.getAbsolutePath();
    }

    public static void close(Closeable item) throws IOException {
        if (item != null) {
            item.close();
        }
    }
}
