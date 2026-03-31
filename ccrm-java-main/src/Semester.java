// Enum for Semester (PDF Section 2.3 - "Enum for Semester")
public enum Semester {
    SPRING, SUMMER, FALL;
    
    // Optional: Add descriptive names
    public String getDisplayName() {
        switch(this) {
            case SPRING: return "Spring Semester";
            case SUMMER: return "Summer Semester";
            case FALL: return "Fall Semester";
            default: return this.toString();
        }
    }
}