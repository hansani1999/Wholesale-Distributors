package controller;

import bo.BOFactory;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.PurchaseOrderBOImpl;
import com.jfoenix.controls.JFXButton;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import views.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;



public class MakeOrderFormController {
    public  ComboBox<String > cmbCustomerId;
    public TextField txtAddress;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public ComboBox<String> cmbItemCode;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public Label digitalClock;
    public Label dateLabel;

    public  static ObservableList<CartTM> itemDetailsTM = FXCollections.observableArrayList();
    //public static ObservableList<String> customerIdList=FXCollections.observableArrayList();

    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableView<CartTM> tblCart;
    public Label lblTotal;
    public Label lblDiscount;
    public Label lblNetPayment;
    public Label lblOrderId;
    public JFXButton btnConfirmOrder;
    public TextField txtCash;
    public Label lblBalance;
    public Label lblUserName;
    ObservableList<BillDTO> billItemList = FXCollections.observableArrayList();


    private PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    int cartSelectedRowForRemove;

    public void initialize() throws SQLException, ClassNotFoundException {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        btnConfirmOrder.setDisable(true);//new
        initDigitalClock();
        setOrderId();
        lblUserName.setText(MainFormController.userName);

        //loadCustomerIds();
        ArrayList<CustomerDTO> allCustomers = purchaseOrderBO.getAllCustomers();
        for (CustomerDTO customer : allCustomers) {
            cmbCustomerId.getItems().add(customer.getCustomerId());
        }

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null) {
                try {
                    setCustomerData(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });

        ArrayList<ItemDTO> allItems = purchaseOrderBO.getAllItems();
        for (ItemDTO item : allItems) {
            cmbItemCode.getItems().addAll(item.getCode());
        }

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null) {
                try {
                    setItemData(newValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int)newValue;
        });

    }

    private void setItemData(String itemCode) throws SQLException {
        ItemDTO i1 = purchaseOrderBO.searchItem(itemCode);
        if(i1!=null){
            txtDescription.setText(i1.getDescription());
            txtPackSize.setText(i1.getPackSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
        }else{
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }
    }

    private void setCustomerData(String customerId) throws SQLException {
        CustomerDTO c1 = purchaseOrderBO.searchCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTitle.setText(c1.getTitle());
            txtName.setText(c1.getCustomerName());
            txtAddress.setText(c1.getCustomerAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }


    private void initDigitalClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(" h:mm:ss a");
            DateTimeFormatter formatter2 =DateTimeFormatter.ofPattern("yyyy-MM-dd");
            digitalClock.setText(LocalDateTime.now().format(formatter1));
            dateLabel.setText(LocalDateTime.now().format(formatter2));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void addNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/AddCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if(cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please select a row for remove!");
        }else{
            itemDetailsTM.remove(cartSelectedRowForRemove);
            calculatePayment();
        }

        if(itemDetailsTM.size()==0){
            btnConfirmOrder.setDisable(true);
        }
    }

    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");

    public void addToCartOnAction(ActionEvent actionEvent) {
        if (!qtyPattern.matcher(txtQty.getText()).matches() || txtQty.getText().isEmpty()  ||Integer.parseInt(txtQtyOnHand.getText()) < Integer.parseInt(txtQty.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid QTY").show();
            return;
        }

        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyForCustomer=Integer.parseInt(txtQty.getText());
        double total = qtyForCustomer * unitPrice;



        System.out.println("Outside if");
        CartTM tm = new CartTM(
                cmbItemCode.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                total
        );

        int rowNumber = isExists(tm);
        if(rowNumber==-1){
            itemDetailsTM.add(tm);
        }else{
            CartTM temp = itemDetailsTM.get(rowNumber);
            CartTM newTM = new CartTM(
                    temp.getItemCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyForCustomer,
                    temp.getUnitPrice(),
                    temp.getTotal()+total
                    //,temp.getDiscount()+discount
            );
            itemDetailsTM.remove(rowNumber);
            itemDetailsTM.add(newTM);
        }
        tblCart.setItems(itemDetailsTM);
        calculatePayment();
        btnConfirmOrder.setDisable(false);
    }

    private void calculatePayment() {
        double total = 0;
        //double totalDiscount=0;
        for (CartTM tm : itemDetailsTM
        ) {
            total += tm.getTotal();
            //totalDiscount+=tm.getDiscount();
        }
        lblTotal.setText(Double.toString(total));

        double getDiscount = assessDiscount(total);
        lblDiscount.setText(Double.toString(getDiscount/*totalDiscount*/));

        double netPayment=total-getDiscount/*totalDiscount*/;
        lblNetPayment.setText(Double.toString(netPayment));

    }

    private double assessDiscount(double total) {
        double discount=0;
        if(total>= 2000){
            discount+=total*0.02;;
        }else if(total>=5000){
            discount+=total*0.05;
        }else if(total>=15000){
            discount+=total*0.15;
        }else{
            discount=0;
        }
        return discount;
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i <itemDetailsTM.size() ; i++) {
            if(tm.getItemCode().equals(itemDetailsTM.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }


    public void cancelOnAction(ActionEvent actionEvent) {
        itemDetailsTM.clear();
        lblTotal.setText("00.00");
        lblDiscount.setText("00.00");
        lblNetPayment.setText("00.00");
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        ArrayList<ItemDetails> items = new ArrayList<>();
        double totalPayment=0;
        for (CartTM tm : itemDetailsTM
             ) {
            totalPayment+= Double.parseDouble(lblNetPayment.getText());
            items.add(new ItemDetails(tm.getItemCode(),tm.getQty(),tm.getUnitPrice()));
        }

        OrderDTO dto = new OrderDTO(
                lblOrderId.getText(),
                /*cmbCustomerId.getValue()*/
                dateLabel.getText(),
                digitalClock.getText(),
                Double.parseDouble(lblNetPayment.getText()),
                Double.parseDouble(lblDiscount.getText()),
                items
        );

        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1})?$");
        if (!pricePattern.matcher(txtCash.getText()).matches() || txtCash.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Invalid Cash Amount! Please check the entered cash amount").show();
            return;
        }else {
            if (purchaseOrderBO.placeOrder(dto,cmbCustomerId.getValue())) {
                new Alert(Alert.AlertType.CONFIRMATION,"Order Saved Successfully..").show();
                viewBill();
                //clearAll();
                setOrderId();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Saving Failed! Try again...").show();
            }
        }

    }

    private void clearAll() {
        cmbCustomerId.setValue("Select ID");
        txtName.clear();
        txtTitle.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();

        cmbItemCode.setValue("Select Item code");
        txtDescription.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtQty.clear();

        itemDetailsTM.clear();
        lblTotal.setText("0.00");
        lblDiscount.setText("0.00");
        lblNetPayment.setText("0.00");
        lblBalance.setText("0.00");
        txtCash.clear();

    }

    private void viewBill() throws JRException {
        for (CartTM cartTM : itemDetailsTM) {
            billItemList.add(new BillDTO(
                    cartTM.getItemCode(),
                    cartTM.getDescription(),
                    cartTM.getQty(),
                    cartTM.getUnitPrice(),
                    cartTM.getTotal()
            ));
        }

        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/WholesaleDistributorsBill.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);

        String cashierId=lblUserName.getText();
        String customerId=cmbCustomerId.getValue();
        String orderId = lblOrderId.getText();
        double netAmount = Double.parseDouble(lblNetPayment.getText());
        double cash = Double.parseDouble(txtCash.getText());
        double balance = Double.parseDouble(lblBalance.getText());
        double totDiscount = Double.parseDouble(lblDiscount.getText());

        HashMap map = new HashMap();
        map.put("cashierName",cashierId);
        map.put("orderId",orderId);
        map.put("customerId",customerId);
        map.put("netAmount",netAmount);
        map.put("cash",cash);
        map.put("balance",balance);
        map.put("discount",totDiscount);

        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,map,new JRBeanArrayDataSource(billItemList.toArray()));
        JasperViewer.viewReport(jasperPrint,false);

    }

    private void setOrderId() {
        try {
            lblOrderId.setText(purchaseOrderBO.generateOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void calculateBalanceOnAction(ActionEvent actionEvent) {
        double balance = Double.parseDouble(txtCash.getText())-Double.parseDouble(lblNetPayment.getText());
        lblBalance.setText(String.valueOf(balance));
    }
}
