package kroryi.board.dao;

import javafx.scene.control.CheckBox;
import kroryi.board.Controller;
import kroryi.board.dto.Board;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardDAO extends JDBConnection {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public List<Board> selectList() {
        List<Board> boardList = new ArrayList<>();
        String sql = "select * from Board";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Board board = new Board();
//                board.setCheckBox(new CheckBox());
                board.setNo(rs.getInt("no"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setWriter(rs.getString("writer"));
                board.setRegDate(dateFormat.format(rs.getTimestamp("reg_date")));
                board.setUpdDate(dateFormat.format(rs.getTimestamp("upd_date")));

                /*System.out.print(rs.getInt("no"));
                System.out.println(" ");
                System.out.print(rs.getString("title"));
                System.out.println(" ");
                System.out.println(rs.getString("content"));*/

                boardList.add(board);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return boardList;

    }

    public Board select(int no) {
        Board board = new Board();
        String sql = "select * from Board where no=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board.setNo(rs.getInt("no"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setWriter(rs.getString("writer"));
                board.setRegDate(dateFormat.format(rs.getTimestamp("reg_date")));
                board.setUpdDate(dateFormat.format(rs.getTimestamp("upd_date")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("게시글 조회시 에러 발생");
        }
        return board;
    }

    public int insert(Board board) {
        int result = 0;

        String sql ="insert into Board(title,writer,content) values(?,?,?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());
            result = pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("게시물 등록시 에러발생");
            e.printStackTrace();
        }
        return result;
    }

    public int update(Board board) {
        int result = 0;

        String sql = "update Board set title=?,writer=?,content=?, upd_date=now() where no=?";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());
            pstmt.setInt(4, board.getNo());
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("게시물 수정 시 에러발생");
        }
        return result;
    }

    public int delete(int no) {
        int result = 0;

        String sql = "delete from Board where no=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("게시물 삭제 시 에러발생");
        }
        return result;
    }
    public int selectTotalCount(){
        String sql ="select count(*) from Board order by no desc";
        int count =0;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                count = rs.getInt(1);
            }
            System.out.println("총 게시물 :" + count);

        }catch (SQLException e){
            System.out.println("충 게시물 개수 조회시 오류 발생");
            e.printStackTrace();
        }
        return  count;
    }

    public List<Board> selectPageList(int pageNo){
        LinkedList<Board> boardLinkedList = new LinkedList<>();
        Controller controller = new Controller();

        try{
            String sql = "select * from Board order by no desc limit ? offset ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, controller.getPageSize());
            pstmt.setInt(2,pageNo * controller.getPageSize());
            rs = pstmt.executeQuery();

            while (rs.next()){
                Board board = new Board();
                board.setNo(rs.getInt("no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setRegDate(dateFormat.format(rs.getTimestamp("reg_date")));
                board.setUpdDate(dateFormat.format(rs.getTimestamp("upd_date")));
                boardLinkedList.add(board);
            }

        }catch (SQLException e){
            System.out.println("게시판 페이징 중 오류 발생");
            e.printStackTrace();
        }
        return boardLinkedList;

    }



}
