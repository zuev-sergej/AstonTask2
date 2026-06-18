import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\AstonHomeWork\\Task2\\student.json");
        List<Student> student = mapper.readValue(file, new TypeReference<ArrayList<Student>>() {
        });

        student.stream()
                .peek(st -> System.out.println(st.getName() + " " + st.getLastName()))
                .peek(b -> System.out.println(b.getBooks()))
                .filter(user -> user.getBooks() != null)
                .flatMap(user -> user.getBooks().stream())
                .toList()
                .stream().sorted(Comparator.comparingInt(Book::getPages))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(2)
                .peek(y -> System.out.println(y.getYear()))
                .findAny()
                .ifPresentOrElse(y -> System.out.println(y.getYear()), () -> System.out.println("Такой книги нет"));


    }
}
