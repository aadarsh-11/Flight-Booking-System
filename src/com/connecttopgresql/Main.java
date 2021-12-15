package com.connecttopgresql;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        System.out.println("\n............... Welcome to our Flight Booking System! ...............\n\n");

        System.out.print("Press 1 if you are a Customer and 2 if Admin: ");
        int customerOrAdmin = in.nextInt();

        Customer customer = new Customer();
        Admin admin = new Admin();
        if (customerOrAdmin == 1) {
            customer.login();
        } else {
            admin.login();
            System.out.print("\nEnter the number of flights to be added: ");
            int numOfFlight = in.nextInt();
            admin.addFlights(numOfFlight);

            System.out.print("\nAdmin task completed, Logging off .... ");
            admin.signedIN = false;
            return;
        }

        System.out.print("\nPress 1 to view previous Bookings and 2 to search for Flights: ");
        int bookOrSearch = in.nextInt();
        if (bookOrSearch == 1) {
            System.out.println("\nHere are your previous booking Details:- ");
            Bookings.showCustomerBookings(customer.pid);
        } else {
            if (Flights.searchFlights(in)) return;

            System.out.print("\nDo you want to book tickets? (press 1 to book and 2 to exit) : ");

            int wantToBook = in.nextInt();
            if (wantToBook == 2) {
                return;
            }
            if (!Bookings.bookTickets(in, customer)) {

                System.out.println("\nBooking unsuccessful, Rerun the program and try again....");
            }
        }
    }

}