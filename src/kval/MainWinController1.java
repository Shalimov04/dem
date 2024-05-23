package kval;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainWinController1 implements Initializable{
	
	MysqlDataSource dataSource = new MysqlDataSource();
	JdbcTemplate jdbc;
	RequestDao applicationDao = new RequestDao(dataSource);
	ExecutorDao executorDao = new ExecutorDao(dataSource);
	ObservableList<Request> appl_lst = FXCollections.observableArrayList();
	ObservableList<Request> executor_lst = FXCollections.observableArrayList();

    @FXML
    private TableView<Request> application_table;
    
    @FXML
    private TableView<Request> executor_table;
	
    @FXML
    private Button add_appl_btn;

    @FXML
    private Button add_exec_btn;

    @FXML
    private Button change_appl_btn;

    @FXML
    private Button change_exec_btn;

    @FXML
    private Button clear_btn;
    
    @FXML
    private Button stats;

    @FXML
    private TableColumn<Request, Integer> col_id_appl;
    
    @FXML
    private TableColumn<Request, String> col_date;

    @FXML
    private TableColumn<Request, String> col_device;
    
    @FXML
    private TableColumn<Request, String> col_status;

    @FXML
    private TableColumn<Request, Integer> col_executor;

    @FXML
    private TableColumn<Request, Integer> col_id_exec;

    @FXML
    private TableColumn<Request, String> col_name;


    @FXML
    private TextField date_field;
    
    @FXML
    private TextField appl_id_field;

    @FXML
    private Button del_appl_btn;

    @FXML
    private Button del_exec_btn;

    @FXML
    private TextField device_field;

    @FXML
    private TextField executor_field;

    @FXML
    private TextField name_field;

    @FXML
    private Button search_btn;

    @FXML
    private TextField status_field;
    
    @FXML
    void get_stats(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);     
        int cnt = jdbc.query(
                "SELECT COUNT(*) AS cnt FROM application WHERE status='выполнено'", 
                (ResultSet, RowNum) -> {
                    return ResultSet.getInt("cnt");
                }
            ).getFirst();
		alert.setContentText("Заявок выполнено: " + Integer.toString(cnt));
		alert.showAndWait();
    }

    public void add_appl(ActionEvent event) {
		String date = date_field.getText();
		String device = device_field.getText();
		String status = status_field.getText();
		Integer executor = Integer.parseInt(executor_field.getText());
		
		if(date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			applicationDao.save(new Request(0, date, device, status, executor));
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);     
			alert.setContentText("Неверный формат даты! Верный: 0000-00-00");
			alert.showAndWait();
		}
		initialize(null, null);
	}
	// Event Listener on Button[#del_appl_btn].onAction
	@FXML
	public void del_appl(ActionEvent event) {
		Request appl = application_table.getSelectionModel().getSelectedItem();
		if(appl != null) {
			applicationDao.delete(appl);
			initialize(null, null);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);     
			alert.setContentText("Вы ничего не выбрали!");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#change_appl_btn].onAction
	@FXML
	public void change_appl(ActionEvent event) {
		Request appl = application_table.getSelectionModel().getSelectedItem();
		if(appl != null) {
			String date = date_field.getText();
			String device = device_field.getText();
			String status = status_field.getText();
			Integer executor = Integer.parseInt(executor_field.getText());
			
			appl.setDate(date);
			appl.setDevice(device);
			appl.setIdExe(executor);
			appl.setStatus(status);
			
			if(date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
				applicationDao.update(appl);
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);     
				alert.setContentText("Неверный формат даты! Верный: 0000-00-00");
				alert.showAndWait();
			}
			initialize(null, null);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);     
			alert.setContentText("Вы ничего не выбрали!");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#add_exec_btn].onAction
	@FXML
	public void add_exec(ActionEvent event) {
		initialize(null, null);
	}
	// Event Listener on Button[#del_exec_btn].onAction
	@FXML
	public void del_exec(ActionEvent event) {
		initialize(null, null);
	}
	// Event Listener on Button[#change_exec_btn].onAction
    @FXML
    void change_exec(ActionEvent event) {

    }

    @FXML
    void clear_search(ActionEvent event) {
    	initialize(null, null);
    }

    @FXML
    void search(ActionEvent event) {
    	Integer executor = null;
		String date = date_field.getText();
		String device = device_field.getText();
		String status = status_field.getText();
		String exec_text = executor_field.getText();
		if(!exec_text.isBlank()) {
			executor = Integer.parseInt(exec_text);
		}
			
		
		application_table.getItems().clear();
		appl_lst.addAll(
			applicationDao.getFiltered(new Request(0, date, device, status, executor))
		);
		application_table.setItems(appl_lst);
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		application_table.getItems().clear();
		executor_table.getItems().clear();
	    dataSource.setServerName("localhost");
	    dataSource.setPort(3306);
	    dataSource.setDatabaseName("apps");
	    dataSource.setUser("root");
	    dataSource.setPassword("");
	    
	    jdbc = new JdbcTemplate(dataSource);
		
		col_id_appl.setCellValueFactory(new PropertyValueFactory<Request, Integer>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<Request, String>("date"));
        col_device.setCellValueFactory(new PropertyValueFactory<Request, String>("device"));
        col_status.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        col_executor.setCellValueFactory(new PropertyValueFactory<Request, Integer>("idExe"));
        
		col_id_exec.setCellValueFactory(new PropertyValueFactory<Request, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Request, String>("name"));
		
		RequestDao applicationDao = new RequestDao(dataSource);
		ExecutorDao executorDao = new ExecutorDao(dataSource);
		appl_lst.addAll(applicationDao.getAll());
		executor_lst.addAll(executorDao.getAll());
	    application_table.setItems(appl_lst);
	    executor_table.setItems(executor_lst);
	}
}


