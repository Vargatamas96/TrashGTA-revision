package TrashGTAGame;

import Multiplayer.Packet02Move;

import java.awt.*;

public class PlayerChar extends GameObject {

    private Handler handler;
    private Game game;
    protected String username;

    public PlayerChar(int x, int y, String username, ID id, Handler handler, Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        this.username = username;
    }

    @Override
    public void tick() {

        if (handler != null) {
            x += velX;
            y += velY;

            collision();

            if (handler.isUp()) velY = -5;  //movement
            else if (!handler.isDown()) velY = 0;

            if (handler.isDown()) velY = 5;
            else if (!handler.isUp()) velY = 0;

            if (handler.isRight()) velX = 5;
            else if (!handler.isLeft()) velX = 0;

            if (handler.isLeft()) velX = -5;
            else if (!handler.isRight()) velX = 0;

            Packet02Move packet = new Packet02Move(this.getUsername(), (int)this.x, (int)this.y);
            packet.writeData(Game.game.socketClient);
        }
    }

    private void collision(){
        for (int i = 0; i < handler.object.size(); i++){

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block){    //collision with a block

                if(getBounds().intersects(tempObject.getBounds())){
                     x += velX * (-1);
                     y += velY * (-1);
                }

            }
            if (tempObject.getId() == ID.Civilian){ //collision with a civilian (might be easier with a || logic)

                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * (-1);
                    y += velY * (-1);
                }
            }

            if (tempObject.getId() == ID.EnemyBullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    game.hpPlayer -= 5;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, 32, 32);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

    public String getUsername(){
    return this.username;
    }
}
