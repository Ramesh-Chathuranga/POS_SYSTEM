package lk.pos.fx.shop.controller;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.ItemBO;
import lk.pos.fx.shop.db.Connection.DBconnection;
import lk.pos.fx.shop.model.ItemDTO;

import lk.pos.fx.shop.view.model.ItemTB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;

public class ItemFormController implements Initializable {

    @FXML
    private AnchorPane itemform;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private TableView<ItemTB> tabItem;

    @FXML
    private AnchorPane butSave;

    @FXML
    private Label labSave;

    @FXML
    private AnchorPane butDelete;

    @FXML
    private Label labDelete;

    @FXML
    private AnchorPane butNew;

    @FXML
    private Label labNew;

    @FXML
    private AnchorPane goHome;

    @FXML
    private Label labHome;


    @FXML
    private AnchorPane butReport;

    @FXML
    private Label labReport;

    ItemBO bo=(ItemBO) BOFactory.getInstance().getBo(BOFactory.BOType.ITEM);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labDelete.setVisible(false);
        labSave.setVisible(false);
        labNew.setVisible(false);
        labHome.setVisible(false);
        butDelete.setDisable(true);
        butSave.setDisable(true);

        tabItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tabItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tabItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tabItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        loadTable();

        tabItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTB>() {
            @Override
            public void changed(ObservableValue<? extends ItemTB> observable, ItemTB oldValue, ItemTB newValue) {
                if(newValue==null){return;}
                String code=newValue.getCode();
                String description=newValue.getDescription();
                String unitPrice=Double.toString(newValue.getUnitPrice());
                String qtyOnHand=Integer.toString(newValue.getQtyOnHand());
                txtCode.setText(code);
                txtDescription.setText(description);
                txtUnitPrice.setText(unitPrice);
                txtQtyOnHand.setText(qtyOnHand);
                txtCode.setEditable(false);
                butSave.setDisable(false);
                butDelete.setDisable(false);
            }
        });

    }

    public void loadTable(){
       try{
           ArrayList<ItemDTO> list= bo.getAllItem();
           ObservableList<ItemDTO> list1 = FXCollections.observableArrayList(list);
           ObservableList<ItemTB>objects =FXCollections.observableArrayList();
           for (ItemDTO dto : list1) {
               ItemTB tb = new ItemTB(dto.getCode(), dto.getDescription(), dto.getUnitPrice(),dto.getQtyOnHand());
               objects.add(tb);
           }
           tabItem.setItems(objects);
       }catch (ClassNotFoundException|SQLException e){
           alertMaker(e.toString(),"       Exception Error",ButtonType.CLOSE);
       }
    }

    public void resetForm(){
        txtCode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        tabItem.getSelectionModel().clearSelection();
        txtCode.setEditable(true);
        txtCode.requestFocus();
        butDelete.setDisable(true);
        butSave.setDisable(false);
        loadTable();
    }

    @FXML
    void createNewField(MouseEvent event) {
        resetForm();
    }

    @FXML
    void deleItem_On_Click(MouseEvent event) {

         String message="Are you sure to delete this Item";
        String  title="                             Confirm";

        boolean isConfirm = alertMaker(message, title, ButtonType.OK, ButtonType.NO);
      try{
          if(isConfirm) {
              String code = txtCode.getText();
              bo.deleteItem(code);
              resetForm();
          }
      }catch (SQLException|ClassNotFoundException e){
          alertMaker(e.toString(),"    Exception Error ",ButtonType.CLOSE);
      }

    }

    @FXML
    void mouse_Entered_Animation(MouseEvent event) {
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

        switch (id){
            case "butSave": this.labSave.setVisible(true);
                break;
            case "butDelete":this.labDelete.setVisible(true);
                break;
            case "butNew":this.labNew.setVisible(true);
                break;
            case "goHome":this.labHome.setVisible(true);
                break;
            default:break;
        }
    }

    @FXML
    void mouse_Exited_Animation(MouseEvent event) {
        AnchorPane anchorPane=(AnchorPane)event.getSource();
        String id = anchorPane.getId();
        ScaleTransition scaleTransition=new ScaleTransition(Duration.millis(1000),anchorPane);
        scaleTransition.setToY(1.0);
        scaleTransition.setToX(1.0);
        scaleTransition.play();
        anchorPane.setEffect(null);
        switch (id){
            case "butSave": this.labSave.setVisible(false);
                break;
            case "butDelete":this.labDelete.setVisible(false);
                break;
            case "butNew":this.labNew.setVisible(false);
                break;
            case "goHome":this.labHome.setVisible(false);
                break;
        }
    }

    @FXML
    void navigator(MouseEvent event) throws IOException {
        Parent root= FXMLLoader.load(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/DashBoardForm.fxml"));
        Scene scene=new Scene(root);
        Stage stage=(Stage)itemform.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveAndUpdateItem_On_Click(MouseEvent event) {
     try {
         String code = txtCode.getText().trim();
         String description = txtDescription.getText().trim();
         String unitPrice = txtUnitPrice.getText().trim();
         String qtyOnHand = txtQtyOnHand.getText().trim();
         if (code.isEmpty()) {
             alertMaker("Code is empty", "       Error_Message", ButtonType.OK);
             txtCode.clear();
             txtCode.requestFocus();
             return;
         } else if (description.isEmpty()) {
             alertMaker("description is empty", "       Error_Message", ButtonType.OK);
             txtDescription.clear();
             txtDescription.requestFocus();
             return;
         } else if (unitPrice.isEmpty()) {
             alertMaker("Unit Price is empty", "       Error_Message", ButtonType.OK);
             txtUnitPrice.clear();
             txtUnitPrice.requestFocus();
             return;
         } else if (qtyOnHand.isEmpty()) {
             alertMaker("Qty On Hands is empty", "       Error_Message", ButtonType.OK);
             txtQtyOnHand.clear();
             txtQtyOnHand.requestFocus();
             return;
         }

         if (tabItem.getSelectionModel().isEmpty()) {
             ObservableList<ItemTB> tbs = tabItem.getItems();

             for (ItemTB tb : tbs) {
                 if (tb.getCode().trim().toUpperCase().equals(code.toUpperCase())) {
                     alertMaker("Item Code cannot be duplicated If you want to Update Relavent Item Select from table first", "      ErrorMessage", ButtonType.FINISH);
                     txtCode.requestFocus();
                     return;
                 }
             }

             boolean isSave = bo.saveItem(new ItemDTO(code, description, Double.parseDouble(unitPrice), Integer.parseInt(qtyOnHand)));
             alertMaker("Item save successfully", "       Message", ButtonType.CLOSE);
             tabItem.getItems().add(new ItemTB(code, description, Double.parseDouble(unitPrice), Integer.parseInt(qtyOnHand)));
         } else {
             ItemTB tb = tabItem.getSelectionModel().getSelectedItem();
             tb.setDescription(description);
             tb.setUnitPrice(Double.parseDouble(unitPrice));
             tb.setQtyOnHand(Integer.parseInt(qtyOnHand));
             boolean isUpdate = bo.updateItem(new ItemDTO(code, description, Double.parseDouble(unitPrice), Integer.parseInt(qtyOnHand)));
             if (isUpdate) {
                 alertMaker("Update successfull", " Successfull", ButtonType.FINISH);
             } else {
                 alertMaker("Update Unsuccessfull", " UnSuccessfull", ButtonType.FINISH);
             }

         }
     }catch (SQLException|ClassNotFoundException e){
         alertMaker(e.toString(),"Exception Error",ButtonType.CLOSE);
     }finally {
         resetForm();
     }



    }

    public boolean alertMaker(String message, String title, ButtonType... type){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,message, type);
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
    void createReport(MouseEvent event) throws JRException, SQLException, ClassNotFoundException {
//        File file=new File("jasperReports/Item.jasper");
//        System.out.println(file.getAbsolutePath());
//        JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
//        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"code","description","unitprice","qtyonhand"},0);
//        ObservableList<ItemTB> tbs = tabItem.getItems();
//        for (ItemTB tb:tbs) {
//            Object[] rowData = {tb.getCode(), tb.getDescription(), tb.getUnitPrice()+"",tb.getQtyOnHand()+""};
//            dtm.addRow(rowData);
//        }
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("pic","jasperReports/Images/manage_paysheets_2x.png");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map,  new JRTableModelDataSource(dtm));
//        JasperViewer.viewReport(jasperPrint,false);
        if(txtCode.getText().trim().isEmpty()){
            File file=new File("jasperReports/ItemDB.jasper");
            JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
            HashMap<String, Object> map = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        }else if(!txtCode.getText().trim().isEmpty()){
            String code=txtCode.getText();
            File file=new File("jasperReports/ItemDB1.jasper");
            JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
            HashMap<String, Object> map = new HashMap<>();
            map.put("codeId",code);
           // map.put("pic","jasperReports/report/Images/manage_employees_2x.png");
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, DBconnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        }
    }

}

