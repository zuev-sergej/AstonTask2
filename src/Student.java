import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Student {
    private String name;
    private String lastName;
    private List<Book> books;

    public Student(String name, String lastName, List<Book> books) {
        this.name = name;
        this.lastName = lastName;
        this.books = books;
    }

    public Student() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Студент: " + name + ", " + lastName + ", " + books;
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("C:\\Users\\Admin\\IdeaProjects\\AstonHomeWork\\Task2\\student.json");
            List<Student> student = mapper.readValue(file, new TypeReference<ArrayList<Student>>() {
            });

            student.stream()
                    .peek(st -> System.out.println(st.getName() + " " + st.getLastName()))
                    .flatMap(st -> Optional.ofNullable(st.getBooks())
                            .orElse(Collections.emptyList())
                            .stream())
                    .sorted(Comparator.comparingInt(Book::getPages))
                    .distinct()
                    .filter(book -> book.getYear() > 2000)
                    .limit(3)
                    .findAny()
                    .ifPresentOrElse(book -> System.out.println(book.getYear()),
                            () -> System.out.println("Такой книги нет"));

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла." + e.getMessage());
        }
    }
}
