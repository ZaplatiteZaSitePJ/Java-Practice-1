package features;

import entities.Menu.Menu;
import java.util.Scanner;

public class MenuSetup {

    public static void configure(Menu menu, StudentFeatures features,
                           Scanner scanner) {
        menu.addOption(1, "Добавить студента вручную",
                features::addStudentManually);

        menu.addOption(2, "Загрузить студентов из файла",
                features::loadStudentsFromFile);

        menu.addOption(3, "Показать всех студентов",
                features::showAllStudents);

        menu.addOption(4, "Фильтр по среднему баллу",
                features::filterStudents);

        menu.addOption(5, "Упрощенный формат таблицы (map)",
                features::showTransformedData);

        menu.addOption(6, "Сортировать по имени",
                features::sortStudentsByName);

        menu.addOption(0, "Выход", () -> {});

        while (true) {
            menu.print();

            try {
                int choice = menu.readChoice();

                if (choice == 0) {
                    System.out.println("Программа завершена.");
                    return;
                }

                menu.execute(choice);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }
    }
}