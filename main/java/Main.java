import domain.CustomExceptions;
import ui.MainMenu;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws CustomExceptions {
        MainMenu mainMenu = new MainMenu();
        mainMenu.mainMenuLoop();

}}