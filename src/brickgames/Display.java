package brickgames;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author Arafat Hossain Ar
 */
public final class Display {

    private final String title;
    private final int width;
    private final int height;

    public JFrame frame;
    public Canvas canvas;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    public void createDisplay() {

        // object of jframe
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //object of canvas
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(Color.pink);
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();

    }

}
