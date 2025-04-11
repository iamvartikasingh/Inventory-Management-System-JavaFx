package com.stockportfoliomanagementsystem.StockKeeper;

import com.stockportfoliomanagementsystem.MainController;
import com.stockportfoliomanagementsystem.MySqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.SelectionMode;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StockKeeperController implements Initializable {
    static Connection conn = MySqlCon.MysqlMethod();

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Circle circle;

    @FXML
    private Label lblAVG;

    @FXML
    private Label lblBought;

    @FXML
    private Label lblSold;
    

    @FXML
    private Label txtName;


    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imageView;

    MainController mainController = new MainController();
    private String Fname;
    private String Lname;


    String username = mainController.getUsername();
    String password = mainController.getPwd();
    private double total;
    byte[] image = new byte[1024];
    private double bought;
    private double sold;

    @FXML
    void onReportsButton(MouseEvent event) {
    //ADD =================================================================================
    }


    @FXML
    void onEditProfile(MouseEvent event) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stockportfoliomanagementsystem/PortfolioManager/EditProfilePM.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage newStage = new Stage();

            // Set the FXML content as the scene for the new stage
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setResizable(false);
            // Show the new stage
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onCustomersButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/viewCustomers.fxml"));
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
    void onInvoiceButton(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/ShowInvoices.fxml"));
            Parent root = loader.load();

            // This is where your controller's initialize() gets triggered
            ShowInvoices controller = loader.getController();
            System.out.println("✅ ShowInvoices Controller initialized");

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1210);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();  // log the error to know if FXML is breaking
        }
    }
    @FXML
    void onUpdateProducts(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/ManageProducts.fxml"));
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
    void onSellProducts(MouseEvent event) throws IOException {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/SelectCustomerType.fxml"));
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
    void onBuyProduct(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/SelectSupplierType.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new IOException(e);
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
    void onRefreshButton(MouseEvent event) {
        loadFromDB();
    }




    @FXML
    void onSupplierButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/StockKeeper/viewSuppliers.fxml"));
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

  
    @Override
  
    	public void initialize(URL url, ResourceBundle resourceBundle) {
    	  
    	    loadFromDB();
    	}
    private void loadFromDB() {
        // Load profile picture if available
        String picQuery = "SELECT Pic FROM Users WHERE Username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(picQuery)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("Pic");

                if (is != null) {
                    imageView.setVisible(false);
                    try (OutputStream os = new FileOutputStream("photo.jpg")) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        Image image = new Image(new FileInputStream("photo.jpg"));
                        circle.setFill(new ImagePattern(image));
                    }
                } else {
                    imageView.setVisible(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load total stock value
        String totalSQL = "SELECT SUM(Total) AS TotalStock FROM stock";
        try (PreparedStatement pstmt = conn.prepareStatement(totalSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getObject("TotalStock") != null ? rs.getDouble("TotalStock") : 0.0;
            }
        
            lblAVG.setText("$" + String.format("%.2f", total));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load user name
        String nameSQL = "SELECT FName, Lname FROM Users WHERE Username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(nameSQL)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Fname = rs.getString("FName");
                Lname = rs.getString("Lname");
            }
            txtName.setAlignment(Pos.CENTER);
            txtName.setText(Fname + " " + Lname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load goods sold
        String soldSQL = """
            SELECT SUM(Total) AS Sold
            FROM transactions_cus
            WHERE YEAR(Date_) = YEAR(CURDATE()) AND MONTH(Date_) = MONTH(CURDATE())
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(soldSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                sold = rs.getObject("Sold") != null ? rs.getDouble("Sold") : 0.0;
            }
           
            lblSold.setText(String.format("$%.2f", sold));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load goods bought
        String boughtSQL = """
            SELECT SUM(Total) AS Bought
            FROM transactions_sup
            WHERE YEAR(Date_) = YEAR(CURDATE()) AND MONTH(Date_) = MONTH(CURDATE())
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(boughtSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                bought = rs.getObject("Bought") != null ? rs.getDouble("Bought") : 0.0;
            }
            lblBought.setText(String.format("$%.2f", bought));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load pie chart data
        pieChart.setData(fetchDataFromDatabase());
        pieChart.getData().forEach(data -> 
            data.nameProperty().setValue(data.getName() + "\nAmount: " + ((int) data.getPieValue()))
        );
    }

//    private void loadFromDB(){
//        String sql = "SELECT Pic FROM Users WHERE Username = ?";
//
//        try {
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                InputStream is = rs.getBinaryStream("Pic");
//
//                // Read the image data and save it to a file
//
//                if(is!=null) {
//                    imageView.setVisible(false);
//                    // Read the image data and save it to a file
//                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
//                    byte[] content = new byte[1024];
//                    int size = 0;
//
//                    while ((size = is.read(content)) != -1) {
//                        os.write(content, 0, size);
//                    }
//                    os.close();
//                    is.close();
//
//                    Image image = new Image(new FileInputStream("photo.jpg"));
//                    circle.setFill(new ImagePattern(image));
//                }else{
//                    imageView.setVisible(true);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException f) {
//            throw new RuntimeException(f);
//        } catch (IOException g) {
//            throw new RuntimeException(g);
//        }
//
//        String totalSQL = "SELECT SUM(Total) AS TotalStock FROM stock";
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(totalSQL);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                total = rs.getDouble("TotalStock");
//            }
//            System.out.println(total);
//            lblAVG.setText("LKR "+total);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        String sql3 = "SELECT FName,Lname FROM Users WHERE Username = ?";
//
//        try {
//            PreparedStatement preparedStatement = conn.prepareStatement(sql3);
//            preparedStatement.setString(1,username);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while(rs.next()){
//                Fname = rs.getString("FName");
//                Lname = rs.getString("Lname");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(Fname+" PM "+Lname);
//        txtName.setAlignment(Pos.CENTER);
//        txtName.setText(Fname+" "+Lname);
//
//        String goodsSold = "SELECT SUM(Total) AS Sold\n" +
//                "FROM transactions_cus\n" +
//                "WHERE YEAR(Date_) = YEAR(CURDATE()) \n" +
//                "AND MONTH(Date_) = MONTH(CURDATE())";
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(goodsSold);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                sold = rs.getDouble("Sold");
//            }
//            System.out.println(sold);
//            lblSold.setText("LKR "+sold);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        String goodsBought = "SELECT SUM(Total) AS Bought\n" +
//                "FROM transactions_sup\n" +
//                "WHERE YEAR(Date_) = YEAR(CURDATE()) \n" +
//                "AND MONTH(Date_) = MONTH(CURDATE())";
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(goodsBought);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                bought= rs.getDouble("Bought");
//            }
//            System.out.println(bought);
//            lblBought.setText("LKR "+bought);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        ObservableList<PieChart.Data> pieChartData = fetchDataFromDatabase();
//
//        pieChart.setData(pieChartData);
//
//        pieChartData.forEach(data ->
//                data.nameProperty().setValue(data.getName() + "\nAmount: " + ((int) data.getPieValue()))
//        );
//    }
    public static void dbUpdate(){
        String stockTotal = "UPDATE stock SET Total = Selling_price*Qty";

        try {
            PreparedStatement pstmt = conn.prepareStatement(stockTotal);
            pstmt.executeUpdate();
            System.out.println("stock update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<PieChart.Data> fetchDataFromDatabase() {


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT P_Name, Qty FROM stock");

            while (resultSet.next()) {
                String category = resultSet.getString("P_Name");
                int value = resultSet.getInt("Qty");

                // Create a PieChart.Data object and add it to the list
                pieChartData.add(new PieChart.Data(category, value));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
}