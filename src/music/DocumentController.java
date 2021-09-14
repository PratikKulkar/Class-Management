/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import com.github.sarxos.webcam.Webcam;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
class DBConnect {

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

class node {

    java.sql.Date data;
    node next;
    String year;

    node(java.sql.Date data) {
        this.data = data;
        next = null;
    }

    node(String year) {
        this.year = year;
        next = null;
    }
}

class inAtEnd {

    node head;

    inAtEnd(node head, java.sql.Date data) {
        if (head == null) {
            node n = new node(data);
            n.next = null;
        } else {
            node n = new node(data);
            this.head = head;
            while (head.next != null) {
                head = head.next;
            }
            head.next = n;
        }
    }

    inAtEnd(node head, String year) {
        if (head == null) {
            node n = new node(year);
            n.next = null;
        } else {
            node n = new node(year);
            this.head = head;
            while (head.next != null) {
                head = head.next;
            }
            head.next = n;
        }
    }

    node rel() {
        return head;
    }
}

class valinput {

    public boolean valid(TextField demo) {
        try {
            Long age = Long.parseLong(demo.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

public class DocumentController implements Initializable {

    DBConnect db = new DBConnect();
    Connection conn = db.connect();

    static String Ename, Ephnno, Eadd, Ecourse, Efees;
    static LocalDate EadmsnFD, Enextfd;
    @FXML
    Label file;
    @FXML
    Button Click;
    @FXML
    TextField name, phn_no, fp;
    @FXML
    TextArea add;
    @FXML
    DatePicker fee_date, admsn_date;
    @FXML
    private ComboBox course;
    @FXML
    private MenuItem search, payFee;
    @FXML
    private AnchorPane Anchor;

    Webcam webcam;
    Boolean PhotoCap;
    static File File;

    public void attDel(String table, String tdate) {
        DBConnect db = new DBConnect();
        Connection conn = db.connect();
        Statement st = null;
        String d = "", m = "";
        String sql = "select * from " + table;
        ResultSet rs = null;
        int i = 1;
        Date minDate = null, maxDate = null, temp = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                if (i == 1) {
                    minDate = rs.getDate(tdate);
                } else {
                    temp = rs.getDate(tdate);
                    if (temp.before(minDate)) {
                        minDate = temp;
                    } else {
                        maxDate = temp;
                    }
                }
                i++;
            }
            DateFormat df = new SimpleDateFormat("MM");
            d = df.format(minDate);
            m = df.format(maxDate);
        } catch (SQLException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
        int diff = Integer.parseInt(m) - Integer.parseInt(d);

        if (diff == 4) {
            String dsql = "select * from " + table;

            try {
                Date date = null;
                st = conn.createStatement();
                rs = st.executeQuery(dsql);
                while (rs.next()) {
                    date = rs.getDate(tdate);
                    DateFormat df = new SimpleDateFormat("MM");
                    String mm = df.format(date);
                    if (mm.equals(d)) {
                        String Dsql = "Delete from " + table + " where " + tdate + "=?";
                        PreparedStatement ps = conn.prepareStatement(Dsql);
                        ps.setDate(1, date);
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
        }
    }

    @FXML
    public void tsearch() {
        Pane mypan = null;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("tranSearch.fxml"));
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
            submit();
        }
    }

    @FXML
    public void expense() {
        Pane mypan = null;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("Expenditure.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void expenseS() {
        Pane mypan = null;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("ExpenSearch.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void hide() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }

    public void Message() {
        DBConnect db = new DBConnect();
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlD = new java.sql.Date(date.getTime());
        int i = 0;
        StringBuffer sb = null;
        int count = 0;
        String phnno = null;
        while (i != 3) {
            String sql = "select date('" + sqlD + "','+" + i + " day');";
            Connection conn = db.connect();
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                java.sql.Date Fsql = java.sql.Date.valueOf(rs.getString(1));
                String psql = "select * from test where nextFD=?";
                PreparedStatement ps = conn.prepareStatement(psql);
                ps.setDate(1, Fsql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    try {
                        count++;
                        phnno = null;
                        phnno = rs.getString("phnno");
                        String msg = new String("Your fee date is near%0Aplease pay as soon as possible%Regards,%0ASwarangan School of Music");
                        msg = msg.replace(' ', '+');
                        String urlLink = "http://127.0.0.1:8080/Message.php?phnno=" + phnno + "&msg=" + msg;
                        URL url = new URL(urlLink);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        sb = new StringBuffer();
                        String line;
                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                        }

                    } catch (MalformedURLException mu) {
                        AlertBox.display("Error", mu.getMessage());
                    } catch (ProtocolException pe) {
                        AlertBox.display("Error", pe.getMessage());
                    } catch (IOException io) {
                        AlertBox.display("Error", io.getMessage());
                    }
                }

            } catch (SQLException ex) {

            }
            i++;
        }
        if (count != 0) {
            AlertBox.display("Notification", sb.toString());
        }
    }

    @FXML
    public void Send() {
        Pane mypan = null;
        try {
            hide();
            Stage stage = new Stage();
            stage.setTitle("Swaragan School of music");
            mypan = null;
            mypan = FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
            Scene scene = new Scene(mypan);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        }
    }

    @FXML
    public void fees() {
        int fees;
        String s;
        String c;
        c = course.getValue().toString();
        fees f = new fees();
        if (null != c) {
            switch (c) {
                case "Piano":
                    fees = f.getPiano() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                case "Keyboard":
                    fees = f.getKeyboard() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                case "Guitar":
                    fees = f.getGuitar() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                case "Tabla":
                    fees = f.getTabla() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                case "Pakhavaaj":
                    fees = f.getPakhavaaj() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                case "Vocal":
                    fees = f.getVocal() + 300;
                    s = Integer.toString(fees);
                    fp.setText(s);
                    break;
                default:
                    break;
            }
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
            stage.showAndWait();
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
    public void camera() {
        Pane pane;
        Stage window = new Stage();
        pane = null;
        try {
            try {
                if (!emptyText(name) || !emptyText(phn_no) || !emptyArea(add) || !emptyBox(course) || !valDate(admsn_date)) {
                    Ename = name.getText();
                    Ephnno = phn_no.getText();
                    Eadd = add.getText();
                    Ecourse = course.getValue().toString();
                    EadmsnFD = admsn_date.getValue();
                    Enextfd = fee_date.getValue();
                    Efees = fp.getText();

                }
            } catch (Exception e) {

            }

            pane = FXMLLoader.load(getClass().getResource("VideoCapture.fxml"));
            Scene scene = new Scene(pane);
            window.setScene(scene);
            window.show();
            window.setOnCloseRequest(e -> {
                if (webcam.isOpen()) {
                    webcam.close();
                }
                Set<Thread> threadset = Thread.getAllStackTraces().keySet();
                Thread[] threadArray = threadset.toArray(new Thread[threadset.size()]);
                for (Thread x : threadArray) {
                    VideoCaptureDifficultWayController.loop=false;
                    if(x.getName().equals("videoTaker")||x.getName().equals("Attach Listener")){
                    x.interrupt();
                    }
                }
                
            });
            hide();
        } catch (IOException ex) {
            AlertBox.display("Error", ex.getMessage());
        } catch (Exception e) {

        }
    }

    @FXML
    public void nextFD() throws ParseException {
        Date FFD = null;
        try {

            LocalDate FD = admsn_date.getValue();
            Date date = java.sql.Date.valueOf(FD);

            Statement st = conn.createStatement();
            String sql = "select date ('"
                    + date + "','+1 month');";

            ResultSet rs = st.executeQuery(sql);
            String nfs = rs.getString(1);

            FFD = java.sql.Date.valueOf(nfs);
            LocalDate GH = FFD.toLocalDate();
            fee_date.setValue(GH);

        } catch (SQLException e) {
            AlertBox.display("Error", e.getMessage());
        }
    }

    public boolean emptyBox(ComboBox demo) {
        if (demo.getSelectionModel().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean emptyText(TextField demo) {
        if (demo.getText().trim().equals("")) {
            return true;
        }
        return false;
    }

    public boolean emptyArea(TextArea demo) {
        if (demo.getText().trim().equals("")) {
            return true;
        }
        return false;
    }

    public boolean valDate(DatePicker demo) {
        try {
            String s = demo.getValue().toString();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public byte[] readFile(String Filename) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(Filename);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bos != null ? bos.toByteArray() : null;
    }

    @FXML
    private void submit() {
        PreparedStatement ps = null, ts = null;
        String sql = "insert into test (phnno,name,Address,Course,nextFD,admsnFD,Fee,profile_pic)" + " values(?,?,?,?,?,?,?,?)";
        String tsql = "insert into FeesT (tran,Date,Cost,Name)" + " values(?,?,?,?)";
        boolean vname, vadd, vcourse, vphnno, vadmsnFD;
        vname = emptyText(name);
        vadd = emptyArea(add);
        vcourse = emptyBox(course);
        vphnno = emptyText(phn_no);
        vadmsnFD = valDate(admsn_date);
        if (vname || vadd || vcourse || vphnno || vadmsnFD || !PhotoCap) {
            AlertBox.display("Error", "Please check all the fields");
        } else if (phn_no.getText().length() < 10 || phn_no.getText().length() > 10) {
            AlertBox.display("Error", "Mobile number should be 10 digit only");
        } else {
            try {
                ps = conn.prepareStatement(sql);
                ts = conn.prepareStatement(tsql);
                ps.setString(1, phn_no.getText());
                ps.setString(2, name.getText());
                ps.setString(3, add.getText());
                ps.setString(4, course.getValue().toString());
                ps.setDate(5, java.sql.Date.valueOf(fee_date.getValue()));
                ps.setDate(6, java.sql.Date.valueOf(admsn_date.getValue()));
                ps.setInt(7, Integer.parseInt(fp.getText()));
                ps.setBytes(8, readFile(File.getAbsolutePath()));
                ps.executeUpdate();
                ts.setString(1, "Admission");
                ts.setDate(2, java.sql.Date.valueOf(admsn_date.getValue()));
                ts.setInt(3, Integer.parseInt(fp.getText()));
                ts.setString(4, name.getText());
                ts.executeUpdate();
                DeleteFile deleteFile = new DeleteFile("FirstCapture.jpg");
                clear();
                AlertBox.display("Notification", "Inserted Sucessfully");
            } catch (SQLException e) {
                AlertBox.display("Notification", e.getMessage());
            }
        }
    }

    public void clear() {
        name.clear();
        add.clear();
        phn_no.clear();
        admsn_date.getEditor().clear();
        fee_date.getEditor().clear();
        fp.clear();
        course.getSelectionModel().clearSelection();
    }

    @FXML
    public void close() {
        Stage stage = (Stage) Anchor.getScene().getWindow();
        stage.close();
    }

    public boolean testInet(String site) {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(site, 80);
        try {
            sock.connect(addr, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(Ename);
        phn_no.setText(Ephnno);
        add.setText(Eadd);
        course.setValue(Ecourse);
        fp.setText(Efees);
        admsn_date.setValue(EadmsnFD);
        fee_date.setValue(Enextfd);
        ObservableList<String> list = FXCollections.observableArrayList("Piano", "Guitar", "Keyboard", "Tabla", "Pakhavaaj", "Vocal");
        // TODO
        PhotoCap = VideoCaptureDifficultWayController.clicked;

        if (PhotoCap) {
            File = VideoCaptureDifficultWayController.file;
            file.setText(File.getName());
        } else {
            file.setText("Choose Your File");
        }
        webcam = Webcam.getDefault();
        if (webcam.isOpen()) {
            webcam.close();
        }
        Click.setText("Click");
        boolean internet = testInet("google.com");
        course.setItems(list);
        File file = new File("C:\\Users\\Rajaram\\Documents\\NetBeansProjects\\Music\\Definitons\\pratik.txt");
        int i = 1;
        while (i != 3) {
            if (!internet) {
                LocalDate now = LocalDate.now();
                java.sql.Date td = java.sql.Date.valueOf(now);
                ReadFile r = new ReadFile();
                r.openFile();
                String info = r.date();
                r.close();
                String[] start = info.split(" ");
                java.sql.Date txtD = java.sql.Date.valueOf(start[0]);
                boolean compare = txtD.equals(td);
                if (compare && start[2].equals("InternetNotified=True")) {
                    AlertBox.display("Error", "Internet not working");
                    DeleteFile d = new DeleteFile("pratik.txt");
                    WriteFile w = new WriteFile();
                    w.openFile();
                    w.writeFile(now.toString(), start[1], "InternetNotified=False");
                    w.CloseFile();
                } else if (!compare && start[2].equals("InternetNotified=False")) {
                    DeleteFile d = new DeleteFile("pratik.txt");
                    WriteFile w = new WriteFile();
                    w.openFile();
                    w.writeFile(now.toString(), "Message=True", "InternetNotified=True");
                    w.CloseFile();
                }
            } else {
                if (file.exists()) {
                    LocalDate now = LocalDate.now();
                    java.sql.Date td = java.sql.Date.valueOf(now);
                    ReadFile r = new ReadFile();
                    r.openFile();
                    String info = r.date();
                    r.close();
                    String[] start = info.split(" ");
                    java.sql.Date txtD = java.sql.Date.valueOf(start[0]);
                    boolean compare = txtD.equals(td);
                    if (compare && start[1].equals("Message=True")) {
                        try {
                            Message();
                        } catch (Exception e) {

                        }
                        DeleteFile d = new DeleteFile("pratik.txt");
                        WriteFile w = new WriteFile();
                        w.openFile();
                        w.writeFile(now.toString(), "Message=False", start[2]);
                        w.CloseFile();
                    } else if (!compare && start[1].equals("Message=False")) {
                        DeleteFile d = new DeleteFile("pratik.txt");
                        WriteFile w = new WriteFile();
                        w.openFile();
                        w.writeFile(now.toString(), "Message=True", "InternetNotified=True");
                        w.CloseFile();
                    }
                } else {
                    AlertBox.display("Error", "File not found");
                }
            }
            i++;
        }
    }
}
