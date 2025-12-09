package library;

public sealed abstract class LibraryItem implements Searchable, Comparable<LibraryItem>
        permits Book, Journal, Film {
    protected final String id;
    protected final String title;
    protected User borrowedBy;
    protected int borrowDate;
    protected boolean overdueNotified = false;

    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.borrowedBy = null;
        this.borrowDate = 0;
        this.overdueNotified = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }

    public boolean isOnLoan() { return borrowedBy != null; }

    public void borrow(User user, int day) {
        this.borrowedBy = user;
        this.borrowDate = day;
        this.overdueNotified = false;
    }

    public void returned() {
        this.borrowedBy = null;
        this.borrowDate = 0;
        this.overdueNotified = false;
    }

    public int daysOverdue(int currentDay) {
        if (!isOnLoan()) return Integer.MIN_VALUE;
        int loanDays = ((Loanable)this).getLoanPeriod(borrowedBy);
        int dueDate = borrowDate + loanDays;
        return currentDay - dueDate;
    }

    public boolean isOverdue(int currentDay) {
        int d = daysOverdue(currentDay);
        if (d==Integer.MIN_VALUE) return false;
        return d > 0;
    }

    public double computeFine(int returnDay) {
        int overdue = daysOverdue(returnDay);
        if (overdue <= 0) return 0.0;
        return overdue * ((Loanable)this).getDailyOverdueFee();
    }

    protected int typeOrder() {
        if (this instanceof Book) return 0;
        if (this instanceof Journal) return 1;
        return 2; // Film
    }

    // in default there no year information
    protected int getYear() { return Integer.MIN_VALUE; }

    @Override
    public int compareTo(LibraryItem other) {
        int t = this.title.compareToIgnoreCase(other.title);
        if (t != 0) return t;
        int to = Integer.compare(this.typeOrder(), other.typeOrder());
        if (to != 0) return to;
        return Integer.compare(this.getYear(), other.getYear());
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null) return false;
        String k = keyword.toLowerCase();
        if (title != null && title.toLowerCase().contains(k)) return true;
        return false;
    }
}
