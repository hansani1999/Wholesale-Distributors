package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import com.jfoenix.controls.JFXButton;
import dto.ItemDTO;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageItemController {

    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQty;
    public TextField txtPrice;
    public TableView<Item> tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public JFXButton btnClear;
    public JFXButton btnDelete;
    ObservableList<Item> items ;

    private ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        btnUpdate.setVisible(false);
        btnAdd.setVisible(true);
        btnDelete.setDisable(true);

        try {
            loadItemDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedItem) -> {
            if (selectedItem!=null) {
                setItemToForm(selectedItem);
            }
        });

    }

    private void setItemToForm(Item item) {
        txtCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtQty.setText(String.valueOf(item.getQtyOnHand()));
        txtPrice.setText(String.valueOf(item.getUnitPrice()));

        btnUpdate.setVisible(true);
        btnAdd.setVisible(false);
        btnDelete.setDisable(false);
        txtCode.setDisable(true);
    }

    private void loadItemDetails() throws SQLException {
        ArrayList<ItemDTO> allItems = itemBO.getAllItems();
        items=FXCollections.observableArrayList();
        items.clear();
        for (ItemDTO temp : allItems) {
            items.add(new Item(temp.getCode(),temp.getDescription(),temp.getPackSize(),temp.getQtyOnHand(),temp.getUnitPrice()));
        }
        tblItems.setItems(items);
    }

    public void searchItemOnAction(ActionEvent actionEvent) throws SQLException {
        ItemDTO i = itemBO.searchItem(txtCode.getText());
        txtDescription.setText(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtQty.setText(Integer.toString(i.getQtyOnHand()));
        txtPrice.setText(Double.toString(i.getUnitPrice()));
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException {
        if (!validateIem()) {
            return;
        }

        ItemDTO i = new ItemDTO(
                txtCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtPrice.getText())
        );

        if (itemBO.ifExistsItem(i.getCode())) {
            new Alert(Alert.AlertType.ERROR,"Item "+i.getCode()+" Already Exists!").show();
            return;
        }

        if(itemBO.saveItem(i)) {
            loadItemDetails();
            new Alert(Alert.AlertType.CONFIRMATION, "Item Saved..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException {
        if (!validateIem()) {
            return;
        }
        ItemDTO i = new ItemDTO(
                txtCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtPrice.getText())
        );

        if(itemBO.updateItem(i)){
            clearAll();
            loadItemDetails();
            new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private boolean validateIem() {
        Pattern codePattern = Pattern.compile("^(I-)[0-9]{3,4}$");
        Pattern descPattern = Pattern.compile("^[A-z 0-9()-]{3,}$");
        Pattern packSize = Pattern.compile("^[A-z1-9]?[A-z0-9]{1,10}$");
        Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1})?$");

        if(!codePattern.matcher(txtCode.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Item Code").show();
            return false;
        }else if(!descPattern.matcher(txtDescription.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data for description").show();
            return false;
        }else if(!packSize.matcher(txtPackSize.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data for Pack Size").show();
            return false;
        }else if(!qtyPattern.matcher(txtQty.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data for Qty").show();
            return false;
        }else if(!pricePattern.matcher(txtPrice.getText()).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid data for Price").show();
            return false;
        }

        return true;
     }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Are you sure whether you want to delete this Item?", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            if (itemBO.deleteItem(txtCode.getText())){
                clearAll();
                loadItemDetails();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }

    }

    public void clearFormOnAction(ActionEvent actionEvent) {
        clearAll();
    }

    private void clearAll() {
        txtCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtQty.clear();
        txtPrice.clear();
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setDisable(true);
        txtCode.setDisable(false);
    }
}
