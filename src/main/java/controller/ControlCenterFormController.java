package controller;

import db.DBConnection;
import dto.UserDTO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ControlCenterFormController {
    public AnchorPane pneControlCenter;
    public TableView<UserDTO> tblUsers;
    public TextField txtSearch;
    public Button btnUpdateQuota;
    public Button btnRemoveUsers;
    public Spinner<Integer> txtQuota;

    public void initialize() {
        Platform.runLater(pneControlCenter::requestFocus);
        FadeTransition fd = new FadeTransition(Duration.millis(1000),pneControlCenter);
        fd.setFromValue(0);
        fd.setToValue(1);
        fd.playFromStart();
        txtQuota.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,16));
        btnRemoveUsers.setDisable(true);
        txtQuota.setDisable(true);
        btnUpdateQuota.setDisable(true);

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("quota"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tblUsers.setItems(FXCollections.observableArrayList(loadAllUsers()));

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String previous, String current) {
                if (previous.equals(current)) return;
                tblUsers.setItems(FXCollections.observableArrayList(findUsers(current)));
            }
        });

        tblUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserDTO>() {
            @Override
            public void changed(ObservableValue<? extends UserDTO> observableValue, UserDTO userDTO, UserDTO t1) {
                btnRemoveUsers.setDisable(tblUsers.getSelectionModel().getSelectedItems().isEmpty());
                txtQuota.setDisable(btnRemoveUsers.isDisable());
                btnUpdateQuota.setDisable(btnRemoveUsers.isDisable());
            }
        });
    }

    public void btnUpdateQuota_OnAction(ActionEvent actionEvent) {
        ObservableList<UserDTO> selectedUsers = tblUsers.getSelectionModel().getSelectedItems();
        for (UserDTO user : selectedUsers) {
            user.setQuota(txtQuota.getValue());
            updateUser(user);
        }
        tblUsers.refresh();
        tblUsers.getSelectionModel().clearSelection();
    }

    public void btnRemoveUsers_OnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete these users ?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.NO) {
            tblUsers.getSelectionModel().clearSelection();
            return;
        }
        ObservableList<UserDTO> selectedUsers = tblUsers.getSelectionModel().getSelectedItems();
        for (UserDTO user : selectedUsers) {
            removeUser(user);
        }
        tblUsers.getItems().removeAll(selectedUsers);
        tblUsers.getSelectionModel().clearSelection();
    }

    private ArrayList<UserDTO> loadAllUsers() {
        ArrayList<UserDTO> users = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM User");
            while (rst.next()) {
                String nic = rst.getString("nic");
                String firstName = rst.getString("first_name");
                String lastName = rst.getString("last_name");
                String address = rst.getString("address");
                int quota = rst.getInt("quota");
                UserDTO user = new UserDTO(nic, firstName, lastName, address, quota);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!");
        }
        return users;
    }

    private ArrayList<UserDTO> findUsers(String query) {
        ArrayList<UserDTO> users = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM User WHERE nic LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR address LIKE ? OR quota LIKE ?");
            query = "%" + query + "%";
            stm.setString(1, query);
            stm.setString(2, query);
            stm.setString(3, query);
            stm.setString(4, query);
            stm.setString(5, query);

            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                String nic = rst.getString("nic");
                String firstName = rst.getString("first_name");
                String lastName = rst.getString("last_name");
                String address = rst.getString("address");
                int quota = rst.getInt("quota");
                UserDTO user = new UserDTO(nic, firstName, lastName, address, quota);
                users.add(user);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!");
        }
        return users;
    }

    private void updateUser(UserDTO user) {
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE User SET quota=? WHERE nic=?");
            stm.setInt(1, user.getQuota());
            stm.setString(2, user.getNic());

            int affectedRows = stm.executeUpdate();
            if (affectedRows == 1) {
                new Alert(Alert.AlertType.INFORMATION, "Users were successfully updated in the database");
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!");
        }
    }

    private void removeUser(UserDTO user) {
        try {
            Connection connection = DBConnection.getInstance().getSingletonConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE FROM User WHERE nic=?");
            stm.setString(1, user.getNic());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 1) {
                new Alert(Alert.AlertType.INFORMATION, "Users were successfully removed from the database");
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
