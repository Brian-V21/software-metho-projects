module org.example.project3softwaremetho {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens org.example.project3softwaremetho to javafx.fxml;
    exports org.example.project3softwaremetho;
}
