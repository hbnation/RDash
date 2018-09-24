package com.game.main;

import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.image.BufferStrategy;

//put together selection menu (other graphic, lines across), blue theme instead, callibration, fix code or nah?
public class Game extends Canvas implements Runnable {

    final JFXPanel fxPanel = new JFXPanel(); // needed to initialize javafx, don't delete

    private int FPS; //displayed FPS
    private static final int WIDTH = 800, HEIGHT = WIDTH / 4 * 3;
    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private Conductor conductor;
    private HUD hud;
    private SelectionMenu sMenu;

    public enum STATE { //for the menu and selection screen. First priority after first song finished
        MENU,
        GAME
    }

    public static STATE gameState = STATE.MENU;


    private Game() {
        handler = new Handler();
        sMenu = new SelectionMenu(this, handler);
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(sMenu);


        //conductor = new Conductor(handler);

        new Window(WIDTH, HEIGHT, "Rhythm Dash", this);

        hud = new HUD();


        start();
    }

    private synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;

    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: + " frames);
                FPS = frames;
                frames = 0;
            }
        }
        stop();
    }



    private void tick() {
        handler.tick();

        if (gameState == STATE.GAME) {
            if (conductor != null) {
                conductor.tick();
            }
            hud.tick();
            if (conductor == null)
                conductor = new Conductor(handler);
        } else if (gameState == STATE.MENU) {
            sMenu.tick();
        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        KeyInput keys = new KeyInput(handler);

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        if (gameState == STATE.GAME) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(0, 500, 800, 500);// lines

            g.drawLine(200, 0, 200, 600);
            g.drawLine(300, 0, 300, 600);
            g.drawLine(400, 0, 400, 600);
            g.drawLine(500, 0, 500, 600);
            g.drawLine(600, 0, 600, 600);

            g.drawString("'SPACE'", 135, 495); // keys
            g.drawString("'F'", 245, 495);
            g.drawString("'G'", 345, 495);
            g.drawString("'H'", 445, 495);
            g.drawString("'J'", 545, 495);

            g.setColor(Color.white);
            Font bigWhite = new Font("SansSerif", Font.BOLD, 18);
            g.setFont(bigWhite);

            Rectangle borderRect = new Rectangle(110, 500, 80, 30);
            drawCenteredString(g, KeyInput.spaceResult, borderRect, bigWhite);
            borderRect.setLocation(210, 500);
            drawCenteredString(g, KeyInput.fResult, borderRect, bigWhite);
            borderRect.setLocation(310, 500);
            drawCenteredString(g, KeyInput.gResult, borderRect, bigWhite);
            borderRect.setLocation(410, 500);
            drawCenteredString(g, KeyInput.hResult, borderRect, bigWhite);
            borderRect.setLocation(510, 500);
            drawCenteredString(g, KeyInput.jResult, borderRect, bigWhite);

            g.drawString("FPS: " + FPS, 710, 560);


            Font gameFont = new Font("Verdana", Font.PLAIN, 25);
            g.setFont(gameFont);
            //g.setColor(Color.BLUE);
            g.drawString("Score: " + keys.getScore(), 620, 35);
            g.drawString("Combo: x" + KeyInput.combo, 620, 60);

            //g.setColor(Color.red);
            g.drawString("Fire Aura", 650, 250);
            g.drawString("Kid2Will", 675, 275);
            hud.render(g);
        } else if (gameState == STATE.MENU) {
            sMenu.render(g);
        }


        g.dispose();
        bs.show();
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }


    public static void main(String[] args) {
        new Game();


    }


}
