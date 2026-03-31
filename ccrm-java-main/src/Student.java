import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Demonstrates: OOP, Encapsulation, Date/Time API, Collections
public class Student {
    // Private fields (Encapsulation - PDF Section 3)
    private String id;
    private String regNo;
    private String fullName;
    private String email;
    private String status;
    private LocalDate enrollmentDate; // Date/Time API (PDF Section 2.1)
    private List<String> enrolledCourses; // Collections (PDF Section 3)
    
    // Constructor
    public Student(String id, String regNo, String fullName, String email) {
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.status = "ACTIVE";
        this.enrollmentDate = LocalDate.now(); // Date/Time API
        this.enrolledCourses = new ArrayList<>(); // Collections
    }
    
    // Getters and setters (Encapsulation - PDF Section 3)
    public String getId() { return id; }
    public String getRegNo() { return regNo; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }
    
    public void setStatus(String status) { this.status = status; }
    public void setEmail(String email) { this.email = email; }
    
    // Methods for enrollment (PDF Section 2.3)
    public void enrollInCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }
    
    public void unenrollFromCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }
    
    // Override toString() (PDF Section 3 - "Override toString() in domain classes")
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", regNo='" + regNo + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
}