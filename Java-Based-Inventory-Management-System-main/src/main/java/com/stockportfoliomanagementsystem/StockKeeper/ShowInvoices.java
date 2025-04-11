package com.stockportfoliomanagementsystem.StockKeeper;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowInvoices implements Initializable {

    Connection conn = MySqlCon.MysqlMethod();

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<ObservableList<String>> tblInvoices;

    @FXML
    void onBackButton(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/StockKeeperDashboard.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFromDB(){
        ObservableList<TableColumn<ObservableList<String>, ?>> columns = tblInvoices.getColumns();
        columns.clear();
        String[] columnNames = {"Transaction ID", "Date", "Quantity", "Price", "Total", "Customer ID", "Product ID"};
     
        double columnWidth = tblInvoices.getPrefWidth() / columnNames.length;

        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnIndex)));
            column.setPrefWidth(columnWidth);
            columns.add(column);
        }

        String sql = "SELECT transaction_id, Date_, Qty, Price, Total, c_ID, P_ID FROM temp_invoice";     // SQL query must match

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnNames.length; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            tblInvoices.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
  
    void onOpenButton(MouseEvent event) {
        System.out.println("Open button clicked");

        ObservableList<String> selectedRow = tblInvoices.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            System.out.println("Selected Invoice: " + selectedRow);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/PaymentRecieptCustomer.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // Optional
                stage.initStyle(StageStyle.DECORATED);          // Optional
                stage.setTitle("Invoice Preview");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No invoice selected");
        }
    }
    @FXML
    void onRefresh(MouseEvent event) {
        loadFromDB();
    }

    @FXML
    void onSellProducts(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/SelectCustomerType.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSupplierButton(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/viewSuppliers.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onUpdateProducts(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/ManageProducts.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBuyProduct(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/SelectSupplierType.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onDeleteButton(MouseEvent event) {
        System.out.println("🗑️ Delete button clicked");

        ObservableList<String> selectedRow = tblInvoices.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            String transactionId = selectedRow.get(0); // Assuming first column is transaction ID

            String deleteSQL = "DELETE FROM temp_invoice WHERE transaction_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, transactionId);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Deleted transaction: " + transactionId);
                    loadFromDB();
                } else {
                    System.out.println("Could not delete transaction.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No invoice selected for deletion.");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblInvoices.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        System.out.println("Invoices screen loaded");
	  
        loadFromDB();
    }
}