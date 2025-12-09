package library;

public final class Book extends LibraryItem implements Loanable {
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
    public int getLoanPeriod(User user) {
        if (user == null) return 14;
        return user.getType() == User.Type.FACULTY ? 28 : 14;
    }

    @Override
    public double getDailyOverdueFee() {
        return 0.5;
    }

    @Override
    public boolean matches(String keyword) {
        if (super.matches(keyword)) return true;
        if (keyword == null) return false;
        String k = keyword.toLowerCase();
        if (author != null && author.toLowerCase().contains(k)) return true;
        if (genre != null && genre.toLowerCase().contains(k)) return true;
        if (publisher != null && publisher.toLowerCase().contains(k)) return true;
        return false;
    }
}
