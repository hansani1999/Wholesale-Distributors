package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class MainFormController {
    public AnchorPane dashBoardPane;
    public Label lblUserName;
    public Label lblStatus;
    public JFXButton btnMakeOrder;
    public JFXButton btnManageOrders;
    public JFXButton btnSettings;
    public JFXButton btnManageItems;
    public JFXButton btnSystemReports;
    static String userName;

   /* public void initialize(){
        if(lblStatus.getText()=="Cashier"){
            btnSettings.setVisible(false);
            btnManageItems.setDisable(true);
            btnSystemReports.setDisable(true);
            btnManageOrders.setDisable(true);
        }
    }*/

    protected void setUserName(String userName){
        this.userName=userName;
    }

    public void openMakeOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("MakeOrderForm");
    }

    public void openManageOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ManageCustomerOrderForm");
    }

    public void manageItemsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ManageItemsForm");
    }

    public void openCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ManageCustomerForm");
    }


    void loadUi(String filName) throws IOException {
        URL resource = getClass().getResource("../views/"+filName+".fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardPane.getChildren().clear();
        dashBoardPane.getChildren().add(load);
    }

    public void viewHomeOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("HomePage");
    }

    public void setUserToDashBoard(String userName,String password){
        lblUserName.setText(userName);
        lblStatus.setText(password);

        if(lblStatus.getText()=="Cashier"){
            btnSettings.setDisable(true);
            btnManageItems.setDisable(true);
            btnSystemReports.setDisable(true);

        }
    }


    public void viewDashBoard(ActionEvent actionEvent) throws IOException {
        loadUi("DashboardForm");
    }
}
