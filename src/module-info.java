/**
 * 
 */
/**
 * 
 */
module kval {
	requires spring.jdbc;
	requires java.sql;
	requires javafx.controls;
	requires javafx.fxml;
	requires mysql.connector.j;
	opens kval to javafx.graphics, javafx.fxml, javafx.base;
}