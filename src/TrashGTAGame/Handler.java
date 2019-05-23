package TrashGTAGame;

import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {  //handles all objects with updating using a loop, so they don't need to be updated one by one

    LinkedList<GameObject> object = new LinkedList<GameObject>();   //object list/array

    private boolean up = false, down = false, right = false, left = false;  //for movement lag removal

    public void tick() {    //loop for updating objects, stored in a temp object then refreshing it
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }

    }

    public void render(Graphics g) {    //same, but with rendering
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }

    }

    public void addObject(GameObject tempObject) {

        object.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {

        object.remove(tempObject);
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (GameObject o : object) {
            if (o instanceof PlayerMP && ((PlayerMP) o).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public  void movePlayer(String username, int x, int y){
        int index = getPlayerMPIndex(username);
        this.object.get(index).x = x;
        this.object.get(index).y = y;
    }

    public  void moveCivilian(int x, int y, int index, int count){
/*        if (civek száma > count) {
            néhány civet kiveszünk a listából
        }*/
        GameObject go = getByNumber(index);
        if (go != null) {
            Civilian civ = (Civilian)go;
            civ.setX(x);
            civ.setY(y);
        }
    }

    public GameObject getByNumber(int number) {
        for (GameObject o : object) {
            if (o.getNumber() == number)
                return o;
        }
        return null;
    }
}
