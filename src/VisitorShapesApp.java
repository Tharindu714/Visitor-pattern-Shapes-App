import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

// -------------------------
// Visitor Pattern: Shape & Visitor interfaces
// -------------------------
/**
 * Shape: element of the object structure.
 * Each concrete shape implement accepts (Visitor).
 */
interface Shape {
    void accept(Visitor v);   // accept a visitor
    String getName();
}

/**
 * Visitor: declares visit operations for each concrete Shape type.
 */
interface Visitor {
    void visit(Circle c);
    void visit(Square s);
    void visit(Triangle t);
}

// -------------------------
// Concrete Shape implementations
// -------------------------
class Circle implements Shape {
    private final String name;
    private final double radius;

    Circle(String name, double radius) {
        this.name = name;
        this.radius = radius;
    }

    public double getRadius() { return radius; }
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this); // double-dispatch: shape calls visitor.visit(this)
        System.out.println("Circle.accept(Visitor) called for " + getName());
    }

    @Override
    public String toString() {
        return String.format("Circle('%s', r=%.2f)", name, radius);
    }
}

class Square implements Shape {
    private final String name;
    private final double side;

    Square(String name, double side) {
        this.name = name;
        this.side = side;
    }

    public double getSide() { return side; }
    public String getName() { return name; }

    @Override
    public void accept(Visitor v) { v.visit(this); }

    @Override
    public String toString() {
        return String.format("Square('%s', s=%.2f)", name, side);
    }
}

class Triangle implements Shape {
    private final String name;
    private final double base;
    private final double height;

    Triangle(String name, double base, double height) {
        this.name = name;
        this.base = base;
        this.height = height;
    }

    public double getBase() { return base; }
    public double getHeight() { return height; }
    public String getName() { return name; }

    @Override
    public void accept(Visitor v) { v.visit(this); }

    @Override
    public String toString() {
        return String.format("Triangle('%s', b=%.2f, h=%.2f)", name, base, height);
    }
}

// -------------------------
// Concrete Visitor: AreaVisitor
// -------------------------
class AreaVisitor implements Visitor {
    private double area;

    @Override
    public void visit(Circle c) {
        area = Math.PI * c.getRadius() * c.getRadius();
    }

    @Override
    public void visit(Square s) {
        area = s.getSide() * s.getSide();
    }

    @Override
    public void visit(Triangle t) {
        area = 0.5 * t.getBase() * t.getHeight();
    }

    public double getArea() { return area; }
}

// -------------------------
// Small input dialogs for shapes
// -------------------------
abstract class ShapeInputDialog<T extends Shape> extends JDialog {
    protected T created;

    ShapeInputDialog(Frame owner, String title) {
        super(owner, true);
        setTitle(title);
        setSize(360, 220);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8,8));
        setResizable(false);
    }

    T getCreated() { return created; }
}

class CircleInputDialog extends ShapeInputDialog<Circle> {
     JTextField nameField;
     JSpinner radiusSpinner;

    CircleInputDialog(Frame owner) {
        super(owner, "Add Circle");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(10,10,10,10));

        nameField = new JTextField("Circle");
        p.add(new JLabel("Name:"));
        p.add(nameField);
        p.add(Box.createVerticalStrut(8));

        radiusSpinner = new JSpinner(new SpinnerNumberModel(30.0, 1.0, 1000.0, 1.0));
        p.add(new JLabel("Radius:"));
        p.add(radiusSpinner);
        p.add(Box.createVerticalStrut(12));

        JPanel batons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        batons.add(ok); batons.add(cancel);

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            double r = ((Number) radiusSpinner.getValue()).doubleValue();
            created = new Circle(name.isEmpty() ? "Circle" : name, r);
            setVisible(false);
        });
        cancel.addActionListener(e -> { created = null; setVisible(false); });

        add(p, BorderLayout.CENTER);
        add(batons, BorderLayout.SOUTH);
    }
}

class SquareInputDialog extends ShapeInputDialog<Square> {
     JTextField nameField;
     JSpinner sideSpinner;

    SquareInputDialog(Frame owner) {
        super(owner, "Add Square");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(10,10,10,10));

        nameField = new JTextField("Square");
        p.add(new JLabel("Name:"));
        p.add(nameField);
        p.add(Box.createVerticalStrut(8));

        sideSpinner = new JSpinner(new SpinnerNumberModel(40.0, 1.0, 2000.0, 1.0));
        p.add(new JLabel("Side length:"));
        p.add(sideSpinner);
        p.add(Box.createVerticalStrut(12));

        JPanel batons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        batons.add(ok); batons.add(cancel);

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            double s = ((Number) sideSpinner.getValue()).doubleValue();
            created = new Square(name.isEmpty() ? "Square" : name, s);
            setVisible(false);
        });
        cancel.addActionListener(e -> { created = null; setVisible(false); });

        add(p, BorderLayout.CENTER);
        add(batons, BorderLayout.SOUTH);
    }
}

class TriangleInputDialog extends ShapeInputDialog<Triangle> {
     JTextField nameField;
     JSpinner baseSpinner;
     JSpinner heightSpinner;

    TriangleInputDialog(Frame owner) {
        super(owner, "Add Triangle");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(10,10,10,10));

        nameField = new JTextField("Triangle");
        p.add(new JLabel("Name:"));
        p.add(nameField);
        p.add(Box.createVerticalStrut(8));

        baseSpinner = new JSpinner(new SpinnerNumberModel(60.0, 1.0, 2000.0, 1.0));
        heightSpinner = new JSpinner(new SpinnerNumberModel(40.0, 1.0, 2000.0, 1.0));
        p.add(new JLabel("Base:"));
        p.add(baseSpinner);
        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Height:"));
        p.add(heightSpinner);
        p.add(Box.createVerticalStrut(12));

        JPanel batons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        batons.add(ok); batons.add(cancel);

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            double b = ((Number) baseSpinner.getValue()).doubleValue();
            double h = ((Number) heightSpinner.getValue()).doubleValue();
            created = new Triangle(name.isEmpty() ? "Triangle" : name, b, h);
            setVisible(false);
        });
        cancel.addActionListener(e -> { created = null; setVisible(false); });

        add(p, BorderLayout.CENTER);
        add(batons, BorderLayout.SOUTH);
    }
}

// -------------------------
// Cell renderer for shapes
// -------------------------
class ShapeCellRenderer extends JLabel implements ListCellRenderer<Shape> {
    public ShapeCellRenderer() { setOpaque(true); setBorder(new EmptyBorder(6,6,6,6)); }

    @Override
    public Component getListCellRendererComponent(JList<? extends Shape> list, Shape value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        setFont(getFont().deriveFont(13f));
        if (isSelected) {
            setBackground(new Color(30, 144, 255));
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}
/**
 * VisitorShapesApp.java
 * Single-file demo of the Visitor pattern for computing areas of shapes.
 * - All classes are instance inner classes (no static nested classes)
 * - Public main class: VisitorShapesApp
 * Run:
 *   javac VisitorShapesApp.java * VisitorShapesApp
 */
public class VisitorShapesApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisitorShapesApp().start());
    }

    // -------------------------
    // App state & UI fields
    // -------------------------
    private JFrame frame;
    private DefaultListModel<Shape> model;
    private JList<Shape> shapeJList;
    private JTextArea logArea;
    private final DecimalFormat fmt = new DecimalFormat("#,##0.00");

    // -------------------------
    // Start the app
    // -------------------------
    private void start() {
        model = new DefaultListModel<>();
        buildUI();

        // Preload a few shapes to demonstrate
        model.addElement(new Circle("Circle A", 40));
        model.addElement(new Square("Square A", 60));
        model.addElement(new Triangle("Triangle A", 80, 50));

        log("✨ VisitorShapesApp started — add shapes, then use the Area visitor to compute areas.");
    }

    // -------------------------
    // Build GUI
    // -------------------------
    private void buildUI() {
        frame = new JFrame("🧮 Visitor Pattern — Shape Areas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 560);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Left - list of shapes and actions
        JPanel left = new JPanel(new BorderLayout(8,8));
        left.setBorder(new EmptyBorder(12,12,12,0));
        left.setPreferredSize(new Dimension(320, 0));

        JLabel title = new JLabel("<html><h2>Shapes</h2></html>");
        left.add(title, BorderLayout.NORTH);

        shapeJList = new JList<>(model);
        shapeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shapeJList.setCellRenderer(new ShapeCellRenderer());
        JScrollPane listScroll = new JScrollPane(shapeJList);
        left.add(listScroll, BorderLayout.CENTER);

        JPanel batons = new JPanel(new GridLayout(0, 1, 6, 6));
        JButton addCircle = new JButton("➕ Add Circle");
        JButton addSquare = new JButton("➕ Add Square");
        JButton addTri = new JButton("➕ Add Triangle");
        JButton remove = new JButton("🗑 Remove Selected");
        JButton clear = new JButton("🧹 Clear All");

        batons.add(addCircle);
        batons.add(addSquare);
        batons.add(addTri);
        batons.add(remove);
        batons.add(clear);
        left.add(batons, BorderLayout.SOUTH);

        // Center - explanatory panel + actions
        JPanel center = new JPanel(new BorderLayout(8,8));
        center.setBorder(new EmptyBorder(12,8,12,8));

        JLabel explain = new JLabel("<html><h3>Visitor Pattern — Compute Areas</h3>"
                + "<p>Use the Area Visitor to compute the area of a selected shape "
                + "or the total area of all shapes. The shapes implement <b>accept(Visitor)</b>, "
                + "and visitors perform operations without changing shape classes.</p></html>");
        center.add(explain, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        JButton computeSelected = new JButton("▶ Compute Selected Area");
        JButton computeAll = new JButton("▶ Compute Total Area");
        JButton showSource = new JButton("📄 Show Visitor Log (explain)");

        actionPanel.add(computeSelected);
        actionPanel.add(computeAll);
        actionPanel.add(showSource);
        center.add(actionPanel, BorderLayout.CENTER);

        // Right - log area
        JPanel right = new JPanel(new BorderLayout(8,8));
        right.setBorder(new EmptyBorder(12,0,12,12));
        right.setPreferredSize(new Dimension(340, 0));

        JLabel logTitle = new JLabel("<html><h3>Activity Log</h3></html>");
        right.add(logTitle, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane logScroll = new JScrollPane(logArea);
        right.add(logScroll, BorderLayout.CENTER);

        // Wire events -----------------------------------------------------

        addCircle.addActionListener(e -> {
            CircleInputDialog d = new CircleInputDialog(frame);
            d.setVisible(true);
            Circle c = d.getCreated();
            if (c != null) {
                model.addElement(c);
                log("➕ Added " + c);
            }
        });

        addSquare.addActionListener(e -> {
            SquareInputDialog d = new SquareInputDialog(frame);
            d.setVisible(true);
            Square s = d.getCreated();
            if (s != null) {
                model.addElement(s);
                log("➕ Added " + s);
            }
        });

        addTri.addActionListener(e -> {
            TriangleInputDialog d = new TriangleInputDialog(frame);
            d.setVisible(true);
            Triangle t = d.getCreated();
            if (t != null) {
                model.addElement(t);
                log("➕ Added " + t);
            }
        });

        remove.addActionListener(e -> {
            Shape sel = shapeJList.getSelectedValue();
            if (sel != null) {
                model.removeElement(sel);
                log("🗑 Removed " + sel);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a shape to remove.", "No selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        clear.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(frame, "Clear all shapes from the list?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                model.clear();
                log("🧹 Cleared all shapes.");
            }
        });

        computeSelected.addActionListener(e -> {
            Shape sel = shapeJList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Select a shape to compute area.", "No selection", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Use AreaVisitor to compute area of one shape
            AreaVisitor visitor = new AreaVisitor();
            sel.accept(visitor);               // <-- Visitor used here
            double area = visitor.getArea();   // <-- result obtained from a visitor
            log("▶ Area for " + sel + " = " + fmt.format(area) + " units²");
        });

        computeAll.addActionListener(e -> {
            AreaVisitor visitor = new AreaVisitor();
            double total = 0.0;
            // iterate shapes and use a visitor for each
            for (int i = 0; i < model.size(); i++) {
                Shape s = model.get(i);
                s.accept(visitor);
                double a = visitor.getArea();
                log(String.format(" • %s => %s units²", s, fmt.format(a)));
                total += a;
            }
            log("Σ Total area = " + fmt.format(total) + " units²");
        });

        showSource.addActionListener(e -> {
            log("ℹ️ Visitor pattern demo: Shape.accept(Visitor) lets visitors compute operations (area) without changing shape classes.");
            log("Example call: Circle.accept(new AreaVisitor()); // triggers AreaVisitor.visit(Circle)");
        });

        // Assemble frame
        frame.add(left, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        frame.add(right, BorderLayout.EAST);
        frame.setVisible(true);
    }

    // -------------------------
    // Logging helper
    // -------------------------
    private void log(String text) {
        String stamp = "[" + java.time.LocalTime.now().withNano(0).toString() + "] ";
        logArea.append(stamp + text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
        System.out.println(stamp + text);
        System.out.println("=".repeat(80)); // separator for console output
    }
}