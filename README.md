# ccrm-java-main[README.md](https://github.com/user-attachments/files/26384811/README.md)
                            ===CAMPUS COURSE & RECORDS MANAGER(CCRM)===
A comprehensive connsole based Java application for managing campus courses,students, enrollmants and grades
##OVERVIEW-
CCRM is a console based project that enables users to -
-Student Records and Profiles
-Course catalog and scheduling
-Student enrollments 
-Grades and Transctipts with GPA evaluation
-Data Import/Export via CSV files
-Backups with integrated timestamping


## How to Run
### Prerequisites
-Java Development Kit(JDK): Version 8 or higher
-GIT for version control
### Execution
-Compilation:
            javac -d . src/*.java
-Run the Application:
            java Main
### 📚 Evolution of Java
-1995: Java 1.0 (Oak) released

-1997: Java 1.1 (JDBC, RMI, Reflection)

-2004: Java 5 (Generics, Autoboxing, Annotations, Enums)

-2014: Java 8 (Lambdas, Stream API, Date/Time API)

-2018: Java 11 (LTS - Local-Variable Syntax, HTTP Client)

-2021: Java 17 (LTS - Sealed Classes, Pattern Matching)

-2023: Java 21 (LTS - Virtual Threads, Record Patterns)
###  Java Architecture
JVM (Java Virtual Machine): Executes Java bytecode, provides platform independence

JRE (Java Runtime Environment): JVM + Libraries = Environment to run Java applications

JDK (Java Development Kit): JRE + Development Tools = Environment to develop Java applications

Relationship: JDK ⊃ JRE ⊃ JVM

##  Windows Installation Guide
Step 1: Download JDK
Visit Oracle JDK Downloads

Download JDK 17+ for Windows x64 Installer

Run the installer and follow setup wizard

Step 2: Set Environment Variables
Open System Properties → Environment Variables

Create new system variable: JAVA_HOME = C:\Program Files\Java\jdk-17

Edit Path variable: Add %JAVA_HOME%\bin

Step 3: Verify Installation
cmd
java -version
javac -version
###  Eclipse IDE Setup
1. Creating New Project
2. File → New → Java Project
3. Project name: CCRM
4. Use default JRE (Java SE-17)
5. Click Finish
### Importing Source Code
File → Import → General → File System
Select your CCRM project folder
Check all source files and import

### Run Configuration
Run → Run Configurations → Java Application
Main class: Main
Apply and Run
###  Project Structure
CCRM/\
├── src/                  # Source code\
│   ├── Main.java        # Application entry point\
│   ├── Student.java     # Student domain class\
│   ├── Course.java      # Course domain class\  
│   ├── Enrollment.java  # Enrollment tracking\
│   ├── Grade.java       # Grade enum with points\
│   ├── Semester.java    # Semester enum\
│   ├── DataStore.java   # Singleton data management\
│   ├── FileManager.java # File I/O operations\
│   └── CLI.java         # Command-line interface\
├── data/                # CSV data files\
│   ├── students.csv     # Student records\
│   ├── courses.csv      # Course catalog\
│   └── enrollments.csv  # Enrollment data\
├── README.md           # Project documentation\
└── .gitignore          # Git exclusion rules\
## Author-
Ojaswa Jain
24BAI10570
