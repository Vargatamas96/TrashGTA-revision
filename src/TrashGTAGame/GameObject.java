package TrashGTAGame;

import java.awt.*;

public abstract class GameObject {

    public float x;
    public float y;
    protected float velX = 0, velY = 0;
    protected ID id;
    static int numberCounter = 1;
    int number;

    public GameObject(float x, float y, ID id) {    //for positions
        this.x = x;
        this.y = y;
        this.id = id;
        number = numberCounter++;
    }

    public abstract void tick();    //updating the objects

    public abstract void render(Graphics g);    //object drawing

    public abstract Rectangle getBounds();  //for hit box or collision

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {

        return velX;
    }

    public float getVelY() {

        return velY;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {

        this.velY = velY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
}
