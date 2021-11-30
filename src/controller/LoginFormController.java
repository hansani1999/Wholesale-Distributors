package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.User;

import java.io.IOException;
import java.util.ArrayList;


public class LoginFormController {
    static ArrayList<User> systemUserList=new ArrayList();
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane loginFormContext;
    public AnchorPane loginPane;
    public JFXTextField txtFxUserName;
    public Label errorLbl;

    {
        systemUserList.add(new User("U001","Shalani","Admin","shalani@123"));
        systemUserList.add(new User("U002","Dulan","Admin","dulan@234"));
        systemUserList.add(new User("U003","Akila","Cashier","567"));
        systemUserList.add(new User("U004","Dilini","Cashier","dilini@890"));
        systemUserList.add(new User("U005","Tharu","Cashier","tharu@111"));
    }

    public  void initialize() {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("this is password field");
        tooltip.setStyle("-fx-border-color : red; ");
        txtPassword.setTooltip(tooltip);
    }
    public boolean isExists(String uName,String pWord) {
        for (User u : systemUserList
        ) {
            if (uName.equalsIgnoreCase(u.getName()) && pWord.equals(u.getPassWord())) {
                return true;
            }
        }
        return false;
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        if(isExists(txtUserName.getText(),txtPassword.getText())){
            loadUserToDashBoard();
        }else {
            errorLbl.setText("Invalid user name/ password");
            //txtPassword.setStyle("-fx-border-color:orangered;");
            //txtFxUserName.setStyle("-jfx-border-color:orangered;");
            //txtFxUserName.setStyle("-jfx-unfocus-color: red");
            //new Alert(Alert.AlertType.WARNING, "Invalid Password..", ButtonType.CLOSE).show();
            //errorLbl.setStyle("-fx-background-color: antiquewhite");
        }

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainForm.fxml"));
        Parent parent = loader.load();
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(parent));*/
    }

    private void loadUserToDashBoard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainForm.fxml"));
        Parent parent = loader.load();
        String status = getStatus(txtUserName.getText());
        MainFormController dashBoardFormController = loader.getController();
        dashBoardFormController.setUserToDashBoard(txtUserName.getText(),status);
        dashBoardFormController.setUserName(txtUserName.getText());
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(parent));
        window.centerOnScreen();
    }

    private String getStatus(String user) {
        for (User u: systemUserList
        ) {
            if(user.equalsIgnoreCase(u.getName())){
                return u.getStatus();
            }
        }
        return "null";
    }


}
