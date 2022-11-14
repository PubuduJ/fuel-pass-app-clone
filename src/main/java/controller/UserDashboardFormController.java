package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class UserDashboardFormController {

    public AnchorPane pneDashboardContainer;
    public Label lblQuota;
    public Label lblName;
    public Label lblNIC;
    public Label lblAddress;
    public Button btnDownload;
    public Button btnPrint;
    public Button btnLogOut;
    public ImageView imgQR;

    public void initialize() {
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneDashboardContainer);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
        Platform.runLater(pneDashboardContainer::requestFocus);
    }

    public void btnDownload_OnAction(ActionEvent actionEvent) {
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) {
    }

    public void btnLogOut_OnAction(ActionEvent actionEvent) {
    }
}
