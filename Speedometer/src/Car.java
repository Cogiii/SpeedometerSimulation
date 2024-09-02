public class Car {
    private String brand, model;
    private double gasCapacity, gasConsumptionRate, currentSpeed, accelerationRate, deaccelerationRate;

    public Car(String brand, String model, double gasCapacity, double gasConsumptionRate, double currentSpeed, double accelerationRate, double deaccelerationRate) {
        this.brand = brand;
        this.model = model;
        this.gasCapacity = gasCapacity;
        this.gasConsumptionRate = gasConsumptionRate;
        this.currentSpeed = currentSpeed;
        this.accelerationRate = accelerationRate;
        this.deaccelerationRate = deaccelerationRate;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model){
        this.model = model;
    }

    public double getGasCapacity() {
        return gasCapacity;
    }
    public void setGasCapacity(double gasCapacity){
        this.gasCapacity = gasCapacity;
    }

    public double getGasConsumptionRate() {
        return gasConsumptionRate;
    }
    public void setGasConsumptionRate(double gasConsumptionRate){
        this.gasConsumptionRate = gasConsumptionRate;
    }

    public double getSpeed() {
        return currentSpeed;
    }

    public void setSpeed(double speed) {
        if (speed >= 0 && speed <= 180) { // Assuming max speed is 100 (180 degrees angle coming from left to right)
            this.currentSpeed = speed;
        } else if (speed < 0) {
            this.currentSpeed = 0;
        }
    }

    public void increaseSpeed(double increment) {
        setSpeed(this.currentSpeed + increment);
    }

    public void decreaseSpeed(double decrement) {
        setSpeed(this.currentSpeed - decrement);
    }

    public double GetAccelerationRate() {
        return accelerationRate;
    }
    public void SetAccelerationRate(double accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    public double GetDeaccelerationRate() {
        return deaccelerationRate;
    }
    public void SetDeaccelerationRate(double deaccelerationRate) {
        this.deaccelerationRate = deaccelerationRate;
    }
}
