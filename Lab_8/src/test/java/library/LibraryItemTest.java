package library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryItemTest {

    @Test
    public void bookOverdueAndFine() {
        Book b = new Book("B1", "Test Book", "A", "genre", "pub");
        User u = new User("U1", User.Type.STUDENT, true);
        b.borrow(u, 1); // day 1
        // loan 14 days => due date = 15
        assertEquals(0, b.daysOverdue(15));
        assertFalse(b.isOverdue(15));
        assertEquals(1, b.daysOverdue(16));
        assertTrue(b.isOverdue(16));
        assertEquals(0.5, b.computeFine(16));
        assertEquals(0.0, b.computeFine(14));
    }

    @Test
    public void filmOverdueAndFine() {
        Film f = new Film("F1", "Test Film", "genre", "Dir", 2016, 100, "PG");
        User u = new User("U2", User.Type.STUDENT, true);
        f.borrow(u, 1);
        // loan 2 days => due date = 3
        assertEquals(0, f.daysOverdue(3));
        assertEquals(1, f.daysOverdue(4));
        assertEquals(5.0, f.computeFine(4));
    }

    @Test
    public void journalDifferentPeriods() {
        Journal j = new Journal("J1", "Test Journal", "e123", "Pub", "v1", "url");
        User student = new User("S1", User.Type.STUDENT, true);
        User faculty = new User("F1", User.Type.FACULTY, true);
        j.borrow(student, 1);
        // student loan 3 days => due date = 4
        assertEquals(0, j.daysOverdue(4));
        assertEquals(1, j.daysOverdue(5));
        assertEquals(2.0, j.computeFine(5));
        j.returned();

        j.borrow(faculty, 1);
        // faculty loan 7 days => due date = 8
        assertEquals(0, j.daysOverdue(8));
        assertEquals(2, j.daysOverdue(10));
        assertEquals(4.0, j.computeFine(10));
    }
}
