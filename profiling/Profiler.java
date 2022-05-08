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
    private static final String DIRECTORY = "profiling_screenshots/";

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        File f = new File(DIRECTORY);
        if(f.list().length == 0){   //If directory is empty.
            System.err.println("NO FILES");
            System.exit(1);
        }
        System.out.println("Press ENTER to start.");
        try {
            System.in.read();   //Wait for user input for start with image processing.
        }  
        catch(Exception e){}
        profiling(f.list());
        System.out.println("Profiling finished.");
    }

    private static void profiling(String[] files){
        Cutter cutter = new Cutter();
        int counter = 0;
        for(String file : files){
            System.out.println(++counter + "/" + files.length);
            Mat picture = Imgcodecs.imread(DIRECTORY + file);
            cutter.loadPicture(picture);
        }
    }
}
