/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author root
 */
public class AttendenceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField name1, name2, name3, name4;

    @FXML
    DatePicker date1, date2, date3, date4;

    @FXML
    ComboBox c1, c2, c3, c4;

    @FXML
    AnchorPane Anchor;

    
    @FXML
    public void delinfo() {
        Pane myPane;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swarangan School of music");
            myPane = null;
            myPane = FXMLLoader.load(getClass().getResource("information.fxml"));
            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }
    
    
    @FXML
    public void trans() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("tranSearch.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }
    
    @FXML
    public void string() {
        boolean a, b, c, d;
        DocumentController dc = new DocumentController();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String edat = df.format(date);
        java.sql.Date f = java.sql.Date.valueOf(edat);
        LocalDate ld = f.toLocalDate();
        boolean b1 = dc.valDate(date1);
        boolean b2 = dc.valDate(date2);
        boolean b3 = dc.valDate(date3);
        boolean b4 = dc.valDate(date4);
        try {
            a = dc.emptyBox(c1);
            if (!a && b1) {
                date1.setValue(ld);
            }
            b = dc.emptyBox(c2);
            if (!b && b2) {
                date2.setValue(ld);
            }
            c = dc.emptyBox(c3);
            if (!c && b3) {
                date3.setValue(ld);
            }
            d = dc.emptyBox(c4);
            if (!d && b4) {
                date4.setValue(ld);
            }
        } catch (Exception e) {

        }
    }

    public void clear() {
        c1.getSelectionModel().clearSelection();
        c2.getSelectionModel().clearSelection();
        c3.getSelectionModel().clearSelection();
        c4.getSelectionModel().clearSelection();
        name1.clear();
        name2.clear();
        name3.clear();
        name4.clear();
        date1.setValue(null);
        date2.setValue(null);
        date3.setValue(null);
        date4.setValue(null);
    }

    @FXML
    public void attS() {
        Pane mypan;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("AttSearch.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void att() {
        Pane mypan;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("Attendence.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }
    
    @FXML
    public void send() {
        Pane mypan;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }
    
    @FXML
    public void expense() {
        Pane mypan;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("Expenditure.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }
    
    @FXML
    public void expsearch() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("ExpenSearch.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }
    
    @FXML
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            submit();
        }   
    }
    
    @FXML
    public void handle1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clear();
        }   
    }
    
    
    @FXML
    public void submit() {
        DBConn db = new DBConn();
        Connection conn = db.connect();
        PreparedStatement ps = null;
        boolean a, b, c, d;
        String calc = "";
        int count = 0;
        String sql = "INSERT into attendence (Name,attDate,Course) VALUES (?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            DocumentController dc = new DocumentController();
            a = dc.emptyText(name1);
            b = dc.emptyText(name2);
            c = dc.emptyText(name3);
            d = dc.emptyText(name4);
            if (!a) {
                calc = calc + 1;
            }
            if (!b) {
                calc = calc + 2;
            }
            if (!c) {
                calc = calc + 3;
            }
            if (!d) {
                calc = calc + 4;
            }
            int ite = Integer.parseInt(calc);
            while (ite != 0) {
                int mod = ite % 10;
                if (mod == 1) {
                    ps.setString(1, name1.getText());
                    ps.setDate(2, java.sql.Date.valueOf(date1.getValue()));
                    ps.setString(3, c1.getValue().toString());
                } else if (mod == 2) {
                    ps.setString(1, name2.getText());
                    ps.setDate(2, java.sql.Date.valueOf(date2.getValue()));
                    ps.setString(3, c2.getValue().toString());
                } else if (mod == 3) {
                    ps.setString(1, name3.getText());
                    ps.setDate(2, java.sql.Date.valueOf(date3.getValue()));
                    ps.setString(3, c3.getValue().toString());
                } else if (mod == 4) {
                    ps.setString(1, name4.getText());
                    ps.setDate(2, java.sql.Date.valueOf(date4.getValue()));
                    ps.setString(3, c4.getValue().toString());
                }
                try {
                    ps.executeUpdate();
                    count++;
                } catch (SQLException e) {
                    AlertBox.display("Error", e.getMessage());
                }
                ite = ite / 10;
            }
            if (count != 0) {
                AlertBox.display("Notification", "Attendence Marked");
            }
        } catch (SQLException ex) {
            AlertBox.display("Error", ex.getMessage());
        } finally {
            try {
                conn.close();
                ps.close();
            } catch (SQLException e) {
                clear();
            }
        }
    }

    @FXML
    public void mainMenu() {
        Pane myPane;
        Stage window = new Stage();
        myPane = null;
        try {
            hide();
            myPane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(myPane);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void search() {
        Pane mypan;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("search.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void fees() {
        Pane pane;
        Stage window = new Stage();
        try {
            pane = null;
            pane = FXMLLoader.load(getClass().getResource("Fees.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }

    @FXML
    public void DeleteStudent() {
        Pane pane;
        Stage window = new Stage();
        try {
            pane = null;
            pane = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }

    @FXML
    public void hide() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list = FXCollections.observableArrayList("Piano", "Guitar", "Keyboard", "Tabla", "Pakhavaaj", "Vocal");
        c1.setItems(list);
        c2.setItems(list);
        c3.setItems(list);
        c4.setItems(list);
    }

}
