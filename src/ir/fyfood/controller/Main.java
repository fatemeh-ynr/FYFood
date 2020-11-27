package ir.fyfood.controller;

import ir.fyfood.configuration.SpringContext;
import ir.fyfood.service.RestaurantService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new AnnotationConfigApplicationContext(SpringContext.class);

        databaseSetup();
        showInputMenu();
    }

    private static void databaseSetup() {
        RestaurantService restaurantService = applicationContext.getBean(RestaurantService.class);
        restaurantService.initializeDBFromFile("restaurant.txt");
    }

    private static void showInputMenu() {
        System.out.println("\nDo you want to log in as a \"customer\" or a \"manager\"?");
        System.out.println("1.Manager");
        System.out.println("2.Customer");
        int option = ScannerReaders.readInt("Select one option: ", 1, 2);
        switch (option) {
            case 1:
                ManagerConsole managerConsole = applicationContext.getBean(ManagerConsole.class);
                managerConsole.StartInteractingWithManager();
                break;
            case 2:
                UserConsole userConsole = applicationContext.getBean(UserConsole.class);
                userConsole.StartInteractingWithUser();
                break;
        }
    }
}
