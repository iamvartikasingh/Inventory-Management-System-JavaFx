package com.stockportfoliomanagementsystem.AccountingManager;

import com.stockportfoliomanagementsystem.Main;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AccountingManagerController implements Initializable {
    Connection conn = MySqlCon.MysqlMethod();
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Scene scene2;
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
    private double sold;
    private double bought;
    private double total;

    MainController mainController = new MainController();
    private String Fname ;
    private String Lname ;
    String username = mainController.getUsername();
    String password = mainController.getPwd();


    @FXML
    private ImageView imageView;

    private void setScene(Scene scene) {
        this.scene2 = scene;
    }

    public static Scene getScene(){
        return scene2;
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
    void onGenerateReports(MouseEvent event) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/GenerateReports.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            setScene(scene);
            newStage.setScene(scene);

            Screen primaryScreen = Screen.getPrimary();

            double screenWidth = primaryScreen.getBounds().getWidth();
            double screenHeight = primaryScreen.getBounds().getHeight();

            System.out.println(screenWidth);
            System.out.println(screenHeight);
            // Calculate the center position
            double centerX = screenWidth / 2;
            double centerY = screenHeight / 2;

            double boundX = centerX - (850 / 2);
            double boundY = centerY - (820 / 2);

            newStage.setX(boundX);
            newStage.setY(boundY);

            newStage.setResizable(false);
            newStage.setTitle("Reports");

            String relativePath = "/com/stockportfoliomanagementsystem/Images/logoIcon.png";
            InputStream iconStream = Main.class.getResourceAsStream(relativePath);
            Image iconImage = new Image(iconStream);
            newStage.getIcons().add(iconImage);
            // Show the new stage
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
    void onCustomers(MouseEvent event) {
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

    @FXML
    void onSuppliers(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/AccountingManager/ShowSuppliers.fxml"));
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

    private void loadFromDB(){
        String sql = "SELECT Pic FROM Users WHERE Username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                InputStream is = rs.getBinaryStream("Pic");

                // Read the image data and save it to a file

                if(is!=null) {
                    imageView.setVisible(false);
                    // Read the image data and save it to a file
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;

                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();

                    Image image = new Image(new FileInputStream("photo.jpg"));
                    circle.setFill(new ImagePattern(image));
                }else{
                    System.out.println("No image");
                    imageView.setVisible(true);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException f) {
            throw new RuntimeException(f);
        } catch (IOException g) {
            throw new RuntimeException(g);
        }

        String totalSQL = "SELECT SUM(Total) AS TotalStock FROM stock";

        try {
            PreparedStatement pstmt = conn.prepareStatement(totalSQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                total = rs.getDouble("TotalStock");
            }
            System.out.println(total);
            lblAVG.setText("$"+total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql3 = "SELECT FName,Lname FROM Users WHERE Username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql3);
            preparedStatement.setString(1,username);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Fname = rs.getString("FName");
                Lname = rs.getString("Lname");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Fname+" PM "+Lname);
        txtName.setAlignment(Pos.CENTER);
        txtName.setText(Fname+" "+Lname);

        String goodsSold = "SELECT SUM(Total) AS Sold\n" +
                "FROM transactions_cus\n" +
                "WHERE YEAR(Date_) = YEAR(CURDATE()) \n" +
                "AND MONTH(Date_) = MONTH(CURDATE())";

        try {
            PreparedStatement pstmt = conn.prepareStatement(goodsSold);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sold = rs.getDouble("Sold");
            }
            System.out.println(sold);
            lblSold.setText("$"+sold);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String goodsBought = "SELECT SUM(Total) AS Bought\n" +
                "FROM transactions_sup\n" +
                "WHERE YEAR(Date_) = YEAR(CURDATE()) \n" +
                "AND MONTH(Date_) = MONTH(CURDATE())";

        try {
            PreparedStatement pstmt = conn.prepareStatement(goodsBought);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bought= rs.getDouble("Bought");
            }
            System.out.println(bought);
            lblBought.setText("$"+bought);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        ObservableList<PieChart.Data> pieChartData = fetchDataFromDatabase();

        pieChart.setData(pieChartData);

        pieChartData.forEach(data ->
                data.nameProperty().setValue(data.getName() + "\nAmount: " + ((int) data.getPieValue()))
        );
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
    void onRefreshButton(MouseEvent event) {
        loadFromDB();
    }

}
