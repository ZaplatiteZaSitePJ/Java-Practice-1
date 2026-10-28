package features;

import entities.student.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentFeatures {
    private final List<Student> students;
    private final Scanner scanner;

    public StudentFeatures(List<Student> students, Scanner scanner) {
        this.students = students;
        this.scanner = scanner;
    }

    // Добавление студента вручную
    public void addStudentManually() {
        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введите средний балл (0–5): ");
        double grade = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Введите номер зачетной книжки: ");
        String number = scanner.nextLine().trim();

        Student student = new Student(name, grade, number);

        boolean duplicate = students.stream()
                .anyMatch(s -> s.getBookNumber()
                        .equalsIgnoreCase(number));

        if (duplicate) {
            throw new IllegalArgumentException(
                    "Студент с таким номером зачетной книжки уже существует."
            );
        }

        students.add(student);

        System.out.println("Студент успешно добавлен.");
    }

    // Загрузка студентов из CSV-файла
    public void loadStudentsFromFile() {
        System.out.print("Введите путь к CSV-файлу (пустое поле означает взять файл по-умолчанию): ");
        String fileName = scanner.nextLine().trim();
        System.out.println(fileName);

        int added = 0;
        int skipped = 0;

        try {
            if (fileName.isEmpty()) {
                fileName = "src/resources/students.csv";
            }
            Path path = Path.of(fileName);

            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    if (line.isBlank()) {
                        continue;
                    }

                    if (lineNumber == 1
                            && line.toLowerCase().startsWith("name,")) {
                        continue;
                    }

                    try {
                        String[] parts = line.split(",", -1);

                        if (parts.length != 3) {
                            throw new IllegalArgumentException(
                                    "Ожидается 3 поля: имя, балл, номер."
                            );
                        }

                        String name = parts[0].trim();
                        double grade = Double.parseDouble(parts[1].trim());
                        String number = parts[2].trim();

                        Student student = new Student(name, grade, number);

                        boolean duplicate = students.stream()
                                .anyMatch(s -> s.getBookNumber()
                                        .equalsIgnoreCase(number));

                        if (duplicate) {
                            throw new IllegalArgumentException(
                                    "Такой номер зачетной книжки уже существует."
                            );
                        }

                        students.add(student);
                        added++;

                    } catch (IllegalArgumentException e) {
                        skipped++;

                        System.out.println(
                                "Ошибка в строке " + lineNumber
                                        + ": " + e.getMessage()
                        );
                    }
                }

                System.out.println("Загрузка '" + fileName + "' завершена.");
                System.out.println("Добавлено студентов: " + added);
                System.out.println("Пропущено строк: " + skipped);
            }

        } catch (IOException e) {
            System.out.println(
                    "Ошибка чтения файла: " + e.getMessage()
            );
        } catch (SecurityException e) {
            System.out.println(
                    "Нет доступа к файлу: " + e.getMessage()
            );
        } catch (java.nio.file.InvalidPathException e) {
            System.out.println("Некорректный путь к файлу.");
        }
    }

    // Вывод всех студентов
    public void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }

        students.forEach(System.out::println);
    }

    // Фильтрация по среднему баллу
    public void filterStudents() {
        System.out.print("Введите минимальный средний балл (0–5): ");

        double minGrade = Double.parseDouble(scanner.nextLine().trim());

        if (!Double.isFinite(minGrade)
                || minGrade < 0
                || minGrade > 5) {
            throw new IllegalArgumentException(
                    "Средний балл должен быть от 0 до 5."
            );
        }

        List<Student> result = students.stream()
                .filter(s -> s.getAverageGrade() >= minGrade)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Студенты не найдены.");
        } else {
            System.out.println("Результаты фильтрации:");
            result.forEach(System.out::println);
        }
    }

    // Преобразование данных с помощью map()
    public void showTransformedData() {
        List<String> result = students.stream()
                .map(s -> s.getName() + " - "
                        + s.getAverageGrade() + " баллов")
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Список студентов пуст.");
        } else {
            result.forEach(System.out::println);
        }
    }

    // Сортировка по имени
    public void sortStudentsByName() {
        List<Student> sorted = students.stream()
                .sorted(Comparator.comparing(
                        Student::getName,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .collect(Collectors.toList());

        if (sorted.isEmpty()) {
            System.out.println("Список студентов пуст.");
        } else {
            System.out.println("Студенты по алфавиту:");
            sorted.forEach(System.out::println);
        }
    }
}