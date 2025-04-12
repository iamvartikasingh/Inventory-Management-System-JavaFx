module com.stockportfoliomanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    
  

    requires jakarta.mail; 
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.apache.pdfbox;

    opens com.stockportfoliomanagementsystem to javafx.fxml;
    opens com.stockportfoliomanagementsystem.PortfolioManager to javafx.fxml;
    opens com.stockportfoliomanagementsystem.HRManager to javafx.fxml;
    opens com.stockportfoliomanagementsystem.AccountingManager to javafx.fxml;
    opens com.stockportfoliomanagementsystem.StockKeeper to javafx.fxml;

    exports com.stockportfoliomanagementsystem;
    exports com.stockportfoliomanagementsystem.PortfolioManager;
    exports com.stockportfoliomanagementsystem.HRManager;
    exports com.stockportfoliomanagementsystem.AccountingManager;
    exports com.stockportfoliomanagementsystem.StockKeeper;
}