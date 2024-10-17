/**
 * Implement a Truck class.
 */
public class Truck extends Vehicle {
    /**
     * Implement a constructor for the class.
     * @param x is the top left x-coordinate of the given vehicle.
     * @param y if the top left y-coordinate of the given vehicle.
     */
    public Truck(int x, int y) {
        super(x, y);

        this.vehicleSpeed = 4;
        this.vehicleWidth = 80;
        this.vehicleHeight  = 40;
    }
}
