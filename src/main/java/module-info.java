module kroryi.board {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.base;
    requires java.desktop;
    requires javafx.swing;


    opens kroryi.board to javafx.fxml, javafx.graphics, javafx.base;
    opens kroryi.board.controller to javafx.fxml, javafx.controls, javafx.graphics, javafx.base;
    opens kroryi.board.dto to javafx.base;
    opens kroryi.board.util to javafx.base;
    exports kroryi.board;
}