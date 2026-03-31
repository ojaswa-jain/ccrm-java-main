import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    private static DataStore instance;
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private List<Enrollment> enrollments;
    
    private DataStore() {
        students = new HashMap<>();
        courses = new HashMap<>();
        enrollments = new ArrayList<>();
    }
    
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }
    
    // Student methods
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }
    
    public Student getStudent(String id) {
        return students.get(id);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    // Course methods
    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }
    
    public Course getCourse(String code) {
        return courses.get(code);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    // Enrollment methods
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        
        // ALSO enroll the student in the course
        Student student = students.get(enrollment.getStudentId());
        if (student != null) {
            student.enrollInCourse(enrollment.getCourseCode());
        }
    }
    
    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId)) {
                result.add(e);
            }
        }
        return result;
    }
    
    public List<Enrollment> getEnrollmentsByCourse(String courseCode) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equals(courseCode)) {
                result.add(e);
            }
        }
        return result;
    }
}