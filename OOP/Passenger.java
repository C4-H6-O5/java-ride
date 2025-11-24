enum UserType {
    REGULAR,
    STUDENT,
    SENIOR,
    PWD
}

public class Passenger extends User {
    private String userType;
    private String idNumber;

    public Passenger(String name, int age, String address, String contactNumber, UserType userType, String idNumber) {
        super(name, age, address, contactNumber);
        this.userType = userType;
        this.idNumber = idNumber;
    }

    public UserType getUserType() { return userType; }
    public String getIdNumber() { return idNumber; }

    public void setUserType(UserType userType) { this.userType = userType; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("User Type: " + userType);
        if (idNumber != null && !idNumber.isEmpty()) {
            System.out.println("ID Number: " + idNumber);
        }
    }

}