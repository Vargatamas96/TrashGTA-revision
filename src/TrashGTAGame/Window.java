package TrashGTAGame;

import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(int width, int height, String title, Game game) {         //constructor
        JFrame frame = new JFrame(title);                                   //new frame object
        frame.setPreferredSize(new Dimension(width, height));               //frame preferred size
        frame.setMaximumSize(new Dimension(width, height));                 //frame maximum size
        frame.setMinimumSize(new Dimension(width, height));                 //frame minimum size

        frame.add(game);                                                    //adding the game object into the frame
        frame.setResizable(false);                                          //shouldn't be resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               //X button closes the frame
        frame.setLocationRelativeTo(null);                                  //frame appears on the center
        frame.setVisible(true);                                             //frame is visible
    }

}
