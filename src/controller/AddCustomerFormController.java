package controller;

import dao.custom.impl.*;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddCustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtTitle;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtCode;


    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        Customer c1 = new Customer(
                txtId.getText(),txtTitle.getText(),txtName.getText(),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtCode.getText()
        );

        //customerIdList.add(c1.getCustomerId());


        if(new CustomerDAOImpl().saveCustomer(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

}
