/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.swproject;

/**
 *
 * @author Najah
 */
import java.sql.*;
import com.myproject.swproject.DBConnection;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Item {

    // =====================
    // ENUMS
    // =====================
    public enum ItemType {
        CLOTHING,
        BAGS,
        SUPPLIES,
        OTHER
    }

    public enum ItemCondition {
        NEW,
        LIKE_NEW,
        GOOD,
        FAIR
    }

    public enum ItemStatus {
        WAITING,
        APPROVED,
        REJECTED,
        REQUESTED,
        COMPLETED
    }

    // =====================
    // FIELDS
    // =====================
    private int itemId;
    private int userId;
    private String title;
    private String description;
    private ItemType itemType;
    private ItemCondition itemCondition;
    private ItemStatus status;
    private String imageUrl;
    private String rejectionReason;
    private Integer approvedBy;
    private Timestamp approvedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String location;

    // =====================
    // CONSTRUCTORS
    // =====================
    public Item() {}

    public Item(int userId, String title, String description,
                ItemType itemType, ItemCondition itemCondition,
                String imageUrl) {

        this.userId = userId;
        this.title = title;
        this.description = description;
        this.itemType = itemType;
        this.itemCondition = itemCondition;
        this.imageUrl = imageUrl;
        this.status = ItemStatus.WAITING;
    }

    // =====================
    // INSERT ITEM
    // =====================
    public static boolean insertItem(Item item) {

        String sql = "INSERT INTO Items " +
                     "(user_id, title, description, item_type, item_condition, image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, item.getUserId());
            stmt.setString(2, item.getTitle());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getItemType().name().toLowerCase());
            stmt.setString(5, item.getItemCondition().name().toLowerCase());
            stmt.setString(6, item.getImageUrl());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
   public static boolean updateItem(Item item){

    String sql = "UPDATE Items SET " +
                 "title = ?, " +
                 "description = ?, " +
                 "item_type = ?, " +
                 "item_condition = ?, " +
                 "image_url = ?, " +
                 "status = 'waiting', " +
                 "updated_at = NOW() " +
                 "WHERE item_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, item.getTitle());
        stmt.setString(2, item.getDescription());
        stmt.setString(3, item.getItemType().name().toLowerCase());
        stmt.setString(4, item.getItemCondition().name().toLowerCase());
        stmt.setString(5, item.getImageUrl());
        stmt.setInt(6, item.getItemId());

        return stmt.executeUpdate() > 0;
    } catch(SQLException e){
            e.printStackTrace();
            return false;
    }
}



    public boolean isValid() {

        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Title cannot be empty.");
            return false;
        }

        if (title.length() > 60) {
            JOptionPane.showMessageDialog(null, "Title cannot exceed 60 characters.");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Description cannot be empty.");
            return false;
        }

        if (description.length() > 300) {
            JOptionPane.showMessageDialog(null, "Description cannot exceed 300 characters.");
            return false;
        }

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please upload an image.");
            return false;
        }

        if (itemType == null) {
            JOptionPane.showMessageDialog(null, "Select item type.");
            return false;
        }

        if (itemCondition == null) {
            JOptionPane.showMessageDialog(null, "Select item condition.");
            return false;
        }

        return true;
    }


    public static ArrayList<Item> getAllItemsList(int currentUserId) throws SQLException {

        ArrayList<Item> list = new ArrayList<>();

            String sql = "SELECT Items.*, Users.location " +
                 "FROM Items " +
                 "JOIN Users ON Items.user_id = Users.user_id " +
                 "WHERE Items.status = 'approved' "+
                 "AND Users.is_active = TRUE " +
                 "AND Items.user_id <> ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);) 
        {
             stmt.setInt(1, currentUserId);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Item i = new Item(
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    ItemType.valueOf(rs.getString("item_type").toUpperCase()),
                    ItemCondition.valueOf(rs.getString("item_condition").toUpperCase()),
                    rs.getString("image_url")
                );

                i.setItemId(rs.getInt("item_id"));
                i.setStatus(ItemStatus.valueOf(rs.getString("status").toUpperCase()));
                i.setLocation(rs.getString("location"));

                list.add(i);
            }
        }

        return list;
    }


    public static ArrayList<Item> getItemsByItemTypeList(ItemType type, int currentUserId) 
            throws SQLException {

        ArrayList<Item> list = new ArrayList<>();

    String sql = "SELECT Items.*, Users.location " +
                 "FROM Items " +
                 "JOIN Users ON Items.user_id = Users.user_id " +
                 "WHERE Items.item_type = ? " +
                 "AND Items.status = 'approved' " +
                 "AND Users.is_active = TRUE " +
                 "AND Items.user_id <> ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, type.name().toLowerCase());
            stmt.setInt(2, currentUserId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Item i = new Item(
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    ItemType.valueOf(rs.getString("item_type").toUpperCase()),
                    ItemCondition.valueOf(rs.getString("item_condition").toUpperCase()),
                    rs.getString("image_url")
                );

                i.setItemId(rs.getInt("item_id"));
                i.setStatus(ItemStatus.valueOf(rs.getString("status").toUpperCase()));
                i.setLocation(rs.getString("location"));

                list.add(i);
            }
        }

        return list;
    }
    
    public static ArrayList<Item> getItemsForSpecificUser(int userId) throws SQLException {

    ArrayList<Item> list = new ArrayList<>();

    String sql = "SELECT Items.*, Users.location " +
                 "FROM Items " +
                 "JOIN Users ON Items.user_id = Users.user_id " +
                 "WHERE Items.user_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Item i = new Item(
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                ItemType.valueOf(rs.getString("item_type").toUpperCase()),
                ItemCondition.valueOf(rs.getString("item_condition").toUpperCase()),
                rs.getString("image_url")
            );

            i.setItemId(rs.getInt("item_id"));
            i.setStatus(ItemStatus.valueOf(rs.getString("status").toUpperCase()));
            i.setLocation(rs.getString("location"));

            list.add(i);
        }
    }

    return list;
}
    
    public static Item getItemById(int itemId) throws SQLException {

    String sql = "SELECT Items.*, Users.location " +
                 "FROM Items JOIN Users ON Items.user_id = Users.user_id " +
                 "WHERE item_id = ?";

    Connection con = DBConnection.getConnection();
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setInt(1, itemId);

    ResultSet rs = stmt.executeQuery();
    
    if (rs.next()) {
        
        Item item = new Item(
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            ItemType.valueOf(rs.getString("item_type").toUpperCase()),
            ItemCondition.valueOf(rs.getString("item_condition").toUpperCase()),
            rs.getString("image_url")
        );

        item.setItemId(rs.getInt("item_id"));
        item.setStatus(ItemStatus.valueOf(rs.getString("status").toUpperCase()));
        item.setLocation(rs.getString("location"));

        return item;
    }

    return null;
}
    
public static String getItemTitleById(Connection con, int itemId)
        throws SQLException {

    String sql = "SELECT title FROM Items WHERE item_id = ?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, itemId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("title");
        }
    }

    return "Item";
}





    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

    public ItemCondition getItemCondition() { return itemCondition; }
    public void setItemCondition(ItemCondition itemCondition) { this.itemCondition = itemCondition; }

    public ItemStatus getStatus() { return status; }
    public void setStatus(ItemStatus status) { this.status = status; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Integer getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Integer approvedBy) { this.approvedBy = approvedBy; }

    public Timestamp getApprovedAt() { return approvedAt; }
    public void setApprovedAt(Timestamp approvedAt) { this.approvedAt = approvedAt; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
