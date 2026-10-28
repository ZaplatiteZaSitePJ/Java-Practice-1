package entities.Menu;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final Map<Integer, MenuOption> options =
            new LinkedHashMap<>(); // Для сохранения порядка добавления

    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    // Добавление пункта меню (номер, название и действие)
    public void addOption(int number, String title, Runnable action) {
        if (number < 0) {
            throw new IllegalArgumentException(
                    "Номер пункта не может быть отрицательным."
            );
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Название пункта не может быть пустым."
            );
        }

        if (action == null) {
            throw new IllegalArgumentException(
                    "Действие не может быть null."
            );
        }

        if (options.containsKey(number)) {
            throw new IllegalArgumentException(
                    "Пункт с номером " + number + " уже существует."
            );
        }

        options.put(number, new MenuOption(number, title, action));
    }

    // Отображение меню
    public void print() {
        System.out.println("\n===== Выберите действие =====");

        options.values().forEach(option ->
                System.out.println(
                        option.getNumber() + ". " + option.getOptionName()
                )
        );
    }

    // Выполнение выбранного действия
    public boolean execute(int number) {
        MenuOption option = options.get(number);

        if (option == null) {
            System.out.println("Такого пункта меню нет! Выберите заново");
            return true;
        }

        System.out.println(
                "Выбрана операция: " + option.getNumber() + ". " + option.getOptionName()
        );

        option.run();

        return true;
    }

    // Чтение выбора пользователя
    public int readChoice() {
        System.out.print("Выберите пункт меню: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

}
