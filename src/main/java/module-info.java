module com.example.integrativetask_ii_ced {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.integrativetask_ii_ced to javafx.fxml;
    exports com.example.integrativetask_ii_ced;
    exports com.example.integrativetask_ii_ced.model.drawing;
    opens com.example.integrativetask_ii_ced.model.drawing to javafx.fxml;
    exports com.example.integrativetask_ii_ced.model.entities;
    opens com.example.integrativetask_ii_ced.model.entities to javafx.fxml;
    exports com.example.integrativetask_ii_ced.model.entities.mob;
    opens com.example.integrativetask_ii_ced.model.entities.mob to javafx.fxml;
    exports com.example.integrativetask_ii_ced.model.entities.objects;
    opens com.example.integrativetask_ii_ced.model.entities.objects to javafx.fxml;
    exports com.example.integrativetask_ii_ced.model.entities.objects.functional;
    opens com.example.integrativetask_ii_ced.model.entities.objects.functional to javafx.fxml;
    exports com.example.integrativetask_ii_ced.structure.graph;
    exports com.example.integrativetask_ii_ced.structure.narytree;
    exports com.example.integrativetask_ii_ced.model.map;
    opens com.example.integrativetask_ii_ced.model.map to javafx.fxml;

}