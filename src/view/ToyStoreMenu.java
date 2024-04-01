package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Toy;

/**
 * The ToyStoreMenu class represents the menu options for a toy store company.
 * It provides methods to draw the main menu, search menu, and print a list of acceptable toys.
 */
/**
 * The ToyStoreMenu class provides methods for drawing different lists of toys on a JavaFX ListView.
 */
public class ToyStoreMenu {
	
	/**
	 * Draws a list of toys on a ListView for the home screen.
	 * 
	 * @param toDraw The list of toys to be drawn.
	 * @param resultsListView The ListView where the toys will be displayed.
	 */
	public static void drawHomeList(List<Toy> toDraw, ListView<Toy> resultsListView) {
		ObservableList<Toy> items = FXCollections.observableArrayList(toDraw);
		resultsListView.setItems(items);
	}

	/**
	 * Draws a list of toys on a ListView for the remove screen.
	 * 
	 * @param toDraw The list of toys to be drawn.
	 * @param removeListView The ListView where the toys will be displayed.
	 */
	public static void drawRemoveList(List<Toy> toDraw, ListView<Toy> removeListView) {
		ObservableList<Toy> items = FXCollections.observableArrayList(toDraw);
		removeListView.setItems(items);
	}

	/**
	 * Draws a list of toys on a ListView for the recommend screen.
	 * 
	 * @param toDraw The list of toys to be drawn.
	 * @param recommendListView The ListView where the toys will be displayed.
	 */
	public static void drawRecommendList(List<Toy> toDraw, ListView<Toy> recommendListView) {
		ObservableList<Toy> items = FXCollections.observableArrayList(toDraw);
		recommendListView.setItems(items);
	}
	
	/**
	 * Draws a list of toys on a ListView for the search screen.
	 * 
	 * @param toDraw The list of toys to be drawn.
	 * @param searchListView The ListView where the toys will be displayed.
	 */
	public static void drawSearchList(List<Toy> toDraw, ListView<Toy> searchListView) {
		ObservableList<Toy> items = FXCollections.observableArrayList(toDraw);
		searchListView.setItems(items);
	}
}

