package com.stockportfoliomanagementsystem.HRManager;

import com.stockportfoliomanagementsystem.MainController;
import com.stockportfoliomanagementsystem.MySqlCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {
    private Connection conn = MySqlCon.MysqlMethod();
    private String cusName;
    private String cusAddress;
    private String cusContact;

    @FXML
    private ImageView imageView;
    @FXML
    private Label lblSuccess;
    @FXML
    private TextField txtCusAddress;
    @FXML
    private TextField txtCusContact;
    @FXML
    private TextField txtCusID;
    @FXML
    private TextField txtCusName;
    @FXML
    private Label txtName;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField txtCusEmail;

    private String cusEmail;
    private String cusID = ManageCustomers.getSelectedCustomer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    	String sql = "SELECT * FROM customer WHERE C_ID = ?";
    	try {
    	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
    	    preparedStatement.setString(1, cusID);
    	    ResultSet rs = preparedStatement.executeQuery();

    	    while(rs.next()){
    	        cusName = rs.getString("name");
    	        cusAddress = rs.getString("address");
    	        cusContact = rs.getString("contact");
    	        cusEmail = rs.getString("email");
    	    }
    	} catch (SQLException e) {
    	    throw new RuntimeException(e);
    	}
        txtCusID.setText(cusID);
        txtCusName.setText(cusName);
        txtCusAddress.setText(cusAddress);
        txtCusContact.setText(cusContact);
        txtCusEmail.setText(cusEmail);

        txtCusID.setEditable(false);
    }


    @FXML
    void onManageSuppliers(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/ManageSuppliers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setHeight(700);
        stage.setWidth(1210);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onStockButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/viewStock.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (NullPointerException e) {
        } catch (IOException e) {
        }
    }
    @FXML
    void onUpdateButton(MouseEvent event) {
        cusName = txtCusName.getText();
        cusAddress = txtCusAddress.getText();
        cusContact = txtCusContact.getText();
        cusEmail = txtCusEmail.getText();

        if (cusName.isEmpty() || cusAddress.isEmpty() || cusContact.isEmpty()) {
            MainController.fillAllTheFieldsAlert();
            return;
        }

        if (!MainController.isPhoneNumberValid(cusContact)) {
            MainController.invalidPhoneNumberAlert();
            return;
        }

        String sql = "UPDATE customer SET name = ?, address = ?, contact = ?, email = ? WHERE C_ID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cusName);
            pstmt.setString(2, cusAddress);
            pstmt.setString(3, cusContact);
            pstmt.setString(4, cusEmail);   // ✅ fix here
            pstmt.setString(5, cusID);      // ✅ fix here
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                lblSuccess.setText("Customer table updated!");
                System.out.println("Customer table updated");
            } else {
                System.out.println("Update failed: No rows affected");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void onBackButton(MouseEvent event){

        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/ManageCustomers.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (NullPointerException e) {
        } catch (IOException e) {
        }
    }


}
