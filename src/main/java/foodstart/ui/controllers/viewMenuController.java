package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class viewMenuController {

    @FXML
    private Text menuNameText;

    @FXML
    private Text menuDescriptionText;

    @FXML
    private TableView menuTable;

    @FXML
    private TableColumn tableIDColumn;

    @FXML
    private TableColumn tableNameColumn;

    @FXML
    private TableColumn tableDescriptionColumn;

    @FXML
    private TableColumn tableVariantsColumn;

    @FXML
    private Button btnSetCurrentMenu;



    @FXML
    public void initialize() {
        //TO-DO
    }
}
