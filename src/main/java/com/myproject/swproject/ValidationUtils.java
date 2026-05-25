/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

/**
 *
 * @author Najah
 */
public class ValidationUtils {
 
// Method to validate the email 
    public static boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email != null && email.matches(emailRegex);
}
    
// Method to validate the username
    public static boolean isValidUsername(String username) {
    if (!username.matches("^[A-Za-z0-9_]{4,20}$")) {
        return false;
    }
    if (username.matches("^[0-9]+$")) {
        return false;
    }
    
    return !username.matches("^_+$");
}

// Method to validate password strength:
// Checks if the password meets security requirements:
// at least 8 characters, contains a capital letter, a number, and a special character.
public static boolean isValidPassword(String password) {
    if (password == null) return false;
    if (password.length() < 8) return false;
    if (!password.matches(".*[A-Z].*")) return false;     

    if (!password.matches(".*[0-9].*")) return false;
      
    return password.matches(".*[!@#$%^&*].*");
}

// Method to validate the phone number 
public static boolean isValidPhone(String phone) {
    if (phone == null) return false;
    phone = phone.trim();
    return phone.matches("\\d{10}"); 
}
}
