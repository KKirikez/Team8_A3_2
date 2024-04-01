package controller;

import java.util.Scanner;

import exceptions.MinimumOverMax;
import exceptions.NegativePrice;
import javafx.scene.control.ListView;
import view.ToyStoreMenu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.*;
/**
 * The `Coordinator` class is responsible for coordinating the different functionalities of the toy store application.
 * It handles the main menu, loading toys from a file, saving toys to a file, searching for toys, adding new toys to the inventory,
 * and purchasing toys.
 */
public class Coordinator {
    private static final String FILE_PATH = "res/toys.txt";
    private static List<Toy> toys = new ArrayList<>();
/**
     * Displays the main menu and handles user input to navigate different functionalities of the toy store application.
     */
    public static void mainMenu() {
       
    }
/**
     * Loads toys from a file into the application's memory.
     */
    public static void loadToysFromFile() {
        try {
            File file = new File(FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String toyString = scanner.nextLine();
                String type = getType(toyString);
                String[] toyArray = toyString.split(";");
                Toy toy = null;
                switch (type) {
                    case "Figure":
                        //0113513686;Toy soldier;Gamen;14.06;2;5;A
                        //String serialNumber, String name, String brand, float price, int availableCount, int ageAppropriate, String classification
                        toy = new Figures(toyArray[0], toyArray[1], toyArray[2], Float.parseFloat(toyArray[3]), Integer.parseInt(toyArray[4]),
                                Integer.parseInt(toyArray[5]), toyArray[6]);
                        break;

                    case "Animal":
                        //3015547049;Dove;Game Zombie;24.55;2;9;Wooden;S
                        //String serialNumber, String name, String brand, float price, int availableCount, int ageAppropriate, String material, String size
                        toy = new Animals(toyArray[0], toyArray[1], toyArray[2], Float.parseFloat(toyArray[3]), Integer.parseInt(toyArray[4]),
                                Integer.parseInt(toyArray[5]), toyArray[6], toyArray[7]);
                        break;

                    case "Puzzle":
                        //5239019250;Nob Yoshigahara Puzzle Design Competition;Game Zombie;67.20;5;3;M
                        //String serialNumber, String name, String brand, float price, int availableCount, String puzzleType
                        toy = new Puzzles(toyArray[0], toyArray[1], toyArray[2], Float.parseFloat(toyArray[3]), Integer.parseInt(toyArray[4]), Integer.parseInt(toyArray[5]),
                                toyArray[6]);
                        break;

                    case "Board Game":
                        //7734720369 ; Doom: The Boardgame ; Gamefluent ; 174.24 ; 10 ; 7 ; 2-5 ; Miller Knights
                        //String serialNumber, String name, String brand, float price, int availableCount, int minPlayers, int maxPlayers, String designers
                        toy = new Boardgames(toyArray[0], toyArray[1], toyArray[2], Float.parseFloat(toyArray[3]), Integer.parseInt(toyArray[4]), Integer.parseInt(toyArray[5]),
                                Integer.parseInt(toyArray[6].substring(0, 1)), Integer.parseInt(toyArray[6].substring(2, 3)), toyArray[7]);
                        break;
                }

                toys.add(toy);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Failed to load toys from file: " + e.getMessage());
        }
    }
     /**
     * Saves the current list of toys to a file.
     */
    public static void saveToysToFile() {
        try {
            File file = new File(FILE_PATH);
            FileWriter writer = new FileWriter(file);
            for (Toy toy : toys) {
                writer.write(toy.toDatabase() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save toys to file: " + e.getMessage());
        }
    }
    /**
     * Allows the user to search for toys based on various criteria.
     */
    public static void searchToys() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        
        String target = null;
        List<Toy> matchingToys = new ArrayList<>();
        switch (choice) {
            case 1:
                System.out.println("Enter the serial number: ");
                target = scanner.nextLine();
                matchingToys = compareToys(target, "Serial");
                break;
            case 2:
                System.out.println("Enter the name of the toy: ");
                target = scanner.nextLine().toLowerCase();
                matchingToys = compareToys(target, "Name");
                break;
            case 3:
                System.out.println("Enter the type of toy: ");
                target = scanner.nextLine();
                matchingToys = compareToys(target, "Type");
                break;
            case 4:
                mainMenu();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.\n");
                    searchToys();
                    scanner.close();
                    return; 
        }
    
        if (!matchingToys.isEmpty()) {
            System.out.println("Matching toys:");
            for (int i = 0; i < matchingToys.size(); i++) {
                System.out.println((i + 1) + ". " + matchingToys.get(i));
            }

            System.out.println((matchingToys.size() + 1) + ". Return to search menu");
            
            System.out.println("Select the number of the toy to purchase, 0 to cancel, or " + (matchingToys.size() + 1) + " to return to search menu:");
            int toyChoice = scanner.nextInt();
            scanner.nextLine(); 
    
            if (toyChoice > 0 && toyChoice <= matchingToys.size()) {
                purchaseToy(matchingToys.get(toyChoice - 1), scanner);
            } else if (toyChoice == matchingToys.size() + 1) {
                searchToys();
            } else {
                System.out.println("Purchase cancelled.");
            }
        } else {
            System.out.println("No matching toys found.");
            searchToys();
        }
    }
     /**
     * allows the purchase of a toy.
     * @param toyToPurchase The toy to be purchased.
     * @param scanner       The scanner object for user input.
     */
    private static void purchaseToy(Toy toyToPurchase, Scanner scanner) {
        // Toy object is directly passed to this method, so no need to search it again
        if (toyToPurchase != null) {
            System.out.println("You have selected: " + toyToPurchase.toString());
            System.out.println("Do you want to purchase this toy? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
    
            if ("yes".equals(confirmation)) {
                System.out.println("The Transaction Successfully Terminated!");
                toyToPurchase.setAvailableCount(toyToPurchase.getAvailableCount() - 1);
                // Remove the toy from the list if it's no longer available
                if (toyToPurchase.getAvailableCount() <= 0) {
                    toys.remove(toyToPurchase);
                }
                System.out.println("Press Enter to continue...");
                scanner.nextLine(); 
                searchToys();
            }
        } else {
            System.out.println("Error: Toy not found.");
        }
    }
    
    public static void purchaseToyGUI(Toy toyToPurchase) {
        if (toyToPurchase == null) {
            System.err.println("The toy to purchase is null. Operation aborted.");
            return;
        }

        int newAvailableCount = toyToPurchase.getAvailableCount() - 1;
        toyToPurchase.setAvailableCount(newAvailableCount);

        // Remove the toy from the list if it's no longer available
        if (newAvailableCount <= 0) {
        }
    }

    
 /**
     * Allows the user to add a new toy to the inventory.
     */
public static void addToy(String serialNumber, String name, String brand, float price, int availableCount, int ageAppropriate, String classification, String material, String size, String puzzleType, int minPlayers, int maxPlayers, String designers) throws NegativePrice, MinimumOverMax {
    
    if (serialNumber.length() != 10) {
        throw new IllegalArgumentException("Invalid serial number. It must be 10 digits long.");
    }
   
    if (!isAllDigits(serialNumber)) {
        throw new IllegalArgumentException("Invalid serial number. It must contain only numbers.");
    }
    
    for (Toy toy : toys) {
        if (toy.getSerialNumber().equals(serialNumber)) {
            throw new IllegalArgumentException("Serial number already exists. Please enter a unique serial number.");
        }
    }

  
    String type = getType(serialNumber);
    Toy toy;
    switch (type) {
        case "Figure":
            toy = new Figures(serialNumber, name, brand, price, availableCount, ageAppropriate, classification);
            break;
        case "Animal":
            toy = new Animals(serialNumber, name, brand, price, availableCount, ageAppropriate, material, size);
            break;
        case "Puzzle":
            toy = new Puzzles(serialNumber, name, brand, price, availableCount, ageAppropriate, puzzleType);
            break;
        case "Board Game":
            if (minPlayers > maxPlayers) {
                throw new MinimumOverMax();
            }
            toy = new Boardgames(serialNumber, name, brand, price, availableCount, ageAppropriate, minPlayers, maxPlayers, designers);
            break;
        default:
            throw new IllegalArgumentException("Invalid toy type based on serial number.");
    }

   
    toys.add(toy);
}

/**
     * Checks if a string contains only digits.
     * @param str The string to be checked.
     * @return true if the string contains only digits, false otherwise.
     */
private static boolean isAllDigits(String str) {
    for (char c : str.toCharArray()) {
        if (!Character.isDigit(c)) {
            return false;
        }
    }
    return true;
}

	/**
     * Allows the user to remove a toy from the inventory.
     */
public static void removeToy(String serialNumber, String id, ListView<Toy> removeListView) {
    Toy toyToRemove = null;
    for (Toy toy : toys) {
        if (toy.getSerialNumber().equalsIgnoreCase(serialNumber)) {
            toyToRemove = toy;
            break;
        }
    }
    if (toyToRemove != null) {
        System.out.println(serialNumber);
    	toys.remove(toyToRemove);
    }
    removeSearch(id, removeListView);
}

    private static boolean compare(Toy toy, String target, String parameterType) {
        switch (parameterType) {
            case "Serial":
                return toy.getSerialNumber().equals(target);
            case "Name":
                return toy.getName().equalsIgnoreCase(target);
            case "Type":
                return toy.getType().equalsIgnoreCase(target);
            default:
                return false;
        }
    }
    /**
     * Suggests toys as a gift based on user input criteria.
     */
    public static void giftSuggestion(int age, String type, double maxPrice,ListView<Toy> recListView) {
    	 List<Toy> acceptableToys = new ArrayList<>();
    	 for (Toy toy : toys) {
    		 if(!(age == -1)) {
    			if(age < toy.getAgeAppropriate()) {
    				continue;
    			}
    		}
    		if(!(type.equals("")) && (type.equals("Puzzle") || type.equals("Board Game") || type.equals("Animal") || (type.equals("Figure")))) {
    			if(!(toy.getType().equals(type))) {
    				continue;
    			}
    		}
    		if(!(maxPrice == 0)) {
    			if(Float.parseFloat(toy.getPrice()) >= maxPrice) {
    				continue;
    			}
    		}
    		acceptableToys.add(toy);
    	}
    	ToyStoreMenu.drawRecommendList(acceptableToys, recListView);
    }
     /**
     * Compares toys based on a given parameter.
     * @param target        The target value to compare against.
     * @param parameterType The type of parameter to compare (e.g., "Serial", "Name", "Type").
     * @return A list of matching toys.
     */
    public static List<Toy> compareToys(String target, String parameterType) {
        List<Toy> matchingToys = new ArrayList<>();
        String targetString = target.toLowerCase();
        
        for (Toy toy : toys) { 
            switch (parameterType) {
                case "Serial":
                    if (toy.getSerialNumber().equalsIgnoreCase(target)) {
                        matchingToys.add(toy);
                    }
                    break;
                case "Name":
                    if (toy.getName().toLowerCase().contains(targetString)) {
                        matchingToys.add(toy);
                    }
                    break;
                case "Type":
                    if (toy.getType().equalsIgnoreCase(target)) {
                        matchingToys.add(toy);
                    }
                    break;
            }
        }
        return matchingToys;
    }
    public static void search(String serialNum, String name, String type, ListView<Toy> resultsListView) {
    	List<Toy> matches = new ArrayList<>();
    	 for (Toy toy : toys) { 
    		 boolean fail = false;
    		 boolean added = false;
    		 //kinda dirty but it works
    		 if(!serialNum.equals("")){
    			 if(toy.getSerialNumber().contains(serialNum)) {
    				 matches.add(toy);
    				 added = true;
    			 } else {
    				 fail = true;
    			 }
    		 }
    		 if(!name.equals("")){
    			 if(toy.getName().contains(name)) {
    				 matches.add(toy);
    				 added = true;
    			 } else {
    				 fail = true;
    			 }
    		 }
			if(!type.equals("")){
				if(toy.getType().contains(type)) {
					matches.add(toy);
					added = true;
				}else {
   				 fail = true;
   			 }
			}
			if(added && fail) {
				matches.remove(toy);
			}
    	 }
    	 ToyStoreMenu.drawHomeList(matches, resultsListView);
    }
    /**
     * Determines the type of toy based on the first digit of its serial number.
     * 
     * @param input The serial number of the toy.
     * @return The type of toy (e.g., "Figure", "Animal", "Puzzle", "Board Game").
     */
    
    private static String getType(String input) {
    	char firstChar = input.charAt(0);

    	switch (firstChar) {
    	    case '0':
    	    case '1':
    	        return "Figure";
    	    case '2':
    	    case '3':
    	        return "Animal";
    	    case '4':
    	    case '5':
    	    case '6':
    	        return "Puzzle";
    	    case '7':
    	    case '8':
    	    case '9':
    	        return "Board Game";
    	    default:
    	        return "Error";
    	}
    }
    
    public static void sortByNum(ListView<Toy> resultsListView) {
    	List<Toy> numSorted = new ArrayList<>();
    	numSorted.addAll(toys);
    	Collections.sort(numSorted, Comparator.comparing(Toy::getSerialNumber));
    	ToyStoreMenu.drawHomeList(numSorted, resultsListView);
    }
    
    public static void sortByBrand(ListView<Toy> resultsListView) {
    	List<Toy> brandSorted = new ArrayList<>();
    	brandSorted.addAll(toys);
    	Collections.sort(brandSorted, Comparator.comparing(Toy::getBrand));
		ToyStoreMenu.drawHomeList(brandSorted, resultsListView);
    }
    
    public static void sortByName(ListView<Toy> resultsListView) {
    	List<Toy> nameSorted = new ArrayList<>();
    	nameSorted.addAll(toys);
    	Collections.sort(nameSorted, Comparator.comparing(Toy::getName));
    	ToyStoreMenu.drawHomeList(nameSorted, resultsListView);
    }
    
    public static void sortByNum(ListView<Toy> resultsListView, List<Toy> toSort) {
    	List<Toy> numSorted = new ArrayList<>();
    	numSorted.addAll(toSort);
    	Collections.sort(numSorted, Comparator.comparing(Toy::getSerialNumber));
    	ToyStoreMenu.drawHomeList(numSorted, resultsListView);
    }
    
    public static void sortByBrand(ListView<Toy> resultsListView, List<Toy> toSort) {
    	List<Toy> brandSorted = new ArrayList<>();
    	brandSorted.addAll(toSort);
    	Collections.sort(brandSorted, Comparator.comparing(Toy::getBrand));
		ToyStoreMenu.drawHomeList(brandSorted, resultsListView);
    }
    
    public static void sortByName(ListView<Toy> resultsListView, List<Toy> toSort) {
    	List<Toy> nameSorted = new ArrayList<>();
    	nameSorted.addAll(toSort);
    	Collections.sort(nameSorted, Comparator.comparing(Toy::getName));
    	ToyStoreMenu.drawHomeList(nameSorted, resultsListView);
    }
    
    public static void removeSearch(String id,ListView<Toy> resultsListView) {
    	List<Toy> mightRemove = new ArrayList<>();
    	for (Toy toy : toys) { 
    		if(toy.getSerialNumber().contains(id)) {
    			 mightRemove.add(toy);
    		 }
    	 }
    	ToyStoreMenu.drawRemoveList(mightRemove, resultsListView);
    }
}
