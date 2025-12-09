package library;

public final class EmailNotifier implements OverdueObserver {
    @Override
    public void notifyOverdue(User user, LibraryItem item, int daysLate) {
        String msg = String.format("email to %s: item %s (%s) is %d days overdue.",
                user.getId(), item.getTitle(), item.getId(), daysLate);
        System.out.println(msg);
    }
}
