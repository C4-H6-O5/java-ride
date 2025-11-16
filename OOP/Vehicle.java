public abstract class Vehicle {
    protected String plateNumber;
    protected double baseFare;

    public Vehicle(String plateNumber, double baseFare) {
        this.plateNumber = plateNumber;
        this.baseFare = baseFare;
    }

    public abstract double calculateFare(double distance);
}

class Motorcycle extends Vehicle {
    public Motorcycle(String plateNumber) {
        super(plateNumber, 10.0);
    }

    @Override
    public double calculateFare(double distance) {
        return baseFare * distance;
    }
}

class Premium extends Vehicle {
    public Premium(String plateNumber) {
        super(plateNumber, 25.0);
    }

    @Override
    public double calculateFare(double distance) {
        return baseFare * distance + 30;
    }
}

class Normal extends Vehicle {
    public Normal(String plateNumber) {
        super(plateNumber, 25.0);
    }

    @Override
    public double calculateFare(double distance) {
        return baseFare * distance + 10;
    }
}
