import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrowningTurtle extends Turtle {
    public DrowningTurtle(int x, int y, int direction) {
        super(x, y, direction);

        new Thread(() -> {
            while (true) {
                this.switchSprite("resources/vector_turtle.png");
                try {
                    Thread.sleep(2000);
                    this.switchSprite("resources/turtle_ripple.png");
                    Thread.sleep(1000);


                    //Thread.sleep(2000);
                    //switchSprite("resources/vector_turtle.png");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
