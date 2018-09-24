package com.game.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectionMenu extends MouseAdapter {


            private BufferedImage FireAuraImg;
            private BufferedImage SymphonyImg;
            private BufferedImage FadeImg;
            private BufferedImage InvincibleImg;
            private Rectangle fireAuraRect;
            private Rectangle SymphonyRect;
            private Game game;
            private Handler handler;

            public SelectionMenu(Game game, Handler handler){
                this.game = game;
                this.handler = handler;
                try {
                    FireAuraImg = ImageIO.read(getClass().getResource("/resources/images/FireAuraPic.gif"));
                    fireAuraRect = new Rectangle(25, 50, FireAuraImg.getWidth(), FireAuraImg.getHeight());
                    SymphonyImg = ImageIO.read(getClass().getResource("/resources/images/SymphonyPic.gif"));
                    SymphonyRect = new Rectangle(25, 175, SymphonyImg.getWidth(), SymphonyImg.getHeight());
                    FadeImg = ImageIO.read(getClass().getResource("/resources/images/FadePic.gif"));
                    InvincibleImg = ImageIO.read(getClass().getResource("/resources/images/InvinciblePic.gif"));

                } catch(IOException e) {
                    e.printStackTrace();
                }
            }


            public void mousePressed(MouseEvent e) {
                System.out.println("clicked");
                int mx = e.getX();
                int my = e.getY();
                if (fireAuraRect.contains(e.getX(), e.getY())) {
                    game.gameState = Game.STATE.GAME;
                    System.out.println("clicked");
                }
                if (SymphonyRect.contains(e.getX(), e.getY())){
                    game.gameState = Game.STATE.GAME;
                }
            }

            public void mouseReleased(MouseEvent e) {

    }



    public void tick(){

    }

    public void render(Graphics g){
        g.drawImage(FireAuraImg, 25, 50,  null);
        g.drawImage(SymphonyImg, 25, 175, null);
        g.drawImage(FadeImg, 25, 300, null);
        g.drawImage(InvincibleImg, 25, 425, null);


    }


}
