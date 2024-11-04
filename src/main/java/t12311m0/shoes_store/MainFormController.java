package t12311m0.shoes_store;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * Shoes Store Controller for managing products.
 */
public class MainFormController implements Initializable {

   @FXML
private TableColumn<Employee, Integer> col_EmployeeId;
@FXML
private TableColumn<Employee, String> col_EmployeeName;
@FXML
private TableColumn<Employee, String> col_EmployeeEmail;
@FXML
private TableColumn<Employee, String> col_EmployeePassword;
@FXML
private TableColumn<Employee, String> col_EmployeePhone;
@FXML
private TableColumn<Employee, String> col_EmployeeGender;

@FXML
private TextField employeeNameFd;
@FXML
private TextField employeeEmailFd;
@FXML
private PasswordField employeePasswordFd;
@FXML
private TextField employeePhoneFd;
@FXML
private ChoiceBox<String> employeeGenderDd;

@FXML
private Button employeeAddbtn;
@FXML
private Button employeeClearbtn;
@FXML
private Button employeeDeletebtn;
@FXML
private Button employeeUpdatebtn;
       @FXML
    private AnchorPane mainpage;
           @FXML
    private AnchorPane productManagementPage;
           
    @FXML
    private AnchorPane employeeManagementPage;

    @FXML
    private Button productAddBtn, productClearBtn, productDeleteBtn, productUpdateBtn, logoutBtn;

    @FXML
    private TextField productNameFd, productBrandFd, productPriceFd, productQuantityFd;

    @FXML
    private TextArea productDescription;

    @FXML
    private ImageView productImageView;
    
        @FXML
    private AnchorPane customerManagementPage;

    @FXML
    private ComboBox<String> productSizeComboBox;

    @FXML
    private TableView<Product> productTableView;
 @FXML
        private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName, colBrand, colDescription, colSize, colImage;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    private Label accName;
    
    @FXML
    private Button logoutButton;
    
  @FXML private TextField customerNameFd;
    @FXML private TextField customerEmailFd;
    @FXML private TextField customerPhoneFd;
    @FXML private ComboBox<String> customerGenderDd;
    @FXML private TableView<Customer> customerTableView;
    
    @FXML private TableColumn<Customer, Integer> col_CustomerId;
    @FXML private TableColumn<Customer, String> col_CustomerName;
    @FXML private TableColumn<Customer, String> col_CustomerEmail;
    @FXML private TableColumn<Customer, String> col_CustomerPhone;
    @FXML private TableColumn<Customer, String> col_CustomerGender;

  
      ObservableList<Customer> customerList = FXCollections.observableArrayList();


    private static final String[] SHOE_SIZES = {
        "Size 38: 24 cm", "Size 39: 24.5 cm", "Size 40: 25 cm",
        "Size 41: 26 cm", "Size 42: 26.5 cm", "Size 43: 27.5 cm",
        "Size 44: 28 cm", "Size 45: 29 cm"
    };
    //chuyen trang 
    @FXML
public void showMainPageReports() {
    // Hiển thị trang báo cáo chính
    mainpage.setVisible(true);
    productManagementPage.setVisible(false);
    employeeManagementPage.setVisible(false);
    customerManagementPage.setVisible(false);
}

@FXML
public void showProductManagementPage() {
    // Hiển thị trang quản lý sản phẩm
    mainpage.setVisible(false);
    productManagementPage.setVisible(true);
    employeeManagementPage.setVisible(false);
    customerManagementPage.setVisible(false);
}

@FXML
public void showEmployeeManagementPage() {
    // Hiển thị trang quản lý nhân viên
    mainpage.setVisible(false);
    productManagementPage.setVisible(false);
    employeeManagementPage.setVisible(true);
    customerManagementPage.setVisible(false);
}

@FXML
public void showCustomerManagementPage() {
    // Hiển thị trang quản lý khách hàng
    mainpage.setVisible(false);
    productManagementPage.setVisible(false);
    employeeManagementPage.setVisible(false);
    customerManagementPage.setVisible(true);
}

    // query
    private static final String SELECT_PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE product_name = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET product_name = ?, brand = ?, price = ?, quantity = ?, description = ?, product_size = ?, image_url = ? WHERE product_name = ?";
    private static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO employees (employee_name, email, password, phone) VALUES (?, ?, ?, ?)";
    private static final String DELETE_EMPLOYEE_QUERY = "DELETE FROM employees WHERE employee_id = ?";
    private static final String UPDATE_EMPLOYEE_QUERY = "UPDATE employees SET employee_name = ?, email = ?, password = ?, phone = ? WHERE employee_id = ?";
    private static final String SELECT_EMPLOYEES_QUERY = "SELECT * FROM employees";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSizeComboBox();
        initializeTableView();
        loadProducts();
        initializeGender();
        initializeEmployee();
        showMainPageReports();
        initializeCustomer();
        initializeCustomerGender();
    }

    @FXML
    public void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(productImageView.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            productImageView.setImage(image);
        }
    }

    private void initializeSizeComboBox() {
        productSizeComboBox.setItems(FXCollections.observableArrayList(SHOE_SIZES));
    }
    
       
    public void initializeGender() {
        // Initialize gender options
        employeeGenderDd.setItems(FXCollections.observableArrayList("Male", "Female"));
    }
    
      public void initializeCustomerGender() {
        // Initialize gender options
        customerGenderDd.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    private void initializeTableView() {
        // Set up the cell value factories for each column in the TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        // Configure the image column to display images from URLs
        colImage.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        colImage.setCellFactory(column -> new TableCell<Product, String>() {
            private final ImageView imageView = new ImageView(); // ImageView to hold the image in each cell

            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty || imageUrl == null || imageUrl.isEmpty()) {
                    // If no image URL is provided, do not display an ImageView
                    setGraphic(null);
                } else {
                    try {
                        // Load the image from the URL with specific dimensions for performance
                        Image image = new Image(imageUrl, 50, 50, true, true);
                        imageView.setImage(image);
                        imageView.setFitHeight(50); // Set the height of the image
                        imageView.setFitWidth(50); // Set the width of the image
                        setGraphic(imageView); // Display the ImageView in the cell
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + imageUrl);
                        setGraphic(null); // Do not display anything if image loading fails
                    }
                }
            }
        });
    }

    public void setAdminName(String adminName) {
        accName.setText("Welcome, " + adminName + "!");
    }

@FXML
public void logout() {
    if (confirmLogout()) {
        closeCurrentWindow();
        loadLoginForm();
    }
}

private boolean confirmLogout() {
    Optional<ButtonType> result = showConfirmationDialog("Logout Confirmation", "Are you sure you want to logout?");
    return result.isPresent() && result.get() == ButtonType.OK;
}

private void closeCurrentWindow() {
    Stage stage = (Stage) logoutButton.getScene().getWindow();
    stage.close();
}


    private void loadLoginForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Shoes Store Management System");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load login form: " + e.getMessage());
        }
    }

    @FXML
    public void clearForm() {
        productNameFd.clear();
        productBrandFd.clear();
        productPriceFd.clear();
        productQuantityFd.clear();
        productDescription.clear();
        productSizeComboBox.getSelectionModel().clearSelection();
        productImageView.setImage(null); // Clear the ImageView
    }

    @FXML
    public void addProduct() {
        String name = productNameFd.getText();
        String brand = productBrandFd.getText();
        String priceStr = productPriceFd.getText();
        String quantityStr = productQuantityFd.getText();
        String description = productDescription.getText();
        String size = productSizeComboBox.getSelectionModel().getSelectedItem();
        String imageUrl = productImageView.getImage() != null ? productImageView.getImage().getUrl() : null;

        if (!areInputsValidP(name, brand, priceStr, quantityStr, size, imageUrl)) {
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        Product product = new Product(0, name, brand, price, quantity, description, size, imageUrl);

        if (product.saveToDatabase()) {
            showAlert("Success", "Product added successfully.");
            clearForm();
            loadProducts(); // Refresh the table view
        } else {
            showAlert("Error", "Failed to add product. Please try again.");
        }
    }

   private boolean areInputsValidP(String name, String brand, String price, String quantity, String size, String imageUrl) {
    // Kiểm tra độ dài của name
    if (name.length() < 5 || name.length() > 25) {
        showAlert("Error", "Name must be between 5 and 25 characters.");
        return false;
    }
    
    // Kiểm tra brand không null
    if (brand == null || brand.isEmpty()) {
        showAlert("Error", "Brand cannot be null or empty.");
        return false;
    }
    
    // Kiểm tra price lớn hơn 1.00
    try {
        double parsedPrice = Double.parseDouble(price);
        if (parsedPrice <= 1.00) {
            showAlert("Error", "Price must be greater than 1.00.");
            return false;
        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Price must be a valid number.");
        return false;
    }
    
    // Kiểm tra quantity lớn hơn 0
    try {
        int parsedQuantity = Integer.parseInt(quantity);
        if (parsedQuantity <= 0) {
            showAlert("Error", "Quantity must be greater than 0.");
            return false;
        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Quantity must be a valid integer.");
        return false;
    }

    // Kiểm tra size và imageUrl không null
    if (size == null || size.isEmpty()) {
        showAlert("Error", "Size cannot be null or empty.");
        return false;
    }
    
    if (imageUrl == null || imageUrl.isEmpty()) {
        showAlert("Error", "Image URL cannot be null or empty.");
        return false;
    }
    
    return true;
}

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    private void loadProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        try (Connection conn = ConnectDB.connectDB(); PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUCTS_QUERY); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getString("product_size"),
                        rs.getString("image_url")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load products from the database: " + e.getMessage());
        }

        productTableView.setItems(productList);
    }

   @FXML
public void handleRowSelect() {
    Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
    if (selectedProduct != null) {
        populateProductForm(selectedProduct);
    }
}

@FXML
public void handleEmployeeRowSelect() {
    Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
    if (selectedEmployee != null) {
        populateEmployeeForm(selectedEmployee);
    }
}

@FXML
public void handleCustomerRowSelect() {
    Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
    if (selectedCustomer != null) {
        populateCustomerForm(selectedCustomer);
    }
}

private void populateProductForm(Product product) {
    productNameFd.setText(product.getName());
    productBrandFd.setText(product.getBrand());
    productPriceFd.setText(String.valueOf(product.getPrice()));
    productQuantityFd.setText(String.valueOf(product.getQuantity()));
    productDescription.setText(product.getDescription());
    productSizeComboBox.getSelectionModel().select(product.getSize());
    productImageView.setImage(new Image(product.getImageUrl()));
}

private void populateEmployeeForm(Employee employee) {
    employeeNameFd.setText(employee.getName());
    employeeEmailFd.setText(employee.getEmail());
    employeePhoneFd.setText(employee.getPhone());
    employeeGenderDd.setValue(employee.getGender());
}

private void populateCustomerForm(Customer customer) {
    customerNameFd.setText(customer.getCustomerName());
    customerEmailFd.setText(customer.getEmail());
    customerPhoneFd.setText(customer.getPhone());
    customerGenderDd.setValue(customer.getGender());
}

    @FXML
    public void deleteProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert("Error", "No product selected.");
            return;
        }

        Optional<ButtonType> result = showConfirmationDialog("Delete Confirmation", "Are you sure you want to delete " + selectedProduct.getName() + "?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (deleteProductFromDatabase(selectedProduct)) {
                showAlert("Success", "Product deleted successfully.");
                loadProducts(); // Refresh the table view
            } else {
                showAlert("Error", "Failed to delete product. Please try again.");
            }
        }
    }

    private boolean deleteProductFromDatabase(Product product) {
        try (Connection conn = ConnectDB.connectDB(); PreparedStatement stmt = conn.prepareStatement(DELETE_PRODUCT_QUERY)) {
            stmt.setString(1, product.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete product: " + e.getMessage());
            return false;
        }
    }

    @FXML
    public void updateProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert("Error", "No product selected.");
            return;
        }

        String name = productNameFd.getText();
        String brand = productBrandFd.getText();
        String priceStr = productPriceFd.getText();
        String quantityStr = productQuantityFd.getText();
        String description = productDescription.getText();
        String size = productSizeComboBox.getSelectionModel().getSelectedItem();
        String imageUrl = productImageView.getImage() != null ? productImageView.getImage().getUrl() : null;

        if (!areInputsValidP(name, brand, priceStr, quantityStr, size, imageUrl)) {
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        Product updatedProduct = new Product(selectedProduct.getId(), name, brand, price, quantity, description, size, imageUrl);

        if (updateProductInDatabase(updatedProduct, selectedProduct.getName())) {
            showAlert("Success", "Product updated successfully.");
            loadProducts(); // Refresh the table view
        } else {
            showAlert("Error", "Failed to update product. Please try again.");
        }
    }

    private boolean updateProductInDatabase(Product updatedProduct, String oldProductName) {
        try (Connection conn = ConnectDB.connectDB(); PreparedStatement stmt = conn.prepareStatement(UPDATE_PRODUCT_QUERY)) {
            stmt.setString(1, updatedProduct.getName());
            stmt.setString(2, updatedProduct.getBrand());
            stmt.setDouble(3, updatedProduct.getPrice());
            stmt.setInt(4, updatedProduct.getQuantity());
            stmt.setString(5, updatedProduct.getDescription());
            stmt.setString(6, updatedProduct.getSize());
            stmt.setString(7, updatedProduct.getImageUrl());
            stmt.setString(8, oldProductName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showAlert("Error", "Failed to update product: " + e.getMessage());
            return false;
        }

    }
//EMployee
   @FXML
public void addEmployee() {
    String name = employeeNameFd.getText();
    String email = employeeEmailFd.getText();
    String password = employeePasswordFd.getText();
    String phone = employeePhoneFd.getText();
    String gender = employeeGenderDd.getValue();

    if (!areEmployeeInputsValid(name, email, password, phone, gender)) {
        return;
    }

    try (Connection conn = ConnectDB.connectDB();
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (employee_name, email, password, phone, gender) VALUES (?, ?, ?, ?, ?)")) {
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, phone);
        stmt.setString(5, gender);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            showAlert("Success", "Employee added successfully.");
            clearEmployeeForm();
            loadEmployees();
        }
    } catch (SQLException e) {
        showAlert("Error", "Failed to add employee: " + e.getMessage());
    }
}

@FXML
public void updateEmployee() {
    Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
    if (selectedEmployee == null) {
        showAlert("Error", "No employee selected.");
        return;
    }

    String name = employeeNameFd.getText();
    String email = employeeEmailFd.getText();
    String password = employeePasswordFd.getText();
    String phone = employeePhoneFd.getText();
    String gender = employeeGenderDd.getValue();

    if (!areEmployeeInputsValid(name, email, password, phone, gender)) {
        return;
    }

    try (Connection conn = ConnectDB.connectDB();
         PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET employee_name = ?, email = ?, password = ?, phone = ?, gender = ? WHERE employee_id = ?")) {
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, phone);
        stmt.setString(5, gender);
        stmt.setInt(6, selectedEmployee.getId());

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            showAlert("Success", "Employee updated successfully.");
            loadEmployees();
        }
    } catch (SQLException e) {
        showAlert("Error", "Failed to update employee: " + e.getMessage());
    }
}

@FXML
public void deleteEmployee() {
    Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
    if (selectedEmployee == null) {
        showAlert("Error", "No employee selected.");
        return;
    }

    Optional<ButtonType> result = showConfirmationDialog("Delete Confirmation", "Are you sure you want to delete " + selectedEmployee.getName() + "?");
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try (Connection conn = ConnectDB.connectDB();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE employee_id = ?")) {
            stmt.setInt(1, selectedEmployee.getId());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert("Success", "Employee deleted successfully.");
                loadEmployees();
                clearEmployeeForm();
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete employee: " + e.getMessage());
        }
    }
}

private boolean areEmployeeInputsValid(String name, String email, String password, String phone, String gender) {
    if ( email.isEmpty() || password.isEmpty() || phone.isEmpty() || gender == null || gender.isEmpty()) {
        showAlert("Error", "Please fill in all fields.");
        return false;
    }
      if (name.length() < 5 || name.length() > 25) {
        showAlert("Error", "Name must be between 5 and 25 characters.");
        return false;
    }

    // Kiểm tra tính hợp lệ của email
    if (!isValidEmail(email)) {
        showAlert("Error", "Email is not valid.");
        return false;
    }

    // Kiểm tra tính hợp lệ của số điện thoại
    if (!isValidPhone(phone)) {
        showAlert("Error", "Phone number must be a valid Vietnamese number.");
        return false;
    }

    return true;
}

// Phương thức kiểm tra tính hợp lệ của email
private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(emailRegex);
}

// Phương thức kiểm tra tính hợp lệ của số điện thoại
private boolean isValidPhone(String phone) {
    // Số điện thoại Việt Nam có 10 hoặc 11 chữ số, bắt đầu bằng 0 và theo sau là các chữ số
    // Ví dụ: 0123456789 hoặc 01234567891
    String phoneRegex = "^(0[3|5|7|8|9][0-9]{8}|0[3|5|7|8|9][0-9]{9})$";
    return phone.matches(phoneRegex);
}


 @FXML
public void clearEmployeeForm() {
    employeeNameFd.clear();
    employeeEmailFd.clear();
    employeePasswordFd.clear();
    employeePhoneFd.clear();
    employeeGenderDd.setValue(null);
}

   @FXML
    public void initializeEmployee() {
        // Set up the columns to match Employee properties
        col_EmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_EmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_EmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_EmployeePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_EmployeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_EmployeeGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Load data into the table
        loadEmployees();
    }

    // Sample function to load employees from database
    private void loadEmployees() {
                ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        employeeList.clear();

        try (Connection conn = ConnectDB.connectDB();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("employee_id"),
                    rs.getString("employee_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("gender")
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage());
        }

        // Bind the list to the TableView
        employeeTableView.setItems(employeeList);
    }
    
    //Customer 
      @FXML
    public void addCustomer() {
        String name = customerNameFd.getText();
        String email = customerEmailFd.getText();
        String phone = customerPhoneFd.getText();
        String gender = customerGenderDd.getValue();

        if (!areCustomerInputsValid(name, email, phone, gender)) {
            return;
        }

        try (Connection conn = ConnectDB.connectDB();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO customers (customer_name, email, phone, gender) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, gender);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Success", "Customer added successfully.");
                clearCustomerForm();
                loadCustomers();
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to add customer: " + e.getMessage());
        }
    }

    @FXML
    public void updateCustomer() {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Error", "No customer selected.");
            return;
        }

        String name = customerNameFd.getText();
        String email = customerEmailFd.getText();
        String phone = customerPhoneFd.getText();
        String gender = customerGenderDd.getValue();

        if (!areCustomerInputsValid(name, email, phone, gender)) {
            return;
        }

        try (Connection conn = ConnectDB.connectDB();
             PreparedStatement stmt = conn.prepareStatement("UPDATE customers SET customer_name = ?, email = ?, phone = ?, gender = ? WHERE customer_id = ?")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, gender);
            stmt.setInt(5, selectedCustomer.getCustomerId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Success", "Customer updated successfully.");
                loadCustomers();
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to update customer: " + e.getMessage());
        }
    }

private boolean areCustomerInputsValid(String name, String email, String phone, String gender) {
    // Kiểm tra nếu các trường không rỗng
    if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender == null || gender.isEmpty()) {
        showAlert("Error", "Please fill in all fields.");
        return false;
    }
    
    // Kiểm tra độ dài tên
    if (name.length() < 5 || name.length() > 25) {
        showAlert("Error", "Name must be between 5 and 25 characters.");
        return false;
    }

    // Kiểm tra định dạng email
    if (!isValidEmail(email)) {
        showAlert("Error", "Invalid email format.");
        return false;
    }

    // Kiểm tra số điện thoại
    if (!isValidPhone(phone)) {
        showAlert("Error", "Phone number must be 10 or 11 digits and start with 0.");
        return false;
    }

    return true;
}



    @FXML
    public void clearCustomerForm() {
        customerNameFd.clear();
        customerEmailFd.clear();
        customerPhoneFd.clear();
        customerGenderDd.setValue(null);
    }

    @FXML
    public void initializeCustomer() {
        // Set up the columns to match Customer properties
        col_CustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_CustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_CustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_CustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_CustomerGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Load data into the table
        loadCustomers();
    }

    // Sample function to load customers from database
    private void loadCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try (Connection conn = ConnectDB.connectDB();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("gender") // Thêm gender vào constructor
                );
                customerList.add(customer);
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to load customers: " + e.getMessage());
        }

        // Bind the list to the TableView
        customerTableView.setItems(customerList);
    }
}

    



