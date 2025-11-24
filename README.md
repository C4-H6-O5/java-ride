
<h1 align="center"># ![JavaRide Logo](https://github.com/C4-H6-O5/java-ride/blob/0f8f7b17765a04145104b53c8ba96504d66093ad/logo.png)</h1>

<h1 align="center">JavaRide</h1>
<h3 align="center"><i>Brewing Better Rides</i></h3>
<p align="center">by REMix</p>
<p align="center">Regina * Elaiza * Mhalik</p>
---

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

## 🔄 **System Flow (Mermaid Diagram)**

```mermaid
flowchart TD
    A[Start / Login Page] --> B{Select User Type}
    
    %% Passenger Flow
    B -->|Passenger| C[Passenger Main Menu]
    C --> D[Book a Ride]
    D --> E[Enter Ride Details]
    E --> F[System Generates Random Driver]
    F --> G{Confirm Ride?}
    G -->|Yes| H[Track Ride Progress]
    G -->|No| C
    H --> I{Progress >= 80%?}
    I -->|Yes| J[Driver Message: "I'm on my way!"]
    J --> H
    H --> K[Arrive at Destination]
    K --> L[Leave Review & Rating]
    L --> C
    C --> M[Check Reviews & Ratings]
    C --> N[View Profile / Booking History / Delete Account]

    %% Driver Flow
    B -->|Driver| O[Driver Main Menu]
    O --> P[Receive Ride Requests]
    P --> Q{Accept or Decline?}
    Q -->|Accept| R[Complete Ride → Receive Review & Rating]
    Q -->|Decline| S[Send Short Message to Passenger]
    R --> O
    S --> O
    O --> T[Booking History: Accepted / Declined]
    O --> U[View Reviews]
    O --> V[My Earnings → Cash Out]
```

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

| Member             | Role                                                          | Responsibilities                                                                                                                                                                                          |
| ------------------ | ------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🧑‍💻 **Regina Bool** | Core Developer (Logic Lead)                                   | Handles program flow, ride simulation, and input handling.<br>- Set up `main()` and menus<br>- Connect classes together<br>- Simulate booking process                                                     |
| 👩‍💻 **Mhalik Perez** | OOP & Structure Lead (Back-End)                               | Focuses on classes, inheritance, and OOP structure.<br>- Create classes like `Vehicle`, `Driver`, `Passenger`<br>- Add constructors, methods, and encapsulation<br>- Design relationships between classes |
| 🧑‍🎨 **Elaiza Espartinez** | Design & Documentation Lead (Front-End / UI ) | Handles console UI, colors, ASCII art, and documentation.<br>- Improve menu design (ASCII borders, color codes)<br>- Add loading or progress animations<br>- Maintain `README.md` and commit updates      |


---

## 📢 **Student Disclaimer**

<small>
This project was created by students as part of an academic requirement.  
It is intended solely for learning and demonstration purposes.  
Students may reference it, but original implementations are strongly encouraged.
</small>

