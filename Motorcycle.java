/**
 * Implement a Truck subclclass.
 */
public class Motorcycle extends Vehicle {
    /**
     * Implement a constructor for the class.
     * @param x is the top left x-coordinate of the given vehicle.
     * @param y if the top left y-coordinate of the given vehicle.
     */

    public Motorcycle(int x, int y, int direction) {
        super(x, y, direction);

        this.folderName = "motorcycles";

        this.vehicleSpeed = direction * 12;
        this.vehicleWidth = 40;
        this.vehicleHeight  = 30;
    }
}