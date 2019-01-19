package lk.pos.fx.shop.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.CustomerBO;
import lk.pos.fx.shop.business.Custom.ItemBO;
import lk.pos.fx.shop.business.Custom.OrderBO;
import lk.pos.fx.shop.model.CustomerDTO;
import lk.pos.fx.shop.model.ItemDTO;
import lk.pos.fx.shop.model.OrderDetailDTO;
import lk.pos.fx.shop.model.OrdersDTO;
import lk.pos.fx.shop.view.model.PlaceOrdersTB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.repo.FileRepositoryPersistenceServiceFactory;
import net.sf.jasperreports.repo.FileRepositoryService;
import net.sf.jasperreports.repo.PersistenceServiceFactory;
import net.sf.jasperreports.repo.RepositoryService;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController implements  Initializable{

    @FXML
    private AnchorPane anchorPaneOrderForm;

    @FXML
    private TableView<PlaceOrdersTB> tableOrders;

    @FXML
    private Label labelTotal;

    @FXML
    private JFXDatePicker txtCalander;

    @FXML
    private AnchorPane buttonHome;

    @FXML
    private ImageView home1;

    @FXML
    private Label labHome;

    @FXML
    private AnchorPane buttonBuy;

    @FXML
    private Label labBuy;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXComboBox<String> comboId;

    @FXML
    private JFXComboBox<String> comboCode;

    @FXML
    private AnchorPane buttonAdd;

    @FXML
    private Label labAdd;

    @FXML
    private AnchorPane buttonRemove;

    @FXML
    private Label labRemove;

    @FXML
    private AnchorPane butNew;

    @FXML
    private Label labNew;

    @FXML
    private Label labOrderID;

    @FXML
    private AnchorPane butReport;

    @FXML
    private Label labReport;

    private ArrayList<ItemDTO> tempItemsDB ;

    private OrderBO bo=(OrderBO) BOFactory.getInstance().getBo(BOFactory.BOType.ORDER);

    private ItemBO ibo=(ItemBO) BOFactory.getInstance().getBo(BOFactory.BOType.ITEM);

    private CustomerBO cbo=(CustomerBO) BOFactory.getInstance().getBo(BOFactory.BOType.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateOrderId();
        buttonRemove.setDisable(true);
        buttonBuy.setDisable(true);
        buttonAdd.setDisable(true);
        labRemove.setVisible(false);
        labAdd.setVisible(false);
        labHome.setVisible(false);
        labBuy.setVisible(false);
        labNew.setVisible(false);

        txtName.setEditable(false);
        txtDescription.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);



        tableOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tableOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tableOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tableOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tableOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        loadComboBox();
        selectCustomer();
        selectItem();
        selectTBRaw();
        try {
            tempItemsDB = ibo.getAllItem();
        }catch (SQLException|ClassNotFoundException e){
            alertMaker(e.toString()+"\n InitialiZer","     Exception Error",ButtonType.CLOSE);
        }
        loadtable();
    }

    private  void loadtable(){
        tableOrders.getItems().addListener(new ListChangeListener<PlaceOrdersTB>() {
            @Override
            public void onChanged(Change<? extends PlaceOrdersTB> c) {
               calculateTotal();
               if(tableOrders.getItems().size()==0){
                   buttonBuy.setDisable(true);
               }else{
                   buttonBuy.setDisable(false);
               }
            }
        });
    }

    private  void loadComboBox(){
       try{
           comboCode.getItems().removeAll(this.comboCode.getItems());
           comboId.getItems().removeAll(this.comboId.getItems());
           ArrayList<CustomerDTO>customerDTOS= cbo.getAllCustomer();
           ArrayList<ItemDTO>itemDTOSDTOS= ibo.getAllItem();
           for (CustomerDTO customerDTO : customerDTOS) {
               this.comboId.getItems().add(customerDTO.getId());
           }

           for (ItemDTO itemDTO : itemDTOSDTOS) {
               this.comboCode.getItems().add(itemDTO.getCode());
           }
       }catch (SQLException|ClassNotFoundException e){
           alertMaker(e.toString()+"\n loadComboBox Method","     Exception Error  ",ButtonType.CLOSE);
       }
    }

    private void selectCustomer(){
        this.comboId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                try {
//                    String cId=newValue;
//                    if(newValue.isEmpty()){
//                        return;
//                    }
//                    CustomerDTO dto = cbo.searchCustomer(cId);
//                    String name = dto.getName();
//                    comboId.getSelectionModel().select(cId);
//                    txtName.setText(name);
//
//
//                        txtCalander.requestFocus();
//
//                }catch (NullPointerException|ClassNotFoundException|SQLException e){
//                    alertMaker(e.getMessage().toString()+" SelectCustomer Method","Exception Error",ButtonType.CLOSE);
//                }
            }
        });
    }

    private void selectItem(){
        this.comboCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

//               try{
//                 String code=newValue;
//                   //String code=observable.getValue();
//                   ItemDTO dto = ibo.searchItem(code);
//                   txtDescription.setText(dto.getDescription());
//                   txtUnitPrice.setText(Double.toString(dto.getUnitPrice()));
//                   txtQtyOnHand.setText(Integer.toString(dto.getQtyOnHand()));
//                   buttonAdd.setDisable(false);
//                   buttonRemove.setDisable(true);
//
//                       txtQty.requestFocus();
//
//                 //  tableOrders.getSelectionModel().clearSelection();
//               }catch (NullPointerException|ClassNotFoundException|SQLException e){
//                  alertMaker(e.getMessage().toString()+"  SelectItem Method","Exception Error",ButtonType.CLOSE);
//               }
            }
        });
    }

    @FXML
    void selectCustomer(ActionEvent event) {
        try {

            CustomerDTO dto = cbo.searchCustomer(comboId.getValue());
            String name = dto.getName();
            comboId.getSelectionModel().select(comboId.getValue());
            txtName.setText(name);


            txtCalander.requestFocus();

        }catch (NullPointerException|ClassNotFoundException|SQLException e){
          //  alertMaker(e.toString()+" SelectCustomer Method","Exception Error",ButtonType.CLOSE);
        }
    }

    @FXML
    void selectItem(ActionEvent event) {

                  try{
                      ItemDTO dto = ibo.searchItem(comboCode.getValue());
                      txtDescription.setText(dto.getDescription());
                      txtUnitPrice.setText(Double.toString(dto.getUnitPrice()));
                      txtQtyOnHand.setText(Integer.toString(dto.getQtyOnHand()));
                      buttonAdd.setDisable(false);
                      buttonRemove.setDisable(true);
                  }catch (NullPointerException|ClassNotFoundException|SQLException e){
                  //  alertMaker(e.toString()+"  SelectItem Method","Exception Error",ButtonType.CLOSE);
                  }

    }
     private  void selectTBRaw(){
       tableOrders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlaceOrdersTB>() {
           @Override
           public void changed(ObservableValue<? extends PlaceOrdersTB> observable, PlaceOrdersTB oldValue, PlaceOrdersTB newValue) {
              try {
                  if(newValue==null){
                      return;
                  }
                  comboCode.getSelectionModel().select(observable.getValue().getCode());
                  txtDescription.setText(newValue.getDescription());
                  txtQty.setText(Integer.toString(newValue.getQty()));
                  txtUnitPrice.setText(Double.toString(newValue.getUnitPrice()));
                  int qtyOnHand=ibo.searchItem(newValue.getCode().trim()).getQtyOnHand()+newValue.getQty();
                  txtQtyOnHand.setText(Integer.toString(qtyOnHand));
                  buttonRemove.setDisable(false);
              }catch (SQLException|ClassNotFoundException e){
                  alertMaker(e.getMessage().toString()+" selectTBRaw","        Exception ERROR",ButtonType.CLOSE);
              }
           }
       });
     }



    @FXML
    void createNewField(MouseEvent event) {
     resetForm();
     generateOrderId();

        txtCalander.setEditable(true);
    }

    @FXML
    void mouseEnteredAnimation(MouseEvent event) {

        AnchorPane anchorPane=(AnchorPane)event.getSource();
        String id=anchorPane.getId();
        ScaleTransition scaleTransition=new ScaleTransition(Duration.millis(1000),anchorPane);
        scaleTransition.setToY(1.2);
        scaleTransition.setToX(1.2);
        scaleTransition.play();
        DropShadow dropShadow=new DropShadow();
        dropShadow.setColor(Color.CORNFLOWERBLUE);
        dropShadow.setWidth(30);
        dropShadow.setHeight(30);
        dropShadow.setRadius(30);
        anchorPane.setEffect(dropShadow);

        try{
           switch (id){
               case "butNew":labNew.setVisible(true);
                   break;
               case "buttonRemove":labRemove.setVisible(true);
                   break;
               case "buttonAdd":labAdd.setVisible(true);
                   break;
               case "buttonBuy": labBuy.setVisible(true);
                   break;
               case "buttonHome":labHome.setVisible(true);
                   break;
               default:
                   return;
           }
       }catch (NullPointerException e){}
    }

    @FXML
    void mouseExitedAnimation(MouseEvent event) {
        AnchorPane anchorPane=(AnchorPane)event.getSource();
        String id = anchorPane.getId();
        ScaleTransition scaleTransition=new ScaleTransition(Duration.millis(1000),anchorPane);
        scaleTransition.setToY(1.0);
        scaleTransition.setToX(1.0);
        scaleTransition.play();
        anchorPane.setEffect(null);
       try{

           switch (id){
               case "butNew":labNew.setVisible(false);
                   break;
               case "buttonRemove":labRemove.setVisible(false);
                   break;
               case "buttonAdd":labAdd.setVisible(false);
                   break;
               case "buttonBuy": labBuy.setVisible(false);
                   break;
               case "buttonHome":labHome.setVisible(false);
                   break;
               default:
                   return;
           }
       }catch (NullPointerException e){

       }
    }

    @FXML
    void setTextFielClear(MouseEvent event) {
      resetField();
      tableOrders.getSelectionModel().clearSelection();
    }

    @FXML
    void navigator(MouseEvent event) throws IOException {
        Parent root= FXMLLoader.load(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/DashBoardForm.fxml"));
        Scene scene=new Scene(root);
        Stage stage=(Stage)anchorPaneOrderForm.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
       // test();
    }

    @FXML
    void placeOrder(MouseEvent event) {

       try{
           System.out.println(comboId.getValue());
            String customerId=comboId.getValue();
           LocalDate date=txtCalander.getValue();
           if(customerId.isEmpty()){
               alertMaker("Please Select user Id ","User id Not Set",ButtonType.CLOSE);
               comboId.requestFocus();
               return;
           }
           else if(date==null){
               alertMaker("Please Select Order Date before Place Order ","Date Not Selected",ButtonType.CLOSE);
               txtCalander.requestFocus();
               return;
           }
           ArrayList<OrderDetailDTO>list=new ArrayList<>();

           for (PlaceOrdersTB tb :tableOrders.getItems()) {

               list.add(new OrderDetailDTO(labOrderID.getText(),tb.getCode(),tb.getDescription(),tb.getQty(),tb.getUnitPrice()));

               ibo.ItemQtymanager(tb.getCode(),tb.getQty());

           }

           OrdersDTO dto=new OrdersDTO(labOrderID.getText(),date,customerId,list);
           boolean isOrderSave = bo.saveOrders(dto);

           if(isOrderSave){
               String message="Order Save Successfully";
               alertMaker(message,"    Save"  ,ButtonType.CLOSE);
               makeBill();
           }else{
               String message="Order Save UnSuccessfully";
               alertMaker(message,"    Not Save"  ,ButtonType.CLOSE);
           }
       }catch (NullPointerException|SQLException|ClassNotFoundException e){

           alertMaker(e.toString(),"   Exception Error"  ,ButtonType.CLOSE);
       } catch (JRException e) {
           e.printStackTrace();
       } finally {


           txtCalander.requestLayout();
           labelTotal.setText("");
           labOrderID.setText("");
           resetForm();
       }
    }

    public void test(){
      try{
          ArrayList<OrdersDTO> order = bo.getAllOrders();
          for (OrdersDTO dto:order) {
              System.out.println(dto);
          }
      }catch (ClassNotFoundException|SQLException e){
          alertMaker(e.getMessage().toString()," EXception Error",ButtonType.CLOSE);
      }
    }

     public void resetField(){
        try{
            if(comboCode.getValue()!=null){
                comboCode.getSelectionModel().clearSelection();
            }
            txtDescription.clear();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtQty.clear();
        }catch (Exception e){
            alertMaker(e.getMessage().toString(),"ResetField Exception error");
        }

     }

    public void resetForm(){
       try{
           if(comboId.getValue()!=null){
               comboId.getSelectionModel().clearSelection();
           }
           resetField();
           txtName.clear();
           tableOrders.getSelectionModel().clearSelection();
           buttonAdd.setDisable(false);
           comboId.requestFocus();
           buttonBuy.setDisable(true);
           buttonRemove.setDisable(true);
           tableOrders.getItems().removeAll();
           tableOrders.getItems().clear();
       }catch (NullPointerException e){
           alertMaker(e.getMessage().toString(),"ResetForm NullPointException error");
       }catch (Exception e){
           alertMaker(e.getMessage().toString(),"ResetForm Exception error");
       }

    }


    private boolean isInteger(String qty){
        char[] chars =qty.toCharArray();
        for (char aChar : chars) {
            if (!Character.isDigit(aChar)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void addUpdateToTable(MouseEvent event) {
     try {
         System.out.println("ok");
         String code=comboCode.getValue();
         String description=txtDescription.getText();
         double unitPrice=Double.parseDouble(txtUnitPrice.getText());
         int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
         int qty=Integer.parseInt(txtQty.getText());

         if(code.isEmpty()){
             alertMaker("this item is not selected ","       Error Report" ,ButtonType.FINISH);
             comboCode.requestFocus();
             return;
         }
         else if(txtQty.getText().trim().isEmpty()){
             alertMaker("qty is not added  ","       Error Report" ,ButtonType.FINISH);
             txtQty.requestFocus();
             return;
         } else if(!isInteger(txtQty.getText())){
             alertMaker("qty is not added  ","       Error Report" ,ButtonType.FINISH);
             txtQty.requestFocus();
             return;
         }else if(qty<0){
             alertMaker("add value to qty more than Zero ","       Error Report" ,ButtonType.FINISH);
             txtQty.requestFocus();
             return;
         } else if(qty>=qtyOnHand){
             alertMaker("qty is"+qty+ "more than Qty On Hand:"+qtyOnHand+" plase checke and add value To Qty","       Error Report" ,ButtonType.FINISH);
             txtQty.requestFocus();
             return;
         }

         if(tableOrders.getSelectionModel().isEmpty()){
             //save
             if(tableOrders.getItems().isEmpty()){
                 //table empty;
                 tableOrders.getItems().add(new PlaceOrdersTB(code,description,unitPrice,qty,unitPrice*(double)qty));
             }else {
                 //table not empty
                 boolean isHastable = notSelectedrawButHastable(code);
                 if(!isHastable){
                     tableOrders.getItems().add(new PlaceOrdersTB(code,description,unitPrice,qty,unitPrice*(double)qty));
                 }else {
                     String i=tableOrders.getSelectionModel().getSelectedItem().getCode();
                     System.out.println(i+"    i     ");
                     qty+=tableOrders.getSelectionModel().getSelectedItem().getQty();
                     double total=(double)qty*tableOrders.getSelectionModel().getSelectedItem().getUnitPrice();
                     tableOrders.getSelectionModel().getSelectedItem().setQty(qty);
                     tableOrders.getSelectionModel().getSelectedItem().setTotal(total);
                     System.out.println(i+"    i     qty :"+qty);

                 }
             }
         }else{
             //update
             if(tableOrders.getSelectionModel().getSelectedItem().getCode().equals(code)){
                 qtyOnHandOfTempDBUpdate(code);
                 PlaceOrdersTB item = tableOrders.getSelectionModel().getSelectedItem();
                 item.setQty(qty);
                 item.setTotal(qty*unitPrice);
             }else{
                 tableOrders.getItems().add(new PlaceOrdersTB(code,description,unitPrice,qty,unitPrice*(double)qty));
             }

         }

         setTempQty(code,qty);
     }catch (NullPointerException |NumberFormatException e){
         alertMaker(e.toString()+"addUPdateMethod","  Excepition ");
     }catch (RuntimeException e){
         alertMaker(e.toString()+"addUPdateMethod","  RunTimeExcepition ");
     }

            tableOrders.refresh();
            resetField();
       // comboCode.getSelectionModel().clearSelection();
           calculateTotal();

    }

    private boolean notSelectedrawButHastable(String code){
        for (PlaceOrdersTB tb:tableOrders.getItems()) {
            if(tb.getCode().equals(code)){
              tableOrders.getSelectionModel().select(tb);
              return true;
            }
        }
        return false;
    }



    private void setTempQty(String code, int qty) {
        for (ItemDTO item : tempItemsDB) {
            if (item.getCode().equals(code)) {
                item.setQtyOnHand(item.getQtyOnHand() - qty);
                break;
            }
        }
    }


    private void qtyOnHandOfTempDBUpdate(String code){
       try{
           ItemDTO dto = ibo.searchItem(code);
           for (ItemDTO itemDTO:tempItemsDB) {
               if(itemDTO.getCode().equals(code)){
                   itemDTO.setQtyOnHand(dto.getQtyOnHand());
               }
           }
       }catch (SQLException|ClassNotFoundException e){
           alertMaker(e.getMessage().toString(),"Exception Error",ButtonType.CLOSE);
       }
    }

    private void calculateTotal(){
        double total=0.00;
        for (PlaceOrdersTB tb:tableOrders.getItems()) {
            total+=tb.getTotal();
        }
        labelTotal.setText(total+"");
    }

    @FXML
    void removeFromTable(MouseEvent event) {
        boolean isDeleteOK = alertMaker("Are You sure To Delete This Item From Your List", " COMFIRMATION MESSAGE ", ButtonType.OK, ButtonType.NO);
        if(isDeleteOK) {
            PlaceOrdersTB item = tableOrders.getSelectionModel().getSelectedItem();
            tableOrders.getItems().remove(item);
            qtyOnHandOfTempDBUpdate(item.getCode());
        }
    }


    private void generateOrderId() {
        String setOrderId = null;

        ArrayList<OrdersDTO> getAllOrders = null;
        int count = 0;
        try {
            getAllOrders = bo.getAllOrders();
            count = getAllOrders.size();
        } catch (SQLException | ClassNotFoundException e) {
            alertMaker(e.getMessage().toString(), "Order Id cannot be prepared", ButtonType.CLOSE);
        }

        if (getAllOrders == null || getAllOrders.isEmpty() || count <= 0) {
            OrdersDTO oDto = new OrdersDTO();
            oDto.setOrderId("D000");
            labOrderID.setText("D001");
            return;
        }
        OrdersDTO oDTO = getAllOrders.get(count - 1);
        String orderid = oDTO.getOrderId();
        String firstIndex = orderid.substring(0, 1);
        String otherIndexs = orderid.substring(1);
        int setOrdeIdInt = 0;
       int y=0;
       int x=0;
          int n=0;
        int calNumber = 0;
        System.out.println(orderid.length() + "" + otherIndexs.length());
        if (Integer.parseInt(orderid.substring(1, 2)) <9) {
            y=Integer.parseInt(orderid.substring(1, 2));
             if (Integer.parseInt(orderid.substring(2, 3)) < 9) {
                 //x
                x=Integer.parseInt(orderid.substring(2, 3));
                if(Integer.parseInt(orderid.substring(3, 4))<9){
                    //n
                     n=Integer.parseInt(orderid.substring(3, 4))+1;
                    setOrderId = firstIndex + y + x +n;
                }else{
                    //!n
                    n=0;
                    x=x+1;
                    setOrderId=firstIndex+y+x+n;
                }

            } else{
                 //!x
                 x=Integer.parseInt(orderid.substring(2, 3));

                 if(Integer.parseInt(orderid.substring(3, 4))<9){
                     //n
                     n=Integer.parseInt(orderid.substring(3, 4))+1;
                     setOrderId = firstIndex + y + x +n;
                 }else{
                     //!n
                     n=0;
                     n=0;
                     y=y+1;
                     n=Integer.parseInt(orderid.substring(3, 4));
                     x=x+1;
                     setOrderId=firstIndex+y+x+n;
                 }

            }
        } else {
            y=Integer.parseInt(orderid.substring(1, 2));

            if (Integer.parseInt(orderid.substring(2, 3)) < 9) {
                //x
                x=Integer.parseInt(orderid.substring(2, 3));
                if(Integer.parseInt(orderid.substring(3, 4))<9){
                    //n
                    n=Integer.parseInt(orderid.substring(3, 4))+1;
                    setOrderId = firstIndex + y + x +n;
                }else{
                    //!n
                    n=0;
                    x=x+1;
                    setOrderId=firstIndex+y+x+n;
                }

            } else{
                //!x
                x=Integer.parseInt(orderid.substring(2, 3));

                if(Integer.parseInt(orderid.substring(3, 4))<9){
                    //n
                    n=Integer.parseInt(orderid.substring(3, 4))+1;
                    setOrderId = firstIndex + y + x +n;
                }else{
                    //!n
                    n=0;
                    n=0;
                    y=y+1;
                    n=Integer.parseInt(orderid.substring(3, 4));
                    x=x+1;
                    setOrderId=firstIndex+y+x+n;
                }

            }
    }
   labOrderID.setText(setOrderId);
    }

    public boolean alertMaker(String message, String title, ButtonType... type){
        System.out.println(message);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION ,message, type);
        Image image=new Image("/lk/pos/fx/shop/asset/icons8_Apple_28px.png");
        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("SHOP"+"                                            "+title);
        stage.getIcons().add(image);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.OK){
            return true;
        }else  if(buttonType.get()==ButtonType.NO){
            return  false;
        }
        return true;
    }


    @FXML
    void createReport(MouseEvent event) throws JRException {
          makeBill();
    }

    private void makeBill() throws JRException {
        try{
            File file=new File("jasperReports");
            SimpleJasperReportsContext ctx=new SimpleJasperReportsContext();
            FileRepositoryService fileRepositoryService=new FileRepositoryService(ctx,file.getAbsolutePath(),false);
            ctx.setExtensions(RepositoryService.class, Collections.singletonList(fileRepositoryService));
            ctx.setExtensions(PersistenceServiceFactory.class,Collections.singletonList(FileRepositoryPersistenceServiceFactory.getInstance()));
            File jasperReport=new File("jasperReports/OrderBill2.jasper");
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(ctx,jasperReport);
            HashMap<String, Object> map = new HashMap<>();
            map.put("oid",labOrderID.getText().toString());
            map.put("cid",comboId.getValue().toString());
            map.put("date",txtCalander.getValue().toString());
            map.put("cname",txtName.getText().toString());
            map.put("nettot",labelTotal.getText().toString());

            DefaultTableModel dtm = new DefaultTableModel(new Object[]{"code","description","up","qty","total"},0);
            ObservableList<PlaceOrdersTB> tbs = tableOrders.getItems();
            for (PlaceOrdersTB tb :tbs) {
                Object[] rowData = {tb.getCode(), tb.getDescription(), tb.getUnitPrice()+"",tb.getQty()+"",tb.getTotal()+""};
                dtm.addRow(rowData);
            }

            JasperPrint filledReport= JasperFillManager.getInstance(ctx).fill(compiledReport,map, new JRTableModelDataSource(dtm));
            JasperViewer.viewReport(filledReport,false);
        }catch (NullPointerException e){

        }
    }
}
