package com.stockportfoliomanagementsystem.AccountingManager;

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
import java.util.ResourceBundle;

public class ShowSuppliers implements Initializable {
    @FXML
    private TableView<ObservableList<String>> tblSuppliers;
    Connection conn = MySqlCon.MysqlMethod();
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    void onBackButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/AccountingManagerDashboard.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TableColumn<ObservableList<String>, ?>> columns = tblSuppliers.getColumns();
        columns.clear();

        // Define fixed column names
        String[] columnNames = {"Supplier Id","Supplier Name","Supplier Address","Contact Number"};

        double columnWidth = (tblSuppliers.getPrefWidth()) / (columnNames.length)-2;

        // Add the columns to the TableView with fixed names
        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnIndex)));
            column.setPrefWidth(columnWidth);
            columns.add(column);
        }

        String sql = "SELECT S_ID, S_Name, S_Location, S_Contact FROM supplier";
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

            tblSuppliers.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onCustomerButton(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/ShowCustomers.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
    }


    @FXML
    void onLogOutButton(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
    }

    @FXML
    void onManageReports(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/ManageReports.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
    }

    @FXML
    void onStock(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/ShowStock.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
    }
}

