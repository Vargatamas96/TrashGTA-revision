package TrashGTAGame;

public class Camera {

    private float x, y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object){

        x += ((object.getX() - x) - 1380/2) * 0.05f; //for smooth transactioning
        y += ((object.getY() - y) - 768/2) * 0.05f;

        if (x <= 0) x = 0;  //stopping the camera movement at the edge of the map
        if (x >= 692) x = 692;  //numbers are debugged, till it works -> no idea why. Map size?
        if (y <= 0) y = 0;
        if (y >= 785 + 16) y = 785 + 16;

    }

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
}
