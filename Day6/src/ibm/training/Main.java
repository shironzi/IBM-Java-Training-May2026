package ibm.training;

import java.sql.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "password123";
        
        Scanner scanner = new Scanner(System.in);
        
        String addStudent =
        	    "INSERT INTO student " +
        	    "(email, password, firstname, lastname, dateadded, dateupdated) " +
        	    "VALUES (?, ?, ?, ?, ?, ?)";
        
        String getStudents = "SELECT * FROM student";
        
        String getStudent = "SELECT * FROM student WHERE studentid = ?";
        
        String updatePassword = "UPDATE student SET password = ? WHERE studentid = ?";
        
        String deleteStudent = "DELETE FROM student WHERE studentid = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
        		Statement stmt = conn.createStatement()){
            while(true) {
            	System.out.println("[A]dd");
                System.out.println("[V]iew");
                System.out.println("[U]pdate Password");
                System.out.println("[D]elete");
                System.out.println("[Q]uit");
                
                System.out.print("Enter choice:");
                String choice = scanner.nextLine();
                
                choice = choice.toUpperCase();
                
                System.out.println("================================");
                
                if (choice.equalsIgnoreCase("Q")) {
                    break;
                } else if (choice.equalsIgnoreCase("A")) {
                	PreparedStatement pstmt = conn.prepareStatement(addStudent);
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    
                    ResultSet rs = stmt.executeQuery(getStudents);

                    System.out.print("Enter password: ");
                    String stu_pass = scanner.nextLine();

                    System.out.print("Enter firstname: ");
                    String firstname = scanner.nextLine();

                    System.out.print("Enter lastname: ");
                    String lastname = scanner.nextLine();
                	
                	pstmt.setString(1, email);
                	pstmt.setString(2, stu_pass);
                	pstmt.setString(3, firstname);
                	pstmt.setString(4, lastname);
                	pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                	pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                	
                	pstmt.executeUpdate();
                	System.out.println("Successfully Added new Student.");
                    
                } else if (choice.equalsIgnoreCase("V")) {
                	ResultSet rs = stmt.executeQuery(getStudents);
                	
                	while(rs.next()) {
                		System.out.println("Student ID: " + rs.getInt("studentid"));
                		System.out.println("Email: " + rs.getString("email"));
                		System.out.println("Password: " + rs.getString("password"));
                		System.out.println("Firstname: " + rs.getString("firstname"));
                		System.out.println("Lastname: " + rs.getString("lastname"));
                		System.out.println("Date Added: " + rs.getTimestamp("dateAdded"));
                		System.out.println("Date Updated: " + rs.getTimestamp("dateUpdated"));
                		System.out.println("================================");
                	}

                } else if (choice.equalsIgnoreCase("U")) {
                	PreparedStatement pstmt = conn.prepareStatement(getStudent);
                    System.out.println("Enter Studentid: ");
                    int studentid = scanner.nextInt();
                    scanner.nextLine();
                    
                    pstmt.setInt(1, studentid);
                    ResultSet rs = pstmt.executeQuery();
                    
                    if(!rs.next()) {
                    	System.out.println("Student not found");
                    }else {
                    	System.out.println("Enter new password: ");
                        String new_password = scanner.nextLine();
                        
                        pstmt = conn.prepareStatement(updatePassword);
                        pstmt.setString(1, new_password);
                        pstmt.setInt(2, studentid);
                        
                        pstmt.executeUpdate();
                    }
                } else if (choice.equalsIgnoreCase("D")) {
                	PreparedStatement pstmt = conn.prepareStatement(deleteStudent);
                    System.out.println("Enter Studentid: ");
                    int studentid = scanner.nextInt();
                    scanner.nextLine();
                    
                    pstmt.setInt(1, studentid);
                    int rowsDeleted = pstmt.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        System.out.println("Successfully deleted student");
                    } else {
                        System.out.println("Student not found");
                    }
                } else {
                    System.out.println("Invalid choice");
                }
            }
            
            conn.close();
        }  catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
	}

}
