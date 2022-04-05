module com.example.java_laba_7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.java_laba_7 to javafx.fxml;
    exports com.example.java_laba_7;
}