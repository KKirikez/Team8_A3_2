package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import controller.Coordinator;
import exceptions.MinimumOverMax;
import exceptions.NegativePrice;

import java.awt.Label;
import java.io.File;
import java.io.IOException;
import model.Toy;
import view.ToyStoreMenu;
import model.Figures;
import model.Animals;
import model.Puzzles;
import model.Boardgames;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class SampleController implements Initializable {
    private static final String FILE_PATH = "res/toys.txt";
    private static ObservableList<Toy> toys = FXCollections.observableArrayList();

    @FXML
    private TextField ageAppropriateField;

    @FXML
    private TextField availableCountField;

    @FXML
    private TextField brandField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField classificationField;

    @FXML
    private Button clearButton;

    @FXML
    private TextField designersField;

    @FXML
    private TextField materialField;

    @FXML
    private TextField maxPlayersField;

    @FXML
    private TextField minPlayersField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nameInput;

    @FXML
    private RadioButton nameSort;

    @FXML
    private TextField priceField;

    @FXML
    private Button purchaseButton;

    @FXML
    private TextField recAge;

    @FXML
    private ListView<Toy> recListView;

    @FXML
    private TextField recPrice;

    @FXML
    private TextField recType;

    @FXML
    private RadioButton remNameSort;

    @FXML
    private RadioButton remSerialNumSort;

    @FXML
    private RadioButton remTypeSort;

    @FXML
    private Button removeButton;

    @FXML
    private ListView<Toy> removeListView;

    @FXML
    private TextField removeSearchBox;

    @FXML
    private Button removeSearchButton;

//    @FXML
//    private Label removeYouSure;

    @FXML
    private ListView<Toy> resultsListView;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField serialNumInput;

    @FXML
    private RadioButton serialNumSort;

    @FXML
    private TextField serialNumberField;

    @FXML
    private TextField sizeField;

    @FXML
    private ToggleGroup sortButtons;

    @FXML
    private ToggleGroup sortButtonsRemove;

    @FXML
    private TextField typeField;

    @FXML
    private TextField typeInput;

    @FXML
    private RadioButton typeSort;



    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override    
    public void initialize(URL location, ResourceBundle resources) {
    	Coordinator.loadToysFromFile();
    	resultsListView.setItems(toys); 
        purchaseButton.setDisable(true);
        resultsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            purchaseButton.setDisable(newSelection == null);
        });
        Coordinator.sortByNum(resultsListView);
        Coordinator.sortByNum(removeListView);  
        
        categoryComboBox.getItems().addAll("Animal", "Board Game", "Figure", "Puzzle");
    }

    /**
     * Clears the input fields for serial number, name, and type.
     * 
     * @param event the action event triggered by the clear button
     */
    @FXML
    void clearButtonPressed(javafx.event.ActionEvent event) {
        serialNumInput.clear();
        nameInput.clear();
        typeInput.clear();
    }

    /**
     * Event handler for the search button.
     * Retrieves inputs from user interface elements and performs a search using the Coordinator class.
     * 
     * @param event The action event triggered by the search button.
     */
    @FXML
    void searchButtonPressed(javafx.event.ActionEvent event) {
        // Retrieve inputs from user interface elements
        String serialNum = serialNumInput.getText().trim();
        String name = nameInput.getText().trim();
        String type = typeInput.getText().trim();

       Coordinator.search(serialNum, name, type, resultsListView);
    }

    /**
     * Event handler for the purchase button.
     * 
     * @param event The action event triggered by the button press.
     */
    @FXML
    void purchaseButtonPressed(javafx.event.ActionEvent event) {
        Toy selectedToy = resultsListView.getSelectionModel().getSelectedItem();
        if (selectedToy != null) {
            Coordinator.purchaseToyGUI(selectedToy);
            
            resultsListView.refresh();
        }
    }

/**
 * This method is called when the add button is clicked.
 * It retrieves the values from the input fields, validates them, and creates a new toy object based on the selected category.
 * The new toy object is then added to the Coordinator class.
 * If any validation errors occur or an exception is thrown, an error message is displayed.
 * After adding the toy, the input fields are cleared.
 *
 * @param event The action event triggered by clicking the add button.
 */

@FXML
private Label messageLabel;

@FXML
void addButtonPressed(ActionEvent event) throws NegativePrice, MinimumOverMax {
    //universal attributes
	String category = "";
    String serialNumber = "";
    String name = "";
    String brand = "";
    float price = 0;
    int availableCount = 0;
    int minAge = 0;
    
    //attributes for toy types
    String material = "";
    String size = "";
    String classification = "";
    int minPlayers = 0;
    int maxPlayers = 0;
    String designers = "";
    String puzzleType = "";
	try {
        // Get the selected type from the ComboBox
        category = categoryComboBox.getValue();

        // Universal attributes
        serialNumber = serialNumberField.getText();
        name = nameField.getText();
        brand = brandField.getText();
        price = Float.parseFloat(priceField.getText());
        availableCount = Integer.parseInt(availableCountField.getText());
        minAge = Integer.parseInt(ageAppropriateField.getText());

        // Attributes for  toy types
        material = materialField.getText();
        size = sizeField.getText();
        classification = classificationField.getText();
        minPlayers = Integer.parseInt(minPlayersField.getText());
        maxPlayers = Integer.parseInt(maxPlayersField.getText());
        designers = designersField.getText();
        puzzleType = typeField.getText();

        

    } catch (Exception e) {

    }
	
	Coordinator.addToy(category, serialNumber, name, brand, price, availableCount, minAge, classification, material, size, puzzleType, minPlayers, maxPlayers, designers);
}


/**
    * Handles the event when the save button is pressed.
    * Calls the `saveToysToFile` method in the `Coordinator` class to save toys to a file.
    *
    * @param event the action event triggered by the save button
    */
   @FXML
   void saveButtonPressed(ActionEvent event) {
	   Coordinator.saveToysToFile();
   }

/**
 * Event handler for the removeButton.
 * Removes a selected toy from the removeListView.
 * If a toy is not selected, it tries to remove a toy based on the provided id from the removeSearchBox.
 * Calls the Coordinator's removeToy method to remove the toy from the system.
 * 
 * @param event The action event triggered by clicking the removeButton.
 */
   @FXML
	void removeButton(ActionEvent event) {
	   Toy toRemove = null;
	   if(toRemove == null) {
		   toRemove = removeListView.getSelectionModel().getSelectedItem();
	   }
	   String id = "";
	   try {
   		id = removeSearchBox.getText();
   		} catch (Exception e) {
   		
   		}
	   Coordinator.removeToy(toRemove.getSerialNumber(), id, removeListView);
	}
    /**
     * Event handler for the removeSearchButton.
     * Removes a search based on the entered ID from the removeListView.
     * If sorting options are selected, sorts the removeListView accordingly.
     * @param event The action event triggered by clicking the removeSearchButton.
     */
    @FXML
    void removeSearchButton(ActionEvent event) {
    	String id = "";
    	try {
    		id = removeSearchBox.getText();
    	} catch (Exception e) {
    		
    	}
    	Coordinator.removeSearch(id, removeListView);
    	//I know this is sloppy, but I can't figure out how to make this work and the deadline is coming up
    	List<Toy> removeListItems = removeListView.getItems();
    	if(remSerialNumSort.isSelected()) {
    		Coordinator.sortByNum(removeListView, removeListItems);    		
    	}
    	if(remNameSort.isSelected()) {
    		Coordinator.sortByName(removeListView, removeListItems);
    	}
    	if(remTypeSort.isSelected()) {
    		Coordinator.sortByBrand(removeListView, removeListItems);
    	}
    	//sloppy code ends
    }

    /**
     * Handles the event when the sort button is pressed.
     * Sorts the resultsListView based on the selected sorting criteria.
     *
     * @param event The action event triggered by the sort button.
     */
    @FXML
    void sortButtonPressed(ActionEvent event) {
    	if(serialNumSort.isSelected()) {
    		Coordinator.sortByNum(resultsListView);    		
    	}
    	if(nameSort.isSelected()) {
    		Coordinator.sortByName(resultsListView);
    	}
    	if(typeSort.isSelected()) {
    		Coordinator.sortByBrand(resultsListView);
    	}
    }

    /**
     * Handles the event when the remSortButton is pressed.
     * Sorts the items in the removeListView based on the selected sorting criteria.
     * If remSerialNumSort is selected, sorts the items by serial number.
     * If remNameSort is selected, sorts the items by name.
     * If remTypeSort is selected, sorts the items by brand.
     *
     * @param event the action event triggered by pressing the remSortButton
     */
    @FXML
    void remSortButtonPressed(ActionEvent event) {
    	List<Toy> removeListItems = removeListView.getItems();
    	if(remSerialNumSort.isSelected()) {
    		Coordinator.sortByNum(removeListView, removeListItems);    		
    	}
    	if(remNameSort.isSelected()) {
    		Coordinator.sortByName(removeListView, removeListItems);
    	}
    	if(remTypeSort.isSelected()) {
    		Coordinator.sortByBrand(removeListView, removeListItems);
    	}
    }
    
    /**
     * Performs a recommendation search based on the provided age, type, and maximum price.
     * Updates the recommendation list view with the results.
     *
     * @param event The action event that triggered the search.
     */
    @FXML
    void recSearch(ActionEvent event) {
    	int age = -1;
    	String type = "";
    	double maxPrice = 0;
    	try {
    		age = Integer.parseInt(recAge.getText());
    	} catch (Exception e) {
    		
    	}
    	try {
    		type = recType.getText();
    	} catch (Exception e) {
    		
    	}
    	try {
    		maxPrice = Double.parseDouble(recPrice.getText());
    	} catch (Exception e) {
    	}

    	Coordinator.giftSuggestion(age,type,maxPrice,recListView);
    }
}
