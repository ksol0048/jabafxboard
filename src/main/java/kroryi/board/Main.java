package kroryi.board;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("boardlist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 880, 600);
        stage.setTitle("JavaFx Board!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

       /* stage.setOnCloseRequest(event -> {
            event.consume();
        })*/
    }

    public static void main(String[] args) {
        launch();
    }
}