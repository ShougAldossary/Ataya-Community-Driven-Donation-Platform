/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Najah
 */
public class ReporManager {
        public static boolean createDonationReport(int donorId, int doneeId, int itemId) throws SQLException {

        String sql = "INSERT INTO donation_reports " +
                "(donor_id, donee_id, item_id, state) " +
                "VALUES (?, ?, ?, 'in_progress')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, donorId);
            stmt.setInt(2, doneeId);
            stmt.setInt(3, itemId);
            stmt.executeUpdate();
           NotificationManage.sendDonationInProgressNotification(con, doneeId, itemId);
            return true;
        }
    }
}
