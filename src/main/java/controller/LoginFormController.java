package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import java.io.IOException;

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

    public void btnLogin_OnAction(ActionEvent actionEvent) {

    }
}
