import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileManager {
    private static final String DATA_DIR = "data/";
    private static final String BACKUP_DIR = "backups/";
    
    // Export students to CSV
    public static void exportStudents() throws IOException {
        Path path = Paths.get(DATA_DIR + "students.csv");
        StringBuilder content = new StringBuilder("id,regNo,fullName,email,status,enrollmentDate\n");
        
        DataStore dataStore = DataStore.getInstance();
        for (Student student : dataStore.getAllStudents()) {
            content.append(student.getId()).append(",")
                   .append(student.getRegNo()).append(",")
                   .append(student.getFullName()).append(",")
                   .append(student.getEmail()).append(",")
                   .append(student.getStatus()).append(",")
                   .append(student.getEnrollmentDate()).append("\n");
        }
        
        // Create directory if it doesn't exist
        Files.createDirectories(Paths.get(DATA_DIR));
        
        Files.write(path, content.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Students exported to " + path.toString());
    }
    
    // Import students from CSV
    public static void importStudents() throws IOException {
        Path path = Paths.get(DATA_DIR + "students.csv");
        if (!Files.exists(path)) {
            System.out.println("No students file found to import.");
            return;
        }
        
        List<String> lines = Files.readAllLines(path);
        DataStore dataStore = DataStore.getInstance();
        
        // Skip header line (index 0)
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                Student student = new Student(parts[0], parts[1], parts[2], parts[3]);
                if (parts.length >= 5) student.setStatus(parts[4]);
                dataStore.addStudent(student);
            }
        }
        System.out.println("Students imported from " + path.toString() + " (" + (lines.size() - 1) + " records)");
    }
    
    // Export courses to CSV
    public static void exportCourses() throws IOException {
        Path path = Paths.get(DATA_DIR + "courses.csv");
        StringBuilder content = new StringBuilder("code,title,credits,instructor,semester,department,active\n");
        
        DataStore dataStore = DataStore.getInstance();
        for (Course course : dataStore.getAllCourses()) {
            content.append(course.getCode()).append(",")
                   .append(course.getTitle()).append(",")
                   .append(course.getCredits()).append(",")
                   .append(course.getInstructor()).append(",")
                   .append(course.getSemester()).append(",")
                   .append(course.getDepartment()).append(",")
                   .append(course.isActive()).append("\n");
        }
        
        Files.createDirectories(Paths.get(DATA_DIR));
        Files.write(path, content.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Courses exported to " + path.toString());
    }
    
    // Import courses from CSV
    public static void importCourses() throws IOException {
        Path path = Paths.get(DATA_DIR + "courses.csv");
        if (!Files.exists(path)) {
            System.out.println("No courses file found to import.");
            return;
        }
        
        List<String> lines = Files.readAllLines(path);
        DataStore dataStore = DataStore.getInstance();
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                // Parse semester from string to enum
                Semester semester = Semester.valueOf(parts[4].toUpperCase());
                
                Course course = new Course(parts[0], parts[1], Integer.parseInt(parts[2]), 
                                         parts[3], semester, parts[5]);
                
                if (parts.length >= 7) {
                    course.setActive(Boolean.parseBoolean(parts[6]));
                }
                
                dataStore.addCourse(course);
            }
        }
        System.out.println("Courses imported from " + path.toString() + " (" + (lines.size() - 1) + " records)");
    }
    
    // Export enrollments to CSV
    public static void exportEnrollments() throws IOException {
        Path path = Paths.get(DATA_DIR + "enrollments.csv");
        StringBuilder content = new StringBuilder("studentId,courseCode,marks,grade\n");
        
        DataStore dataStore = DataStore.getInstance();
        for (Student student : dataStore.getAllStudents()) {
            List<Enrollment> enrollments = dataStore.getEnrollmentsByStudent(student.getId());
            for (Enrollment enrollment : enrollments) {
                content.append(enrollment.getStudentId()).append(",")
                       .append(enrollment.getCourseCode()).append(",")
                       .append(enrollment.getMarks()).append(",")
                       .append(enrollment.getGrade()).append("\n");
            }
        }
        
        Files.createDirectories(Paths.get(DATA_DIR));
        Files.write(path, content.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Enrollments exported to " + path.toString());
    }
    
    // Import enrollments from CSV
    public static void importEnrollments() throws IOException {
        Path path = Paths.get(DATA_DIR + "enrollments.csv");
        if (!Files.exists(path)) {
            System.out.println("No enrollments file found to import.");
            return;
        }
        
        List<String> lines = Files.readAllLines(path);
        DataStore dataStore = DataStore.getInstance();
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                Enrollment enrollment = new Enrollment(parts[0], parts[1]);
                enrollment.setMarks(Double.parseDouble(parts[2]));
                // Grade is automatically set when marks are set
                dataStore.addEnrollment(enrollment);
            }
        }
        System.out.println("Enrollments imported from " + path.toString() + " (" + (lines.size() - 1) + " records)");
    }
    
    // Backup data with timestamp
    public static void backupData() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String backupDirName = BACKUP_DIR + "backup_" + timestamp + "/";
        
        Path backupPath = Paths.get(backupDirName);
        Files.createDirectories(backupPath);
        
        // Export current data
        exportStudents();
        exportCourses();
        exportEnrollments();
        
        // Copy files to backup directory
        Path sourceStudents = Paths.get(DATA_DIR + "students.csv");
        Path sourceCourses = Paths.get(DATA_DIR + "courses.csv");
        Path sourceEnrollments = Paths.get(DATA_DIR + "enrollments.csv");
        
        if (Files.exists(sourceStudents)) {
            Files.copy(sourceStudents, backupPath.resolve("students.csv"));
        }
        if (Files.exists(sourceCourses)) {
            Files.copy(sourceCourses, backupPath.resolve("courses.csv"));
        }
        if (Files.exists(sourceEnrollments)) {
            Files.copy(sourceEnrollments, backupPath.resolve("enrollments.csv"));
        }
        
        System.out.println("Backup created at: " + backupPath.toString());
    }
}