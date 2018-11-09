package sample;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

  @FXML
  private Label firstName;

  @FXML
  private Label lastName;

  @FXML
  void connect(ActionEvent event) {
    final String DATABASE_URL = "jdbc:derby:C:\\Users\\ncrespo2300\\OneDrive\\IdeaProjects\\Books\\lib\\books";
    final String SELECT_QUERY =
        "SELECT authorID, firstName, lastName FROM authors";

    // use try-with-resources to connect to and query the database
    try (
        Connection connection = DriverManager.getConnection(
            DATABASE_URL, "deitel", "deitel");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
    {
      resultSet.next();
      firstName.setText(resultSet.getString(2));
      lastName.setText(resultSet.getString(3));
    } // AutoCloseable objects' close methods are called now
    catch (SQLException sqlException)
    {
      sqlException.printStackTrace();
    }
  }
}

