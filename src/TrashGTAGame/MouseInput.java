package TrashGTAGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private Game game;  //for ammo

    public MouseInput(Handler handler, Camera camera, Game game) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.PlayerMP && game.ammo >= 1) {
                handler.addObject(new Bullet((int)tempObject.getX() + 16, (int)tempObject.getY() + 24, ID.Bullet, handler, mx, my));    //position from where the bullet starts and where it is travel to
                game.ammo--;
            }
        }
    }

}
