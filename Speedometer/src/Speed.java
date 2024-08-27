public class Speed {
    private double speed;

    public Speed(double initialSpeed) {
        this.speed = initialSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (speed >= 0 && speed <= 180) { // Assuming max speed is 100 (180 degrees angle coming from left to right)
            this.speed = speed;
        }
    }

    public void increaseSpeed(double increment) {
        setSpeed(this.speed + increment);
    }

    public void decreaseSpeed(double decrement) {
        setSpeed(this.speed - decrement);
    }
}
