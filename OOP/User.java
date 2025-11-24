public abstract class User{
    private final String name;
    private final int age;
    private final String address;
    private final String contactNumber;

    public User(String name, int age, String address, String contactNumber) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Age: " + age + "\n" +
               "Address: " + address + "\n" +
               "Contact: " + contactNumber;
    }

}