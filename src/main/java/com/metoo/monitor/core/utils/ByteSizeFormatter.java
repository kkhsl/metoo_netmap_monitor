package com.metoo.monitor.core.utils;

public class ByteSizeFormatter {

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};

    public static String formatBytes(long bytes) {
        if (bytes <= 0) {
            return "0 B";
        }

        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), UNITS[digitGroups]);
    }

    public static void main(String[] args) {
        long fileSizeInBytes = 123456789;
        String formattedSize = formatBytes(fileSizeInBytes);
        System.out.println("File size: " + formattedSize);  // 输出示例: File size: 117.7 MB
    }

}
