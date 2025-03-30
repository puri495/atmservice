package com.nativeatm;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class ATMService extends HttpServlet {
    private ATMTransaction transaction;

    public void init() {
        transaction = new ATMTransaction();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String user = request.getParameter("user");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if ("createAccount".equals(action)) {
            int pin = Integer.parseInt(request.getParameter("pin"));
            int balance = Integer.parseInt(request.getParameter("balance"));
            boolean success = transaction.createAccount(user, pin, balance);
            out.println(success ? "Account Created" : "User Already Exists");

        } else if ("login".equals(action)) {
            int pin = Integer.parseInt(request.getParameter("pin"));
            var userDoc = transaction.findUser(user);

            if (userDoc != null && userDoc.getInteger("pin") == pin) {
                out.println("Login Successful");
            } else {
                out.println("Invalid Credentials");
            }

        } else if ("checkBalance".equals(action)) {
            int balance = transaction.getBalance(user);
            out.println("Balance: " + (balance >= 0 ? balance : "User Not Found"));

        } else if ("withdraw".equals(action)) {
            int amount = Integer.parseInt(request.getParameter("amount"));
            boolean success = transaction.updateBalance(user, -amount);
            out.println(success ? "Withdraw Successful" : "User Not Found");

        } else if ("deposit".equals(action)) {
            int amount = Integer.parseInt(request.getParameter("amount"));
            boolean success = transaction.updateBalance(user, amount);
            out.println(success ? "Deposit Successful" : "User Not Found");
        }
    }
}

