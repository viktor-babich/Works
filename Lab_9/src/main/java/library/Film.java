package library;

public final class Film extends LibraryItem implements Loanable {
    private final String genre;
    private final String director;
    private final int year;
    private final String rating;

    public Film(String id, String title, String genre, String director, int year, int runtimeMinutes, String rating) {
        super(id, title);
        this.genre = genre;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    @Override
    public int getLoanPeriod(User user) {
        if (user == null) return 2;
        return user.getType() == User.Type.FACULTY ? 3 : 2;
    }

    @Override
    public double getDailyOverdueFee() {
        return 5.0;
    }

    @Override
    protected int getYear() { return year; }

    @Override
    public boolean matches(String keyword) {
        if (super.matches(keyword)) return true;
        if (keyword == null) return false;
        String k = keyword.toLowerCase();
        if (genre != null && genre.toLowerCase().contains(k)) return true;
        if (director != null && director.toLowerCase().contains(k)) return true;
        if (rating != null && rating.toLowerCase().contains(k)) return true;
        return false;
    }
}
