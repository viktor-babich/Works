package library;

public class Journal extends LibraryItem {
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
    protected int loanPeriodDaysFor(User user) {
        if (user == null) return 3;
        return user.getType() == User.Type.STUDENT ? 3 : 7;
    }

    @Override
    protected double dailyFine() {
        return 2.0;
    }
}
