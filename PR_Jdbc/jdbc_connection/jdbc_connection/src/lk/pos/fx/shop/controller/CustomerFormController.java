package lk.pos.fx.shop.controller;

import com.jfoenix.controls.JFXTextField;
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
import lk.pos.fx.shop.business.Custom.CustomerBO;
import lk.pos.fx.shop.db.Connection.DBconnection;
import lk.pos.fx.shop.model.CustomerDTO;
import lk.pos.fx.shop.view.model.CustomerTB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;

import static sun.net.www.MimeTable.loadTable;

public class CustomerFormController implements Initializable{
    @FXML
    private AnchorPane customerform;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private TableView<CustomerTB> tabCustomer;

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




    private  ArrayList<CustomerTB>tbsCustomer;

    private CustomerBO bo=(CustomerBO) BOFactory.getInstance().getBo(BOFactory.BOType.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       labDelete.setVisible(false);
       labSave.setVisible(false);
       labNew.setVisible(false);
       labHome.setVisible(false);
       butSave.setDisable(true);
       butSave.setDisable(true);

        tabCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tabCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tabCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        loadTable();

       tabCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTB>() {
           @Override
           public void changed(ObservableValue<? extends CustomerTB> observable, CustomerTB oldValue, CustomerTB newValue) {
               if(newValue==null){
                   return;
               }
               txtId.setText(newValue.getId());
               txtName.setText(newValue.getName());
               txtAddress.setText(newValue.getAddress());
               txtId.setEditable(false);
               butSave.setDisable(false);
               butDelete.setDisable(false);
           }
       });
    }

    public void loadTable(){
        try {
            ArrayList<CustomerDTO> list = bo.getAllCustomer();
            ObservableList<CustomerDTO> list1 = FXCollections.observableArrayList(list);
            ObservableList<CustomerTB> objects = FXCollections.observableArrayList();
            for (CustomerDTO dto : list1) {
                CustomerTB tb = new CustomerTB(dto.getId(), dto.getName(), dto.getAddress());
                objects.add(tb);
            }
            tabCustomer.setItems(objects);
        }catch (SQLException|ClassNotFoundException e){
            alertMaker(e.toString(),"Exception Error",ButtonType.FINISH);
        }
    }

    public void resetForm(){
      txtId.clear();
      txtAddress.clear();
      txtName.clear();
      tabCustomer.getSelectionModel().clearSelection();
      txtId.setEditable(true);
      txtId.requestFocus();
      butDelete.setDisable(true);
      butSave.setDisable(false);
      loadTable();
    }


            @FXML
    void createNewField(MouseEvent event) {
       resetForm();
    }

    @FXML
    void deleCustomer_On_Click(MouseEvent event) {
        String message="Are you sure to delete this Customer";
        String  title="                             Confirm";

        boolean isConfirm = alertMaker(message, title, ButtonType.OK, ButtonType.NO);
        try {
            if (isConfirm) {
                String id = txtId.getText();
                bo.deleteCustomer(id);
                resetForm();
            }
        }catch(SQLException|ClassNotFoundException e){
            alertMaker(e.toString(),"Exception Error",ButtonType.FINISH);
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
        Stage stage=(Stage)customerform.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveAndUpdateCustomer_On_Click(MouseEvent event) {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        if(  id.isEmpty() ){ alertMaker("Customer id is empty","       Error_Message",ButtonType.OK);txtId.clear(); txtId.requestFocus(); return;}
        else if(  name.isEmpty() ){ alertMaker("Customer name is empty","       Error_Message",ButtonType.OK);txtName.clear(); txtName.requestFocus();return;}
        else if(  address.isEmpty() ){ alertMaker("Customer address is empty","       Error_Message",ButtonType.OK);txtAddress.clear(); txtAddress.requestFocus();return;}
      try {
          if(tabCustomer.getSelectionModel().isEmpty()) {
              ObservableList<CustomerTB> tbs = tabCustomer.getItems();

              for (CustomerTB tb:tbs) {
                  if(tb.getId().trim().toUpperCase().equals(id.toUpperCase())){
                      alertMaker("Customer Id cannot be duplicated If you want to Update Relavent Customer Select from table first","      ErrorMessage" ,ButtonType.FINISH);
                      txtId.requestFocus();
                      return;
                  }
              }

              boolean isSave = bo.saveCustomer(new CustomerDTO(id, name, address));
              alertMaker("Customer save successfully","       Message",ButtonType.CLOSE);
              tabCustomer.getItems().add(new CustomerTB(id,name,address));
          }else{
              CustomerTB tb = tabCustomer.getSelectionModel().getSelectedItem();
              tb.setName(name);
              tb.setAddress(address);
              boolean isUpdate = bo.updateCustomer(new CustomerDTO(id, name, address));
              if(isUpdate){alertMaker("Update successfull"," Successfull",ButtonType.FINISH);}
              else{alertMaker("Update Unsuccessfull"," UnSuccessfull",ButtonType.FINISH);}

          }
      }catch (SQLException|ClassNotFoundException e){
          alertMaker(e.toString(),"Exception Error",ButtonType.FINISH);
      }finally {
          resetForm();
      }


    }


    public boolean alertMaker(String message,String title,ButtonType ... type){
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
//       File file=new File("jasperReports/Customer.jasper");
//        System.out.println(file.getAbsolutePath());
//        JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
//        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"id","name","address"},0);
//        ObservableList<CustomerTB> tbs = tabCustomer.getItems();
//        for (CustomerTB tb:tbs) {
//            Object[] rowData = {tb.getId(), tb.getName(), tb.getAddress()};
//            dtm.addRow(rowData);
//        }
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("pic","D:\\DEP\\MY\\2\\PR_Jdbc\\jasperReports\\Images/manage_employees_2x.png");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map,  new JRTableModelDataSource(dtm));
//        JasperViewer.viewReport(jasperPrint,false);

        File file=new File("jasperReports/CustomerDB1.jasper");

        HashMap<String, Object> map = new HashMap<>();
        map.put("pic","jasperReports/report/Images/manage_employees_2x.png");
        JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,map, DBconnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}


