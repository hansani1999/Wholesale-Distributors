package bo;

import bo.custom.CustomerBO;
import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        if (boFactory==null) {
            boFactory=new BOFactory();
        }
        return boFactory;
    }

    public static SuperBO getBO(BOTypes BOType){
        switch (BOType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            case MANAGE_ORDER:
                return new ManageOrderBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes{
        CUSTOMER, ITEM, PURCHASE_ORDER, MANAGE_ORDER,DASHBOARD
    }
}
