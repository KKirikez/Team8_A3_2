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
    private ComboBox<Category> categoryComboBox;

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
    }

    private Toy createToyFromString(String toyString) {
        String[] toyArray = toyString.split(";");
        if (toyArray.length < 2) return null;
        
        String type = toyArray[1];
        switch (type) {
            case "Figure":
                return new Figures(toyArray[2], toyArray[3], toyArray[4], Float.parseFloat(toyArray[5]), Integer.parseInt(toyArray[6]), Integer.parseInt(toyArray[7]), toyArray[8]);
            case "Animal":
                return new Animals(toyArray[2], toyArray[3], toyArray[4], Float.parseFloat(toyArray[5]), Integer.parseInt(toyArray[6]), Integer.parseInt(toyArray[7]), toyArray[8], toyArray[9]);
            case "Puzzle":
                return new Puzzles(toyArray[2], toyArray[3], toyArray[4], Float.parseFloat(toyArray[5]), Integer.parseInt(toyArray[6]), Integer.parseInt(toyArray[7]), toyArray[8]);
            case "Board Game":
                return new Boardgames(toyArray[2], toyArray[3], toyArray[4], Float.parseFloat(toyArray[5]), Integer.parseInt(toyArray[6]), Integer.parseInt(toyArray[7]), Integer.parseInt(toyArray[8]), Integer.parseInt(toyArray[9]), toyArray[10]);
            default:
                return null; 
        }
    }

    @FXML
    void clearButtonPressed(javafx.event.ActionEvent event) {
        serialNumInput.clear();
        nameInput.clear();
        typeInput.clear();
      //sloppy again, but it works!
    	if(serialNumSort.isSelected()) {
    		Coordinator.sortByNum(resultsListView);    		
    	}
    	if(nameSort.isSelected()) {
    		Coordinator.sortByName(resultsListView);
    	}
    	if(typeSort.isSelected()) {
    		Coordinator.sortByBrand(resultsListView);
    	}
    	//sloppy code ends
    }

    @FXML
    void searchButtonPressed(javafx.event.ActionEvent event) {
        // Retrieve inputs from user interface elements
        String serialNum = serialNumInput.getText().trim();
        String name = nameInput.getText().trim();
        String type = typeInput.getText().trim();
        if(!(serialNum.equals("") && name.equals("") && type.equals(""))) {
        	Coordinator.search(serialNum, name, type, resultsListView);
        } else {
        	//sloppy again, but it works!
        	if(remSerialNumSort.isSelected()) {
        		Coordinator.sortByNum(resultsListView);    		
        	}
        	if(remNameSort.isSelected()) {
        		Coordinator.sortByName(resultsListView);
        	}
        	if(remTypeSort.isSelected()) {
        		Coordinator.sortByBrand(resultsListView);
        	}
        	//sloppy code ends
        }
    }

    @FXML
    void purchaseButtonPressed(javafx.event.ActionEvent event) {
        Toy selectedToy = resultsListView.getSelectionModel().getSelectedItem();
        if (selectedToy != null) {
            Coordinator.purchaseToyGUI(selectedToy);
            
            resultsListView.refresh();
        }
    }

   @FXML 
 Void addButton(ActionEvent event) {
    try {
        String category = categoryComboBox.getValue();
        String serialNumber = serialNumberField.getText().trim();
        String name = nameField.getText().trim();
        String brand = brandField.getText().trim();
        float price = Float.parseFloat(priceField.getText().trim());
        int availableCount = Integer.parseInt(availableCountField.getText().trim());
        int ageAppropriate = Integer.parseInt(ageAppropriateField.getText().trim());

        if (price < 0) {
            throw new NegativePrice();
        }

        Toy newToy = null;
        switch (category) {
            case "Figure":
                String classification = classificationField.getText().trim();
                newToy = new Figures(serialNumber, name, brand, price, availableCount, ageAppropriate, classification);
                break;
            case "Animal":
                String material = materialField.getText().trim();
                String size = sizeField.getText().trim();
                newToy = new Animals(serialNumber, name, brand, price, availableCount, ageAppropriate, material, size);
                break;
            case "Puzzle":
                String type = typeField.getText().trim();
                newToy = new Puzzles(serialNumber, name, brand, price, availableCount, ageAppropriate, type);
                break;
            case "Board Game":
                int minPlayers = Integer.parseInt(minPlayersField.getText().trim());
                int maxPlayers = Integer.parseInt(maxPlayersField.getText().trim());
                if (minPlayers > maxPlayers) {
                    throw new MinimumOverMax();
                }
                String designers = designersField.getText().trim();
                newToy = new Boardgames(serialNumber, name, brand, price, availableCount, ageAppropriate, minPlayers, maxPlayers, designers);
                break;
            default:
                showAlert("Error", "Please select a valid category.");
                return;
        }

        Coordinator.addToy(newToy); 
        showAlert("Success", "New toy added successfully.");
        clearAddToyFields();
    } catch (NegativePrice | MinimumOverMax e) {
        showAlert("Error", e.getMessage());
    } catch (NumberFormatException e) {
        showAlert("Error", "Please enter valid numeric values for price, available count, etc.");
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}

   @FXML
   void saveButtonPressed(ActionEvent event) {
	   Coordinator.saveToysToFile();
   }

   
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

    @FXML
    void sortButtonPressed(ActionEvent event) {
    	System.out.println("PRESS");
    	List<Toy> resultsListItems = resultsListView.getItems();
    	if(serialNumSort.isSelected()) {
    		Coordinator.sortByNum(resultsListView, resultsListItems);    		
    	}
    	if(nameSort.isSelected()) {
    		Coordinator.sortByName(resultsListView, resultsListItems);
    	}
    	if(typeSort.isSelected()) {
    		Coordinator.sortByBrand(resultsListView, resultsListItems);
    	}
    }

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
