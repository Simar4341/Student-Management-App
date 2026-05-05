import java.io.*;
import java.util.*;

// ---------------- STUDENT CLASS ----------------
class Student implements Serializable {
    private int id;
    private String name;
    private List<String> courses;
    private Map<String, Double> grades;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void addCourse(String course) {
        courses.add(course);
    }

    public void addGrade(String course, double grade) {
        grades.put(course, grade);
    }

    public void display() {
        System.out.println("\nID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Courses: " + courses);
        System.out.println("Grades: " + grades);
    }
}

// ---------------- DATABASE CLASS ----------------
class StudentDatabase {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentDatabase() {
        students = loadFromFile();
    }

    public void addStudent(Student s) {
        students.add(s);
        saveToFile();
    }

    public Student findStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (Student s : students) {
            s.display();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private List<Student> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

// ---------------- MAIN SYSTEM ----------------
public class StudentManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static StudentDatabase db = new StudentDatabase();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Add Grade");
            System.out.println("4. View Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addCourse();
                case 3 -> addGrade();
                case 4 -> viewStudent();
                case 5 -> db.displayAll();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudent() {
        int id = getIntInput("Enter ID: ");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        Student s = new Student(id, name);
        db.addStudent(s);
        System.out.println("Student added successfully.");
    }

    private static void addCourse() {
        int id = getIntInput("Enter Student ID: ");
        Student s = db.findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Name: ");
        String course = sc.nextLine();
        s.addCourse(course);

        System.out.println("Course added.");
    }

    private static void addGrade() {
        int id = getIntInput("Enter Student ID: ");
        Student s = db.findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        double grade = getDoubleInput("Enter Grade: ");
        s.addGrade(course, grade);

        System.out.println("Grade recorded.");
    }

    private static void viewStudent() {
        int id = getIntInput("Enter Student ID: ");
        Student s = db.findStudent(id);

        if (s != null) s.display();
        else System.out.println("Student not found.");
    }

    private static int getIntInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private static double getDoubleInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }
}
