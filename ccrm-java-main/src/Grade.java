// Enum for Grade with grade points (PDF Section 2.3 - "Enum for Grade")
public enum Grade {
    S(10.0, "Excellent"), 
    A(9.0, "Very Good"), 
    B(8.0, "Good"), 
    C(7.0, "Average"), 
    D(6.0, "Below Average"), 
    F(0.0, "Fail");
    
    private final double gradePoints;
    private final String description;
    
    // Enum constructor (PDF Section 3 - "Enums with constructors & fields")
    Grade(double gradePoints, String description) {
        this.gradePoints = gradePoints;
        this.description = description;
    }
    
    public double getGradePoints() {
        return gradePoints;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Method to calculate grade from marks
    public static Grade fromMarks(double marks) {
        if (marks >= 90) return S;
        else if (marks >= 80) return A;
        else if (marks >= 70) return B;
        else if (marks >= 60) return C;
        else if (marks >= 50) return D;
        else return F;
    }
}