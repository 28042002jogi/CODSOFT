package codsoft;
import java.sql.*;
import java.util.Scanner;



    public class CourseRegistration {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/task5";
        private static final String USER = "root";
        private static final String PASS = "JP1315@pankti";

        public static void main(String[] args) {
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    if (conn != null) {
                        DatabaseMetaData meta = conn.getMetaData();
                        System.out.println("The driver name is " + meta.getDriverName());
                        System.out.println("A connection to the database has been established.\n");

                        Scanner scanner = new Scanner(System.in);
                        while (true) {
                            System.out.println("Choose an option:");
                            System.out.println("1. Display available courses");
                            System.out.println("2. Register a student for a course");
                            System.out.println("3. Drop a course for a student");
                            System.out.println("4. Exit");
                            System.out.print("Enter your choice: ");
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (choice) {
                                case 1:
                                    displayCourses(conn);
                                    break;
                                case 2:
                                    System.out.print("Enter Student ID: ");
                                    String studentId = scanner.nextLine();
                                    System.out.print("Enter Course Code: ");
                                    String courseCode = scanner.nextLine();
                                    registerStudent(conn, studentId, courseCode);
                                    break;
                                case 3:
                                    System.out.print("Enter Student ID: ");
                                    studentId = scanner.nextLine();
                                    System.out.print("Enter Course Code: ");
                                    courseCode = scanner.nextLine();
                                    dropCourse(conn, studentId, courseCode);
                                    break;
                                case 4:
                                    System.out.println("Exiting...");
                                    scanner.close();
                                    return;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error connecting to the database:");
                    System.out.println(e.getMessage());
                }
            }

            public static void displayCourses(Connection conn) {
                String sql = """
            SELECT 
                c.course_code, 
                c.title, 
                c.description, 
                c.capacity, 
                c.schedule, 
                (c.capacity - COUNT(r.registration_id)) AS available_slots
            FROM 
                Course c
            LEFT JOIN 
                Registration r ON c.course_code = r.course_code
            GROUP BY 
                c.course_code;
        """;

                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("Available Courses:");
                    System.out.println("--------------------------------------------------");
                    while (rs.next()) {
                        System.out.printf("Course Code: %s\nTitle: %s\nDescription: %s\nCapacity: %d\nSchedule: %s\nAvailable Slots: %d\n\n",
                                rs.getString("course_code"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("capacity"),
                                rs.getString("schedule"),
                                rs.getInt("available_slots"));
                    }
                    System.out.println("--------------------------------------------------");
                } catch (SQLException e) {
                    System.out.println("Error displaying courses:");
                    System.out.println(e.getMessage());
                }
            }

            public static void registerStudent(Connection conn, String studentId, String courseCode) {
                // Check if student exists
                if (!entityExists(conn, "Student", "student_id", studentId)) {
                    System.out.println("Registration failed: Student ID " + studentId + " does not exist.");
                    return;
                }
                // Check if course exists
                if (!entityExists(conn, "Course", "course_code", courseCode)) {
                    System.out.println("Registration failed: Course code " + courseCode + " does not exist.");
                    return;
                }

                String sql = """
            INSERT INTO Registration (student_id, course_code)
            SELECT ?, ?
            WHERE EXISTS (
                SELECT 1 FROM Course WHERE course_code = ? AND
                (SELECT COUNT(*) FROM Registration WHERE course_code = ?) < (SELECT capacity FROM Course WHERE course_code = ?)
            );
        """;

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, studentId);
                    pstmt.setString(2, courseCode);
                    pstmt.setString(3, courseCode);
                    pstmt.setString(4, courseCode);
                    pstmt.setString(5, courseCode);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.printf("Student %s successfully registered for course %s.\n", studentId, courseCode);
                    } else {
                        System.out.println("Registration failed: Course is full or does not exist.");
                    }
                } catch (SQLException e) {
                    System.out.println("Registration failed: " + e.getMessage());
                }
            }

            public static void dropCourse(Connection conn, String studentId, String courseCode) {
                String sql = "DELETE FROM Registration WHERE student_id = ? AND course_code = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, studentId);
                    pstmt.setString(2, courseCode);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.printf("Student %s successfully dropped course %s.\n", studentId, courseCode);
                    } else {
                        System.out.println("Dropping course failed: Student is not registered for this course.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error dropping course: " + e.getMessage());
                }
            }

            private static boolean entityExists(Connection conn, String tableName, String columnName, String value) {
                String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, value);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                } catch (SQLException e) {
                    System.out.println("Error checking existence of " + value + " in " + tableName + ": " + e.getMessage());
                }
                return false;
            }
        }
