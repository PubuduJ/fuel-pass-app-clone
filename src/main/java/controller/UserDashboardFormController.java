package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import db.DBConnection;
import dto.UserDTO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.Navigation;
import util.Routes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void setData(String nic) throws WriterException {
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM User WHERE nic=?");
            stm.setString(1,nic);
            ResultSet rst = stm.executeQuery();
            rst.next();
            String nic_number = rst.getString("nic");
            String firstName = rst.getString("first_name");
            String lastName = rst.getString("last_name");
            String address = rst.getString("address");
            int quota = rst.getInt("quota");

            UserDTO user = new UserDTO(nic_number, firstName, lastName, address, quota);

            lblNIC.setText(user.getNic());
            lblName.setText(user.getFirstName() + " " + user.getLastName());
            lblAddress.setText(user.getAddress());
            lblQuota.setText(user.getQuota() + " L");

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String plainSecret = user.getNic() + "-" + user.getFirstName();
            BitMatrix encode = qrCodeWriter.encode(plainSecret, BarcodeFormat.QR_CODE, 218, 225);
            WritableImage image = new WritableImage(218,225);
            PixelWriter pixelWriter = image.getPixelWriter();
            for (int y = 0; y < encode.getHeight(); y++) {
                for (int x = 0; x < encode.getWidth(); x++) {
                    if (encode.get(x,y)) pixelWriter.setColor(x,y, Color.BLACK);
                    else pixelWriter.setColor(x,y, Color.WHITE);
                }
            }
            imgQR.setImage(image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDownload_OnAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save QR Code");
        File file = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(file);
        fileChooser.setInitialFileName("QR-code");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files (*.png)","*.png"));
        File saveLocation = fileChooser.showSaveDialog(btnDownload.getScene().getWindow());
        if (saveLocation != null) {
            BufferedImage bufferedQRImage = SwingFXUtils.fromFXImage(imgQR.getImage(), null);
            boolean saved = ImageIO.write(bufferedQRImage, "png", saveLocation);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION,"Downloaded").show();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Failed to download").show();
            }
        }
    }

    public void btnPrint_OnAction(ActionEvent actionEvent) {
        if (Printer.getDefaultPrinter() == null) {
            new Alert(Alert.AlertType.ERROR,"No default printer has been selected").showAndWait();
            return;
        }
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            printerJob.showPageSetupDialog(btnPrint.getScene().getWindow());
            boolean success = printerJob.printPage(imgQR);
            if (success) {
                printerJob.endJob();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Failed to print, try again").showAndWait();
            }
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Failed to initialize a new printer job").show();
        }
    }

    public void btnLogOut_OnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.WELCOME);
    }
}
