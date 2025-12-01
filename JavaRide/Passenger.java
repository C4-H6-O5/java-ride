public class Passenger extends User {
    private final UserType userType;
    private final String idNumber;

    public Passenger(String name, int age, String address, String contactNumber, UserType userType, String idNumber) {
        super(name, age, address, contactNumber);
        this.userType = userType;
        
        this.idNumber = (idNumber == null || idNumber.trim().isEmpty()) ? "N/A" : idNumber;
    }

    public UserType getUserType() { return userType; }
    public String getIdNumber() { return idNumber; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); 
        sb.append("\nUser Type: ").append(userType);
        if (userType != UserType.REGULAR) {
            sb.append("\nID Number: ").append(idNumber);
        }
        return sb.toString();
    }
}