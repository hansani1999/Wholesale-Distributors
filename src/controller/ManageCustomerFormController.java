package controller;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageCustomerFormController {

    public JFXTextField txtId;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXButton btnClear;
    public JFXTextField txtPostalCode;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXTextField txtCustomerName;
    public JFXComboBox<String> titleCombo;
    public TableColumn colCustomerId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public TableView<CustomerDTO> tblCustomer;
    public JFXButton btnDelete;
    ObservableList<CustomerDTO> customers;

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        titleCombo.getItems().addAll("Mr.","Mrs.","Miss.","Ven.");
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setDisable(true);
        try {
            loadAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomer) -> {
            if (selectedCustomer!=null) {
                setCustomerDetailsToForm(selectedCustomer);
                btnDelete.setDisable(false);
            }
        });
    }

    private void setCustomerDetailsToForm(CustomerDTO customer) {
        txtId.setText(customer.getCustomerId());
        titleCombo.setValue(customer.getTitle());
        txtCustomerName.setText(customer.getCustomerName());
        txtAddress.setText(customer.getCustomerAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());

        btnAdd.setVisible(false);
        btnUpdate.setVisible(true);
        txtId.setDisable(true);
    }

    private void loadAllCustomers() throws SQLException {
        List<CustomerDTO> allCustomers=customerBO.getAllCustomers();
        customers = FXCollections.observableArrayList();
        customers.clear();
        for (CustomerDTO customer : allCustomers) {
            customers.add(new dto.CustomerDTO(customer.getCustomerId(),customer.getTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
        }
        tblCustomer.setItems(customers);
    }

    public void clearFormOnAction(ActionEvent actionEvent) {
        clearAll();
    }

    private void clearAll(){
        txtId.clear();
        titleCombo.setValue("");
        txtCustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();

        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setDisable(true);
        txtId.setDisable(false);
    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        if (!validateCustomer()) {
            return;
        }

        CustomerDTO customer = new CustomerDTO(
                txtId.getText(),
                titleCombo.getValue(),
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );


        try {
            if (customerBO.ifExistsCustomer(customer.getCustomerId())) {
                new Alert(Alert.AlertType.ERROR,"Customer "+customer.getCustomerId()+" Already Exists!").show();
                return;
            }
            if (customerBO.saveCustomer(customer)) {
                loadAllCustomers();
                new Alert(Alert.AlertType.INFORMATION,"Customer saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error...Try again").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean validateCustomer() {
        Pattern idPattern  = Pattern.compile("^(C)[0-9]{3,4}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{3,30}$");
        Pattern city = Pattern.compile("^[A-z ]{3,20}$");
        Pattern province = Pattern.compile("^[A-z ]{3,20}$");
        Pattern postalCode = Pattern.compile("^[0-9]{5}$");

        if(!idPattern.matcher(txtId.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid ID").show();
            return false;
        }else if(!namePattern.matcher(txtCustomerName.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return false;
        }else if(!addressPattern.matcher(txtAddress.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return false;
        }else if(!city.matcher(txtCity.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data").show();
            return false;
        }else if(!province.matcher(txtProvince.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data").show();
            return false;
        }else if(!postalCode.matcher(txtPostalCode.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data").show();
            return false;
        }

        return true;
    }


    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        if (!validateCustomer()) {
            return;
        }
        CustomerDTO customer = new CustomerDTO(
                txtId.getText(),
                titleCombo.getValue(),
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );

        if (customerBO.updateCustomer(customer)) {
            loadAllCustomers();
            clearAll();
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Try again").show();
        }
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Are you sure whether you want to delete this Customer?", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            if (customerBO.deleteCustomer(txtId.getText())) {
                loadAllCustomers();
                clearAll();
                //btnDelete.setDisable(true);
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try again").show();
            }
        }

    }
}
