package com.stockportfoliomanagementsystem.StockKeeper;


import com.stockportfoliomanagementsystem.EmailUtil;
import com.stockportfoliomanagementsystem.MySqlCon;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentRecieptCustomer implements Initializable{

    Connection conn = MySqlCon.MysqlMethod();
    String cid;

    private String CustomerTypeNew = AddNewCustomer.getCustomerType();
    private String CustomerTypeExisting = SelectExistingCustomer.getCustomerType();

    int pdfButtonCount = 0;
    private FileInputStream fis;
    private String pdfFilePath;

    private String pdfFilename;
    private byte[] pdf;

    Scene scene = SellExisting.getScene();

    private String cName;
    private String cAddress;
    private String cContact;
    private double Total;

    @FXML
    private Stage stage;
    private Parent root;
    private String max;
    private int numericId;


    @FXML
    private Label lblAddress;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblCusID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblInvoiceID;

    @FXML
    private Label lblName;

    @FXML
    private Label txtTotal;
    private String max1;
    private int numericId2;

    @FXML
    private TableView<ObservableList<String>> tblInvoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customer Type: "+CustomerTypeNew);
        System.out.println("Customer Type: "+CustomerTypeExisting);

        if(CustomerTypeNew != null) {
            cid = AddNewCustomer.getCusIDNew();
            System.out.println("Customer ID: " + cid);
        }else if(CustomerTypeExisting != null){
            cid = SelectExistingCustomer.getCustomerIndex();
            System.out.println("Customer ID: " + cid);
        }

        loadInvoiceFromDB();
        tblInvoice.setSelectionModel(null);
    }

    private void loadInvoiceFromDB() {

        ObservableList<TableColumn<ObservableList<String>, ?>> columns = tblInvoice.getColumns();
        columns.clear();
        String[] columnNames = {"Product ID","Product Name","Quantity","Price","Amount"};

        double columnWidth = tblInvoice.getPrefWidth() / columnNames.length;

        // Add the columns to the cart table with fixed names
        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnIndex)));
            column.setPrefWidth(columnWidth-2);
            columns.add(column);
        }

        String sql = "SELECT temp_invoice.P_ID, stock.P_Name, temp_invoice.Qty, temp_invoice.Price, temp_invoice.Total " +
                "FROM temp_invoice " +
                "JOIN stock ON temp_invoice.P_ID = stock.P_ID";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= 5; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            tblInvoice.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblDate.setText(": "+ LocalDate.now());

        String sql1 = "SELECT MAX(transaction_id) FROM temp_invoice";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql1);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                max = rs.getString(1);
                System.out.println("Last : "+max);
            }
            Pattern pattern = Pattern.compile("\\d+");
            if(max == null){
                max = "I_000";
            }
            // Use a Matcher to find the numeric part
            Matcher matcher = pattern.matcher(max);

            if (matcher.find()) {
                // Extract the numeric part as a string
                String numericPart = matcher.group();

                // Convert the numeric part to an integer if needed
                numericId = Integer.parseInt(numericPart);

                // Now you have the numeric ID as an integer
                System.out.println("Numeric ID: " + numericId);
            } else {
                System.out.println("No numeric part found in the C_ID value.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(numericId == 0){
            lblInvoiceID.setText("I_001");
        }else if(numericId < 9) {
            lblInvoiceID.setText("I_00" + (numericId + 1));
        }else if(numericId < 99){
            lblInvoiceID.setText("I_0" + (numericId + 1));
        }else{
            lblInvoiceID.setText("I_" + (numericId + 1));
        }

        String sql3= "SELECT C_ID, name, address, contact FROM customer WHERE C_ID = ?";;// Aluthen pdf invoice table ekak hadala e table eke count ek aran invoice id ek hdnn ona

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql3);
            preparedStatement.setString(1,cid);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
            	cName = rs.getString("name");
            	cAddress = rs.getString("address");
            	cContact = rs.getString("contact");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblCusID.setText(": "+cid);
        lblAddress.setText(": "+cAddress);
        lblContact.setText(": "+cContact);
        lblName.setText(": "+cName);

        String sql4= "SELECT SUM(Total) FROM temp_invoice";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql4);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql5= "DELETE FROM temp_invoice";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql5);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtTotal.setText(String.valueOf(Total));
    }

    private void captureScne() {
        String imgFilename = "img_"+ LocalDate.now()+".png";
        pdfFilename = "I_"+(numericId+1)+"_"+ LocalDate.now()+".pdf";

        Screen primaryScreen = Screen.getPrimary();

        double screenWidth = primaryScreen.getBounds().getWidth();
        double screenHeight = primaryScreen.getBounds().getHeight();

        System.out.println(screenWidth);
        System.out.println(screenHeight);
        // Calculate the center position
        double centerX = screenWidth / 2;
        double centerY = screenHeight / 2;

        double boundX = centerX - (850 / 2);
        double boundY = centerY - (620 / 2);

        try{
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle((int) boundX+7, (int) boundY+30,850,620);
            BufferedImage bi = robot.createScreenCapture(rectangle);
            ImageIO.write(bi, "png", new File("src/main/java/com/stockportfoliomanagementsystem/Invoices/png/"+ imgFilename));

        } catch (AWTException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String imageFilePath = "src/main/java/com/stockportfoliomanagementsystem/Invoices/png/"+ imgFilename;
        pdfFilePath = "src/main/java/com/stockportfoliomanagementsystem/Invoices/pdf/"+ pdfFilename;

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imageFilePath));
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            page.setMediaBox(new PDRectangle(850, 620));
            document.addPage(page);

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(pdImage, 0,0);
            contentStream.close();

            try(OutputStream os = new FileOutputStream(pdfFilePath)) {
                document.save(os);
            }
            document.close();

            try{
                fis = new FileInputStream(pdfFilePath);
                pdf = new byte[fis.available()];
                fis.read(pdf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String sql = "INSERT INTO temp_invoice (transaction_id, Date_, Qty, Price, Total, c_ID, P_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, numericId+1);                                // transaction_id
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));             // Date_
            pstmt.setInt(3, 1);                                           // Qty (dummy)
            pstmt.setDouble(4, 10.0);                                     // Price (dummy)
            pstmt.setDouble(5, 10.0);                                     // Total (dummy)
            pstmt.setString(6, cid != null ? cid : "C_001");             // c_ID
            pstmt.setString(7, "P_001");                                 // P_ID
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPDF() {
        try {
            InputStream pdfInputStream = new FileInputStream(pdfFilePath);
            Path tempPdfFile = Files.createTempFile("temp_pdf_", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempPdfFile.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            // Open the temporary PDF file with the default PDF viewer
            Desktop.getDesktop().open(tempPdfFile.toFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnPDF(MouseEvent event) {
        captureScne();
        showPDF();
    }
    @FXML
//    void onBtnEmailInvoice(MouseEvent event) {
//        captureScne(); // generate invoice PDF (still useful even without attachment)
//
//        // Fetch customer email
//        String email = null;
//        String query = "SELECT email FROM customer WHERE C_ID = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, cid);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                email = rs.getString("email");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (email == null || email.trim().isEmpty()) {
//            System.out.println("No email found for customer " + cid);
//            return;
//        }
//
//        // Compose email content
//        String subject = "Invoice from Invatron - " + lblInvoiceID.getText();
//        String body = "Dear " + cName + ",\n\n"
//                + "Thank you for your purchase. Below are your invoice details:\n\n"
//                + "Invoice ID: " + lblInvoiceID.getText() + "\n"
//                + "Customer ID: " + cid + "\n"
//                + "Name: " + cName + "\n"
//                + "Contact: " + cContact + "\n"
//                + "Address: " + cAddress + "\n"
//                + "Date: " + LocalDate.now() + "\n"
//                + "Total: " + txtTotal.getText() + "\n\n"
//                + "If you need the PDF copy, please contact our support.\n\n"
//                + "Best regards,\nInvatron Team";
//
//        EmailUtil.sendInvoiceEmail(email, subject, body);
//    }
    
    
    
  
    void onBtnEmailInvoice(MouseEvent event) {
        captureScne(); // makes the PDF and sets pdfFilePath

        String email = null;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT email FROM customer WHERE C_ID = ?")) {
            pstmt.setString(1, cid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (email == null || email.isEmpty()) {
            System.out.println("Email not found for customer: " + cid);
            return;
        }

        String subject = "Your Invoice from Invatron - " + lblInvoiceID.getText();
        String message = "Dear " + cName + ",\n\nPlease find your invoice attached.\n\nRegards,\nInvatron Team";
        File invoiceFile = new File(pdfFilePath);

        EmailUtil.sendInvoiceWithAttachment(email, subject, message, invoiceFile);
    }
}
