
### 🚀 Project Title  
# **RideHailingApp** — Console-based Ride Hailing Simulation

### 📝 Description / Overview  
RideHailingApp is a **console-only Java application** that simulates a ride-hailing service.  
Users can register or log in as **Passengers** or **Drivers**, book rides, view history, and add reviews.  
The project demonstrates **OOP principles** (Encapsulation, Inheritance, Polymorphism, Abstraction) and features a **polished console UI**: ASCII art, color output, and loading animations.

### 💡 OOP Concepts Used  
- **Encapsulation**: Private fields with getters/setters (e.g., `User`, `Vehicle`)  
- **Inheritance**: `User` → `Passenger`, `Driver`; `Vehicle` → `Motorcycle`, `NormalCar`, `PremiumCar`  
- **Polymorphism**: `Vehicle` references hold subclass instances; methods like `getType()` are overridden  
- **Abstraction**: `Vehicle` is an abstract class  
- **Exception Handling**: Input parsing and thread sleep wrapped with `try/catch`  
- **Collections**: `ArrayList` used for users, drivers, vehicles, bookings, and reviews  

### 🏗️ Program Structure (Files)  
- `Main.java` — Entry point; handles menus, ASCII art, colors, animations  
- `User.java` — Parent class for users (name, age)  
- `Passenger.java` — Extends `User`  
- `Driver.java` — Extends `User`; holds a `Vehicle` reference  


### ▶️ How to Run the Program  

