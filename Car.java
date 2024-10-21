public class Car extends Vehicle {

    public Car(int x, int y, int direction) {
        super(x, y, direction);

        this.vehicleSpeed = direction * 10;
        this.vehicleWidth = 60;
        this.vehicleHeight  = 30;
    }
}