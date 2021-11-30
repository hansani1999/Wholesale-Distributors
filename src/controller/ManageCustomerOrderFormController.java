package controller;

import bo.BOFactory;
import bo.custom.ManageOrderBO;
import bo.custom.impl.ManageOrderBOImpl;
import dao.custom.*;
import dao.custom.impl.*;
import db.DbConnection;
import dto.*;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import views.tm.OrderItemTm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageCustomerOrderFormController {
    public TextField txtSearchBar;

    public ListView<String> orderIdNoList;
    public TableView<ItemDTO> tblItemDetails;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    //public Label lblOrderId;
    public Label lblOrder;
    public Label lblOrderID;


    ArrayList<CustomDTO> list = new ArrayList<>();
    public Label lblTotal;
    public Label lblDiscount;
    public Label lblNetPayment;

    public TableView<OrderItemTm> tblListOfItems;
    public TableColumn colDelete;
    public TableColumn colItemCode;
    public TableColumn colDescL;
    public TableColumn<OrderItemTm, Integer> colOrderQty;
    public TableColumn colUnitPriceL;
    ObservableList<OrderItemTm> orderItemDetailList = FXCollections.observableArrayList();
    int cartSelectedRowForEdit;

    private ManageOrderBO manageOrderBO = (ManageOrderBO)BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MANAGE_ORDER);

    public void initialize() throws SQLException {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescL.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colUnitPriceL.setCellValueFactory(new PropertyValueFactory<>("UPrice"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        tblListOfItems.setEditable(true);
        colOrderQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tblListOfItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForEdit = (int) newValue;
            System.out.println(cartSelectedRowForEdit);
        });
        /*new IntegerStringConverter()*/
        loadItemsToUI();


        orderIdNoList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue + " Selected");
            lblOrder.setText("Order Id -");
            lblOrderID.setText(newValue);

            try {
                viewOrderDetails(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void viewOrderDetails(String oId) throws SQLException {
        list = manageOrderBO.getOrderItemDetails(oId);
        System.out.println(list);
        loadOrderDetailsToTable(oId);

    }

    public void loadOrderDetailsToTable(String oId) throws SQLException {
        orderItemDetailList = FXCollections.observableArrayList();

        double total = 0;

        for (CustomDTO temp : list
        ) {
            total += temp.getUPrice() * temp.getOrderQty();
            // Button editButton = new Button("Edit    ");
            Button deleteButton = new Button("Delete");

            deleteButton.setOnAction(event -> {
                double oldTotal = 0;
                double discount = 0;
                double newTotal = 0;
                double netPayment = 0;
                int qty = 0;

                try {
                    OrderDTO order = manageOrderBO.searchOrder(oId);
                    oldTotal = order.getTotal() + order.getDiscount();
                    newTotal += oldTotal - (temp.getUPrice() * temp.getOrderQty());
                    discount = assessDiscount(newTotal);
                    netPayment = newTotal - discount;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                System.out.println("delete button");
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you sure whether you want to delete this Item from Order?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no) == yes) {
                    OrderDTO orderDTO = null;
                    try {
                        orderDTO = manageOrderBO.searchOrder(oId);
                        orderDTO.setTotal(netPayment);
                        orderDTO.setDiscount(discount);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    //OrderDTO order = new OrderDTO(oId,netPayment,discount);

                    if (manageOrderBO.updateOrderForDelete(temp, orderDTO)) {
                        lblTotal.setText(Double.toString(newTotal));
                        lblDiscount.setText(Double.toString(discount));
                        lblNetPayment.setText(Double.toString(netPayment));
                        //refresh table
                        list.remove(temp);
                        orderItemDetailList.remove(temp);
                        try {
                            loadOrderDetailsToTable(oId);
                            refreshItemListTable();
                            new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully...");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        if (list.size() == 0) {
                            try {
                                manageOrderBO.deleteOrder(oId);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                        System.out.println(list.size());
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Order item not deleted!").show();
                    }

                } else {
                    //new Alert(Alert.AlertType.ERROR,"Order item not deleted!").show();
                }
            });

            orderItemDetailList.add(new OrderItemTm(temp.getItemCode(), temp.getDescription(), temp.getOrderQty(), temp.getUPrice(), deleteButton));
        }

        tblListOfItems.setItems(orderItemDetailList);
        getPaymentInfo(oId, total);
        System.out.println(total);

    }

    private void refreshItemListTable() throws SQLException {
        itemList.clear();
        loadItemsToUI();
    }

    private void getPaymentInfo(String oId, double total) throws SQLException {
        lblTotal.setText(Double.toString(total));
        double discount;
        double netPayment;
        if (oId!=null) {
            OrderDTO order = manageOrderBO.searchOrder(oId);

            lblDiscount.setText(String.valueOf(order.getDiscount()));
            discount = order.getDiscount();
            netPayment = total - discount;
            lblNetPayment.setText(Double.toString(netPayment));
        }

    }

    ObservableList<ItemDTO> itemList = FXCollections.observableArrayList();

    private void loadItemsToUI() throws SQLException {
        itemList = FXCollections.observableArrayList();
        ArrayList<ItemDTO> items = manageOrderBO.getAllItems();
        for (ItemDTO item : items) {
            itemList.add(item);
        }
        tblItemDetails.setItems(itemList);
    }

    public void searchOrdersOnAction(ActionEvent actionEvent) {
        try {
            ObservableList<String> orderIdList = FXCollections.observableArrayList();
            ArrayList<OrderDTO> customerOrders = manageOrderBO.getCustomerOrder(txtSearchBar.getText());
            for (OrderDTO order : customerOrders) {
                orderIdList.add(order.getOrderId());
                orderIdNoList.setItems(orderIdList);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void EditOrderQty(TableColumn.CellEditEvent<OrderItemTm, Integer> cellEditEvent) throws SQLException {
        OrderItemTm item = tblListOfItems.getSelectionModel().getSelectedItem();
        int qty = cellEditEvent.getNewValue();
        item.setOrderQty(cellEditEvent.getNewValue());
        System.out.println("@EditOrderQty " + item.getDescription() + " :" + cellEditEvent.getNewValue());
        double unitPriceOfItem = item.getUPrice();
        editPaymentInfo(item.getItemCode(), qty, unitPriceOfItem);
    }

    private void editPaymentInfo(String itemCode, Integer newQty, double unitPriceOfItem) throws SQLException {
        String oId = lblOrderID.getText();
        int oldQty = getQty(itemCode, oId);
        double oldTotal = 0;
        double newTotal = 0;

        OrderDTO order = manageOrderBO.searchOrder(oId);
        System.out.println("@EditPaymentInfo -Order : " + order);
        oldTotal = order.getTotal() + order.getDiscount();
        System.out.println("Old total : " + oldTotal);
        /*if (order!=null) {
        }*/


        double oldItemTotal = unitPriceOfItem * oldQty;
        double newItemTotal = unitPriceOfItem * newQty;

        newTotal = (oldTotal - oldItemTotal) + newItemTotal;

        // double netPayment;
        double discount = assessDiscount(newTotal);
        double netPayment = newTotal - discount;

        lblTotal.setText(Double.toString(newTotal));
        lblDiscount.setText(Double.toString(discount));
        lblNetPayment.setText(Double.toString(netPayment));
    }

    private int getQty(String itemCode, String oId) throws SQLException {
        System.out.println("itemCode : " + itemCode);
        System.out.println("orderId : " + oId);
        OrderDetailDTO orderDetail = manageOrderBO.searchOrderDetail(oId, itemCode);
        int qty = 0;
        System.out.println("Old qty : " + qty);
        qty = orderDetail.getOrderQty();
        /*if (orderDetail!=null) {
        }*/
        System.out.println("Old qty : " + qty);
        return qty;
    }


    private double assessDiscount(double total) {
        double discount = 0;
        if (total >= 2000) {
            discount += total * 0.02;
            ;
        } else if (total >= 5000) {
            discount += total * 0.05;
        } else if (total >= 15000) {
            discount += total * 0.15;
        } else {
            discount = 0;
        }
        return discount;
    }

    public void confirmOrderEdit(ActionEvent actionEvent) throws SQLException {
        String oId = lblOrderID.getText();

        OrderItemTm temp = orderItemDetailList.get(cartSelectedRowForEdit);
        System.out.println(temp);

        String itemCode = temp.getItemCode();
        System.out.println("@confirmOrderEdit : " + itemCode);

        double netPayment = Double.parseDouble(lblNetPayment.getText());
        double discount = Double.parseDouble(lblDiscount.getText());

        int newQty = temp.getOrderQty();
        System.out.println("new qty : " + newQty);

        int oldQty = getQty(itemCode, oId);
        System.out.println("old qty : " + oldQty);

        ItemDTO item = manageOrderBO.searchItem(itemCode);
        int oldQtyOnHand = 0;
        if (item != null) {
            oldQtyOnHand = item.getQtyOnHand();
        }

        int newQtyOnHand = 0;
        if (oldQty > newQty) {
            System.out.println("inside if");
            newQtyOnHand = oldQtyOnHand + (oldQty - newQty);
            System.out.println("new qtyOnHand : " + newQtyOnHand);
        } else if (oldQty < newQty) {
            System.out.println("inside else");
            newQtyOnHand = oldQtyOnHand - (newQty - oldQty);
            System.out.println("new qtyOnHand : " + newQtyOnHand);
        }

        Order order = new Order(oId, netPayment, discount);
        if (manageOrderBO.updateOrderForEdit(temp, order, newQtyOnHand)) {
            refreshItemListTable();
            new Alert(Alert.AlertType.INFORMATION, "Order updated successfully...").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Try again").show();
        }

    }


    public void cancelOrderOnAction(ActionEvent actionEvent) throws SQLException {
        String orderId = lblOrderID.getText();
        ArrayList<OrderDetailDTO> orderItemDetailList = manageOrderBO.getOrderDetails(orderId);
        if (updateOrderForCancel(orderItemDetailList)) {
            refreshItemListTable();
            orderItemDetailList.clear();
            new Alert(Alert.AlertType.INFORMATION, "Order Deleted successfully...").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order Delete Failed").show();
        }
    }

    private boolean updateOrderForCancel(ArrayList<OrderDetailDTO> orderItemDetailList) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                , "Are you sure whether you want to delete this Order?", yes, no);
        alert.setTitle("Confirmation Alert");
        Optional<ButtonType> result = alert.showAndWait();

        OrderDAO orderDAO = new OrderDAOImpl();
        Connection con = DbConnection.getInstance().getConnection();

        if (result.orElse(no) == yes) {
            if (manageOrderBO.cancelOrder(orderItemDetailList)) {
                return true;
            }
        }
        return false;
    }

}
