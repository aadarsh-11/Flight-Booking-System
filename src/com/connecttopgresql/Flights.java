package com.connecttopgresql;

import java.sql.*;
import java.util.Scanner;


public class Flights {

    public static int getFlightData(String origin, String destination) {

        boolean noFlights = true;
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");

            java.sql.Time arrTime;
            java.sql.Time depTime;

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM flights\n" +
                    "WHERE origin='" + origin.toLowerCase() + "' AND destination= '" + destination.toLowerCase() + "';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                noFlights = false;
                //Retrieve by column name
                int fid = rs.getInt("fid");
                String dbOrigin = rs.getString("origin");
                String dbDestination = rs.getString("destination");
                arrTime = rs.getTime("arrTime");
                depTime = rs.getTime("depTime");
                int price = rs.getInt("price");
                int capacity = rs.getInt("capacity");

                //Display values
                System.out.println("\nFID: " + fid);
                System.out.println(", Origin: " + dbOrigin);
                System.out.println(", Destination: " + dbDestination);
                System.out.println(", Arrival Time: " + arrTime);
                System.out.println(", Departure Time: " + depTime);
                System.out.println(", Ticket price: " + price);
                System.out.println(", capacity: " + capacity);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (noFlights) {
            System.out.println("\nSorry, there are no flights available ....");
            return 0;
        }
        return 1;
    }

    static boolean searchFlights(Scanner in) {
        System.out.println("\nPlease enter the following to search for flights:- ");
        String origin, destination;
        System.out.print("\n\tEnter origin: ");
        origin = in.next();
        System.out.print("\tEnter destination: ");
        destination = in.next();
        System.out.println("\nThese are the available flights for your destination:- ");
        int flightAvailable = getFlightData(origin, destination);

        return flightAvailable == 0;
    }

    public static int getFlightPrice(int bookingFid) {
        int price = 0;
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Flight-Booking-System",
                    "postgres", "password");

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM flights\n" +
                    "WHERE fid='" + bookingFid + "';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                price = rs.getInt("price");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
}