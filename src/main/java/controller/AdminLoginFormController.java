package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AdminLoginFormController {
    public AnchorPane pneAdminLoginForm;
    public PasswordField txtPassword;

    public void initialize() {
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneAdminLoginForm);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
        Platform.runLater(txtPassword::requestFocus);
    }

    public void txtPassword_OnAction(ActionEvent actionEvent) {

    }
}
