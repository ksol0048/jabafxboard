package kroryi.board.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;
import kroryi.board.util.SceneUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateController {

    @FXML
    public TextField tfTitle;
    @FXML
    public TextField tfWriter;
    @FXML
    public TextField tfRegDate;
    @FXML
    public TextArea taContent;

    int boardNo;

    List<Integer> numArray = new ArrayList<>();
    private BoardService boardService = new BoardServiceImpl();
    int tagetValue = boardNo;
    int nextValue = -1;
    int prevValue = -1;

    public void read(int boardNo) {
        numArray = listNum();
        this.tagetValue = boardNo;
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        tfRegDate.setText(board.getRegDate());
        taContent.setText(board.getContent());
    }

    public void UpdatemovetoList(ActionEvent event) throws IOException {
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void UpdatemovetoUpdate(ActionEvent event) throws IOException {
        Board board = new Board(tfTitle.getText(), tfWriter.getText(), taContent.getText());
        board.setNo(boardNo);
        int result = boardService.update(board);
        if (result > 0) {
            System.out.println("글 수정 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        } else {
            System.out.println("수정 중 에러 발생");
        }
    }

    public void UpdatemovetoDelete(ActionEvent event) throws IOException {
        showAlert(event);
    }

    public void UpdatemovetoNext(ActionEvent event) {
        numArray = listNum();
        read(nextValue);
    }

    public void UpdatemovetoPrev(ActionEvent event) {
        numArray = listNum();
        read(prevValue);
    }

    private void showAlert(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(("게시글 삭제"));
        alert.setHeaderText("게시글을 정말 삭제 하시겠습니까?(글번호 :" + boardNo + ")");
        alert.setContentText("삭제 후 될돌릴 수 없습니다.");
        int result = 0;
        if (alert.showAndWait().get() == ButtonType.OK) {
            result = boardService.delete(boardNo);
        }
        if(result > 0) {
            System.out.println("글 삭제 완료");
            SceneUtil.getInstance().switchScene(event,UI.LIST.getPath());
        }
    }
    public List<Integer> listNum() {
        List<Board> boardList = new ArrayList<>();
        boardList = boardService.list();
        numArray.clear();
        for (Board board : boardList) {
            numArray.add(board.getNo());
        }

        tagetValue = boardNo;
        int idx = numArray.indexOf(tagetValue);
        if (idx > 0) {
            prevValue = numArray.get(idx - 1);
        }
        if (idx < numArray.size() - 1) {
            nextValue = numArray.get(idx + 1);
        }
        return numArray;
    }
}
