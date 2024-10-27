/**
 * Implement a Truck subclclass.
 */
public class Car extends Vehicle {
    /**
     * Implement a constructor for the class.
     * @param x is the top left x-coordinate of the given vehicle.
     * @param y if the top left y-coordinate of the given vehicle.
     */

    public Car(int x, int y, int direction) {
        super(x, y, direction);

        this.folderName = "cars";

        this.vehicleSpeed = direction * 10;
        this.vehicleWidth = 60;
        this.vehicleHeight  = 40;
    }
}