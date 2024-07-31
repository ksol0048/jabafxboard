package kroryi.board.dao;

import javafx.beans.property.SimpleStringProperty;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;

import java.sql.*;

public class JDBConnection {
    public Connection con;
    public Statement stmt;
    public PreparedStatement pstmt;
    public ResultSet rs;

    public JDBConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/javafxboard?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "12345";

            con = DriverManager.getConnection(url, username, password);
            System.out.println("DB 연결 성공!!!!");


        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("DB연결 실패");
        }
    }

  /*  public static void main(String[] args){
//        JDBConnection jdbc = new JDBConnection();
        BoardDAO dao = new BoardDAO();
        dao.selectList();
//        dao.select(1);
        System.out.println(dao.select(1).toString());
//        Board board = new Board(new SimpleStringProperty("오늘 뭐하니"),new SimpleStringProperty("날씨가 좋네"),new SimpleStringProperty("홍길동"));
//        dao.insert(board);
//        dao.selectList();

//        Board board = new Board(new SimpleStringProperty("오늘 뭐하니1111"),new SimpleStringProperty("날씨가 좋네1111"),new SimpleStringProperty("홍길동111111111"));
//        board.setNo(3);
//        dao.update(board);

//        dao.delete(1);


        BoardService boardService=new BoardServiceImpl();
        boardService.list();
    }*/
}
