package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.stock.Ingredient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class InventoryController {
	private Stage stage;
	@FXML
	private TableView<Ingredient> inventoryView;
	@FXML
	private TableColumn<Ingredient, String> id;
	@FXML
	private TableColumn<Ingredient, String> name;
	@FXML
	private TableColumn<Ingredient, String> truckStock;
	@FXML
	private TableColumn<Ingredient, String> kitchenStock;
	@FXML
	private TableColumn<Ingredient, String> dietary;
	private FXMLLoader loader;

	@FXML
	public void initialize() {
		populateTable();
	}

	private void populateTable() {
		IngredientManager ingredientManager = Managers.getIngredientManager();
		Set<Ingredient> ingredientSet = ingredientManager.getIngredientSet();
		ObservableList<Ingredient> ingredientObserver = FXCollections.observableArrayList(ingredientSet);
		inventoryView.setItems(ingredientObserver);
		id.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		name.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		truckStock.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getTruckStock())));
		kitchenStock.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getKitchenStock())));
		dietary.setCellValueFactory(cell -> new SimpleStringProperty(ingredientManager.safeForString(cell.getValue().getId())));
	}

	public void addIngredient() {
		loader = new FXMLLoader(getClass().getResource("addIngredientPopUp.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(loader.getRoot());
		stage = new Stage();
		stage.setTitle("Add Item");
		stage.setScene(scene);
		stage.show();


	}


}