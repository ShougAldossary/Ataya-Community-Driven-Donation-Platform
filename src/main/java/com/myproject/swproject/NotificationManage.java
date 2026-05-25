/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NotificationManage {

private static void insertNotification(
        Connection conn, int userId, Integer itemId,
        Notification.NotificationType type, String message) throws SQLException {

    String sql = "INSERT INTO notifications (user_id, item_id, type, message, status) " +
                 "VALUES (?, ?, ?, ?, 'unread')";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        if (itemId == null)
            stmt.setNull(2, java.sql.Types.INTEGER);
        else
            stmt.setInt(2, itemId);
        stmt.setString(3, type.name());
        stmt.setString(4, message);
        stmt.executeUpdate();
    }
}
    



    // ================= SEND NOTIFICATIONS =================

    public static void sendDonationRequestNotification(
        Connection con,
        int donorId,
        int itemId
) throws SQLException {

    String itemTitle = Item.getItemTitleById(con, itemId);

    String message =
        "Your item \"" + itemTitle + "\" has been requested.";

    insertNotification(
        con,
        donorId,
        itemId,
        Notification.NotificationType.request_received,
        message
    );
}


public static void sendDonationCompletedNotification(
        Connection con,
        int userId,
        int itemId
) throws SQLException {

    String itemTitle = Item.getItemTitleById(con, itemId);

    String message =
        "Donation for \"" + itemTitle + "\" has been completed successfully.";

    insertNotification(
        con,
        userId,
        itemId,
        Notification.NotificationType.donation_completed,
        message
    );
}


    public static void sendItemApprovedNotification(
            Connection conn, 
            int userId,
            int itemId
    ) throws SQLException {
        
        String itemTitle = Item.getItemTitleById(conn, itemId);

        String message = "Your item \"" + itemTitle + "\" has been approved.";
        insertNotification(conn, userId, itemId,
                Notification.NotificationType.item_approved, message);
    }

    public static void sendItemRejectedNotification(
        Connection con,
        int userId,
        int itemId,
        String reason
    ) throws SQLException {
        
        String itemTitle = Item.getItemTitleById(con, itemId);

        String message = "Your item \"" + itemTitle + "\" was rejected. Reason: " + reason;
        insertNotification(con, userId, itemId,
                Notification.NotificationType.item_rejected, message);
    }
    
        public static void sendDonationInProgressNotification(
        Connection con,
        int doneeId,
        int itemId
) throws SQLException {

    String itemTitle = Item.getItemTitleById(con, itemId);

    String message =
        "the item \"" + itemTitle + "\" you requested is in progress.";

    insertNotification(
        con,
        doneeId,
        itemId,
        Notification.NotificationType.request_received,
        message
    );
}
    
    

    // ================= GET USER NOTIFICATIONS =================

    public static List<Notification> getUserNotifications(int userId) throws SQLException {

        List<Notification> list = new ArrayList<>();

        String sql = "SELECT * FROM Notifications " +
                     "WHERE user_id = ? " +
                     "ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setUserId(rs.getInt("user_id"));

                n.setItemId(
                        rs.getObject("item_id") == null
                                ? null
                                : rs.getInt("item_id")
                );

                n.setType(
                        Notification.NotificationType.valueOf(rs.getString("type"))
                );

                n.setStatus(
                        Notification.NotificationStatus.valueOf(rs.getString("status"))
                );

                n.setMessage(rs.getString("message"));
                n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                list.add(n);
            }
        }

        return list;
    }
    
    
    // ===== GET NOTIFICATIONS BY STATUS (NEW) =====
    public static List<Notification> getNotificationsByStatus(int userId,
            Notification.NotificationStatus status) throws SQLException {
    List<Notification> list = new ArrayList<>();
    String sql = "SELECT * FROM notifications WHERE user_id = ? AND status = ? ORDER BY created_at DESC";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, userId);
        stmt.setString(2, status.name()); 
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Notification n = new Notification();
            n.setNotificationId(rs.getInt("notification_id"));
            n.setUserId(rs.getInt("user_id"));
            n.setItemId(rs.getObject("item_id") == null ? null : rs.getInt("item_id"));
            n.setType(Notification.NotificationType.valueOf(rs.getString("type")));
            n.setStatus(Notification.NotificationStatus.valueOf(rs.getString("status")));
            n.setMessage(rs.getString("message"));
            n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            list.add(n);
        }
    }
    return list;
}

    // ================= MARK AS READ =================

    public static void markAsRead(int notificationId) throws SQLException {

        String sql = "UPDATE Notifications SET status = 'read' WHERE notification_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        }
    }

    public static void markAllAsRead(int userId) throws SQLException {

        String sql = "UPDATE Notifications SET status = 'read' WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // ================= COUNT UNREAD =================

    public static int getUnreadCount(int userId) throws SQLException {

        String sql = "SELECT COUNT(*) FROM Notifications " +
                     "WHERE user_id = ? AND status = 'unread'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt(1);
        }

        return 0;
    }
}

