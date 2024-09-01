public class Car {
    private String brand, model;
    private double gasCapacity, gasConsumptionRate, speed;

    public Car(String brand, String model, double gasCapacity, double gasConsumptionRate, double speed) {
        this.brand = brand;
        this.model = model;
        this.gasCapacity = gasCapacity;
        this.gasConsumptionRate = gasConsumptionRate;
        this.speed = speed;
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
