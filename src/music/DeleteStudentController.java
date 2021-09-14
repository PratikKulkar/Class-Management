/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author root
 */
class DBConn {

    Connection conn = null;
    String url = "jdbc:sqlite:C:\\Users\\Rajaram\\Documents\\NetBeansProjects\\Music\\Database\\music";

    public Connection connect() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            AlertBox.display("Error", e.getMessage());
        }
        return conn;
    }
}

public class DeleteStudentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField phnno, phn_no, Name, course, Fee;

    @FXML
    TextArea add;

    @FXML
    DatePicker admsnFD, nextFD, prev_date;

    @FXML
    Button delete;

    @FXML
    AnchorPane Anchor;

    @FXML
    ImageView image;

    static File file;

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
    public void hide() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }

    public boolean emptyText() {
        if (phnno.getText().trim().equals("")) {
            return true;
        }
        return false;
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

    public void clear() {
        phn_no.clear();
        phnno.clear();
        add.clear();
        admsnFD.setValue(null);
        nextFD.setValue(null);
        prev_date.setValue(null);
        Name.clear();
        course.clear();
        Fee.clear();
    }

    @FXML
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    @FXML
    public void handle1(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            update();
        }
    }

    @FXML
    public void handle2(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            delete();
        }
    }

    @FXML
    public void choosePhoto() {
        DocumentController dc = new DocumentController();
        boolean text = dc.emptyText(phnno);
        if (text) {
            AlertBox.display("Error", "Please, First Search for an Record");
        } else {
            try {
                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(null);
                this.file = file;
                String FilePath = file.toURI().toURL().toString();
                Image image1 = new Image(FilePath);
                image.setImage(image1);
            } catch (MalformedURLException ex) {
                Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public byte[] readFile(String FilePath) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(FilePath);
            fis = new FileInputStream(file);
            byte[] array = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(array)) != -1;) {
                bos.write(array, 0, len);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bos != null ? bos.toByteArray() : null;
    }

    @FXML
    public void update() {
        boolean photoUpdate = false;
        boolean exec = false;
        int count = 0;
        String sql = "Update test SET ";
        String tsql = "Select * from test where phnno= '" + phnno.getText() + "'";
        DBConnect db = new DBConnect();
        Connection conn = db.connect();
        DocumentController dc = new DocumentController();
        boolean phn = dc.emptyText(phnno);
        boolean phno = dc.emptyText(phn_no);
        byte[] readFile = null;
        if (phn) {
            AlertBox.display("Error", "First Perform any serach");
        } else {
            if (!phno) {

            }
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(tsql);
                try {
                    readFile = readFile(file.getAbsolutePath());
                } catch (Exception e) {

                }
                if (!rs.getString("name").equals(Name.getText())) {
                    sql = sql + "Name='" + Name.getText() + "'_";
                    exec = true;
                }
                if (!rs.getString("Course").equals(course.getText())) {
                    sql = sql + "Course='" + course.getText() + "'_";
                    exec = true;
                }
                if (!rs.getString("Address").equals(add.getText())) {
                    sql = sql + "Address='" + add.getText() + "'_";
                    exec = true;
                }
                if (!rs.getString("phnno").equals(phn_no.getText())) {
                    sql = sql + "phnno='" + phn_no.getText() + "'_";
                    exec = true;
                }
                String[] start = sql.split("_");
                for (int i = 0; i < start.length; i++) {
                    if (i == 0) {

                    }
                    if (i == 1) {
                        sql = start[0] + " " + start[i];
                    } else {
                        sql = sql + "," + start[i];
                    }
                }
                sql = sql + " Where phnno='" + phnno.getText() + "'";
                if (exec) {
                    Statement ts = conn.createStatement();
                    ts.executeUpdate(sql);
                    clear();
                }

                if (!Arrays.equals(rs.getBytes("profile_pic"), readFile)) {
                    if (readFile != null) {
                        String Isql = "Update test set profile_pic=? where phnno=?";
                        PreparedStatement ps = conn.prepareStatement(Isql);
                        ps.setBytes(1, readFile);
                        ps.setString(2, phnno.getText());
                        ps.executeUpdate();
                        ps.close();
                        photoUpdate = true;
                    }
                }
                if (!exec && photoUpdate) {
                    AlertBox.display("Notification", "Photo Updated");
                } else if (exec && !photoUpdate) {
                    AlertBox.display("Notification", "Information Changes done");
                } else if (exec && photoUpdate) {
                    AlertBox.display("Notification", "Photo and Information Updated");
                } else {
                    AlertBox.display("Error", "Please first update or change values");
                }

            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        }
    }

    @FXML
    public void delete() {
        ConfirmBoxController cb = new ConfirmBoxController();
        boolean control = cb.set();
        DBConnect db = new DBConnect();
        Connection conn = db.connect();
        Statement st = null;
        try {
            st = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "Delete from test where phnno = '" + phnno.getText() + "';";
            st.executeUpdate(sql);
            if (control) {
                conn.commit();
                AlertBox.display("Notification", "Deleted Sucessfully");
                clear();
            } else {
                conn.rollback();
                AlertBox.display("Error", "Transaction Rollbacked");
            }
        } catch (SQLException ex) {
            AlertBox.display("Error", ex.getMessage());
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException e) {

            }
        }

    }

    public boolean prevDate(ResultSet rs) {
        try {
            LocalDate s = rs.getDate("prev_date").toLocalDate();
            return true;
        } catch (Exception e) {
            return false;
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
    public void expense() {
        Pane pane;
        Stage window = new Stage();
        try {
            pane = null;
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
    public void att() {
        Pane pane;
        Stage window = new Stage();
        try {
            pane = null;
            pane = FXMLLoader.load(getClass().getResource("Attendence.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            hide();
        } catch (IOException e) {
            AlertBox.display("Error", e.getMessage());
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
    public void send() {
        Pane pane;
        Stage window = new Stage();
        try {
            pane = null;
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
    public void searchF() {
        Pane myPane;
        Stage window = new Stage();
        myPane = null;
        try {
            hide();
            myPane = FXMLLoader.load(getClass().getResource("search.fxml"));
            Scene scene = new Scene(myPane);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void search() {
        Connection conn = null;
        DBConnect cb = new DBConnect();
        conn = cb.connect();
        Statement st = null;
        ResultSet rs = null;
        try {
            boolean answer = emptyText();
            if (answer) {
                AlertBox.display("Notification", "Please Enter the mobile number");
            } else {
                st = conn.createStatement();
                String sql = "select * from test where phnno='" + phnno.getText() + "';";
                rs = st.executeQuery(sql);
                //File file=new File("ProfilePic.jpg");
                try {
                    File image = new File("output.jpg");
                    FileOutputStream fos = new FileOutputStream(image);
                    byte[] buffer = new byte[1];
                    InputStream is = rs.getBinaryStream("profile_pic");
                    while (is.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                    fos.close();
                } catch (FileNotFoundException ex) {
                    AlertBox.display("Error", ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedImage image1 = null;
                try {
                    image1 = ImageIO.read(new File("C:\\Users\\Rajaram\\Documents\\NetBeansProjects\\Music\\output.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(DeleteStudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                WritableImage card = SwingFXUtils.toFXImage(image1, null);
                image.setImage(card);
                Name.setText(rs.getString("Name"));
                phn_no.setText(rs.getString("phnno"));
                add.setText(rs.getString("Address"));
                admsnFD.setValue(rs.getDate("admsnFd").toLocalDate());
                nextFD.setValue(rs.getDate("nextFD").toLocalDate());
                boolean prev = prevDate(rs);
                if (prev) {
                    prev_date.setValue(rs.getDate("prev_date").toLocalDate());
                } else {
                    prev_date.setPromptText("New Admission");
                }
                course.setText(rs.getString("course"));
                Fee.setText(Integer.toString(rs.getInt("Fee")));
            }
        } catch (SQLException e) {
            if (e.getMessage().equals("ResultSet closed")) {
                AlertBox.display("Error", "No record found");
            } else {
                AlertBox.display("Error", e.getMessage());
            }
        } finally {
            try {
                conn.close();
                st.close();
            } catch (SQLException ex) {

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
