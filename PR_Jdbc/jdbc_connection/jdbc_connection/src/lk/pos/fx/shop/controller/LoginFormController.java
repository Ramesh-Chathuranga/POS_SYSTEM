package lk.pos.fx.shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.UserBO;


public class LoginFormController implements Initializable {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    private AnchorPane singUpPane;

    @FXML
    private Label butSingUP;

    @FXML
    private AnchorPane logPane;

    @FXML
    private JFXButton butLogin;

    @FXML
    private AnchorPane loginform;

    UserBO bo=(UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       butSingUP.setOpacity(0.2);
       butLogin.setOpacity(0.2);
       if(txtUser.getText().trim().isEmpty()) {
           txtPassword.setEditable(false);
       }
        Platform.runLater(() -> {
            txtUser.requestFocus();
        });

    }
    public void getFromSingup(String name){
        this.txtUser.setText(name);
        txtPassword.requestFocus();
        txtPassword.setEditable(true);
    }

    @FXML
    void butAnimationMouseEntered(MouseEvent event) {
        AnchorPane pane= (AnchorPane) event.getSource();
        String labelId = pane.getId();
        if(labelId.trim().equals("singUpPane")){
           butSingUP.setOpacity(1);
            butSingUP.setStyle("-fx-border-width: 0px 0px 2px 0px;" +
                    "-fx-border-color: white");
        }else if(labelId.trim().equals("logPane")){
            butLogin.setOpacity(1);
            butLogin.setStyle("-fx-text-fill: White; " +
                               "-fx-border-width: 0px 0px 2px 0px;" +
                               "-fx-border-color: White");
        }
    }

    @FXML
    void butAnimationMouseExited(MouseEvent event) {
        AnchorPane pane= (AnchorPane) event.getSource();
        String labelId = pane.getId();
        if(labelId.trim().equals("singUpPane")){
            butSingUP.setOpacity(0.2);
            butSingUP.setStyle("-fx-border-width: 0px 0px 0px 0px;" +
                    "-fx-border-color: transparent");
        }else if(labelId.trim().equals("logPane")){
            butLogin.setOpacity(0.2);
            butLogin.setStyle("-fx-text-fill: black; " +
                              "-fx-border-width: 0px;" +
                              " -fx-border-color: transparent");
        }
    }

    @FXML
    void onclick_TOREG(MouseEvent event) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/SingUpForm.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=(Stage)this.loginform.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("User Register");
        stage.show();
        animation_maker(parent,-scene.getWidth());
    }

    public void animation_maker(Parent root,double number){
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(2000),root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(10);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.millis(2000),root);
        translateTransition.setFromX(number/2);
        translateTransition.setToX(0);
        translateTransition.play();
    }

    @FXML
    void LoginToDashBoardKeyPress(KeyEvent event) {
        int key = event.getCode().ordinal();
        if(key==0){
            System.out.println("ok");
            loginTo();
        }
    }

    @FXML
    void LoginToDashBoardOnAction(ActionEvent event) {
      loginTo();
    }

    public void loginTo(){
        String name=this.txtUser.getText().trim().toString();
        String password=this.txtPassword.getText().trim().toString();

        try {

            if (bo.searchUser(name)) {
                System.out.println(name+password);
                boolean isHaSUser = bo.getUserId(name, password);
                if (isHaSUser) {


                        setPathToDash();

                } else {
                    System.out.println("can not login "+isHaSUser);
                    String message = "Invalid  Password";
                    String title = "Error Message";
                    alertMaker(message, title);
                    this.txtPassword.requestFocus();
                    return;
                }
            } else {
                String message = "User Name Invalid";
                String title = "Error Message";
                alertMaker(message, title);
                this.txtUser.requestFocus();
                this.txtUser.clear();
                return;
            }
        }catch(ClassNotFoundException| SQLException |IOException e){
           new Alert(Alert.AlertType.CONFIRMATION,e.toString(),ButtonType.OK).showAndWait();
            System.out.println(e.toString()+e.getMessage().toString());
        }
    }

    public void setPathToDash() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/DashBoardForm.fxml"));
        Parent parent=fxmlLoader.load();
        DashBoardFormController controller=fxmlLoader.getController();
        controller.setDashBoardValue(this.txtUser.getText());
        Scene scene=new Scene(parent);
        Stage stage=(Stage) this.loginform.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        animation_maker(parent,-scene.getWidth());
    }

    public void alertMaker(String message,String title){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.OK);
        Image image=new Image("/lk/pos/fx/shop/asset/icons8_Apple_28px.png");
        Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
        alert.setTitle("SHOP"+"                                            "+title);
        stage.getIcons().add(image);
        alert.showAndWait();
    }
    @FXML
    void requestFocustSet(KeyEvent event) {
        try{
            int key = event.getCode().ordinal();
            TextField textField= (TextField) event.getSource();
            String fieldId = textField.getId();
            if(fieldId.trim().equals("txtUser")&& !textField.getText().trim().isEmpty()){
                txtPassword.setEditable(true);
                if( key==0){
                    txtPassword.requestFocus();
                }
            }else if(fieldId.trim().equals("txtPassword") && key==0){
                txtUser.requestFocus();
            }else if(!this.txtUser.getText().trim().isEmpty() && !this.txtPassword.getText().trim().isEmpty() && key==5){
                this.butLogin.requestFocus();
                this.butLogin.setOpacity(1);
            }
        }catch (Exception e){}
    }

}

