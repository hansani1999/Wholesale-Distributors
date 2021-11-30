package controller;

import bo.BOFactory;
import bo.custom.DashboardBO;
import dto.CustomDTO;
import dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane pieChartPane;
    public Label lblTodayIncome;
    public Label lblCustomerCount;
    public Label lblTotalIncome;
    ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();

    DashboardBO dashboardBO = (DashboardBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.DASHBOARD);
    public void initialize() throws SQLException {
        //System.out.println(dashboardBO);
        for (CustomDTO customDTO : dashboardBO.getItemMobility()) {
            pieList.add(new PieChart.Data(customDTO.getItemCode(),customDTO.getItemExpenditure()));
        }
        PieChart pieChart = new PieChart(pieList);
        pieChartPane.getChildren().add(pieChart);

        setCustomerCount();

        setTodayIncome();

        setTotalIncome();
    }

    private void setTotalIncome() throws SQLException {
        double totIncome = dashboardBO.getTotalIncome();
        lblTotalIncome.setText("Rs."+totIncome);
    }

    private void setTodayIncome() throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();

        double todayIncome = dashboardBO.getTodayIncome(formatter.format(date));
        lblTodayIncome.setText("Rs."+todayIncome);
    }

    private void setCustomerCount() throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();
        int noOfCustomers = dashboardBO.getCustomerCount(formatter.format(date));

        lblCustomerCount.setText(String.valueOf(noOfCustomers));
    }
}
