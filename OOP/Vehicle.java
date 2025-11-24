public abstract class Vehicle {
    private final String plateNumber;
    private final double baseFare;
    private VehicleType type;

    public Vehicle(String plateNumber, double baseFare, VehicleType type) {
        this.plateNumber = plateNumber;
        this.baseFare = baseFare;
        this.type = type;
    }

    public String getPlateNumber(){return plateNumber;}
    public double getBaseFare(){return baseFare;}
    public VehicleType getVehicleType(){return type;}

    @Override
    public String toString() {
        return String.format("%s (Plate: %s)", type, plateNumber);
    }

    public abstract double calculateFare(double distance);
}

class Motorcycle extends Vehicle {
    public Motorcycle(String plateNumber) {
        super(plateNumber, 10.0, VehicleType.MOTORCYCLE);
    }

    @Override
    public double calculateFare(double distance) {
        return getBaseFare() * distance;
    }
}

class Normal extends Vehicle {
    public Normal(String plateNumber) {
        super(plateNumber, 25.0, VehicleType.NORMAL);
    }

    @Override
    public double calculateFare(double distance) {
        return getBaseFare() * distance + 10;
    }
}

class Premium extends Vehicle {
    public Premium(String plateNumber) {
        super(plateNumber, 25.0, VehicleType.PREMIUM);
    }

    @Override
    public double calculateFare(double distance) {
        return getBaseFare() * distance + 30;
    }
}
