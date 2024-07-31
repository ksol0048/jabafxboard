package kroryi.board.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;
import kroryi.board.util.SceneUtil;

import java.io.IOException;


public class InsertController {
    @FXML
    public TextField tfTitle;
    @FXML
    public TextField tfWriter;
    @FXML
    public TextArea taContent;


    Stage stage;
    Scene scene;
    Parent root;

    BoardService boardService = new BoardServiceImpl();

    public void movetoList(ActionEvent actionEvent) throws IOException {
        // 이전 코딩
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.LIST.getPath()));

        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        // 수정 코딩
        SceneUtil.getInstance().switchScene(actionEvent,UI.LIST.getPath());
    }


    public void Insert(ActionEvent actionEvent) throws IOException {
        String title = tfTitle.getText();
        String writer = tfWriter.getText();
        String content = taContent.getText();
        if(title==null || title.trim().isEmpty()){
            showAlert("제목을 입력하세요");
            return;

        }
        if(writer==null || writer.trim().isEmpty()){
            showAlert("작성자를 입력하세요");
            return;

        }
        if(content==null || content.trim().isEmpty()){
            showAlert("내용을 입력하세요");
            return;

        }

        Board board = new Board(tfTitle.getText(), tfWriter.getText(), taContent.getText());
        int result = boardService.insert(board);
        if (result > 0) {
            System.out.println("글쓰기 성공");
            SceneUtil.getInstance().switchScene(actionEvent,UI.LIST.getPath());
        }else{
            showAlert("입력에 문제가 있습니다.");
        }
    }
    private void showAlert(String message) {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle(("입력오류"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
