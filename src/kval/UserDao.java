package kval;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
  JdbcTemplate jb;

  void save(User user) {
    jb.update(
          "insert into `user`(login, password) values(?, ?)",
          user.getLogin(),
          user.getPassword()
        );
  }
  
  void delete(User user) {
    jb.update(
          "delete from `user`where id = ?",
          user.getId()
        );
  }
  
  void update(User user) {
    jb.update(
          "update `user` set `login` = ?, `password` = ? where id = ?",
          user.getId()
        );
  }
  
  List <User> getAll() {
    List <User> users = jb.query(
        "select * from `user`", 
        (resultSet, rowNum) -> {
              User user = new User();
              user.setId(resultSet.getInt("id"));
              user.setLogin(resultSet.getString("login"));
              user.setPassword(resultSet.getString("password"));
              return user;
        });
        return users;
  }
}

