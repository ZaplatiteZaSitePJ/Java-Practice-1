package entities.Menu;

public class MenuOption {
    private int number;
    private String optionName;
    private Runnable action;

    public int getNumber() {
        return number;
    }

    public String getOptionName() {
        return optionName;
    }

    public MenuOption(int number, String optionName, Runnable action) {
        this.number = number;
        this.optionName = optionName;
        this.action = action;
    }

    public void run() {
        action.run();
    }
}
