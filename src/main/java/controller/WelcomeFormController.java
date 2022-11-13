package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import java.io.IOException;

public class WelcomeFormController {

    public AnchorPane pneWelcome;
    public Button btnRegister;
    public Button btnLogin;

    public void initialize() {
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneWelcome);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
    }

    public void btnRegister_OnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REGISTRATION);
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) {
    }
}
