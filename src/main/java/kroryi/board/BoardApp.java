package kroryi.board;

import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;

public class BoardApp {
    static BoardService boardService = new BoardServiceImpl();

    public static void main(String[] args) {
        //목록보기
        /*for (Board board : boardService.list()) {
            System.out.println(board.getNo() + " " + board.getTitle() + " " + board.getWriter() + " " + board.getRegDate());
        }*/

        //게시글 등록
        /*Board board = new Board();
        board.setTitle("신로운 세상!!!");
        board.setWriter("홍길동");
        board.setContent("날씨가 무지무지 덥레용.");
        boardService.insert(board);*/

        //상세보기
        /*for (Board board : boardService.list()) {
            if (board.getNo() == 2) {
                System.out.println(board.getNo() + " " + board.getTitle() + " " + board.getWriter() + " " + board.getRegDate());
            }
        }*/

        /*Board board = boardService.select(2);
        System.out.println(board.getNo()+" "+board.getTitle()+" "+board.getContent()+" "+ board.getWriter()+" "+board.getRegDate());*/

        //수정
        /*Board board = boardService.select(2);
        board.setTitle("수정예제");
        board.setContent("수정하였습니다.");
        board.setWriter("이재준");
        boardService.update(board);
        System.out.println(board.getNo()+" "+board.getTitle()+" "+board.getContent()+" "+ board.getWriter()+" "+board.getRegDate());*/

        // 삭제
        int board = boardService.delete(2);



    }
}
