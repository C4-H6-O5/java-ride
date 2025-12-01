<p align="center">
  <img src="https://github.com/C4-H6-O5/java-ride/blob/0f8f7b17765a04145104b53c8ba96504d66093ad/logo.png" alt="JavaRide Logo" width="600"/>
</p>

<h1 align="center">JavaRide</h1>
<p align="center"><em>Brewing Better Rides</em></p>
<p align="center">✨ A Project Brewed by <strong>REMix</strong> ✨</p>
<p align="center">Regina • Elaiza • Mhalik</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange" alt="Java">
</p>

---

## 🚗 About JavaRide

**JavaRide** is a Java console-based ride-hailing simulator that replicates core behaviors of ride-hailing apps: user registration, driver assignment, ride tracking, feedback collection, and earnings management.

**Key goals**
- Realistic ride simulation
- Menu-driven, user-friendly console UI
- Clear application of OOP principles
- Modular, maintainable code

---

## 📋 Table of Contents

- 📘 [Core Functionalities](#-core-functionalities)  
- ▶️ [How to Run](#-how-to-run)
- 🗂️ [Project Structure](#-project-structure)  
- 📝 [Console Flow (Preview)](#-console-flow-preview)  
- 🔄 [System Flow](#-system-flow)  
- 🔍 [Feature Comparison](#-feature-comparison-table)  
- 👥 [Contributors](#-contributors)  
- ✨ [Acknowledgement](#-acknowledgement)  
- 📢 [Disclaimer](#-disclaimer)  

---

## 📘 Core Functionalities

### 👥 User Registration
- Passenger (Regular / Student / Senior / PWD)
- Driver (vehicle type, plate number, short bio)

### 🧳 Passenger Features
- Book a ride (passengers, pickup/drop-off, vehicle type)
- Fare estimate & randomized driver assignment
- Track ride progress (with messages at thresholds)
- Cancel under rules (e.g., allowed if \<50% progress)
- Leave reviews & ratings
- View profile & booking history

### 🚙 Driver Features
- Receive ride requests (accept/decline)
- View pickup/drop-off, distance, passengers
- Send short messages to passenger
- Booking history (accepted/declined)
- View passenger reviews, track earnings

### 🔧 System Enhancements
- Randomized driver assignment
- Color-coded console UI (ANSI-friendly)
- ASCII art, animated loading/progress bars

---

## 🗂️ **Project Structure**
```
REMix/
└── JavaRide/
    ├── src/
    │   ├── Main.java
    │   ├── Utility.java
    │   ├── User.java
    │   ├── UserType.java
    │   ├── Passenger.java
    │   ├── Driver.java
    │   ├── Vehicle.java
    │   ├── VehicleType.java
    │   ├── Booking.java
    │   ├── BookingStatus.java
    │   ├── Review.java
    │   ├── DataGenerator.java
    │   └── LocationManager.java
    └── README.md
```
---

## ▶️ How to Run

**Compile all Java files**

```
javac *.java

```

**Run:**

```
java Main
```

---

## 📝 **Console Flow**


```
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

## ✨ **ACKNOWLEDGEMENT** ✨
We, the proponents of JavaRide, sincerely express our gratitude to everyone who supported the completion of this project. We thank our instructor for the guidance, feedback, and encouragement that strengthened our skills and understanding of Object-Oriented Programming, and we appreciate our classmates and peers for their collaboration and helpful insights throughout the process. To our families, thank you for your patience, motivation, and unwavering support during long hours of development and documentation. We also acknowledge the tools, references, and learning materials that contributed to the success of this project. This achievement reflects the collective support and encouragement we received, and we are deeply grateful to all who contributed in any way.

---

## 📢 **DISCLAIMER**

<small> The JavaRide project was developed by Computer Science students as part of the academic requirements for CS 211: Object-Oriented Programming. It is intended exclusively for educational and demonstration purposes. Students are welcome to review and learn from the project, but creating original implementations is highly encouraged. </small>

