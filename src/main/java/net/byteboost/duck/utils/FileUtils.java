package net.byteboost.duck.utils;

import java.io.File;

public class FileUtils {
    public static boolean checkPDF(File file) {
        String fileName = file.getName().toUpperCase();
        return fileName.endsWith(".PDF");
    }
    public static boolean checkDOCX(File file) {
        String fileName = file.getName().toUpperCase();
        return fileName.endsWith(".DOCX");
    }
    public static boolean checkTXT(File file) {
        String fileName = file.getName().toUpperCase();
        return fileName.endsWith(".TXT");
    }
}
