package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller {
    @FXML private TextArea textArea;
    @FXML private javafx.scene.control.Button Button;
    @FXML private Label outputLabel;
    @FXML private TextField textField;

    private Connection con;

    @FXML
    public void initialize() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mainAcad", "Lemetriss", "123");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonClick() {
        String text = textField.getText();
        outputLabel.setText("Hello, " + text);
    }

    public void resetButton() {
        outputLabel.setText("");
    }

    public void helloButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setContentText("Here was a " + textField.getText());
        alert.setTitle("Info");
        alert.setHeaderText("Do you want delete "+textField.getText());
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.YES)) {
            System.out.println("Yes");
            String text = queryDB();
            textArea.setText(text);
        } else {
            System.out.println("No");
        }
    }

    private String queryDB() {
        StringBuilder builder = new StringBuilder();
        try(Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
//                String time = resultSet.getTime("time");
                builder.append(String.format("%3d|%-15s%-15s%3d\n", id, name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return builder.toString();
        }
    }

    public void checkText(KeyEvent inputMethodEvent) {
        Button.setDisable(textField.getText().isEmpty());
    }


}