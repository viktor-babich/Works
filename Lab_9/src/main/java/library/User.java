package library;

import java.util.ArrayList;
import java.util.List;

public class User {
    public enum Type { STUDENT, FACULTY }

    private final String id;
    private final Type type; 
    private final boolean onTime; 
    private final List<LibraryItem> borrowed; 

    public User(String id, Type type, boolean onTime) {
        this.id = id;
        this.type = type;
        this.onTime = onTime;
        this.borrowed = new ArrayList<>();
    }

    public Type getType() { return type; }
    public String getId() { return id; }
    public boolean isOnTime() { return onTime; }

    public List<LibraryItem> getBorrowed() { return borrowed; }

    public void addBorrowed(LibraryItem item) { borrowed.add(item); }
    public void removeBorrowed(LibraryItem item) { borrowed.remove(item); }

    public boolean canBorrow(LibraryItem item) {
        if (type == Type.FACULTY) return true;
        // student limits: max 1 movie, 3 books, 3 journals
        int movies=0, books=0, journals=0;
        for (LibraryItem li : borrowed) {
            if (li instanceof Film) movies++;
            else if (li instanceof Book) books++;
            else if (li instanceof Journal) journals++;
        }
        if (item instanceof Film) return movies < 1;
        if (item instanceof Book) return books < 3;
        if (item instanceof Journal) return journals < 3;
        return true;
    }
}
