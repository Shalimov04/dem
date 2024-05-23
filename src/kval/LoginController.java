package kval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class LoginController implements Initializable{
	
	MysqlDataSource dataSource = new MysqlDataSource();
	JdbcTemplate jdbc = new JdbcTemplate(dataSource);

    @FXML
    private Button btn_login;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    void login(ActionEvent event) throws IOException {
        jdbc = new JdbcTemplate(dataSource);
        String enteredLogin = login.getText();
        String enteredPassword = password.getText();

        if (enteredLogin.isEmpty() || enteredPassword.isEmpty()) {
            System.out.println("Пожалуйста, введите логин и пароль.");
            return;
        }
        
        int condition = jdbc.query(
            "SELECT COUNT(*) AS cnt FROM `User` WHERE `Login`='" + enteredLogin + "' AND `Password`='" + enteredPassword + "'", 
            (ResultSet, RowNum) -> {
                return ResultSet.getInt("cnt");
            }
        ).getFirst();
        
        if (condition == 1) {
            Pane root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
            Stage stage = (Stage) btn_login.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 433));
        } else {
			Alert alert = new Alert(AlertType.INFORMATION);     
			alert.setContentText("Неверные данные!");
			alert.showAndWait();
        }
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setPort(3306);
		dataSource.setDatabaseName("TechRepair");
		dataSource.setUser("root");
		dataSource.setPassword("");
		
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
	}

}
