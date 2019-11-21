import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception{
        primaryStage.setTitle("数据侦测脚本");
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        //如果使用 Parent root = FXMLLoader.load(...) 静态读取方法，无法获取到Controller的实例对象
        Scene scene = new Scene(root, 800, 600);
        //加载css样式
        //scene.getStylesheets().add(getClass().getResource("style1.css").toExternalForm());
        primaryStage.setScene(scene);
        Controller controller = fxmlLoader.getController();   //获取Controller的实例对象
        //Controller中写的初始化方法
        controller.Init();
        primaryStage.show();


        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                if (Share.timer != null) {
                    Share.timer.cancel();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
