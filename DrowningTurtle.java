/**
 * Define a DrowningTurtle subclass of Turtle.
 * 
 * DrowningTurtle represents a turtle which will "sink" underwater every
 * few seconds.
 * 
 * If the player comes in contact during that period, they will lose the current game.
 */
public class DrowningTurtle extends Turtle {
    /**
     * Define a constructor for the DrowningTurtle subclass.
     * @param x is the x-coordinate.
     * @param y is the y-coordinate.
     * @param direction is the direction of the DrowningTurtle.
     */
    public DrowningTurtle(int x, int y, int direction) {
        super(x, y, direction);

        new Thread(() -> {
            while (true) {
                // Switch sprite every few seconds.
                this.switchSprite("resources/vector_turtle.png");
                this.touchable = true;
                try {
                    Thread.sleep(3000);
                    this.switchSprite("resources/turtle_ripple.png");
                    this.touchable = false;
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
