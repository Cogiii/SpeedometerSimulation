public class Needle {
    private double angle;

    public Needle() {
        this.angle = 0; // Start at 0 degrees
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        if (angle >= 0 && angle <= 180) { // needle can rotate upto 180 degrees
            this.angle = angle;
        }
    }

    public void updateAngle(Speed speed) {
        setAngle(speed.getSpeed()); // Direct mapping for simplicity
    }
}
