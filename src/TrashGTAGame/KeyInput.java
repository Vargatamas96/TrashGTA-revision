package TrashGTAGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;    //no need for new instance
    Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {   //looping through object, then moving them
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.PlayerMP) {
                if (key == KeyEvent.VK_W) handler.setUp(true);
                if (key == KeyEvent.VK_S) handler.setDown(true);
                if (key == KeyEvent.VK_A) handler.setLeft(true);
                if (key == KeyEvent.VK_D) handler.setRight(true);
                if(key == KeyEvent.VK_R){   // reloading and stopping movement
                     handler.setUp(false);
                     handler.setDown(false);
                     handler.setLeft(false);
                     handler.setRight(false);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    game.ammo = 12;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.PlayerMP) {
                if (key == KeyEvent.VK_W) handler.setUp(false);
                if (key == KeyEvent.VK_S) handler.setDown(false);
                if (key == KeyEvent.VK_A) handler.setLeft(false);
                if (key == KeyEvent.VK_D) handler.setRight(false);
            }
        }
    }

}
