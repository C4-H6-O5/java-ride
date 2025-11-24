<p align="center">
  <img src="https://github.com/C4-H6-O5/java-ride/blob/0f8f7b17765a04145104b53c8ba96504d66093ad/logo.png" 
       alt="JavaRide Logo" 
       width="1000"/>
</p>

<h1 align="center">JavaRide</h1>
<h3 align="center"><i>Brewing Better Rides</i></h3>
<p align="center"> -- by REMix -- </p>
<p align="center">Regina * Elaiza * Mhalik</p>
<hr>


## 🚗 **About the Project**

**JavaRide** is a Java console-based ride-hailing simulation.
It replicates key functionalities of modern ride-hailing apps, including user registration, driver assignment, ride tracking, feedback collection, and earnings management.

**Key Goals:**

* Realistic ride simulation
* Menu-driven, user-friendly interface
* Clear implementation of Object-Oriented Programming (OOP) principles
* Modular and maintainable code structure

---

## 📘 **Core Functionalities**

### 👤 User Registration

* **Passenger**
* **Senior / PWD / Student** (Fare discounts)
* **Driver** (choose vehicle type & set bio)

---

### 🧳 Passenger Features

* Book a ride:

  * Number of passengers
  * Pickup & drop-off points
  * Vehicle type
  * Fare estimate & driver assignment
    
* Track ride progress

  * **Driver message at ≥80%:** *“I’m on my way!”*
  * Cancellation allowed only if **<50% progress**
    
* Leave review & rating
  
* View profile and booking history

---

### 🚙 Driver Features

* Receive ride requests:

  * Accept / decline
  * Check distance, passengers, pickup & drop-off points
  * Send short messages to passengers
    
* Track booking history (accepted / declined)
* View passenger reviews
* Track earnings and cash-out

---

### 🌐 System Enhancements

* Randomized driver assignment
* Color-coded console UI
* ASCII art and text animations

---

## 🧠 **OOP Concepts Applied**

| Concept           | Implementation                                                                             |
| ----------------- | ------------------------------------------------------------------------------------------ |
| **Encapsulation** | Private fields with getters/setters in `User`, `Passenger`, `Driver`, `Vehicle`, `Booking` |
| **Inheritance**   | `User → Passenger / Driver`; `Vehicle → Motorcycle / NormalCar / PremiumCar`               |
| **Polymorphism**  | Overridden methods in Vehicle subclasses (`getType()`, `calculateFare()`)                  |
| **Abstraction**   | `Vehicle` is abstract; base logic implemented in subclasses                                |

---

## 🗂️ **Project Structure**

```
src/
└── javaride/
    ├── Main.java
    ├── User.java
    ├── Passenger.java
    ├── Driver.java
    ├── Vehicle.java
    ├── Motorcycle.java
    ├── NormalCar.java
    ├── PremiumCar.java
    ├── Booking.java
    ├── Review.java
    └── DataManager.java
```

---

## ▶️ **How to Run**

**Compile:**

```
javac javaride/*.java
```

**Run:**

```
java javaride.Main
```

---

## 📝 **Sample Console Flow**

```
Welcome to JavaRide!
Enter your name: Lia
Choose user type:
1. Regular Passenger
2. Senior/PWD/Student
Your choice: 1

Booking a Ride...
Pickup: Ayala Terminal
Drop-off: Divisoria
Vehicle: Normal Car
Estimated Fare: ₱112.00

Driver matched: Carlo (Normal Car)
Start ride? (Y/N): Y

[35%] Heading to your location...
[80%] "I'm on my way!"
[100%] Ride completed!

Rate your driver (1–5): 5
Review submitted. Thank you!
```

---


## 🔄 **System Flow (Textual / Step-by-Step)**

### **1️⃣ Login Page**

* Enter Name, Age, Address, Contact Number
* Select User Type:

  * Passenger / Senior / PWD / Student
  * Driver (choose vehicle type & set bio)

---

### **2️⃣ Passenger Menu**

1. **Book a Ride**

   * Enter number of passengers
   * Specify Pickup & Drop-off points
   * Select Vehicle Type
   * Fare Estimate displayed
   * System generates a random driver
   * Confirm or cancel ride

     * Cancellation allowed only if **<50%** trip progress
   * Ride progress updates

     * **Driver message at ≥80%:** *“I’m on my way!”*
   * Upon arrival, leave review & rating
2. **Check Reviews & Ratings**
3. **View Profile**

   * Show personal details
   * View Booking History
   * Delete Account

---

### **3️⃣ Driver Menu**

1. **Ride Requests**

   * Accept or Decline
   * View Pickup, Drop-off, Distance, Passengers
   * After Drop-off → receive review & rating
   * Optional: send short message to passenger
2. **Booking History**

   * Accepted / Declined rides
3. **Reviews**

   * View passenger feedback
4. **My Earnings**

   * Track total income
   * Cash-out simulation

---

### **4️⃣ Loop / Continuation**

* After each action, return to **Main Menu** (Passenger or Driver)
* Allows multiple bookings, rides, and management actions

---

## 🔍 **Feature Comparison Table**

| Feature / Menu         | Passenger | Driver                 |
| ---------------------- | --------- | ---------------------- |
| Book a Ride            | ✅ Yes     | ❌ No                   |
| Confirm Ride           | ✅ Yes     | ❌ No                   |
| Track Ride Progress    | ✅ Yes     | ❌ No                   |
| Leave Review & Rating  | ✅ Yes     | ✅ Yes (after drop-off) |
| Receive Ride Requests  | ❌ No      | ✅ Yes                  |
| Accept / Decline Ride  | ❌ No      | ✅ Yes                  |
| Send Messages          | ❌ No      | ✅ Yes                  |
| View Profile           | ✅ Yes     | ✅ Yes                  |
| Booking History        | ✅ Yes     | ✅ Yes                  |
| Check Reviews          | ✅ Yes     | ✅ Yes                  |
| My Earnings / Cash Out | ❌ No      | ✅ Yes                  |

---

## 👥 **Contributors**

| Member                          | Role                                      | Responsibilities                                                                                                                                                                                          |
|---------------------------------|-------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🧑‍💻 **Regina Annemarie Bool**  | Core Developer (Logic Lead)               | Handles program flow, ride simulation, and input handling.<br>- Set up `main()` and menus<br>- Connect classes together<br>- Simulate booking process                                                     |
| 👩‍💻 **Mhalik Perez**           | OOP & Structure Lead (Back-End)           | Focuses on classes, inheritance, and OOP structure.<br>- Create classes like `Vehicle`, `Driver`, `Passenger`<br>- Add constructors, methods, and encapsulation<br>- Design relationships between classes |
| 🧑‍🎨 **Elaiza Espartinez**       | Design & Documentation Lead (Front-End / UI) | Handles console UI, colors, ASCII art, and documentation.<br>- Improve menu design (ASCII borders, color codes)<br>- Add loading or progress animations<br>- Maintain `README.md` and commit updates      |



---

## 📢 **DISCLAIMER**

<small> The JavaRide project was developed by Computer Science students as part of the academic requirements for CS 211: Object-Oriented Programming. It is intended exclusively for educational and demonstration purposes. Students are welcome to review and learn from the project, but creating original implementations is highly encouraged. </small>

