Marine Ecosystem - 2D Simulation

## Project Description
This project is a computational simulation of a simplified marine ecosystem, developed for the **Programming II** course. The simulation takes place on a 2D grid where various organisms interact, feed, reproduce, and die according to specific biological rules.

## Class Hierarchy (OOP Architecture)
The project was designed focusing on **Inheritance** and **Polymorphism** principles, following this structure:

* **`Organism` (Abstract):** The base class defining position (`x, y`), age, and basic life cycle.
* **`Algae`:** A producer organism (static) that serves as the foundation of the food chain.
* **`Animal` (Abstract):** Extends `Organism` and adds **Energy** management and starvation logic.
    * **`Zooplankton`:** Primary consumer (feeds on Algae).
    * **`Jellyfish` :** Secondary consumer (feeds on Zooplankton; features sexual reproduction).
    * **`Turtle` :** Apex predator (selective diet and complex sexual reproduction).



## Implemented Features
* **Movement:** Algorithms to search for adjacent free cells based on specific movement ranges.
* **Food Chain:** Dynamic interactions where predators remove prey from the grid and gain energy.
* **Reproduction:**
    * *Asexual:* (e.g., Algae and Zooplankton) based on probability and age.
    * *Sexual:* (e.g., Turtles and Jellyfish) requires the proximity of a compatible partner.
* **Turn Management:** The `step()` method in the `Grid` class utilizes **Snapshots** to ensure the simulation remains consistent and free from `ConcurrentModificationException`.

## Technologies & Concepts
* **Java 17+**
* **OOP Principles:** Abstraction, Encapsulation, Inheritance, and Polymorphism.
* **Refactoring:** Applied "Extract Method" technique to ensure high cohesion and the **Single Responsibility Principle (SRP)**.
* **Collections:** Efficient use of `ArrayList` and Lambda predicates (`removeIf`) for object management.

---
### Technical Note for Evaluation
The simulation engine is **extensible** (Open/Closed Principle). New species can be added by simply extending the `Animal` or `Organismo` classes without needing to modify the core logic of the Grid class.
