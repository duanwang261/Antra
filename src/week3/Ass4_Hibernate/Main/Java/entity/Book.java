package week3.Ass4_Hibernate.Main.Java.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int  id ;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<BookAuthor> author_books = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookAuthor> getAuthor_books() {
        return author_books;
    }

    public void setAuthor_books(List<BookAuthor> ab) {
        this.author_books = ab;
    }

    public void addAuthor_books(BookAuthor ab) {
        this.author_books.add(ab);
    }

    @Override
    public String toString() {
        return "Book: Id: " + getId() + " name: " + getName();
    }

    @Override
    public  boolean  equals ( Object  o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
