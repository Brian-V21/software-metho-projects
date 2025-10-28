module org.example.project3softwaremetho {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.project3softwaremetho to javafx.fxml;
    exports org.example.project3softwaremetho;
}