package library;

public class Film extends LibraryItem {
    private final String genre;
    private final String director;
    private final int year;
    private final int runtimeMinutes;
    private final String rating;

    public Film(String id, String title, String genre, String director, int year, int runtimeMinutes, String rating) {
        super(id, title);
        this.genre = genre;
        this.director = director;
        this.year = year;
        this.runtimeMinutes = runtimeMinutes;
        this.rating = rating;
    }

    @Override
    protected int loanPeriodDaysFor(User user) {
        return 2; 
    }

    @Override
    protected double dailyFine() {
        return 5.0;
    }
}
