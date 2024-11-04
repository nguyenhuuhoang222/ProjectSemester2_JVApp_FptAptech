package t12311m0.shoes_store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Product {

    private int id; // ID of the product
    private String name;
    private String brand;
    private double price;
    private int quantity; // Quantity of the product
    private String description;
    private String size;
    private String imageUrl;
    private Status status; // Changed to Enum



    public enum Status { // Enum for status
        AVAILABLE, UNAVAILABLE
    }

    // Constructor
    public Product(int id, String name, String brand, double price, int quantity, String description, String size, String imageUrl) {
        this.id = id; // Initialize id
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity; // Initialize quantity
        this.description = description;
        this.size = size;
        this.imageUrl = imageUrl;
        this.status = Status.AVAILABLE; // Default status
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Save product to database
    public boolean saveToDatabase() {
        try (Connection conn = ConnectDB.connectDB()) {
            String sql = "INSERT INTO products (product_name, brand, price, quantity, description, product_size, image_url, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, brand);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setString(5, description);
            stmt.setString(6, size);
            stmt.setString(7, imageUrl);
            stmt.setString(8, status.name()); // Use enum name for status

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && quantity == product.quantity &&
                name.equals(product.name) && brand.equals(product.brand) && 
                description.equals(product.description) && size.equals(product.size) &&
                imageUrl.equals(product.imageUrl) && status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, price, quantity, description, size, imageUrl, status);
    }
}
