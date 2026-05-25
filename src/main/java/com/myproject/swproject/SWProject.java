/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.swproject;

/**
 *
 * @author Deema
 */
import com.myproject.Interfaces.Welcome_Page;

public class SWProject {

    public static void main(String[] args) {
         DBConnection.getConnection();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Welcome_Page().setVisible(true);
            }
        });
    }
}
    