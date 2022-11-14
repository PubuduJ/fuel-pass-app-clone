package controller;

import db.DBConnection;
import dto.UserDTO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {
    public AnchorPane pneRegisterForm;
    public TextField txtNIC;
    public Label lblNICStatus;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAddress;
    public Button btnRegister;

    public void initialize() {
        Platform.runLater(txtNIC::requestFocus);
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneRegisterForm);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();

        btnRegister.setDisable(true);

        txtNIC.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String previous, String current) {
                setDisableElements(true);
                if (current.isBlank()) {
                    lblNICStatus.setText("Please Enter valid NIC to proceed");
                    lblNICStatus.setTextFill(Color.BLACK);
                    return;
                }
                else {
                    if (current.matches("^[0-9]{9}[Vv]$")) {
                        lblNICStatus.setText("Valid NIC ✅");
                        lblNICStatus.setTextFill(Color.GREEN);
                        setDisableElements(false);
                    }
                    else {
                        lblNICStatus.setText("Invalid NIC ❌");
                        lblNICStatus.setTextFill(Color.RED);
                    }
                }
            }
        });
    }

    public void lblLogin_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.LOGIN);
    }

    public void btnRegister_OnAction(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        if (firstName.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "First name can't be empty").showAndWait();
            txtFirstName.requestFocus();
            return;
        }
        else if (!firstName.matches("^[A-Za-z][A-Za-z ]+$")) {
            new Alert(Alert.AlertType.ERROR,"First name is invalid, Please enter a valid first name").showAndWait();
            txtFirstName.requestFocus();
            txtFirstName.selectAll();
            return;
        }
        else if (!lastName.equals("")) {
            if (!lastName.matches("^[A-Za-z][A-Za-z ]+$")) {
                new Alert(Alert.AlertType.ERROR,"Last name is invalid, Please enter a valid last name").showAndWait();
                txtLastName.requestFocus();
                txtLastName.selectAll();
                return;
            }
        }
        if (!txtAddress.getText().matches("^[A-Za-z0-9][A-Za-z0-9 \\\\/,.:;'\"]+$")) {
            new Alert(Alert.AlertType.ERROR,"Address is invalid, Please enter a valid address").showAndWait();
            txtAddress.requestFocus();
            txtAddress.selectAll();
            return;
        }
        UserDTO dto = new UserDTO(txtNIC.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), 16);

        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO User (nic,first_name,last_name,address,quota) VALUES (?,?,?,?,?)");
            stm.setString(1,dto.getNic().toUpperCase());
            stm.setString(2,dto.getFirstName());
            stm.setString(3,dto.getLastName());
            stm.setString(4,dto.getAddress());
            stm.setInt(5,dto.getQuota());
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 1) {
                new Alert(Alert.AlertType.INFORMATION,"Registration is success, You will be redirected to Login Form").showAndWait();
                lblLogin_OnMouseClicked(null);
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Fail to save the user in to the database").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"NIC is already existed, Please double check your NIC").showAndWait();
            txtNIC.requestFocus();
            txtNIC.selectAll();
        }
    }

    private void setDisableElements(boolean disable) {
        txtFirstName.setDisable(disable);
        txtLastName.setDisable(disable);
        txtAddress.setDisable(disable);
        btnRegister.setDisable(disable);
    }
}
