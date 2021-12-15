package com.connecttopgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
    boolean signedIN = false;
    String email = "aadarsh@123.com";
    String password = "abcd1234";

    public void login() {
        System.out.print("\nEnter the credentials to Sign In: ");
        Scanner in = new Scanner(System.in);
        while (!signedIN) {
            System.out.print("\n\tEnter email: ");
            String email = in.next();
            System.out.print("\tEnter password: ");
            String password = in.next();
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            signedIN = true;
            System.out.println("\nSuccessfully Signed In ...");
        } else {

            System.out.println("\nSign In Failed, try again! ...");
        }
    }

    public void addFlights(int numOfFlight) {
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < numOfFlight; i++) {
            int fno = i + 1;
            System.out.print("\nEnter the Flight " + fno + " data: ");

            System.out.print("\n\tEnter FID: ");
            int fid = in.nextInt();

            System.out.print("\tEnter Origin: ");
            String origin = in.next();

            System.out.print("\tEnter Destination: ");
            String destination = in.next();

            System.out.print("\tEnter Arrival Time: ");
            String arrTime = in.next();

            System.out.print("\tEnter Departure Time: ");
            String depTime = in.next();

            System.out.print("\tEnter Ticket Price: ");
            int price = in.nextInt();

            System.out.print("\tEnter Capacity: ");
            int capacity = in.nextInt();

            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                        "postgres", "password");
                Statement stmt = con.createStatement();

                String sql = "INSERT into flights values('" + fid + "','" + origin + "' ,'" + destination + "','" + arrTime + "' ,'" + depTime + "' ,'" + price + "','" + capacity + "' )";

                int x = stmt.executeUpdate(sql);
                if (x > 0) {
                    System.out.println("\nFlight " + fno + " successfully added .....");

                } else {
                    System.out.println("\nUnable to add flight ....");
                }
                stmt.close();
                con.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
