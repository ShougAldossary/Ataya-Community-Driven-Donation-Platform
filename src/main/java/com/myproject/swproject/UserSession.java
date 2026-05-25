/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

/**
 *
 * @author Najah
 */
public class UserSession {
    private static int userId;
    private static String selectedUserType;

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
    
    public static void setselectedUserType(String selectedUType) {
        selectedUserType = selectedUType;
    }

    public static String getselectedUserType() {
        return selectedUserType;
    }

    public static void clearSession() {
        userId = 0;
    }
}
