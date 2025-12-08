package library;

public class Book extends LibraryItem {
    private final String author;
    private final String genre;
    private final String publisher;

    public Book(String id, String title, String author, String genre, String publisher) {
        super(id, title);
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
    }

    @Override
    protected int loanPeriodDaysFor(User user) {
        return 14; 
    }

    @Override
    protected double dailyFine() {
        return 0.5;
    }
}
