package GameSetup;

import brickgames.Display;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * @author Arafat Hossain Ar
 */
public class GameSetup implements Runnable, KeyListener {

    private Display display;
    private final String title;
    private final int width;
    private final int height;
    private BufferStrategy buffer;
    private Graphics g;

    private Thread thread;

    private int score;
    // ball
    private final int ballX = 200 + 20;
    private final int ballY = 550 - 50;
    //bat
    int batX = 200;
    int baty = 515;

    boolean left, right;
    //bricks 
    int brickX = 40;
    int brickY = 70;

    //move
    int moveX = 1;
    int moveY = -1;
    //game over
    boolean gameOver = true;

    Rectangle Ball = new Rectangle(ballX, ballY, 15, 15);
    Rectangle Bat = new Rectangle(batX, baty, 50, 15);
    Rectangle[] Bricks;

    public GameSetup(String title, int width, int height) {

        this.title = title;
        this.width = width;
        this.height = height;
        score = 0;
    }

    public void init() {
        display = new Display(title, width, height);
        display.frame.addKeyListener(this);
        Bricks = new Rectangle[24];

        for (int i = 0; i < Bricks.length; i++) {

            Bricks[i] = new Rectangle(brickX, brickY, 50, 20);
            if (i == 8) {
                brickX = 40;
                brickY = 70 + 25;
            }
            if (i == 15) {
                brickX = 85;
                brickY = 70 + 25 + 25;
            }
            if (i == 20) {
                brickX = 130;
                brickY = 70 + 25 + 25 + 25;
            }
            brickX += 45;

        }
    }

    public void drawBall(Graphics g) {

        g.setColor(Color.black);
        g.fillOval(Ball.x, Ball.y, 15, 15);
    }

    public void drawBat(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(Bat.x, Bat.y, 60, 15);
    }

    public void drawBricks(Graphics g) {
        for (Rectangle Brick : Bricks) {
            if (Brick != null) {
                g.setColor(Color.white);
                g.fillRect(Brick.x - 2, Brick.y - 2, 40 + 2, 15 + 2);
                g.setColor(Color.blue);
                g.fillRect(Brick.x, Brick.y, 40, 15);
            }
        }

    }

    public void drawScore(Graphics g) {
        String a = Integer.toString(score);
        g.setColor(Color.red);
        g.setFont(new Font("arial", Font.BOLD, 25));
        g.drawString("Score: " + a, 20, 40);

    }

    public void gameEnd(Graphics g) {
        g.setColor(Color.green);
        g.drawString("Game Over", 200, 225);
    }

    public void tick() {
        if (Ball.x >= 445 || Ball.x <= 20) {
            moveX = -moveX;
        }
        if (Ball.y >= 515 || Ball.y <= 50) {
            moveY = -moveY;
        }
        if (Ball.y >= 445) {
            gameOver = true;
        }
        //bat right and left
        if (left) {
            if (Bat.x >= 20) {
                Bat.x -= 1;
            }
        }
        if (right) {
            if (Bat.x <= 400) {
                Bat.x += 1;
            }
        }
        // ball and bat collision
        if (Bat.intersects(Ball)) {
            moveY = -moveY;
        }
        //ball bat collision
        for (int i = 0; i < Bricks.length; i++) {
            if (Bricks[i] != null) {
                if (Bricks[i].intersects(Ball)) {
                    moveY = -moveY;
                    Bricks[i] = null;
                    score += 5;
                }
            }
        }
        Ball.x += moveX;
        Ball.y += moveY;

    }

    public void draw() {
        buffer = display.canvas.getBufferStrategy();
        if (buffer == null) {
            display.canvas.createBufferStrategy(3);
            return;
        }
        g = buffer.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        //start drawing
        g.setColor(Color.white);
        g.fillRect(20, 50, 440, 480);
        //draw  
        if (gameOver) {
            drawBall(g);
            drawBat(g);
            drawBricks(g);
            //draw score
            drawScore(g);
        }
        if (!gameOver) {
            gameEnd(g);
        }
        //end
        buffer.show();
        g.dispose();
    }

    public synchronized void start() {

        thread = new Thread(this);
        thread.start();

    }

    public synchronized void stop() throws InterruptedException {
        thread.join();
    }

    //control panel
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int source = e.getKeyCode();

        if (source == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (source == KeyEvent.VK_RIGHT) {
            right = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (source == KeyEvent.VK_RIGHT) {
            right = false;
        }

    }

    @Override
    public void run() {
        init();
        while (true) {

            thread.currentThread();
            try {
                thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            tick();
            draw();
        }
    }

}
