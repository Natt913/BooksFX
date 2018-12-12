package sample;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import org.apache.derby.iapi.db.Database;

import javax.swing.text.TableView;

public class Controller {

  final String Database_Url =
          "jdbc:derby:C:\\Users\\ncrespo2300\\OneDrive\\IdeaProjects\\BooksFX\\lib\\books";
  final String Select_Query =
          "SELECT authorID, firstName, lastName FROM authors";


  @FXML
  private Label firstName;

  @FXML
  private Label lastName;

  @FXML
  void connect(ActionEvent event) {

    // use try-with-resources to connect to and query the database
    //Main thing with databases is to have the connection, statement, and resultset
    try (
            Connection connection = DriverManager.getConnection(
                    Database_Url, "deitel", "deitel");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Select_Query)) {
      resultSet.next();
      firstName.setText(resultSet.getString(2));
      lastName.setText(resultSet.getString(3));
      // AutoCloseable objects' close methods are called now
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
  }

  @FXML
  private TableView authors;
  @FXML
  private TableColumn firstNameCol;
  @FXML
  private TableColumn lastNameCol;


  @FXML
  void populateTable(ActionEvent populate) {

    try (
            Connection connection = DriverManager.getConnection(
                    Database_Url, "deitel", "deitel");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Select_Query)) {
      // get ResultSet's meta data
      ResultSetMetaData metaData = resultSet.getMetaData();
      int numberOfColumns = metaData.getColumnCount();

      System.out.printf("Authors Table of Books Database:%n%n");

      // display the names of the columns in the ResultSet
      for (int i = 1; i <= numberOfColumns; i++)
        System.out.printf("%-8s\t", metaData.getColumnName(i));
      System.out.println();

      // display query results
      while (resultSet.next()) {
        for (int i = 1; i <= numberOfColumns; i++)
          System.out.printf("%-8s\t", resultSet.getObject(i));
        System.out.println();
      }
    } // AutoCloseable objects' close methods are called now
    catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
  }

  @FXML
  private Button submitGuessButton;

  //TextField giving me problems.
  //@FXML
  //TextField input;

  @FXML
  private Label rightOrwrong;

  //@FXML
  //void submitGuess(ActionEvent submit) {

    //try (
      //      Connection connection = DriverManager.getConnection(
      //              Database_Url, "deitel", "deitel");
      //      Statement statement = connection.createStatement();
      //      ResultSet resultSet = statement.executeQuery(Select_Query)) {

      //int guessAuthors = Integer.parseInt(inputGuess.getText());

      //int correctAnswer = 0;

      //while (resultSet.next()) {
      //  correctAnswer++;
      //}

      //if (guessAuthors != correctAnswer) {
      //  rightOrwrong.setText("Wrong!");
      //} else {
      //  rightOrwrong.setText("Correct!");
      //}

    //} catch (SQLException sqlException) {
    //  sqlException.printStackTrace();
    //}
  //}

  @FXML
  private Label answer;

  @FXML
  public void displayAnswer(ActionEvent display) {
    try (
            Connection connection = DriverManager.getConnection(
                    Database_Url, "deitel", "deitel");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Select_Query)) {

      int correctAnswer = 0;
      while (resultSet.next()) {
        correctAnswer++;
      }

      answer.setText(Integer.toString(correctAnswer));

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
  }

  @FXML
  public void deleteAuthor(ActionEvent delete) {
    try {
      Connection connection = DriverManager.getConnection(
              Database_Url, "deitel", "deitel");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(Select_Query);
      {
      }
      int deleteResult = statement.executeUpdate("delete from authorID, firstName, lastName where authors = ");
    } catch (SQLException sqlException) {
      System.err.println(sqlException.getMessage());
    } finally {
      try {
        Connection connection = DriverManager.getConnection(
                Database_Url, "deitel", "deitel");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Select_Query);
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }


}

