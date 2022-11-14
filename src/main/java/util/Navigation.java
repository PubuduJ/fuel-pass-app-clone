package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class Navigation {

    private static AnchorPane pneContainer;

    public static void init(AnchorPane anchorPane) {
        Navigation.pneContainer = anchorPane;
    }

    public static Object navigate(Routes route) throws IOException {
        pneContainer.getChildren().clear();
        URL resource = null;
        switch (route) {
            case WELCOME:
                resource = Navigation.class.getResource("/view/WelcomeForm.fxml");
                break;
            case REGISTRATION:
                resource = Navigation.class.getResource("/view/RegisterForm.fxml");
                break;
            case LOGIN:
                resource = Navigation.class.getResource("/view/LoginForm.fxml");
                break;
            case ADMIN_LOGIN:
                resource = Navigation.class.getResource("/view/AdminLoginForm.fxml");
                break;
            case DASHBOARD:
                resource = Navigation.class.getResource("/view/UserDashboardForm.fxml");
                break;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = fxmlLoader.load();
        pneContainer.getChildren().add(root);
        AnchorPane.setLeftAnchor(root,0.0);
        AnchorPane.setRightAnchor(root,0.0);
        AnchorPane.setBottomAnchor(root,0.0);
        AnchorPane.setTopAnchor(root,0.0);
        return fxmlLoader.getController();
    }
}
