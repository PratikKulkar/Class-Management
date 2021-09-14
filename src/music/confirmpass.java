/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author pratik
 */
public class confirmpass {

    public boolean accept;

    public void True() {
        accept = true;
    }

    public void False() {
        accept = false;
    }

    public boolean open() {
        DocumentController dc = new DocumentController();
        GridPane grid = new GridPane();
        Label l1 = new Label();
        Label user = new Label("Username:");
        user.setId("otherlab");
        Label username = new Label("admin");
        username.setId("label-admin");
        Button submit = new Button("submit");
        l1.setText("Password:");
        l1.setId("otherlab");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(8);
        grid.setVgap(10);
        Stage window = new Stage();
        window.setTitle("Notification");
        window.initModality(Modality.APPLICATION_MODAL);
        PasswordField pass = new PasswordField();
        pass.setPromptText("Admin password here");
        grid.setConstraints(user, 0, 0);
        grid.setConstraints(username, 1, 0);
        grid.setConstraints(l1, 0, 1);
        grid.setConstraints(pass, 1, 1);
        grid.setConstraints(submit, 0, 2);
        submit.setOnAction(e -> {
            boolean proceed = dc.emptyText(pass);
            if (proceed) {
                AlertBox.display("Error", "Field can't be empty");
            } else {
                try {
                    DBConnect db = new DBConnect();
                    Connection conn = db.connect();
                    Statement ps = null;
                    ResultSet rs = null;
                    String sql = "Select * from credentials where userID='admin'";
                    ps = conn.createStatement();
                    rs = ps.executeQuery(sql);
                    if (pass.getText().equals(rs.getString("password"))) {
                        accept = true;
                        window.close();
                    } else {
                        accept = false;
                        AlertBox.display("Error", "Wrong Password");
                    }
                    conn.close();
                    ps.close();
                    rs.close();
                } catch (SQLException ex) {
                    
                }
            }
        });
        grid.getChildren().addAll(user, username, l1, pass, submit);
        Scene scene = new Scene(grid, 300, 150);
        scene.getStylesheets().add(getClass().getResource("/CSS/textbox.css").toString());
        scene.getStylesheets().add(getClass().getResource("/CSS/button.css").toString());
        scene.getStylesheets().add(getClass().getResource("/CSS/Background.css").toString());
        scene.getStylesheets().add(getClass().getResource("/CSS/label.css").toString());
        window.setScene(scene);
        window.showAndWait();
        return accept;
    }
}
