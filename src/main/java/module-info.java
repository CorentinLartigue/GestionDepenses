module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires reload4j;
    requires org.apache.logging.log4j.core;


    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;
    exports com.example.javafx.models;
    opens com.example.javafx.models to javafx.fxml;
    exports com.example.javafx.controllers;
    opens com.example.javafx.controllers to javafx.fxml;
    exports com.example.javafx.dao;
    opens com.example.javafx.dao to javafx.fxml;


}
