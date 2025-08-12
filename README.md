# 🧮 VisitorShapesApp — Elegant Area Calculation with Visitor Pattern

> **Compute areas cleanly — without changing your shape classes.**
> A lightweight, educational, and visually delightful Java Swing application demonstrating the **Visitor design pattern** to compute areas for multiple shape types (Circle, Square, Triangle) while keeping code open for extension.

---

## 🚀 Highlights (Why you’ll love this)

* **Pattern-first** — teaches the Visitor pattern with real UI interactions. 🔍
* **Interactive GUI** — add shapes, compute single or total area, and observe visitor calls in the activity log. 🖱️
* **Single-file, instance-based** — everything implemented as instance inner classes, easy to read and reuse. 🧩
* **Educational & production-ready** — perfect for demos, teaching, or as a starter template for graphics/geometry apps.

---

## ✨ Features

* Add circles, squares, and triangles via friendly dialogs. ➕
* Compute area for a selected shape with `AreaVisitor`. ▶️
* Compute the total area of all shapes by iterating and visiting each. Σ
* Activity log with timestamps and emoji badges. 🧾
* Clean, responsive Swing layout with custom cell rendering for shape list. 🎨

---

## 🧠 Visitor Pattern — Theory (Plain English)

The **Visitor design pattern** separates algorithms from the objects on which they operate. Instead of adding new behavior to many classes (which violates the Open/Closed Principle), we define a **Visitor** that implements operations for each concrete element type.

**Core participants:**

* **Element (Shape)** — declares `accept(Visitor)`.
* **ConcreteElement (Circle, Square, Triangle)** — implements `accept` and delegates to the visitor.
* **Visitor (AreaVisitor)** — declares visit methods for each concrete element type and performs the operation.

**Why use Visitor here?**

* You can add new operations (e.g., perimeter, bounding box) without modifying shape classes.
* Each operation is centralized in one place (visitor), improving maintainability and extensibility.

---

## 🔗 How the Visitor Pattern is applied in this project

* **`Shape`** (interface) — declares `accept(Visitor)` and `getName()`.
* **Concrete shapes** (`Circle`, `Square`, `Triangle`) — each implements `accept(Visitor)` by calling the corresponding `visit(this)`.
* **`Visitor`** (interface) — declares `visit(Circle)`, `visit(Square)`, `visit(Triangle)`.
* **`AreaVisitor`** — concrete visitor that computes and stores the last computed area. The GUI calls `accept(areaVisitor)` on shapes to compute results.
* The GUI demonstrates **double-dispatch**: `shape.accept(visitor)` → `visitor.visit(concreteShape)` so the right method is selected at runtime without `instanceof` checks.

---

## 🛠 Build & Run

**Prerequisites**

* JDK 8+ installed.

**Compile & run**

```bash
javac VisitorShapesApp.java
java VisitorShapesApp
```

If you split classes into files, compile all `.java` files in the same directory:

```bash
javac *.java
java VisitorShapesApp
```

---

## 🧭 Usage Walkthrough (quick demo)

1. Launch the app — you’ll see a list area, control buttons, and an activity log. ✅
2. Click **Add Circle / Square / Triangle** to create shapes with custom sizes. ➕
3. Select a shape in the list and click **Compute Selected Area** — the app uses `AreaVisitor` to compute the area and logs the result. ▶️
4. Click **Compute Total Area** to run the visitor across all shapes and display the sum. Σ
5. Inspect the activity log to see how visitors are invoked — it’s a great teaching aid. 🧾

---

## 📐 UML Diagram

> **Paste your PlantUML or image here**

<p align="center">
  <img width="757" height="685" alt="image" src="https://github.com/user-attachments/assets/7d64396b-dd68-4e4a-8d7c-ac95857c10cb" />
</p>

---

## 📸 GUI Screenshot

> **Insert your GUI screenshot here**

<img width="1075" height="651" alt="image" src="https://github.com/user-attachments/assets/62234ac3-2e8b-432d-82a7-dfe5c2a7cdd1" />

---

## 🔧 Code Structure (conceptual)

```
VisitorShapesApp (main)    -> builds UI and demonstrates visitor usage
Shape (interface)          -> accept(Visitor), getName()
Circle, Square, Triangle   -> concrete elements (implement accept)
Visitor (interface)        -> visit(Circle), visit(Square), visit(Triangle)
AreaVisitor                -> computes area (visit methods)
Dialog classes             -> UI helpers to add shapes
Cell Renderer              -> nicer list display
```

---

## 💡 Extensions & Next Steps

* Add `PerimeterVisitor` or `BoundingBoxVisitor` to showcase multiple operations. 🔁
* Export shapes and computed metrics to JSON/CSV. 📤
* Add a drawing canvas to display shapes graphically and interactively. 🖼️
* Integrate unit tests for Visitor logic (e.g., area formulas). 🧪

---

## 🧾 License & Credits

Use this as you like — attribution appreciated. Suggestion: add an `LICENSE` file (MIT) if you plan to publish.

---

## 🎉 Final Notes

This app is designed to be both an effective learning tool and a polished demo. Paste your UML and screenshot in the placeholders above, and you’ll have a presentation-ready README that’s both **professional** and **approachable**. Want me to paste a PlantUML diagram into the UML section now?


