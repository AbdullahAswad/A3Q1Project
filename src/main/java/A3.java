import java.sql.*;
import java.util.Scanner;

public class A3 {

    // Defines the database we'll be connecting to
    private static final String url = "jdbc:postgresql://localhost:5432/COMP3005A3";
    private static final String user = "postgres";
    private static final String password = "DarkSniper22";

    // Outputs the student table
    public static void getAllStudents(){
        try{
            // Connects to the database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            // enters the query to retrieve info from the student table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            // Outputs the next entry as long as there remains entries
            while(resultSet.next()){
                System.out.println(
                    resultSet.getInt("student_id") + "  | " +
                    resultSet.getString("first_name") + " " +
                    resultSet.getString("last_name") + "    | " +
                    resultSet.getString("email") + "    | " +
                    resultSet.getDate("enrollment_date")
                );
            }

        }
        catch(Exception e){e.printStackTrace();}
    }

    // adds a new student entry to the table
    public static void addStudent(String first_Name, String last_Name, String email, Date enrollment_Date) {
        try {
            // Connects to the database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            // enters the query to insert teh new student entry and checks if it is successful
            Statement statement = connection.createStatement();
            String line = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + first_Name + "', '" + last_Name + "', '" + email + "', '" + enrollment_Date.toString() + "')";
            int result = statement.executeUpdate(line);
            if (result > 0) {System.out.println("Entry inserted");}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    // Updates the email of one of the students in the table
    public static void updateStudentEmail(int student_Id, String email){
        try {
            // connects to the database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            // enters the query to update the given students email
            Statement statement = connection.createStatement();
            String line = "UPDATE students SET email = '" + email + "' WHERE student_id = " + student_Id;
            int result = statement.executeUpdate(line);
            if (result > 0) {System.out.println("Email updated");}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    // Deletes a student entry from the table
    public static void deleteStudent(int student_Id) {
        try {
            // connects to the database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            // enters the query to delete the given students entry
            Statement statement = connection.createStatement();
            String line = "DELETE FROM students WHERE student_id = " + student_Id;
            int result = statement.executeUpdate(line);
            if (result > 0) {System.out.println("Entry deleted");}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    // Controller of the database
    public static void main(String[] args){
        // Attempts to connect to the database before allowing the user to control it
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);}
        catch(Exception e){
            System.out.println("Database Failed to Connect");
            e.printStackTrace();}
        System.out.println("Database Connected.");

        // Allows user to continuously edit the database until satisfied (5 is entered)
        int value = 0;
        while (value != 5){
            // Outputs the controls
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. getAllStudents()");
            System.out.println("2. addStudent()");
            System.out.println("3. updateStudentEmail()");
            System.out.println("4. deleteStudent()");
            System.out.println("5. Quit");

            // User inputs their choice
            System.out.print("Input a number: ");
            Scanner choice = new Scanner(System.in);
            value = choice.nextInt();
            int student_Id;
            String first_Name, last_Name, email, enrollment_Date;

            // Program addresses the choice appropriately
            switch(value){
                case 1:
                    // Calls the function to print table
                    getAllStudents();
                    break;
                case 2:
                    // Asks user for first name
                    System.out.print("Enter first name: ");
                    choice = new Scanner(System.in);
                    first_Name = choice.nextLine();

                    // Asks user for last name
                    System.out.print("Enter last name: ");
                    choice = new Scanner(System.in);
                    last_Name = choice.nextLine();

                    // Asks user for email
                    System.out.print("Enter email: ");
                    choice = new Scanner(System.in);
                    email = choice.nextLine();

                    // Asks user for enrollment date
                    System.out.print("Enter enrollment date (Ex. 2024-01-01): ");
                    choice = new Scanner(System.in);
                    enrollment_Date = choice.nextLine();

                    // calls the function to add the new student
                    addStudent(first_Name, last_Name, email, Date.valueOf(enrollment_Date));
                    break;
                case 3:
                    // asks user for id of the student
                    System.out.print("Enter student id: ");
                    choice = new Scanner(System.in);
                    student_Id = choice.nextInt();

                    // asks user for the new email
                    System.out.print("Enter email: ");
                    choice = new Scanner(System.in);
                    email = choice.nextLine();

                    // calls a function to update a student's email
                    updateStudentEmail(student_Id, email);
                    break;
                case 4:
                    // asks user for the student id to delete
                    System.out.print("Enter student id: ");
                    choice = new Scanner(System.in);
                    student_Id = choice.nextInt();

                    // calls the function to remove a student entry
                    deleteStudent(student_Id);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
    }
}
