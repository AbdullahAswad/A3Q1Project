import java.sql.*;
import java.util.Scanner;

public class A3 {
    private static final String url = "jdbc:postgresql://localhost:5432/COMP3005A3";
    private static final String user = "postgres";
    private static final String password = "DarkSniper22";

    public static void getAllStudents(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

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

    public static void addStudent(String first_Name, String last_Name, String email, Date enrollment_Date) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            String line = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + first_Name + "', '" + last_Name + "', '" + email + "', '" + enrollment_Date.toString() + "')";
            int inserted = statement.executeUpdate(line);
            if (inserted > 0) {System.out.println("Entry inserted");}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static void updateStudentEmail(int student_Id, String email){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            String line = "UPDATE students SET email = '" + email + "' WHERE student_id = " + student_Id;
            int updated = statement.executeUpdate(line);
            if (updated > 0) {System.out.println("Email updated");}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static void main(String[] args){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);}
        catch(Exception e){
            System.out.println("Database Failed to Connect");
            e.printStackTrace();}
        System.out.println("Database Connected.");

        int value = 0;
        while (value != 5){
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. getAllStudents()");
            System.out.println("2. addStudent()");
            System.out.println("3. updateStudentEmail()");
            System.out.println("4. deleteStudent()");
            System.out.println("5. Quit");

            System.out.print("Input a number: ");
            Scanner choice = new Scanner(System.in);
            value = choice.nextInt();
            int student_Id;
            String first_Name, last_Name, email, enrollment_Date;
            switch(value){
                case 1:
                    getAllStudents();
                    break;
                case 2:
                    System.out.print("Enter first name: ");
                    choice = new Scanner(System.in);
                    first_Name = choice.nextLine();

                    System.out.print("Enter last name: ");
                    choice = new Scanner(System.in);
                    last_Name = choice.nextLine();

                    System.out.print("Enter email: ");
                    choice = new Scanner(System.in);
                    email = choice.nextLine();

                    System.out.print("Enter enrollment date (Ex. 2024-01-01): ");
                    choice = new Scanner(System.in);
                    enrollment_Date = choice.nextLine();

                    addStudent(first_Name, last_Name, email, Date.valueOf(enrollment_Date));
                    break;
                case 3:
                    System.out.print("Enter student id: ");
                    choice = new Scanner(System.in);
                    student_Id = choice.nextInt();

                    System.out.print("Enter email: ");
                    choice = new Scanner(System.in);
                    email = choice.nextLine();

                    updateStudentEmail(student_Id, email);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
    }
}
