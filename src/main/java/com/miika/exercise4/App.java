package com.miika.exercise4;

import java.sql.*;
import java.util.Scanner;

public class App {
	
	static Connection conn = null;
	
	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/users";
		String username = "root";
		String password = "password";
		
		conn = DriverManager.getConnection(url, username, password);
		
		System.out.println("connection established");
		
		
		return conn;
	}
	
	public static void queryFromUsers(String username, String password) throws SQLException {		
		String sql = "SELECT * FROM USERS";
		ResultSet Rs = conn.createStatement().executeQuery(sql);
		while(Rs.next()) {
			System.out.println(Rs.getString("username"));
		}
	}
	
	public static void login() throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your username: ");
		String username = input.next();
		System.out.println("Enter your password: ");
		String password = input.next();
		String sql = "SELECT * from USERS WHERE username=? AND password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		
		ResultSet result = pstmt.executeQuery();
		
		if(result.next()) {
			System.out.println("login successful\n");
		} else {
			System.out.println("login failed\n");
		}
		
	}
  
	public static void updatePassword(String username, String password) throws SQLException {
		String sql = "SELECT * from USERS WHERE username=? AND password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		
		ResultSet result = pstmt.executeQuery();
		Scanner input = new Scanner(System.in);
		
		if(result.next()) {
			System.out.println("enter your new password: ");
			String newPassword = input.next();
			
			String newPasswordQuery = "UPDATE USERS SET password=? WHERE username=?";
			PreparedStatement newPstmt = conn.prepareStatement(newPasswordQuery);
			newPstmt.setString(1,newPassword);
			newPstmt.setString(2,username);
			
			int newResult = newPstmt.executeUpdate();
			
			if(newResult > 0) {
				System.out.println("change successful\n");
			}
			
		} else {
			System.out.println("no user found with set credentials");
		}
	}
	
	public static void signUp(String username, String password) throws SQLException {
		String sql = "INSERT INTO USERS (username, password) VALUES (?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		
		int result = pstmt.executeUpdate();

		if(result > 0) {
			System.out.println("sign up successful\n");
		} else {
			System.out.println("sign up unsuccessful\n");
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException {
		Scanner input = new Scanner(System.in);
		
		String username, password;
		
		conn = connect();
		
		String[] options = {"1- login",
                "2- sign up",
                "3- update password",
                "4- query users",
                "5- exit"
		};
			int option;
			while (true){
				printMenu(options);
				option = input.nextInt();
				switch (option) {
				case 1:
					login();
					break;
				case 2:
					System.out.println("Enter your username: ");
					username = input.next();
					System.out.println("Enter your password: ");
					password = input.next();
					signUp(username,password);
					break;
				case 3:
					System.out.println("Enter your username: ");
					username = input.next();
					System.out.println("Enter your password: ");
					password = input.next();
					updatePassword(username,password);
					break;
				case 4:
					System.out.println("Enter your username: ");
					username = input.next();
					System.out.println("Enter your password: ");
					password = input.next();
					queryFromUsers(username,password);
					break;
				case 5:
					System.exit(0);
				}
		}
		
	}
	
	public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
  
}
