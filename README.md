<p align="center">
  <img src="https://github.com/C4-H6-O5/java-ride/blob/0f8f7b17765a04145104b53c8ba96504d66093ad/logo.png" 
       alt="JavaRide Logo" 
       width="1000"/>
</p>

<h1 align="center">JavaRide</h1>
<h3 align="center"><i>Brewing Better Rides</i></h3>
<p align="center"><i>✨ A Project Brewed by <b>REMix</b> ✨</i></p>
<p align="center">Regina • Elaiza • Mhalik</p>
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
🗂️REMix/
└── 🗂️JavaRide Test/
    ├── Main.java
    ├── Utility.java
    ├── User.java
    ├── UserType.java
    ├── Passenger.java
    ├── Driver.java
    ├── Vehicle.java
    ├── VehicleType.java
    |    ├── Motorcycle
    |    ├── Normal
    |    └── Premium
    |    
    ├── Booking.java
    ├── BookingStatus.java
    ├── Review.java
    ├── DataGenerator.java
    └── LocationManager.java

```

---

## ▶️ **How to Run**

**Compile:**

```
javac /*.java
```

**Run:**

```
java Main
```

---

## 📝 **Console Flow**

           )))                                               
         (((                                       ____________
           )))                                _____//___||___\\_____                                                                                                           
          ____                               (______    __        __)
         /   /_____ ___ ___    ___  _____ __ /  ___ \  (__)______/ /____
  ____  /   //  ___ ` / \  \  /  //  __  ` //  /__/ / /  //  ___  //  _ \
 /   /_/   //  /_ /  /   \  \/  //  /__/  //  __,__/ /  //  /__/ //  __/ 
 \_______ / \____,__/     \____/ \____,__//__/  \__\/__/ \___,__/ \____/ 

------------------------------------------------------------------------
                      Welcome to JavaRide                                                                                                                                      
                  Brewing Better Rides For You!
------------------------------------------------------------------------

Press [ENTER] to continue...

+=============================================================+
|                      JAVA RIDE MAIN MENU                    |
+=============================================================+
[1] Passenger
[2] Driver
[3] Exit Application

Select your account type: 1

+=============================================================+
|                  NEW PASSENGER REGISTRATION                 |
+=============================================================+
Name: Elaiza
Age: 20
Address: Sitio Calumpang
Contact Number: 09123456789

+=============================================================+
|                       SELECT USER TYPE                      |
+=============================================================+
[1] REGULAR
[2] STUDENT
[3] SENIOR
[4] PWD
 
Enter number for type: 2
Enter your ID Number (STUDENT): 24-01788

 --- All set! Let's hit the road, Elaiza!---
> Loading your dashboard...
Initializing Dashboard
██████████████████████████████ 100%

============================================ 

Hello, Elaiza! Your setup is now complete.

Press [ENTER] to continue to the Home Page... 

```

---


## 🔄 **System Flow**

### **1️⃣ Login Page**

* Select User Type:

  * Passenger 
  * Driver 

---

### **2️⃣ Passenger Menu**

1. **Passenger**
   * Enter Name, Age, Address, Contact Number
   * User type (Regular, Student (**StudentID**), Senior, and PWD)

2. **Book a Ride**

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

3. **Check Reviews & Ratings**
   * System Generates Random Review and rating

4. **View Profile**

   * Show personal details
   * View Booking History
   * Delete Account

---

### **3️⃣ Driver Menu**

1. **Driver**
   * Enter Name, Age, Address, Contact Number, Short Bio and Plate Number
   * Vehicle Type (Motorcycle, Normal, and Premium)

2. **Ride Requests**
    * Accept or Decline
    * View Pickup, Drop-off, Distance, Passengers
    * After Drop-off → receive review & rating
    * Optional: send short message to passenger

3. **Booking History**
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

## ✨ **| ACKNOWLEDGEMENT |** ✨
*We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our aappreciation to our peers for their cooperation and encouragement during the develepmont process. And also to our parents

---

## 📢 **DISCLAIMER**

<small> The JavaRide project was developed by Computer Science students as part of the academic requirements for CS 211: Object-Oriented Programming. It is intended exclusively for educational and demonstration purposes. Students are welcome to review and learn from the project, but creating original implementations is highly encouraged. </small>

