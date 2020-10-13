import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Frame;

public class Main{
    private static JFrame frame = new JFrame("EDGE DETECTOR");
    private static JButton buttonPrevious = new JButton("PREVIOUS");
    private static JButton buttonNext = new JButton("NEXT");

    private int fileIndex = 0;
    private static String[] pathNames;

    public static void main(String[] args){
        File f = new File("screenshots");
        pathNames = f.list();
        if(pathNames.length == 0){
            System.err.println("NO FILES");
            System.exit(1);
        }

        for(String file : pathNames){
            System.out.println(file);
        }
        setUp();
    }

    private static void setUp(){
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void changeImage(int moveBy){
        int index = this.fileIndex + moveBy;
        if(index < 0){
            index = this.pathNames.length;
        }else if(index > this.pathNames.length){
            index = 0;
        }
        //TODO change image
    }
}
