package TrashGTAGame;

import java.awt.*;

public class EnemyBullet extends GameObject {

    private Handler handler;



    public EnemyBullet(int x, int y, ID id, Handler handler, float playerx, float playery) {
        super(x, y, id);
        this.handler = handler;

        velX = (playerx - x) / 10;  //travel time
        velY = (playery - y) / 10;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i < handler.object.size(); i++) {   //bullet collision and removing bullet
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(this);
                }
            }

        }
    }

        @Override
        public void render (Graphics g){
            g.setColor(Color.BLACK);
            g.fillOval((int) x, (int) y, 8, 8);
        }

        @Override
        public Rectangle getBounds () {
            return new Rectangle((int) x, (int) y, 16, 16);
        }
    }
