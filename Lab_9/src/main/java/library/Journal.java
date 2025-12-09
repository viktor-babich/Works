package library;

public final class Journal extends LibraryItem implements Loanable {
    private final String eissn;
    private final String publisher;
    private final String latestIssue;
    private final String url;

    public Journal(String id, String title, String eissn, String publisher, String latestIssue, String url) {
        super(id, title);
        this.eissn = eissn;
        this.publisher = publisher;
        this.latestIssue = latestIssue;
        this.url = url;
    }

    @Override
    public int getLoanPeriod(User user) {
        if (user == null) return 3;
        return user.getType() == User.Type.STUDENT ? 3 : 7;
    }

    @Override
    public double getDailyOverdueFee() {
        return 2.0;
    }

    @Override
    public boolean matches(String keyword) {
        if (super.matches(keyword)) return true;
        if (keyword == null) return false;
        String k = keyword.toLowerCase();
        if (eissn != null && eissn.toLowerCase().contains(k)) return true;
        if (publisher != null && publisher.toLowerCase().contains(k)) return true;
        if (latestIssue != null && latestIssue.toLowerCase().contains(k)) return true;
        if (url != null && url.toLowerCase().contains(k)) return true;
        return false;
    }
}
