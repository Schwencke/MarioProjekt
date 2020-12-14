package ui;

import domain.Pizza;
import persistence.Database;
import persistence.DbMenuCardMapper;

import java.util.List;

public class MainMenu {

    private final String USER = "testdb_user";
    private final String PASSWORD = "1234";
    private final String URL = "jdbc:mysql://localhost:3306/mario?serverTimezone=CET&useSSL=false";

    private Database database = new Database(USER, PASSWORD, URL);
    private DbMenuCardMapper dbMenuCardMapper = new DbMenuCardMapper(database);

    public void mainMenuLoop() {

        boolean running = true;

        while (running) {
            showMenu();
            switch(Input.getInt("Vælg 1-6: ")){
                case 1: showMenuCard(); break;
                case 2: showSinglePizza(); break;
                case 3: deletePizza(); break;
                case 4: insertPizza(); break;
                case 5: updatePizza(); break;
                case 6: running = false; break;
            }
        }
        System.out.println("Tak for denne gang!");
    }

    private void showMenu() {
        System.out.println("**** Marios pizzabar - hovedmenu ******");
        System.out.println("[1] Vis menukort [2] Vis enkelt pizza [3] Fjern pizza [4] Opret ny pizza [5] Opdater pizza [6] Afslut");
    }

    private void updatePizza() {
        System.out.println("***** Opdater pizza *******");
        int pizzaNo = Input.getInt("Indtast pizza nummer på den du vil rette: ");
        System.out.println("Indtast ny værdi, hvis den skal rettes - eller blot <retur>: ");
        Pizza pizza = dbMenuCardMapper.getPizzaById(pizzaNo);
        String newPizzaNoInput = Input.getString("Pizzanummer: (" + pizza.getPizzaNo() + "): " );
        if (newPizzaNoInput.length() > 0){
            pizza.setPizzaNo(Integer.parseInt(newPizzaNoInput));
        }
        String newPizzaNameInput = Input.getString("Pizza navn: (" + pizza.getName() + "): ");
        if (newPizzaNameInput.length() > 0){
            pizza.setName(newPizzaNameInput);
        }
        String newPizzaIngredientsInput = Input.getString("Pizza ingredienser: (" + pizza.getIngredients() + "): ");
        if (newPizzaIngredientsInput.length() > 0){
            pizza.setIngredients(newPizzaIngredientsInput);
        }
        String newPizzaPriceInput = Input.getString("Pizza pris: (" + pizza.getPrice() + "): ");
        if (newPizzaPriceInput.length() > 0){
            pizza.setPrice(Integer.parseInt(newPizzaPriceInput));
        }
        boolean result = dbMenuCardMapper.updatePizza(pizza);
        if (result){
            System.out.println("Pizzaen med nr = " + pizzaNo + " er nu opdateret");
        } else {
            System.out.println("Vi kunne desværre ikke opdatere den nye pizza.");
        }
    }

    private void insertPizza() {
        System.out.println("**** Opret ny pizza *******");
        int pizzaNo = Input.getInt("Indtast pizza nummer: ");
        String name = Input.getString("Indtast navn på pizza: ");
        String ingredients = Input.getString("Indtast indhold: ");
        int price = Input.getInt("Indtast pris: ");
        Pizza newPizza = new Pizza(pizzaNo, name, ingredients, price);
        Pizza insertedPizza = dbMenuCardMapper.insertPizza(newPizza);
        if (insertedPizza != null){
            System.out.println("Pizzaen med nr = " + pizzaNo + " er nu tilføjet");
            System.out.println("Pizzaen har fået DB id = " + insertedPizza.getPizzaId());
        } else {
            System.out.println("Vi kunne desværre ikke oprette den nye pizza. PizzaNo findes allerede.");
        }


    }

    private void deletePizza() {
        int pizzaNo = Input.getInt("Indtast nummer på pizza som skal fjernes: ");
        boolean result = dbMenuCardMapper.deletePizza(pizzaNo);
        if (result){
            System.out.println("Pizzaen med nr = " + pizzaNo + " er nu fjernet");
        } else {
            System.out.println("Pizzaen med nr = " + pizzaNo + " findes ikke, og kan derfor ikke fjernes");
        }

    }

    private void showSinglePizza() {
        int pizzaNo = Input.getInt("Indtast pizzanummer: ");
        Pizza pizza = dbMenuCardMapper.getPizzaById(pizzaNo);
        if (pizza != null){
            System.out.println("Du har fundet pizza nummer: " + pizzaNo);
            System.out.println(pizza.toString());
        } else {
            System.out.println("Pizza med nr = " + pizzaNo + " findes desværre ikke");
        }
    }

    private void showMenuCard() {
        System.out.println("**** Menukort hos Marios ******");
        List<Pizza> menuCard = dbMenuCardMapper.getAllPizzas();
        for (Pizza pizza : menuCard) {
            System.out.println(pizza.toString());
        }
    }

}
