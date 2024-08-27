public class Car {
    private String brand;
    private String model;
    private double gasCapacity;
    private double gasConsumptionRate;

    public Car(String brand, String model, double gasCapacity, double gasConsumptionRate) {
        this.brand = brand;
        this.model = model;
        this.gasCapacity = gasCapacity;
        this.gasConsumptionRate = gasConsumptionRate;
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
}
