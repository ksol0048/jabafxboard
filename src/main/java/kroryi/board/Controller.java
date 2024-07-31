package kroryi.board;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import kroryi.board.controller.ReadController;
import kroryi.board.controller.UI;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;
import kroryi.board.util.SceneUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
 // 컨트롤 메뉴
public class Controller implements Initializable {
    @FXML
    private TableView<Board> boardTableView;
    @FXML
    public CheckBox chkSelected;
    @FXML
    private TableColumn<Board, Boolean> colCheckbox;
    @FXML
    private TableColumn<Board, Integer> colNo;
    @FXML
    private TableColumn<Board, String> colTitle;
    @FXML
    private TableColumn<Board, String> colWriter;
    @FXML
    private TableColumn<Board, String> colRegDate;
    @FXML
    private TableColumn<Board, String> colDpbDate;

    Stage stage;
    Scene scene;
    Parent root;

    ///////// 페이지 관련 자료
    @FXML
    private Pagination pagination;
    private int totalCount = 0;
    private final int pageSize = 4;
    private int totalPage;

    public int getPageSize(){
        return  pageSize;
    }
///////////////////////////////

    BoardService boardService = new BoardServiceImpl();
    List<Board> boardList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SwingNode swingNode = new SwingNode();
        // Swing 구성 요소를 생성하고 SwingNode에 설정합니다.
        createAndSetSwingContent(swingNode);


        totalCount = boardService.totalListCount();
        totalCount = totalCount == 0 ? 1 : totalCount;
        totalPage = (totalCount + pageSize -1)/ pageSize;

        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(pageSize);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                pageListAll(pagination.getCurrentPageIndex());

                return new Label(String.format("현재 페이지 :%d", pagination.getCurrentPageIndex()+1));
            }
        });



        colCheckbox.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());

        colCheckbox.setCellFactory(new Callback<TableColumn<Board, Boolean>, TableCell<Board, Boolean>>() {
            @Override
            public TableCell<Board, Boolean> call(TableColumn<Board, Boolean> boardBooleanTableColumn) {
                return new TableCell<Board, Boolean>() {
                    private final CheckBox checkBox = new CheckBox();

                    {
                        checkBox.setOnAction(event -> {
                            int index = getIndex();
                            if (index >= 0 && index < boardTableView.getItems().size()) {
                                boardTableView.getSelectionModel().select(index, colCheckbox);
                                System.out.println(boardTableView.getSelectionModel().getSelectedItem());
                            }
                            System.out.println("checkbox index: " + index);
                        });
                    }

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            checkBox.setSelected(item != null && item);
                            setGraphic(checkBox);
                        }
                    }
                };
            }

        });


        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && boardTableView.getSelectionModel().getSelectedItem() != null) {
                    int boardNo = boardTableView.getSelectionModel().getSelectedItem().getNo();

                    try {
                        ReadController readController = (ReadController) SceneUtil.getInstance().getController(UI.READ.getPath());
                        readController.read(boardNo);
                        Parent root = SceneUtil.getInstance().getRoot();
                        SceneUtil.getInstance().switchScene(mouseEvent, UI.READ.getPath(), root);

                    } catch (IOException event) {
                        System.out.println("목록-> 읽기 이동중 에러 발생");
                        event.printStackTrace();
                    }
                }
            }
        });

        chkSelected.setOnAction(event -> {
            boolean selected = chkSelected.isSelected();
            for (Board board : boardList) {
                board.setSelected(selected);
            }
        });

    }



    public void moveToInsert(ActionEvent actionEvent) throws IOException {
        System.out.println("글쓰기 화면 이동");
        // 이전 코딩
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.INSERT.getPath()));

        root=loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        // 수정 코딩
        SceneUtil.getInstance().switchScene(actionEvent, UI.INSERT.getPath());
    }

    public void Close(ActionEvent actionEvent) {
    }

    public void moveToDelete(ActionEvent event) throws IOException {
        showAlert(event);

    }

    private void showAlert(ActionEvent event) throws IOException {
        for (Board board : boardTableView.getItems()) {

            /*if (board.getCheckBox().isSelected()) {
                int boardNo = board.getNo();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(("게시글 삭제"));
                alert.setHeaderText("게시글을 정말 삭제 하시겠습니까?(글번호 :" + boardNo + ")");
                alert.setContentText("삭제 후 될돌릴 수 없습니다.");
                int result = 0;
                if (alert.showAndWait().get() == ButtonType.OK) {
                    result = boardService.delete(boardNo);
                }
                if (result > 0) {
                    System.out.println("글 삭제 완료");

                }
            }*/
        }
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }

    private void createAndSetSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            // Swing JTextField 생성
            JTextField textField = new JTextField(20);
            // JTable 생성 및 설정
            String[] columnNames = {"Column1", "Column2"};
            Object[][] data = {
                    {"Data1", "Data2"},
                    {"Data3", "Data4"},
            };
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

            // 레이아웃 설정을 위해 JPanel 사용
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(textField, BorderLayout.NORTH);
            panel.add(new JScrollPane(table), BorderLayout.CENTER);

            // SwingNode에 JPanel 추가
            swingNode.setContent(panel);
        });
    }


    public void pageListAll(int pageIndex){
        boardList = boardService.pageList(pageIndex);
        totalCount = boardList.size();

        ObservableList<Board> list = FXCollections.observableArrayList(boardList);
        colNo.setCellValueFactory(new PropertyValueFactory<>("No"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colWriter.setCellValueFactory(new PropertyValueFactory<>("Writer"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("RegDate"));
        colDpbDate.setCellValueFactory(new PropertyValueFactory<>("UpdDate"));

        boardTableView.setItems(list);
    }
}