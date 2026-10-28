package entities.student;

public class Student {
    private String name;
    private double averageGrade;
    private String bookNumber;

    public Student(String name, double averageGrade, String bookNumber) {
        setName(name);
        setAverageGrade(averageGrade);
        setbookNumber(bookNumber);
    }

    public String getName() {
        return name;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ошибка! Имя не может быть пустым."
            );
        }
        this.name = name.trim();
    }

    public void setAverageGrade(double averageGrade) {
        if (!Double.isFinite(averageGrade)
                || averageGrade < 0 || averageGrade > 5) {
            throw new IllegalArgumentException(
                    "Ошибка! Средний балл должен быть от 0 до 5."
            );
        }
        this.averageGrade = averageGrade;
    }

    public void setbookNumber(String bookNumber) {
        if (bookNumber == null) {
            throw new IllegalArgumentException(
                    "Ошибка! Пустой номер зачетной книжки."
            );
        }
        this.bookNumber = bookNumber;
    }

    @Override
    public String toString() {
        return "Студент: " + name
                + ", средний балл: " + averageGrade
                + ", номер зачетной книжки: " + bookNumber;
    }
}
