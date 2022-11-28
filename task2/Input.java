package task2;


import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileFilter;

public class Input {
    private static ArrayList<String> directories = new ArrayList<String>();
    private static ArrayList<String> filenames = new ArrayList<String>();
    private static ArrayList<String> names1 = new ArrayList<String>();
    private static ArrayList<String> names2 = new ArrayList<String>();

    public static void addDirectories(String name) throws IOException {
        File path = new File(name);
        // System.out.println("path " + path);
        File[] filesList = path.listFiles();
        // System.out.println(filesList);
        for (File file : filesList) {
            if (file.isDirectory()) {
                // System.out.println(file.getName() + " is a directory");
                directories.add(file.getAbsolutePath());
                names1.add(file.getName());
                addDirectories(file.getAbsolutePath());
            } else {
                if (file.getName().endsWith(".java")) {
                    // System.out.println(file.getName() + " is a file");
                    filenames.add(file.getAbsolutePath());
                    names2.add(file.getName());

                }

            }
        }

        // System.out.println(names);
    }

    public static void main(String[] args) {
        try {
            // Input obj = new Input();
            // System.out.println(args[0]);
            addDirectories((args[0]));
            // checkFiles(args[0]);
            System.out.println(names1);
            System.out.println("diff=======");
            System.out.println(names2);

        } catch (IOException e) {
        }
    }
}
