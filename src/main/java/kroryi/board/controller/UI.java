package kroryi.board.controller;

public enum UI {
    LIST("/kroryi/board/boardlist-view.fxml"),
    INSERT("/kroryi/board/INSERT.fxml"),
    READ("/kroryi/board/READ.fxml"),
    UPDATE("/kroryi/board/UPDATE.fxml");

    private final String path;

    UI(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
