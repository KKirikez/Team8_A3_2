package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.Toy;

/**
 * The ToyStoreMenu class represents the menu options for a toy store company.
 * It provides methods to draw the main menu, search menu, and print a list of acceptable toys.
 */
public class ToyStoreMenu {
	public static void drawHomeList(List<Toy> toDraw, ListView<String> resultsListView) {
	    ObservableList<String> items = FXCollections.observableArrayList();
	    for (Toy toy : toDraw) {
	        items.add(toy.toString());
	    }
	    resultsListView.setItems(items);
	}
	public static void drawRemoveList(List<Toy> toDraw, ListView<String> removeListView) {
		ObservableList<String> items = FXCollections.observableArrayList();
	    for (Toy toy : toDraw) {
	        items.add(toy.toString());
	    }
	    removeListView.setItems(items);
	}
	public static void drawRecommendList(List<Toy> toDraw) {
		
	}
}
