package lk.pos.fx.shop.main;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/lk/pos/fx/shop/view/fxml/loginForm.fxml"));
        Scene scene=new Scene(parent);
        Image image=new Image("/lk/pos/fx/shop/asset/icons8_Apple_28px.png");
        primaryStage.setScene(scene);
        primaryStage.setTitle("                                                                         SHOP");
        primaryStage.getIcons().add(image);
        primaryStage.setResizable(false);
        primaryStage.show();

        animation_maker(parent,scene.getHeight());
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
}
