package week3.Ass4_Hibernate.Main.Java.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author_book")
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int  id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public  boolean  equals ( Object  o ) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return  false ;
        }
        BookAuthor temp = (BookAuthor) o;
        return temp.id == id && Objects.equals(temp.author, author) && Objects.equals(temp.book, book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, book);
    }
}
