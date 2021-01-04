package ui;

import domain.CustomExceptions;
import domain.Orders;
import domain.Pizza;
import domain.Statistics;
import persistence.Database;
import persistence.DbMenuCardMapper;
import persistence.DbOrderMapper;
import domain.LogWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private final String USER = "testdb_user";
    private final String PASSWORD = "1234";
    private final String URL = "jdbc:mysql://localhost:3306/mario_db?serverTimezone=CET&useSSL=false";

    private Database database;
    private DbMenuCardMapper dbMenuCardMapper;
    private DbOrderMapper dbOrderMapper;
    private boolean running = true;
    LogWriter logWriter = new LogWriter();
    StringWriter errorsToString = new StringWriter();
    //TODO: Håndter ToString konvertering i LogWriter klassen

    public MainMenu() {
        try {
            this.database = new Database(USER,PASSWORD,URL);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
            e.printStackTrace(new PrintWriter(errorsToString));
            logWriter.addLogMessageToFile(errorsToString.toString());
            this.running = false;
        }
        this.dbOrderMapper = new DbOrderMapper(database);
        this.dbMenuCardMapper = new DbMenuCardMapper(database);
    }

    public void mainMenuLoop() throws CustomExceptions {



        while (this.running) {
            showMenu();
            switch(Input.getInt("Vælg 1-5: ")){
                case 1: newOrder(); break;
                case 2: showMenuCard(); break;
                case 3: showSinglePizza(); break;
                case 4: admin(); break;
                case 5: this.running = false; break;
                default:
                    System.out.println("Du forsøger at tilgå et menupunkt der ikke eksistere"); break;
            }
        }
        System.out.println("Tak for denne gang!");
    }

    private void showMenu() {
        System.out.println("\n**** Marios pizzabar - HOVDEMENU ******");
        System.out.println("[1]Opret Ordre");
        System.out.println("[2]Vis menukort");
        System.out.println("[3]Vis en enkelt pizza");
        System.out.println("[4]Admin");
        System.out.println("[5]Afslut");
    }

    private void admin() throws CustomExceptions {
        System.out.println("\n**** Marios pizzabar - ADMIN ******");
        System.out.println("[1]Pizza redigering");
        System.out.println("[2]Ordre håndtering");
        System.out.println("[3]Statistik");
        System.out.println("[4]Tilbage til hovedmenuen");
       boolean running = true;
       if (running) {
        switch (Input.getInt("Vælg 1-4: ")){
            case 1:pizzaEdit(); break;
            case 2:orderEdit(); break;
            case 3:Statistik(); break;
            default: break;
        }}
    }

    private void Statistik() throws CustomExceptions {
        System.out.println("\n**** Marios pizzabar - STATISTIK ******");
        System.out.println("[1]Statistik på enkelt pizzanr");
        System.out.println("[2]Statistik på alle pizzaer");
        System.out.println("[3]Tilbage til hovdemenuen");
        switch (Input.getInt("Vælg 1-3")){
            case 1:statistics(); break;
            case 2:statisticsTotal(); break;
            default: break;
        }
    }

    private void removeOrder() throws CustomExceptions {
        System.out.println("\n**** Marios pizzabar - REMOVE ORDER ******");
        readOrder();
        int orderIdtoRemove = Input.getInt("Hvilken order vil du fjerne?");
        dbOrderMapper.removeOrder(orderIdtoRemove);

    }

    private void statistics() throws CustomExceptions {
        System.out.println("\n**** Marios pizzabar - STATISTIK ******");
    int amountSold = 0;
    int uiPizzaNo = 0;

    List<Statistics> pizzaAmountSold = new ArrayList<>(dbOrderMapper.statisticsArchived());

      uiPizzaNo = Input.getInt("Hvilken pizza vil du se statistik for? ");

        for (Statistics statistics : pizzaAmountSold) {
            if (statistics.getPizzaNo() == uiPizzaNo) amountSold += statistics.getAmount();

        }
        if (amountSold <=0 ) System.out.println(" MAKKER der er ikke solgt nogen af pizzanr :" + uiPizzaNo);
            else {
            System.out.println("\nDer er solgt: " + amountSold + "stk");
            System.out.println("af pizzavariant: " + dbMenuCardMapper.getPizzaById(uiPizzaNo).getName());
            System.out.println("total omsætning på " + amountSold * dbMenuCardMapper.getPizzaById(uiPizzaNo).getPrice() + " kr \n");
        }
    }

    private void statisticsTotal() throws CustomExceptions {


        List<Statistics> pizzaAmountSold = new ArrayList<>(dbOrderMapper.statisticsArchived());

        for (int i = 0; i < pizzaAmountSold.toArray().length; i++) {
            int amountSold =0;
            for (Statistics statistics : pizzaAmountSold) {
                if (statistics.getPizzaNo() == i) {
                    amountSold += statistics.getAmount();
                }
            }
            if (amountSold >0 ) {
                System.out.println("\nDer er solgt: " + amountSold + "stk");
                System.out.println("af pizzavariant: " + dbMenuCardMapper.getPizzaById(i).getName());
                System.out.println("total omsætning på " + amountSold * dbMenuCardMapper.getPizzaById(i).getPrice() + " kr");
            }
        }
        }



    private void readOrder() throws CustomExceptions {
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

    private boolean newOrder() throws CustomExceptions {
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

    private void pizzaEdit () throws CustomExceptions {

        System.out.println("**** Marios pizzabar - PIZZA-REDIGERING ******");
        System.out.println("[1] fjern pizza");
        System.out.println("[2] opret ny pizza");
        System.out.println("[3] opdater pizza");
        System.out.println("[4] tilbage til hovedmenu");
        switch (Input.getInt("vælg 1-4")){
            case 1 : deletePizza();break;
            case 2 : insertPizza();break;
            case 3 : updatePizza();break;
            default: break;

        }

    }

    private void orderEdit() throws CustomExceptions {

        System.out.println("**** Marios pizzabar - ORDREHÅNDTERING ******");
        System.out.println("[1]  se igangværende ordre");
        System.out.println("[2] fjern ordre");
        System.out.println("[3] opdater ordre");
        System.out.println("[4] tilbage til hovedmenu");
        switch (Input.getInt("vælg 1-4")) {
            case 1:
                readOrder();
                break;
            case 2:
                removeOrder();
                break;
            case 3:
                updateOrder(); break;
            default: break;
        }

    }

    private void updatePizza() throws CustomExceptions {
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

    private void insertPizza() throws CustomExceptions {
      try{  System.out.println("**** Opret ny pizza *******");
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
        }}catch (CustomExceptions e){
          System.out.println(e.getMessage());
          e.printStackTrace(new PrintWriter(errorsToString));
          logWriter.addLogMessageToFile(errorsToString.toString());
          this.running = false;
    }}

    private void deletePizza() throws CustomExceptions {
      try{  int pizzaNo = Input.getInt("Indtast nummer på pizza som skal fjernes: ");
        boolean result = dbMenuCardMapper.deletePizza(pizzaNo);
        if (result){
            System.out.println("Pizzaen med nr = " + pizzaNo + " er nu fjernet");
        } else {
            System.out.println("Pizzaen med nr = " + pizzaNo + " findes ikke, og kan derfor ikke fjernes");
        }}catch (CustomExceptions e){
          System.out.println(e.getMessage());
          e.printStackTrace(new PrintWriter(errorsToString));
          logWriter.addLogMessageToFile(errorsToString.toString());
          this.running = false;

      }

    }

    private void showSinglePizza() throws CustomExceptions {
        try {
            int pizzaNo = Input.getInt("Indtast pizzanummer: ");
            Pizza pizza = dbMenuCardMapper.getPizzaById(pizzaNo);
            if (pizza != null) {
                System.out.println("Du har fundet pizza nummer: " + pizzaNo);
                System.out.println(pizza.toString());
            } else {
                System.out.println("Pizza med nr = " + pizzaNo + " findes desværre ikke");
            }
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
            e.printStackTrace(new PrintWriter(errorsToString));
            logWriter.addLogMessageToFile(errorsToString.toString());
            this.running = false;
        }
    }

    private void updateOrder() throws CustomExceptions {
        System.out.println("***** Opdater Ordre *******");
        int ordreId = Input.getInt("Hvilket ordre id vil du rette: ");

        Orders orders = dbOrderMapper.getOrderById(ordreId);
        if (orders !=null){
            System.out.println("Indtast ny værdi, hvis den skal rettes - eller blot <retur>: ");
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
        }}else System.out.println("Det ordreID du forsøger at finde, eksistere ikke");
    }

    private void showMenuCard() throws CustomExceptions {
        System.out.println("**** Menukort hos Marios ******");
//        List<Pizza> menuCard = dbMenuCardMapper.getAllPizzas();
//        for (Pizza pizza : menuCard) {
//            System.out.println(pizza.toString());
//        }
       try{ List<Pizza> menuCard = dbMenuCardMapper.getAllPizzas();
          if (menuCard != null){
                for (Pizza pizza : menuCard) {
                    int pizzaNo = pizza.getPizzaNo();
                    String name = pizza.getName();
                    String ingredients = pizza.getIngredients();
                    double price = pizza.getPrice();
                    String text,dotLine;
                    int dotLenght = 100;
                    //  System.out.println("");
                    dotLine = ".";
                    text = pizzaNo + ". " + name + ": " + ingredients;
                    for (int i = 0; i < dotLenght - text.length(); i++) {
                        dotLine = dotLine + ".";
                    }
                    text = text + " " + dotLine + " " + price + " kr.";
                    System.out.println(text);
                }}else this.running = false;} catch (CustomExceptions e){
           System.out.println(e.getMessage());
           e.printStackTrace(new PrintWriter(errorsToString));
           logWriter.addLogMessageToFile(errorsToString.toString());
           this.running = false;

           }
       }

    }


