package kval;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.cj.jdbc.MysqlDataSource;

public class RequestDao {
    
	MysqlDataSource dataSource;
	JdbcTemplate jdbc;

    public RequestDao(MysqlDataSource data) {
		super();
		jdbc = new JdbcTemplate(data);
    }

    public void save(Request application) {
        jdbc.update(
            "insert into `Request`(`IdRequest`, `AddDate`, `Comments`, `Status`, `IdTech`, `IdClient`) values(?, ?, ?, ?, ?, ?)",
            application.getId_request(),
            application.getAdd_date(),
            application.getComments(),
            application.getStatus(),
            application.getIdtech(),
            application.getIdclient()
        );
    }

    public void delete(Request application) {
    	jdbc.update(
            "delete from `Request` where id = ?",
            application.getId_request()
        );
    }

    public void update(Request application) {
    	jdbc.update(
            "update `Request` set `AddDate` = ?, `Comments` = ?, `Status` = ?, `IdTech` = ?, `IdClient` = ? where `IdRequest` = ?",
            application.getAdd_date(),
            application.getComments(),
            application.getStatus(),
            application.getIdtech(),
            application.getIdclient(),
            application.getId_request()
        );
    }

    public List<Request> getAll(){
        List<Request> application = jdbc.query("select * from `Request`", (resultSet, rowNum) -> {
          Request app = new Request();
          app.setId_request(resultSet.getInt("IdRequest"));
          app.setAdd_date(resultSet.getString("AddDate"));
          app.setComments(resultSet.getString("Comments"));
          app.setStatus(resultSet.getString("Status"));
          app.setIdtech(resultSet.getInt("IdTech"));
          app.setIdclient(resultSet.getInt("IdClient"));
          return app;
        });
        return application;
      }
    

}
