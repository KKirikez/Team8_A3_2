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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import controller.Coordinator;

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
    private Button clearButton;

    @FXML
    private ListView<Toy> listView;

    @FXML
    private TextField nameInput;

    @FXML
    private Button removeButton;

    @FXML
    private ListView<Toy> removeListView; 

    @FXML
    private TextField removeSearchBox;

    @FXML
    private Button removeSearchButton;

    @FXML
    private javafx.scene.control.Label removeYouSure;

    @FXML
    private ListView<Toy> resultsListView;

    @FXML
    private Button searchButton;

    @FXML
    private TextField serialNumInput;
    
    @FXML
    private TextField typeInput;
    
    @FXML
    private Button purchaseButton; 
    
    @FXML
    private RadioButton nameSort;
    
    @FXML
    private RadioButton serialNumSort;
    
    @FXML
    private RadioButton typeSort;
    
    @FXML
    private ToggleGroup sortButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Coordinator.loadToysFromFile();
    	resultsListView.setItems(toys); 
        purchaseButton.setDisable(true);
        resultsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            purchaseButton.setDisable(newSelection == null);
        });
        Coordinator.sortByNum(resultsListView);
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
        Coordinator.drawHomeList(resultsListView);
    }

    @FXML
    void searchButtonPressed(javafx.event.ActionEvent event) {
        String serialNum = serialNumInput.getText();
    	String name = nameInput.getText().trim();
    	String type = typeInput.getText();
        
        if (searchTerm.isEmpty()) {
            resultsListView.setItems(toys);
        } else {
            ObservableList<Toy> filteredToys = FXCollections.observableArrayList();
            for (Toy toy : toys) {
                if (toy.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredToys.add(toy);
                }
            }
            resultsListView.setItems(filteredToys);
        }
    }

    @FXML
    void purchaseButtonPressed(javafx.event.ActionEvent event) {
        Toy selectedToy = listView.getSelectionModel().getSelectedItem();
        if (selectedToy != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Do you want to purchase " + selectedToy.getName() + "?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selectedToy.setAvailableCount(selectedToy.getAvailableCount() - 1);
                    if (selectedToy.getAvailableCount() <= 0) {
                        toys.remove(selectedToy);
                    }
                    Alert infoAlert = new Alert(AlertType.INFORMATION, "The Transaction Successfully Terminated!");
                    infoAlert.showAndWait();
                    listView.refresh(); 
                }
            });
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
    String serialNumber = removeSerialNumberField.getText().trim();
    if (!serialNumber.isEmpty()) {
        boolean removed = Coordinator.removeToy(serialNumber); 
        if (removed) {
            showAlert("Success", "Toy removed successfully.");
            removeSerialNumberField.clear();

        } else {
            showAlert("Error", "Toy with serial number " + serialNumber + " not found.");
        }
    } else {
        showAlert("Error", "Please enter a serial number.");
    }
}
    @FXML
    void removeSearchButton(ActionEvent event) {
    	
    }

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
}
