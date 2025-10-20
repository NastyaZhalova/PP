package org.example;

import java.io.*;
import java.util.jar.*;
import java.util.zip.Deflater;

public class Main {
    public static void packFolder(File folder, JarOutputStream jos, String basePath) throws IOException {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            String entryName = basePath + file.getName();
            if (file.isDirectory()) {
                packFolder(file, jos, entryName + "/");
            } else {
                JarEntry entry = new JarEntry(entryName);
                jos.putNextEntry(entry);

                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    jos.write(buffer, 0, len);
                }
                fis.close();
                jos.closeEntry();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Введите путь к папке, которую нужно архивировать:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String folderPath = reader.readLine();

            System.out.println("Введите имя выходного JAR-файла (с полным путём):");
            String jarFileName = reader.readLine();

            File folder = new File(folderPath);
            if (!folder.exists() || !folder.isDirectory()) {
                System.err.println("Ошибка: указанная папка не существует или не является директорией.");
                return;
            }

            JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFileName));
            jos.setLevel(Deflater.DEFAULT_COMPRESSION);

            packFolder(folder, jos, "");
            jos.close();

            System.out.println("Архивация завершена: " + jarFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
