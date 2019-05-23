package TrashGTAGame;

import java.awt.*;

public class Ballas extends GameObject {

    private Handler handler;
    private Game game;
    private Spawn spawn;
    private GameObject playerMP;

    private int timer = 100;


    int hp = 100;

    public Ballas(int x, int y, ID id, Handler handler, Game game, Spawn spawn) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        this.spawn = spawn;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.PlayerMP)
                playerMP = handler.object.get(i);       //checking the player
        }
    }

    @Override
    public void tick() {

        if (game.ballasTrigger >= 1) {
            timer--;
            x += velX;
            y += velY;

            float diffX = playerMP.getX() - x - 30;
            float diffY = playerMP.getY() - y - 30;
            float distance = (float) Math.sqrt((x - playerMP.getX()) * (x - playerMP.getX()) + (y - playerMP.getY()) * (y - playerMP.getY()));

            velX = ((1 / distance) * diffX);
            velY = ((1 / distance) * diffY);


            if (timer == 0) {
                handler.addObject(new EnemyBullet((int) x + 16, (int) y + 24, ID.EnemyBullet, handler, (int) playerMP.getX(), (int) playerMP.getY()));
                timer += 100;
            }
            }


        if (hp <= 0) {
            handler.removeObject(this);
            game.ballasTrigger = game.ballasTrigger + 1;
            game.score = game.score + 100;
            spawn.ballasCounter -= 1;
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 10) * (-1) + (playerMP.getX() - x)/(-70); //just a random idea
                    y += (velY * 10) * (-1) + (playerMP.getY() - y)/(-70); //for wallstuck "debugging"
                    velX *= (-1);
                    velY *= (-1);
                }
            }
            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    handler.removeObject(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0xD70096));
        g.fillRect((int) x, (int) y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 64, 64);
    }
}

