package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class AdminLoginFormController {
    private static int attempts = 3;
    private static final String ADMIN_PASSWORD = "password";
    public AnchorPane pneAdminLoginForm;
    public PasswordField txtPassword;



    public void initialize() {
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneAdminLoginForm);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
        Platform.runLater(txtPassword::requestFocus);
    }

    public void txtPassword_OnAction(ActionEvent actionEvent) throws IOException, URISyntaxException {
        if (!txtPassword.getText().equals(ADMIN_PASSWORD)) {
            if (attempts == 0) {
                new Alert(Alert.AlertType.ERROR,"You have reached maximum number of attempts...").showAndWait();
                Platform.exit();
                return;
            }
            URL resource = this.getClass().getResource("/audios/alertSound.mp3");
            Media media = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Admin Password. You have " + attempts + " more attempts to try again");
            attempts -= 1;

            InputStream resourceAsStream = this.getClass().getResourceAsStream("/images/padlock.png");
            Image image = new Image(resourceAsStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(45);
            imageView.setFitHeight(45);
            alert.setGraphic(imageView);
            alert.setTitle("Access Denied");
            alert.setHeaderText("Invalid Login Credentials");
            alert.showAndWait();

            mediaPlayer.dispose();
            txtPassword.requestFocus();
            return;
        }
        Navigation.navigate(Routes.CONTROL_CENTER);
    }
}
