package com.connecttopgresql;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class Bookings {

    public static void showCustomerBookings(String pid) {

        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM bookings\n" +
                    "WHERE pid='" + pid + "' ORDER BY dateofbooking DESC;";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int fid = rs.getInt("fid");
                Date dof = rs.getDate("dateOfFlight");
                Date dob = rs.getDate("dateOfBooking");
                int not = rs.getInt("numberOfTickets");
                int amount = rs.getInt("total_price");

                //Display values
                System.out.println("\nBooking Date: " + dob);
                System.out.println(", Date of Flight: " + dof);
                System.out.println(", FID: " + fid);
                System.out.println(", Number of Tickets: " + not);
                System.out.println(", Amount Paid: " + amount);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean bookTickets(Scanner in, Customer customer) {
        System.out.println("\nPlease enter the details to book the flight:- ");

        System.out.print("\n\tEnter FID: ");
        int bookingFid = in.nextInt();

        System.out.print("\tEnter Date in (dd-mm-yyyy) format: ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String str = in.next();
        String dateOfFlight = "";
        try {
            Date dof = sdf.parse(str);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateOfFlight = sdf.format(dof);

        } catch (ParseException e) {
            System.out.println("Date Parse Exception");
        }

        System.out.print("\tEnter Number of Tickets: ");
        int numOfTickets = in.nextInt();

        Date date = new Date();
        String currDate = sdf.format(date);

        int price = Flights.getFlightPrice(bookingFid)*numOfTickets;

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");
            Statement stmt = con.createStatement();

            String sql = "INSERT into Bookings values(" + bookingFid + ",'" + customer.pid + "' ,TO_DATE('" + dateOfFlight + "', 'yyyy-MM-dd') , TO_DATE('" + currDate + "', 'yyyy-MM-dd') ,'" + numOfTickets + "' ," + price + " )";

            int x = stmt.executeUpdate(sql);

            stmt.close();
            con.close();

            if (x > 0) {

                if (!makePayment(in, price)) {
                    System.out.println("\nPayment unsuccessful, try again .... ");
                    return false;
                }

                System.out.println("\nPayment successful .... ");

                System.out.println("\nDear customer, thank you for booking, your ticket for flight with Flight Id " + bookingFid + " is confirmed! \nPlease find the details below:-");
                System.out.println("\n\tName: "+ customer.fname+" "+customer.lname);
                System.out.println("\tGender: "+ customer.gender);
                System.out.println("\tAge: "+ customer.age);
                System.out.println("\tMobile: "+ customer.mobile);
                System.out.println("\tFlight Date: "+ dateOfFlight);
                System.out.println("\tNumber of Tickets: "+ numOfTickets);
                System.out.println("\tAmount paid: "+ price);

                return true;

            } else {
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean makePayment(Scanner in, int price) {

        System.out.println("\nTotal amount to be paid is Rs "+price+", Choose a payment method:- ");

        System.out.println("\n\t1) Debit card ");
        System.out.println("\t2) Credit card ");
        System.out.println("\t3) Amazon Pay UPI ");
        System.out.println("\t4) Google Pay UPI\n ");

        System.out.print("\tOption number: ");
        int x = in.nextInt();

        return x > 0 && x < 5;
    }
}
