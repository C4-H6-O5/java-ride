public class Passenger extends User {
    private String userType;
    private String idNumber;

    public Passenger(String name, int age, String address, String contactNumber, String userType, String idNumber) {
        super(name, age, address, contactNumber);
        this.userType = userType;
        this.idNumber = idNumber;
    }

    public String getUserType() { return userType; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("User Type: " + userType);
        if (idNumber != null && !idNumber.isEmpty()) {
            System.out.println("ID Number: " + idNumber);
        }
    }

}