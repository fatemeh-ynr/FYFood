package controller;

import service.RestaurantService;

public class Main {

    public static void main(String[] args) {
        databaseSetup();
        showInputMenu();
    }

    private static void databaseSetup() {
        RestaurantService restaurantService = new RestaurantService();
        restaurantService.initializeDBFromFile("restaurant.txt");
    }

    private static void showInputMenu() {
        System.out.println("\nDo you want to log in as a \"customer\" or a \"manager\"?");
        System.out.println("1.Manager");
        System.out.println("2.Customer");
        int option = ScannerReaders.readInt("Select one option: ", 1, 2);
        switch (option) {
            case 1:
                ManagerConsole.StartInteractingWithManager();
                break;
            case 2:
                UserConsole.StartInteractingWithUser();
                break;
        }
    }
}
