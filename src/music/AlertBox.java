package music;



import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class AlertBox {
       
    public static void display(String title, String message) {
        Stage window = new Stage();
        Label label=new Label();
        if(title.equals("Error")){
        label.setId("red");
        }else if(title.equals("Notification")){
        label.setId("green");
        }
        
        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);

        
        label.setText(message);
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("CSS/alertboxcss.css");
        scene.getStylesheets().add("CSS/button.css");
        window.getIcons().add(new Image("/img/Alert Icon.png"));
        window.setScene(scene);
        window.showAndWait();
    }

}