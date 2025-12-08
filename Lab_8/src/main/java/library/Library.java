package library;

import java.util.*;

public class Library {
    private final Map<String, LibraryItem> items = new HashMap<>();
    private final List<User> users = new ArrayList<>();

    public void addItem(LibraryItem it) { items.put(it.getId(), it); }
    public LibraryItem getItemById(String id) { return items.get(id); }

    public void addUser(User u) { users.add(u); }

    public boolean borrowItem(String itemId, User user, int day) {
        LibraryItem it = items.get(itemId);
        if (it == null) return false;
        if (it.isOnLoan()) return false;
        if (!user.canBorrow(it)) return false;
        it.borrow(user, day);
        user.addBorrowed(it);
        return true;
    }

    public double returnItem(String itemId, int day) {
        LibraryItem it = items.get(itemId);
        if (it == null || !it.isOnLoan()) return 0.0;
        User borrower = it.borrowedBy;
        double fine = it.computeFine(day);
        it.returned();
        if (borrower != null) borrower.removeBorrowed(it);
        return fine;
    }

    public Optional<LibraryItem> pickRandomAvailable(Class<?> cls, Random rnd) {
        List<LibraryItem> list = new ArrayList<>();
        for (LibraryItem it : items.values()) {
            if (!it.isOnLoan() && cls.isInstance(it)) list.add(it);
        }
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(rnd.nextInt(list.size())));
    }

    public java.util.Collection<LibraryItem> getAllItems() {
        return items.values();
    }
}
