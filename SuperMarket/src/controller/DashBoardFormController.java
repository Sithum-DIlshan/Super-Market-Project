package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.LoginBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import bo.custom.impl.LoginBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class DashBoardFormController {
    public JFXTextField cashierUsrName;
    public JFXPasswordField cashierPsswrd;
    public JFXTextField adminUsrName;
    public JFXPasswordField adminPsswrd;
    @FXML
    private ImageView imageuser, imageadmin;
    @FXML
    private AnchorPane user, admin;
    private final LoginBO loginBO = (LoginBO) BoFactory.getBoFactory().getBO(BoFactory.BoTypes.LOGIN);


    @FXML
    public void handleButtonOnAction(MouseEvent event) {
        if (event.getTarget() == imageuser) {
            user.setVisible(true);
            admin.setVisible(false);
        } else if (event.getTarget() == imageadmin) {
            admin.setVisible(true);
            user.setVisible(false);
        }
    }

    public void adminLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (loginBO.adminLoginSuccess(adminUsrName.getText(), adminPsswrd.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login Successful");
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {

            } else if (result.get() == ButtonType.OK) {
                Parent load = FXMLLoader.load(getClass().getResource("../view/AdminAccessForm.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
            //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void cashierLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (loginBO.cashierLoginSuccess(cashierUsrName.getText(), cashierPsswrd.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login Successful");
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {

            } else if (result.get() == ButtonType.OK) {
                Parent load = FXMLLoader.load(getClass().getResource("../view/CashierAccessForm.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
            //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }
}
