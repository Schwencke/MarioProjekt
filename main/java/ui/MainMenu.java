package ui;

import domain.Orders;
import domain.Pizza;
import persistence.Database;
import persistence.DbMenuCardMapper;
import persistence.DbOrderMapper;

import java.util.List;

public class MainMenu {

    private final String USER = "testdb_user";
    private final String PASSWORD = "1234";
    private final String URL = "jdbc:mysql://localhost:3306/mario?serverTimezone=CET&useSSL=false";

    private Database database = new Database(USER, PASSWORD, URL);
    private DbMenuCardMapper dbMenuCardMapper = new DbMenuCardMapper(database);
    private DbOrderMapper dbOrderMapper = new DbOrderMapper(database);

    public void mainMenuLoop() {

        boolean running = true;

        while (running) {
            showMenu();
            switch(Input.getInt("Vælg 1-9: ")){
                case 1: showMenuCard(); break;
                case 2: showSinglePizza(); break;
                case 3: deletePizza(); break;
                case 4: insertPizza(); break;
                case 5: updatePizza(); break;
                case 6: newOrder(); break;
                case 7: readOrder(); break;
                case 8: updateOrder(); break;
                case 9: running = false; break;
            }
        }
        System.out.println("Tak for denne gang!");
    }

    private void readOrder() {
        List<Orders> ordersList = dbOrderMapper.readOrders();
        for (Orders orders : ordersList) {
            System.out.println("---");
            System.out.println("ORDER ID :" + orders.getOrderID());
            System.out.println("PIZZA NR :" + orders.getPizzaNo());
            System.out.println("ANTAL :" + orders.getAmount());
            System.out.println("KUNDE NAVN :" + orders.getCustomerName());
            System.out.println("AFHENTNINGS TIDSPUNKT :" + Input.getMinutesToTimeFormat(orders.getPickupTime()));
            System.out.println("ORDRE TIDSPUNKT :" + orders.getOrderTime());
            System.out.println("TELEFON :" + orders.getPhoneNo());
            System.out.println("---");

        }
    }

    private boolean newOrder() {
    int pizzaNo = Input.getInt("hvilken pizza skal du ha?: ");
    int amount = Input.getInt("hvor mange ønsker du?: ");
    int pickUpTime = Input.getTimeInMinutes("Afhentningstidpunkt - hh.mm"); // hh.mm
     String name = Input.getString("Navn: ");
     String phone = Input.getString("Telefonnummer: ");
     Orders orders = new Orders(pizzaNo, amount, pickUpTime, name, phone);

     boolean result = dbOrderMapper.newOrder(orders);
     if (result) {
         System.out.println("Bestillingen gik godt");}
         else {
             System.out.println("Der gik noget galt med bestillingen");}
     return result;
    }



    private void showMenu() {
        System.out.println("**** Marios pizzabar - hovedmenu ******");
        System.out.println("[1]Vis menukort [2]Vis enkelt pizza [3]Fjern pizza [4]Opret ny pizza [5]Opdater Pizza [6]Opret Ordre [7]Vis Ordre [8]Opdater ordre [9]Afslut");
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
    private void updateOrder() {
        System.out.println("***** Opdater Ordre *******");
        int ordreId = Input.getInt("Hvilket ordre id vil du rette: ");
        System.out.println("Indtast ny værdi, hvis den skal rettes - eller blot <retur>: ");

        Orders orders = dbOrderMapper.getOrderById(ordreId);
        // SÅ HAR VI VORES ORDRE OBJEKT FYLDT MED INFO
        String newPizzaNoInput = Input.getString("Pizzanummer: (" + orders.getPizzaNo() + "): " );
        if (newPizzaNoInput.length() > 0){
            orders.setPizzaNo(Integer.parseInt(newPizzaNoInput));
        }
        String newOrdreAmount = Input.getString("Nuværende antal: (" + orders.getAmount() + "): ");
        if (newOrdreAmount.length() > 0){
            orders.setAmount(Integer.parseInt(newOrdreAmount));
        }
        int newPickupTime = Input.getTimeInMinutes("Nuværende afhentnings tidspunkt: (" + Input.getMinutesToTimeFormat(orders.getPickupTime()) + "): ");
        if (newPickupTime > 0){
            orders.setPickupTime(newPickupTime);
        }
        String newCustomerName = Input.getString("Nuværende navn på kunde: (" + orders.getCustomerName() + "): ");
        if (newCustomerName.length() > 0){
            orders.setCustomerName(newCustomerName);
        }
        String newPhone = Input.getString("Nuværende tlf nr: " + orders.getPhoneNo() + "): ");
        if (newPhone.length() > 0){
            orders.setPhoneNo(newPhone);
        }
        boolean result = dbOrderMapper.updateOrders(orders);

        if (result){
            System.out.println("Ordre med nr = " + ordreId + " er nu opdateret");
        } else {
            System.out.println("Vi kunne desværre ikke opdatere ordren.");
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
