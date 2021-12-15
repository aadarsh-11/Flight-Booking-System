package com.connecttopgresql;

import java.sql.*;
import java.util.Scanner;

public class Customer {
    boolean signedIN = false;
    String pid = "0";
    String fname;
    String lname;
    String email;
    String password;
    String mobile;
    int age;
    String gender;

    public void login() {
        System.out.print("Press 1 to Register or 2 to Sign In: ");
        Scanner in = new Scanner(System.in);
        int rors = in.nextInt();
        if (rors == 1) {
            while (!signedIN) {
                registerUser();
            }
        } else {

            while (!signedIN) {
                System.out.print("\n\tEnter email: ");
                String email = in.next();
                System.out.print("\tEnter password: ");
                String password = in.next();
                signIn(email, password);
            }
        }
    }

    public void registerUser() {
        Scanner in = new Scanner(System.in);

        System.out.print("\n\tEnter PASSPORT ID: ");
        String pid = in.next();

        System.out.print("\tEnter First Name: ");
        String fname = in.next();

        System.out.print("\tEnter Last Name: ");
        String lname = in.next();

        System.out.print("\tEnter Email ID: ");
        String email = in.next();

        System.out.print("\tEnter Password: ");
        String password = in.next();

        System.out.print("\tMobile Number: ");
        String mobile = in.next();

        System.out.print("\tEnter age: ");
        int age = in.nextInt();

        System.out.print("\tEnter gender: ");
        String gender = in.next();

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");
            Statement stmt = con.createStatement();

            String sql = "INSERT into Customer values('" + pid + "','" + fname + "' ,'" + lname + "','" + email + "' ,'" + password + "' ,'" + mobile + "','" + age + "','" + gender + "' )";

            int x = stmt.executeUpdate(sql);

            if (x > 0) {
                System.out.println("\nRegistered successful, Sign In below to continue ....");
                this.pid = pid;
                this.fname = fname;
                this.lname = lname;
                this.email = email;
                this.password = password;
                this.mobile = mobile;
                this.age = age;
                this.gender = gender;
            } else {
                System.out.println("\nRegistration unsuccessful, try again ....");
            }

            stmt.close();
            con.close();

            while (x > 0 && !signedIN) {
                System.out.print("\n\tEnter email: ");
                String emailInp = in.next();
                System.out.print("\tEnter password: ");
                String passwordInp = in.next();
                signIn(emailInp, passwordInp);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void signIn(String email, String password) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM customer\n" +
                    "WHERE email='" + email + "' AND password= '" + password + "';";
            ResultSet rs = stmt.executeQuery(sql);

            String pid = "0";
            String fname = "";
            String lname = "";
            String mobile = "";
            int age = 0;
            String gender = "";

            while (rs.next()) {
                pid = rs.getString("pid");
                fname = rs.getString("fname");
                lname = rs.getString("lname");
                mobile = rs.getString("mobile");
                age = rs.getInt("age");
                gender = rs.getString("gender");
            }

            if (!pid.equals("0")) {
                System.out.println("\nSuccessfully Signed In ...");
                signedIN = true;
                this.pid = pid;
                this.fname = fname;
                this.lname = lname;
                this.email = email;
                this.password = password;
                this.mobile = mobile;
                this.age = age;
                this.gender = gender;
            } else {
                System.out.println("\nSign In Failed, try again! ...");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}