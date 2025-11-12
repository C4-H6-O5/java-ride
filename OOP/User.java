//not yet abstract, hindi pa inaapply ang inheritance
public abstract class User{
    protected String name;
    protected int age;
    protected String address;
    protected String contactNumber;

    public User(String name, int age, String address, String contactNumber) {
        this.name = name;
        this.age =  age;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
        System.out.println("Contact: " + contactNumber);
    }

}