package lk.pos.fx.shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.util.Duration;
import lk.pos.fx.shop.business.BOFactory;
import lk.pos.fx.shop.business.Custom.UserBO;
import lk.pos.fx.shop.model.UsersDTO;


public class SingUpFormController implements  Initializable{

            @FXML
            private PasswordField txtPassword;

            @FXML
            private TextField txtUserId;

            @FXML
            private AnchorPane loginPane;

            @FXML
            private Label butlogin;

            @FXML
            private PasswordField txtRepassword;

            @FXML
            private AnchorPane RegPane;

            @FXML
            private JFXButton butReg;

            @FXML
            private AnchorPane SingUpPane;

            UserBO bo=(UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

            @Override
            public void initialize(URL location, ResourceBundle resources) {
                Platform.runLater(() -> {
                    txtUserId.requestFocus();
                });

              butlogin.setOpacity(0.3);
              butReg.setOpacity(0.3);
              txtPassword.setEditable(false);
              txtRepassword.setEditable(false);
            }

            @FXML
            void LoginTo_LoginPage_Press(KeyEvent event) throws IOException {
                int key=event.getCode().ordinal();
                if(key==0){
                    userregistration();

                }
            }

            @FXML
            void LoginTo_loginpage_OnAction(ActionEvent event) throws IOException {
               userregistration();

            }

            public void setPath() throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/loginForm.fxml"));
                Parent parent=(Parent)fxmlLoader.load();
                LoginFormController controller=fxmlLoader.getController();
                controller.getFromSingup(this.txtUserId.getText());
                Scene scene=new Scene(parent);
                Stage stage=(Stage)this.SingUpPane.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

            public void userregistration() throws IOException {
                String name=txtUserId.getText().trim().toString();
                String password=txtPassword.getText().trim().toString();
                String re_Password=txtRepassword.getText().trim().toString();
                if(!name.isEmpty() && !password.isEmpty() && !re_Password.isEmpty()){
                    if(password.equals(re_Password)){
                        boolean isAvailable = false;
                        try {
                            isAvailable = bo.searchUser(name);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if(isAvailable){
                            String message=" This User Name available in Database Try Another Name";
                            String title=" Error Mesage";
                            alertMaker(message,title);
                            txtUserId.requestFocus();
                        }else{
                            boolean isAdded = false;
                            try {
                                isAdded = bo.addUser(name,password);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(isAdded){
                                String message=" Successfully User added";
                                String title=" Successful";
                                alertMaker(message,title);
                                setPath();
                            }
                        }
                    }else{
                        String message=" Password And Re-Passsword are not Equla";
                        String title=" Error Mesage";
                        alertMaker(message,title);
                        this.txtPassword.clear();
                        this.txtRepassword.clear();
                        this.txtPassword.requestFocus();
                    }
                }
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
            void butAnimationMouseEntered(MouseEvent event) {
                AnchorPane pane= (AnchorPane) event.getSource();
                String labelId = pane.getId();
                if(labelId.trim().equals("loginPane")){
                    butlogin.setOpacity(1);
                    butlogin.setStyle("-fx-border-width: 0px 0px 2px 0px;" +
                                      "-fx-border-color: White");
                }else if(labelId.trim().equals("RegPane")){
                    butReg.setOpacity(1);
                    butReg.setStyle("-fx-text-fill: White; " +
                            "-fx-border-width: 0px 0px 2px 0px;" +
                            "-fx-border-color: White");
                }
            }

            @FXML
            void butAnimationMouseExited(MouseEvent event) {
                AnchorPane pane= (AnchorPane) event.getSource();
                String labelId = pane.getId();
                if(labelId.trim().equals("loginPane")){
                    butlogin.setOpacity(0.2);
                    butlogin.setStyle("-fx-border-width: 0px 0px 0px 0px;" +
                                       "-fx-border-color: transparent");
                }else if(labelId.trim().equals("RegPane")){
                    butReg.setOpacity(0.2);
                    butReg.setStyle("-fx-text-fill: black; " +
                            "-fx-border-width: 0px;" +
                            " -fx-border-color: transparent");
                }
            }

            public void requestFocustSet(KeyEvent keyEvent) {

                int key=keyEvent.getCode().ordinal();
                TextField textField= (TextField) keyEvent.getSource();
                String fieldId = textField.getId();

                if(fieldId.trim().equals("txtUserId") && !txtUserId.getText().trim().isEmpty() ){
                    this.txtPassword.setEditable(true);
                    if(key==0){
                        txtPassword.requestFocus();
                    }
                }else if(fieldId.trim().equals("txtPassword")  && !this.txtPassword.getText().trim().isEmpty()  ){
                    this.txtRepassword.setEditable(true);
                    if(key==0){
                        txtRepassword.requestFocus();
                    }
                }else if(fieldId.trim().equals("txtRepassword") && key==0){
                    txtUserId.requestFocus();
                }else if(!txtUserId.getText().trim().isEmpty()  && !txtPassword.getText().trim().isEmpty() && !txtRepassword.getText().trim().isEmpty() && key==5){
                    butReg.setOpacity(1);
                    butReg.requestFocus();
                    butReg.setStyle("-fx-border-color: white;-fx-text-fill: white;-fx-border-width: 0px 0px 2px 0px");
                }
            }

            public void onclick_To_Login(MouseEvent mouseEvent) throws IOException {
                Parent root= FXMLLoader.load(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/loginForm.fxml"));
                Scene scene=new Scene(root);
                Stage stage=(Stage)this.SingUpPane.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                animation_maker(root,scene.getWidth());
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
        }

