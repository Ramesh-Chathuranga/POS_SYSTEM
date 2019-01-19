package lk.pos.fx.shop.business;

import lk.pos.fx.shop.business.Custom.impl.*;

public class BOFactory {
    public enum BOType{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,USER
    }
    private static BOFactory boFactory;
    public static BOFactory getInstance(){
        if(boFactory==null){boFactory=new BOFactory();}
        return boFactory;
    }

    public SuperBO getBo(BOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDERDETAIL:
                return new OrderDetailBOImpl();
            case USER:
                return new UserBOImpl();
            default: return null;
        }
    }
}
