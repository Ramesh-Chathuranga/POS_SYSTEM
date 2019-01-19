package lk.pos.fx.shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.UserBO;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    @FXML
    private Label labUser;

    @FXML
    private JFXButton singOut;

    @FXML
    private Pane customerPane;

    @FXML
    private Label labcustomer;

    @FXML
    private Pane ItemPane;

    @FXML
    private Label labItem;

    @FXML
    private Pane OrderPane;

    @FXML
    private Label labOrder;

    @FXML
    private Pane ODPane;

    @FXML
    private Label labODView;

    @FXML
    private AnchorPane dashboardForm;

    UserBO bo=(UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      labcustomer.setVisible(false);
      labItem.setVisible(false);
      labOrder.setVisible(false);
      labODView.setVisible(false);
      String name= bo.getCurrentUser();
      if(!name.isEmpty()){
          labUser.setText(name);
      }
    }

    @FXML
    void mouseEnteredPaneAnimation(MouseEvent event) {
        Pane pane=(Pane)event.getSource();
        String paneId = pane.getId();
        ScaleTransition scaleTransition=new ScaleTransition(Duration.millis(2000),pane);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        DropShadow dropShadow=new DropShadow();
        dropShadow.setColor(Color.CORNFLOWERBLUE);
        dropShadow.setWidth(30);
        dropShadow.setHeight(30);
        dropShadow.setRadius(30);
        pane.setEffect(dropShadow);
        switch (paneId){
            case "customerPane":labcustomer.setVisible(true);
                break;
            case "ItemPane": labItem.setVisible(true);
                break;
            case "OrderPane":labOrder.setVisible(true);
                break;
            case "ODPane":labODView.setVisible(true);
                break;
            default:
                break;
        }

    }

    @FXML
    void mouseExitedPaneAnimation(MouseEvent event) {
        Pane pane=(Pane)event.getSource();
        String paneId = pane.getId();
        ScaleTransition scaleTransition=new ScaleTransition(Duration.millis(1000),pane);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
        pane.setEffect(null);
        switch (paneId){
            case "customerPane":labcustomer.setVisible(false);
                break;
            case "ItemPane": labItem.setVisible(false);
                break;
            case "OrderPane":labOrder.setVisible(false);
                break;
            case "ODPane":labODView.setVisible(false);
                break;
            default:
                break;
        }
    }

    @FXML
    void navigator(MouseEvent event) throws IOException {
      Pane pane=(Pane)event.getSource();
        String paneId = pane.getId();
        switch (paneId){
            case "customerPane":
                String path_C= "/lk/pos/fx/shop/view/fxml/CustomerForm.fxml";
                String title_C="CUSTOMER_FORM                                                                      SHOP";
                goToPage(pane,path_C,title_C);
                return;
            case "ItemPane":
                String path_I= "/lk/pos/fx/shop/view/fxml/ItemForm.fxml";
                String title_I="Item_FORM                                                                          SHOP";
                goToPage(pane,path_I,title_I);
                return;
            case "OrderPane":
                String path_O= "/lk/pos/fx/shop/view/fxml/OrdersForm.fxml";
                String title_O="Order_FORM                                                                         SHOP";
                goToPage(pane,path_O,title_O);
                return;
            case "ODPane":
                String path_OD= "/lk/pos/fx/shop/view/fxml/SearchOrderForm.fxml";
                String title_OD="Order_Detail_FORM                                                                 SHOP";
                goToPage(pane,path_OD,title_OD);
                return;
            default:
                return;
        }
    }
    public   void  goToPage(Pane pane,String path,String title){
        Parent root= null;
        try {
            root = FXMLLoader.load(this.getClass().getResource(path));
            Scene scene=new Scene(root);
            Stage stage= (Stage) pane.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title);
            stage.getIcons().add(new Image("/lk/pos/fx/shop/asset/icons8_Apple_28px.png"));
            stage.show();
            animation_maker(root,scene.getHeight() / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void animation_maker(Parent root,double number){
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(2000),root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(10);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.millis(2000),root);
        translateTransition.setFromY(-number/2);
        translateTransition.setToY(0);
        translateTransition.play();
    }

    @FXML
    void singOutMouseEntered(MouseEvent event) {
       singOut.setStyle("-fx-text-fill: Red ;-fx-border-color: Red;-fx-border-radius: 1;");

    }

    @FXML
    void singOutMouseExited(MouseEvent event) {
        singOut.setStyle("-fx-text-fill: #1c4508;-fx-border-color:#1c4508;-fx-border-radius: 0;");
    }

    @FXML
    void User_singOut(ActionEvent event) {

    String path="/lk/pos/fx/shop/view/fxml/loginForm.fxml";
    String title="                                                                         SHOP";
    goToPage(this.dashboardForm,path,title);
    }

    public void setDashBoardValue(String name){this.labUser.setText(name);}
}
