/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author root
 */
public class ConfirmBoxController {

    /**
     * Initializes the controller class.
     */
    public boolean answer;

    public boolean set() {
        Button yes, no;
        Label l1 = new Label();
        GridPane grid = new GridPane();
        l1.setText("Are you sure want to continue?");
        l1.setId("otherlab");
        Stage window = new Stage();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        window.setTitle("Warning!!!");
        window.initModality(Modality.APPLICATION_MODAL);
        HBox hb = new HBox(50);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(l1);
        grid.setConstraints(hb, 0, 0);
        yes = new Button("Yes");
        grid.setConstraints(yes, 0, 2);
        no = new Button("no");
        grid.setConstraints(no, 2, 2);
        yes.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                answer = true;
                window.close();
            }
        });
        no.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                answer = false;
                window.close();
            }
        });
        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });
        grid.getChildren().addAll(hb, yes, no);
        Scene scene = new Scene(grid, 300, 200);
        scene.getStylesheets().add("CSS/button.css");
        scene.getStylesheets().add("CSS/label.css");
        scene.getStylesheets().add("CSS/background.css");
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }

}
