// File: Navigator.java
package com.stockportfoliomanagementsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;
public class Navigator {
    public static Stack<String> screenHistory = new Stack<>();

    public static void switchScreen(MouseEvent event, String currentFxmlPath, String nextFxmlPath) {
        try {
            // Push current FXML path onto the stack
            screenHistory.push(currentFxmlPath);

            Parent root = FXMLLoader.load(Navigator.class.getResource(nextFxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goBack(MouseEvent event) {
        if (!screenHistory.isEmpty()) {
            try {
                String previousFXML = screenHistory.pop();
                Parent previousRoot = FXMLLoader.load(Navigator.class.getResource(previousFXML));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(previousRoot));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}