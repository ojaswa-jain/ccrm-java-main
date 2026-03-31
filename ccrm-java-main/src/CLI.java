import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private DataStore dataStore;
    
    public CLI() {
        scanner = new Scanner(System.in);
        dataStore = DataStore.getInstance();
    }
    
    public void start() {
        System.out.println("=== Campus Course & Records Manager (CCRM) ===");
        System.out.println("Welcome to the Campus Management System!\n");
        
        // Try to import existing data
        try {
            System.out.println("Loading existing data...");
            FileManager.importStudents();
            FileManager.importCourses();
            FileManager.importEnrollments();
            System.out.println("Data loaded successfully!\n");
        } catch (IOException e) {
            System.out.println("No existing data found. Starting with empty system.\n");
        }
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageEnrollments();
                    break;
                case 4:
                    manageGrades();
                    break;
                case 5:
                    generateReports();
                    break;
                case 6:
                    handleFileOperations();
                    break;
                case 7:
                    System.out.println("Thank you for using CCRM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grade Management");
        System.out.println("5. Reports & Analytics");
        System.out.println("6. File Operations");
        System.out.println("7. Exit");
    }
    
    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                viewAllStudents();
                break;
            case 3:
                updateStudent();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = getStringInput("Student ID: ");
        String regNo = getStringInput("Registration Number: ");
        String fullName = getStringInput("Full Name: ");
        String email = getStringInput("Email: ");
        
        Student student = new Student(id, regNo, fullName, email);
        dataStore.addStudent(student);
        System.out.println("Student added successfully!");
    }
    
    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        java.util.List<Student> students = dataStore.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + " | Name: " + student.getFullName() + 
                             " | Email: " + student.getEmail() + " | Status: " + student.getStatus());
        }
    }
    
    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        String id = getStringInput("Enter student ID to update: ");
        Student student = dataStore.getStudent(id);
        
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current email: " + student.getEmail());
        String newEmail = getStringInput("New email (press Enter to keep current): ");
        if (!newEmail.isEmpty()) {
            student.setEmail(newEmail);
        }
        
        System.out.println("Current status: " + student.getStatus());
        String newStatus = getStringInput("New status (ACTIVE/INACTIVE, press Enter to keep current): ");
        if (!newStatus.isEmpty()) {
            student.setStatus(newStatus.toUpperCase());
        }
        
        System.out.println("Student updated successfully!");
    }
    
    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                viewAllCourses();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        String code = getStringInput("Course Code: ");
        String title = getStringInput("Course Title: ");
        int credits = getIntInput("Credits: ");
        String instructor = getStringInput("Instructor: ");
        
        System.out.println("Available Semesters: SPRING, SUMMER, FALL");
        String semesterInput = getStringInput("Semester: ").toUpperCase();
        Semester semester = Semester.valueOf(semesterInput);
        
        String department = getStringInput("Department: ");
        
        Course course = new Course(code, title, credits, instructor, semester, department);
        dataStore.addCourse(course);
        System.out.println("Course added successfully!");
    }
    
    private void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        java.util.List<Course> courses = dataStore.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        for (Course course : courses) {
            System.out.println("Code: " + course.getCode() + " | Title: " + course.getTitle() + 
                             " | Credits: " + course.getCredits() + " | Instructor: " + course.getInstructor());
        }
    }
    
    private void manageEnrollments() {
        System.out.println("\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. View All Enrollments");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                enrollStudent();
                break;
            case 2:
                viewAllEnrollments();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        // Check if student exists
        if (dataStore.getStudent(studentId) == null) {
            System.out.println("Student not found!");
            return;
        }
        
        // Check if course exists
        if (dataStore.getCourse(courseCode) == null) {
            System.out.println("Course not found!");
            return;
        }
        
        Enrollment enrollment = new Enrollment(studentId, courseCode);
        dataStore.addEnrollment(enrollment);
        System.out.println("Student enrolled successfully!");
    }
    
    private void viewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        boolean found = false;
        
        for (Student student : dataStore.getAllStudents()) {
            java.util.List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(student.getId());
            if (!enrollments.isEmpty()) {
                found = true;
                System.out.println("\nStudent: " + student.getFullName());
                for (Enrollment enrollment : enrollments) {
                    Course course = dataStore.getCourse(enrollment.getCourseCode());
                    System.out.println("  - " + course.getCode() + ": " + course.getTitle() + 
                                     " (Grade: " + enrollment.getGrade() + ")");
                }
            }
        }
        
        if (!found) {
            System.out.println("No enrollments found.");
        }
    }
    
    private void manageGrades() {
        System.out.println("\n--- Grade Management ---");
        System.out.println("1. Enter Grades");
        System.out.println("2. View Student GPA");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                enterGrades();
                break;
            case 2:
                viewStudentGPA();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void enterGrades() {
        System.out.println("\n--- Enter Grades ---");
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        // Find enrollment
        Enrollment enrollment = null;
        for (Enrollment e : dataStore.getEnrollmentsByStudent(studentId)) {
            if (e.getCourseCode().equals(courseCode)) {
                enrollment = e;
                break;
            }
        }
        
        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }
        
        double marks = getDoubleInput("Enter marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }
        
        enrollment.setMarks(marks);
        System.out.println("Grade recorded: " + enrollment.getGrade());
    }
    
    private void viewStudentGPA() {
        System.out.println("\n--- View Student GPA ---");
        String studentId = getStringInput("Student ID: ");
        
        Student student = dataStore.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        java.util.List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }
        
        double totalGradePoints = 0;
        int totalCredits = 0;
        
        System.out.println("\nStudent: " + student.getFullName());
        System.out.println("Courses:");
        
        for (Enrollment enrollment : enrollments) {
            Course course = dataStore.getCourse(enrollment.getCourseCode());
            totalGradePoints += enrollment.getGradePoints() * course.getCredits();
            totalCredits += course.getCredits();
            
            System.out.println("  - " + course.getCode() + ": " + enrollment.getGrade() + 
                             " (" + enrollment.getMarks() + "%)");
        }
        
        double gpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0;
        System.out.println("\nGPA: " + String.format("%.2f", gpa));
    }
    
    private void generateReports() {
        System.out.println("\n--- Reports & Analytics ---");
        System.out.println("1. Student Transcripts");
        System.out.println("2. Course Statistics");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                generateTranscripts();
                break;
            case 2:
                showCourseStats();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void generateTranscripts() {
        System.out.println("\n--- Student Transcripts ---");
        for (Student student : dataStore.getAllStudents()) {
            System.out.println("\n=== Transcript for " + student.getFullName() + " ===");
            System.out.println("Student ID: " + student.getId());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Status: " + student.getStatus());
            System.out.println("\nCourses:");
            
            java.util.List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(student.getId());
            if (enrollments.isEmpty()) {
                System.out.println("  No courses enrolled.");
                continue;
            }
            
            double totalGradePoints = 0;
            int totalCredits = 0;
            
            for (Enrollment enrollment : enrollments) {
                Course course = dataStore.getCourse(enrollment.getCourseCode());
                totalGradePoints += enrollment.getGradePoints() * course.getCredits();
                totalCredits += course.getCredits();
                
                System.out.println("  - " + course.getCode() + ": " + course.getTitle());
                System.out.println("    Grade: " + enrollment.getGrade() + " | Marks: " + enrollment.getMarks() + "%");
                System.out.println("    Credits: " + course.getCredits() + " | Instructor: " + course.getInstructor());
            }
            
            double gpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0;
            System.out.println("\nOverall GPA: " + String.format("%.2f", gpa));
            System.out.println("==================================================");
        }
    }
    
    private void showCourseStats() {
        System.out.println("\n--- Course Statistics ---");
        for (Course course : dataStore.getAllCourses()) {
            java.util.List<Enrollment> enrollments = dataStore.getEnrollmentsByCourse(course.getCode());
            System.out.println("\nCourse: " + course.getCode() + " - " + course.getTitle());
            System.out.println("Enrolled students: " + enrollments.size());
            
            if (!enrollments.isEmpty()) {
                double totalMarks = 0;
                for (Enrollment enrollment : enrollments) {
                    totalMarks += enrollment.getMarks();
                }
                double average = totalMarks / enrollments.size();
                System.out.println("Average marks: " + String.format("%.1f", average) + "%");
            }
        }
    }
    
    private void handleFileOperations() {
        System.out.println("\n--- File Operations ---");
        System.out.println("1. Export Data to CSV");
        System.out.println("2. Import Data from CSV");
        System.out.println("3. Create Backup");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        try {
            switch (choice) {
                case 1:
                    FileManager.exportStudents();
                    FileManager.exportCourses();
                    FileManager.exportEnrollments();
                    System.out.println("Data exported successfully!");
                    break;
                case 2:
                    FileManager.importStudents();
                    FileManager.importCourses();
                    FileManager.importEnrollments();
                    System.out.println("Data imported successfully!");
                    break;
                case 3:
                    FileManager.backupData();
                    System.out.println("Backup created successfully!");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}