package task2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager{

    static ArrayList<String> directories = new ArrayList<String>();
    static ArrayList<String> names1 = new ArrayList<String>();

    public static void findDirectory(String dpath){
        File path=new File(dpath);
        File fileList[]= path.listFiles();
        System.out.println(fileList[0]);
    }
    public static void main(String[] args) throws IOException{
        System.out.println(args[0]);
        findDirectory(args[0]);
        //System.out.println(names1);
    }
}