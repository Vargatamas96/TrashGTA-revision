package TrashGTAGame;

import java.util.Random;

public class Spawn {

    private Handler handler;
    private Game game;
    private Random r = new Random();
    public int ballasCounter = 1;
    public int policeCounter = 1;


    public Spawn(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    public void tick() { //spawns mobs at random places on the map

        if (game.ballasTrigger >= 1 && ballasCounter < game.ballasTrigger + 1) {
            handler.addObject(new Ballas(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.Ballas, handler, game, this));
            ballasCounter += 1;
        }
        if (game.wanted >= 1 && policeCounter < game.wanted + 1) {
            handler.addObject(new Police(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.Police, handler, game, this));
            policeCounter += 1;
        }
    }
}

