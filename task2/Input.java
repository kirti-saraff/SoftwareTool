package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Input {
    private static ArrayList<String> filepaths = new ArrayList<String>();
    private static ArrayList<String> fnames = new ArrayList<String>(); // storing file names
    private static ArrayList<String> packages = new ArrayList<String>(); // storing all packages
    private static HashMap<String, int[]> packMap = new HashMap<>(); // mapping the packages to each other

    public static void addDirectories(String name) throws IOException {
        File path = new File(name);
        File[] filesList = path.listFiles();
        for (File file : filesList) {
            if (file.isDirectory()) {
                addDirectories(file.getAbsolutePath());
            } else {
                if (file.getName().endsWith(".java")) {
                    if((!file.getName().contains("Test"))&&(!file.getName().contains("test"))){
                        filepaths.add(file.getAbsolutePath());
                        fnames.add(file.getName().replace(".java", ""));

                        BufferedReader r = new BufferedReader(new FileReader(file.getPath()));  // checking package name
                        String line= r.readLine();
                        if(!line.isEmpty() && line.startsWith("package")){
                            String[] split = line.split(" ");
                            String packageName = split[1].replace(";", "");
                            if(!packages.contains(packageName)){
                                packages.add(packageName);
                            }
                        }
                    }
                }
            }
        }
    }

    public static double Instability(String name) throws IOException{
        for(int i=0; i<packages.size(); i++){
            int[] v=new int[]{0,0};
            packMap.put(packages.get(i), v);
        }
        
        for(int i=0; i< filepaths.size();i++){
            String currentP= null;
            ArrayList<String> imports = new ArrayList<String>();

            BufferedReader r = new BufferedReader(new FileReader(filepaths.get(i))); // checking package name
            String line= r.readLine();
            if(!line.isEmpty() && line.startsWith("package")){
                String[] split = line.split(" ");
                String packageName = split[1].replace(";", "");
                if(packages.contains(packageName)){
                    currentP= packageName;
                }
            }
            while((line=r.readLine()) !=null){      // counting fan-in(afferent)
                if(line.startsWith("import")){
                    String[] split = line.split(" ");
                    String importPackName= split[1].replace(";", "");
                    if(packages.contains(importPackName) && !imports.contains(importPackName)){
                        imports.add(importPackName);
                        int [] InCnt=packMap.get(importPackName);
                        int [] InCnt2= new int[]{ InCnt[0], InCnt[1] + 1};
                        packMap.put(importPackName,InCnt2 );
                    }
                }
            }
            int OutCnt = imports.size();           // fan-out(efferent)
            int[] OutCnt1= packMap.get(currentP);
            int[] OutCnt2 = new int[] {OutCnt, OutCnt1[1]};
            packMap.put(currentP, OutCnt2);
        }
        double calc= ((packMap.get((name))[0])+(packMap.get((name))[1]));
        double instability= (double)(packMap.get(name)[0])/calc;
        return instability;
    }

    public static Class getExternalClasses(String className, String packageName){
        try{
            return Class.forName(packageName+"."+className);
        }
        catch(ClassNotFoundException e){
        }
        return null;
    }

    public static double abstractness(String pkg){
        int cntAbs=0;
        for(int i=0; i<fnames.size();i++){
            Class c = getExternalClasses(fnames.get(i), pkg);
            int mod = c.getModifiers();
            boolean value= Modifier.isAbstract(mod);
            if(value==true){
                cntAbs++;
            }
        }
        double abs = (double)cntAbs/fnames.size();
        return abs;
    }

    public static void main(String[] args) {
        try {
            int choice=0;
            do{
                System.out.println();
                System.out.println("Press 1 for calculating abstractness of a package ");
                System.out.println("Press 2 for calculating instability of a particular package ");
                System.out.println("Press 3 to exit ");
                System.out.println("Please enter your choice: ");
                Scanner ch=new Scanner(System.in);
                choice=ch.nextInt();
                filepaths.clear();
                fnames.clear();
                packages.clear();
                switch(choice){
                    case 1:
                    System.out.println();
                    System.out.println("Calculating abstractness ");
                    Scanner obj=new Scanner(System.in);
                    System.out.println("enter package name :");
                    String Abspkg=obj.nextLine();
                    Scanner obj2=new Scanner(System.in);
                    System.out.println("enter project path :");
                    String AbsProPath=obj2.nextLine();
                    
                    addDirectories((AbsProPath));
                    double abs = abstractness(Abspkg);
                    System.out.println();
                    System.out.println(("Abstarctness of the pakage is : " +abs));
                    System.out.println();
                    System.out.println("Note: For calculating abstractness of another package please export the classpath and run the file again.");
                    break;

                    case 2:
                    System.out.println();
                    System.out.println("Calculating Instability");
                    Scanner sc=new Scanner(System.in);
                    System.out.println("enter project/software path:");
                    String proPath=sc.nextLine();
                    
                    Scanner sc2=new Scanner(System.in);
                    System.out.println("enter package name for calculating instability:");
                    String packageName=sc2.nextLine();
                    
                    addDirectories(proPath);
                    double instability= Instability(packageName);
                    System.out.println(("Instability of package " +packageName+" is : " +instability));
                    break;

                    case 3:
                    System.out.println();
                    System.out.println("exiting......");
                    break;

                    default:
                    System.out.println();
                    System.out.println("Choice not found try again");
                }
            }while(choice!=3);
        } catch (IOException e) {
        }
    }
}
