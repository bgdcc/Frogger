/**
 * Implement a Truck class.
 */
public class Truck extends Vehicle {
    /**
     * Implement a constructor for the class.
     * @param x is the top left x-coordinate of the given vehicle.
     * @param y if the top left y-coordinate of the given vehicle.
     */
    public Truck(int x, int y, int direction) {
        super(x, y, direction);

        this.vehicleSpeed = direction * 4;
        this.vehicleWidth = 200;
        this.vehicleHeight  = 40;
    }
}
