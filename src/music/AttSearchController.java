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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AttSearchController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList<String> list = FXCollections.observableArrayList("Piano", "Guitar", "Keyboard", "Tabla", "Pakhavaaj", "Vocal");

    @FXML
    AnchorPane Anchor;

    @FXML
    ComboBox s_course;

    @FXML
    DatePicker s_date;

    @FXML
    TextField s_name;

    @FXML
    TableView<StudentTable> table;

    @FXML
    TableColumn<StudentTable, String> Name, Course, attDate;

    @FXML
    public void close() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }

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

    public void clear() {
        s_name.clear();
        s_course.getSelectionModel().clearSelection();
        s_date.setValue(null);
    }

    @FXML
    public void hide() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void find() {
        int count = 0;
        SearchController sc = new SearchController();
        boolean course = sc.cBox(s_course);
        boolean text = sc.textField(s_name);
        boolean date = sc.dateF(s_date);
        DBConnect db = new DBConnect();
        Connection conn = db.connect();
        PreparedStatement st = null;
        String sql = "select * from attendence";
        ResultSet rs = null;
        if (course && text && date) {
            AlertBox.display("Error", "Please Select any option");
        } else if (!course && text && date) {
            sql = sql + " where Course=?";
            try {
                sc.delete(table);
                st = conn.prepareStatement(sql);
                st.setString(1, s_course.getValue().toString());
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries found");
                }
                clear();
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        } else if (!date && text && course) {
            sql = sql + " where attDate=?";
            try {
                sc.delete(table);
                st = conn.prepareStatement(sql);
                st.setDate(1, java.sql.Date.valueOf(s_date.getValue()));
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        } else if (!text && course && date) {
            sql = sql + " where Name Like '" + s_name.getText() + "%';";
            try {
                sc.delete(table);
                st = conn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException e) {
                AlertBox.display("Error", e.getMessage());
            }
        } else if (!date && !course && text) {
            sql = sql + " where Course = ? AND attDate = ?";
            try {
                st = conn.prepareStatement(sql);
                st.setString(1, s_course.getValue().toString());
                st.setDate(2, java.sql.Date.valueOf(s_date.getValue()));
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        } else if (!date && !text && course) {
            sql = sql + " where attDate=? AND Name Like '" + s_name.getText() + "%';";
            try {
                sc.delete(table);
                st = conn.prepareStatement(sql);
                st.setDate(1, java.sql.Date.valueOf(s_date.getValue()));
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        } else if (!text && !course && date) {
            sql = sql + " where Course=? AND Name LIKE '" + s_name.getText() + "%';";
            try {
                sc.delete(table);
                st = conn.prepareStatement(sql);
                st.setString(1, s_course.getValue().toString());
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException e) {
                AlertBox.display("Error", e.getMessage());
            }
        } else {
            sql = sql + " where attDate=? AND Course=? AND Name LIKE '" + s_name.getText() + "%';";
            try {
                st = conn.prepareStatement(sql);
                st.setDate(1, java.sql.Date.valueOf(s_date.getValue()));
                st.setString(2, s_course.getValue().toString());
                System.out.println(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    count++;
                    table.getItems().add(new StudentTable(rs.getString("Name"), rs.getString("Course"), rs.getDate("attDate").toString()));
                    Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Course.setCellValueFactory(new PropertyValueFactory<>("course"));
                    attDate.setCellValueFactory(new PropertyValueFactory<>("attDate"));
                }
                if (count == 0) {
                    AlertBox.display("Notification", "No Entries Found");
                }
                clear();
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        }
    }

    @FXML
    public void MainMenu() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
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
    public void send() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }

    @FXML
    public void expense() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("Expenditure.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
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
    public void att() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("Attendence.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void DeleteStudent() {
        Pane myPane;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swarangan School of music");
            myPane = null;
            myPane = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }

    @FXML
    public void payFees() {
        Pane feePane;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Music");
            feePane = null;
            feePane = FXMLLoader.load(getClass().getResource("Fees.fxml"));
            Scene scene = new Scene(feePane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
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
            stage.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            find();
        }   
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        s_course.setItems(list);
    }

}
