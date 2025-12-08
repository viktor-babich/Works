package library;

public abstract class LibraryItem {
    protected final String id;
    protected final String title;
    protected User borrowedBy;
    protected int borrowDate; 

    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.borrowedBy = null;
        this.borrowDate = 0;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }

    public boolean isOnLoan() { return borrowedBy != null; }

    public void borrow(User user, int day) {
        this.borrowedBy = user;
        this.borrowDate = day;
    }

    public void returned() {
        this.borrowedBy = null;
        this.borrowDate = 0;
    }

    protected abstract int loanPeriodDaysFor(User user);
    protected abstract double dailyFine();

    public int daysOverdue(int currentDay) {
        if (!isOnLoan()) return Integer.MIN_VALUE;
        int loanDays = loanPeriodDaysFor(borrowedBy);
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
        return overdue * dailyFine();
    }
}
