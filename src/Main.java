
import entities.menu.Menu;
import entities.student.Student;
import features.MenuSetup;
import features.StudentFeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        StudentFeatures features =
                new StudentFeatures(students, scanner);

        Menu menu = new Menu(scanner);

        MenuSetup.configure(menu, features, scanner);

        scanner.close();
    }
}
