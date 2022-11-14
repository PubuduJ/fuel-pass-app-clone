package controller;

import com.google.zxing.WriterException;
import db.DBConnection;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    public AnchorPane pneLoginForm;
    public TextField txtNIC;
    public Button btnLogin;

    public void initialize() {
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneLoginForm);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
        Platform.runLater(txtNIC::requestFocus);
    }

    public void lblRegister_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.REGISTRATION);
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) throws IOException, WriterException {
        if (!txtNIC.getText().matches("^[0-9]{9}[Vv]$")) {
            new Alert(Alert.AlertType.ERROR,"Please enter a valid NIC number to login !").showAndWait();
            txtNIC.selectAll();
            txtNIC.requestFocus();
            return;
        }
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM User WHERE nic=?");
            stm.setString(1, txtNIC.getText().toUpperCase());
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                UserDashboardFormController ctrl = (UserDashboardFormController) Navigation.navigate(Routes.DASHBOARD);
                ctrl.setData(txtNIC.getText());
            }
            else {
                new Alert(Alert.AlertType.ERROR,"NIC number does not exist in the data base, Register first !! You will be redirected to Registration Form").showAndWait();
                lblRegister_OnMouseClicked(null);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").showAndWait();
            txtNIC.requestFocus();
            txtNIC.selectAll();
        }
    }
}
