// Demonstrates: Enrollment system, Grade handling, Business logic
public class Enrollment {
    private String studentId;
    private String courseCode;
    private double marks;
    private Grade grade;
    
    // Constructor
    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.marks = 0.0;
        this.grade = Grade.F; // Default grade
    }
    
    // Getters
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public double getMarks() { return marks; }
    public Grade getGrade() { return grade; }
    
    // Set marks and automatically calculate grade (PDF Section 2.3)
    public void setMarks(double marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
        this.marks = marks;
        this.grade = Grade.fromMarks(marks); // Auto-calculate grade
    }
    
    // Get grade points for GPA calculation
    public double getGradePoints() {
        return grade.getGradePoints();
    }
    
    // Check if student passed the course
    public boolean isPassed() {
        return grade != Grade.F;
    }
    
    @Override
    public String toString() {
        return "Enrollment{" +
                "studentId='" + studentId + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", marks=" + marks +
                ", grade=" + grade +
                ", gradePoints=" + getGradePoints() +
                ", passed=" + isPassed() +
                '}';
    }
}