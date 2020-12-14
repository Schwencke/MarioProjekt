package domain;

public class Pizza {

    private int pizzaId;  // Id i databasen
    private int pizzaNo;
    private String name;
    private String ingredients;
    private int price;

    public Pizza(int pizzaNo, String name, String ingredients, int price) {
        this.pizzaNo = pizzaNo;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public Pizza(int pizza_id, int pizzaNo, String name, String ingredients, int price) {
        this.pizzaId = pizza_id;
        this.pizzaNo = pizzaNo;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public Pizza() {
    }

    public int getPizzaNo() {
        return pizzaNo;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getPrice() {
        return price;
    }

    public void setPizzaNo(int pizzaNo) {
        this.pizzaNo = pizzaNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaId=" + pizzaId +
                ", pizzaNo=" + pizzaNo +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", price=" + price +
                '}';
    }
}
