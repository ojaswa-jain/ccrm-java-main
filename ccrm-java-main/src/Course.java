// Demonstrates: OOP, Encapsulation, Enums, toString() override
public class Course {
    // Private fields (Encapsulation - PDF Section 3)
    private String code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester; // Enum (PDF Section 2.3)
    private String department;
    private boolean active;
    
    // Constructor
    public Course(String code, String title, int credits, String instructor, 
                 Semester semester, String department) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.semester = semester;
        this.department = department;
        this.active = true;
    }
    
    // Getters and setters (Encapsulation)
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setActive(boolean active) { this.active = active; }
    
    // Business method to check if course is available
    public boolean isAvailable() {
        return active && credits > 0;
    }
    
    // Override toString() (PDF Section 3)
    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", instructor='" + instructor + '\'' +
                ", semester=" + semester +
                ", department='" + department + '\'' +
                ", active=" + active +
                '}';
    }
}