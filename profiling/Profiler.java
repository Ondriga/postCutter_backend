/*
 * Source code for the backend of Bachelor thesis.
 * Profiler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import java.io.File;
import java.util.Scanner;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Core;

import postCutter.Cutter;

/**
 * Profiling class.
 */
public class Profiler {

    /// Constant for directory name in which are screenshots for process.
    private static final String DIRECTORY = "profiling_photos/";

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Cutter cutter = new Cutter();

        File f = new File(DIRECTORY);
        if(f.list().length == 0){   //If directory is empty.
            System.err.println("NO FILES");
            System.exit(1);
        }
        System.out.println("Press any key to start.");
        Scanner scanner = new Scanner(System.in);
        scanner.next(); //Wait for user input for start with image processing.  
        for(String filePath : f.list()){
            Mat picture = Imgcodecs.imread(DIRECTORY + filePath);
            cutter.loadPicture(picture);
        }
        System.out.println("Profiling finished.");
    }
}
