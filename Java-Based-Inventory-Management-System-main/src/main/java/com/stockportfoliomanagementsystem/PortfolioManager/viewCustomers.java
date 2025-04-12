package com.stockportfoliomanagementsystem.PortfolioManager;

import com.stockportfoliomanagementsystem.MySqlCon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class viewCustomers implements Initializable {
	@FXML private javafx.scene.control.TextField txtSearch;
	@FXML private javafx.scene.control.Button btnSearch;
	@FXML private javafx.scene.control.Button btnReset;
    @FXML
    private TableView<ObservableList<String>> tblCustomers;
    Connection conn = MySqlCon.MysqlMethod();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
  
    private Map<String, ObservableList<String>> customerMap = new HashMap<>();
    @FXML
    void manageUsers(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/PortfolioManager/ManageUsers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setHeight(700);
        stage.setWidth(1210);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onBackButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/PortfolioManager/PortfolioManagerDashboard.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    void onStockButton(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/PortfolioManager/viewStock.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setHeight(700);
        stage.setWidth(1210);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onSupplierButton(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/PortfolioManager/viewSuppliers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setHeight(700);
        stage.setWidth(1210);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    @Override
   
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TableColumn<ObservableList<String>, ?>> columns = tblCustomers.getColumns();
        columns.clear();

        // Define fixed column names
        String[] columnNames = {"Customer Id", "Customer Name", "Customer Address", "Contact Number"};
        double columnWidth = (tblCustomers.getPrefWidth()) / (columnNames.length) - 2;

        // Add the columns to the TableView with fixed names
        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnIndex)));
            column.setPrefWidth(columnWidth);
            column.setSortable(true); // enable sorting 
            columns.add(column);
        }

        // Load data from DB

        String sql = "SELECT C_ID, name, address, contact FROM customer";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            customerMap.clear();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnNames.length; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                customerMap.put(row.get(0), row);  // search by Customer ID (C_ID)
            }

            tblCustomers.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
  
    void onSearchClicked(MouseEvent event) {
        String id = txtSearch.getText().trim();
        if (id.isEmpty()) return;

        // find the row
        ObservableList<String> resultRow = customerMap.get(id);
        if (resultRow != null) {
            ObservableList<ObservableList<String>> result = FXCollections.observableArrayList();
            result.add(resultRow); // ✅ wrap the single row inside a list
            tblCustomers.setItems(result);
        } else {
            tblCustomers.setItems(FXCollections.observableArrayList()); // empty list if not found
        }
    }

    @FXML
    void onResetClicked(MouseEvent event) {
        initialize(null, null); // re-fetch and show all
        txtSearch.clear();
    }
}
