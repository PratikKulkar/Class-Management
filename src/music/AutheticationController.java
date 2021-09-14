/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pratik
 */
public class AutheticationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    PasswordField password;

    @FXML
    AnchorPane Anchor;

    @FXML
    public void authenticate() {
        DocumentController dc = new DocumentController();
        boolean textField = dc.emptyText(password);
        DBConnect db = new DBConnect();
        Connection conn = db.connect();
        Statement st = null;
        ResultSet rs = null;
        int count = 0;
        String sql = "select * from credentials where userID='admin' AND password='" + password.getText() + "';";
        if (textField) {
            AlertBox.display("Error", "Field can't be empty");
        } else {
            try {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    count++;
                }
                if (count != 0) {
                    Pane pane = null;
                    Stage window = new Stage();
                    pane = FXMLLoader.load(getClass().getResource("Registration.fxml"));
                    Scene scene = new Scene(pane);
                    window.setScene(scene);
                    window.show();
                    Stage stage = (Stage) Anchor.getScene().getWindow();
                    stage.close();
                } else {
                    AlertBox.display("Error", "Wrong Credentials");
                }
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            } catch (IOException ie) {
                AlertBox.display("Error", ie.getMessage());
            }
        }
    }

    @FXML
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            authenticate();
        }   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
