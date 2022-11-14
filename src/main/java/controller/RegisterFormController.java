package controller;

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

    public void btnRegister_OnAction(ActionEvent actionEvent) {
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
    }

    private void setDisableElements(boolean disable) {
        txtFirstName.setDisable(disable);
        txtLastName.setDisable(disable);
        txtAddress.setDisable(disable);
        btnRegister.setDisable(disable);
    }
}
