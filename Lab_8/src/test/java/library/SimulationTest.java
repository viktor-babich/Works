package library;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void runYearSimulation() throws Exception {
        Library lib = new Library();

        CSVLoader.loadBooks(lib, null);
        CSVLoader.loadJournals(lib, null);
        CSVLoader.loadFilms(lib, null);

        List<User> users = new ArrayList<>();
        // 80 students, 20 faculty. 67 on-time, 33 delayed total 
        for (int i=0;i<80;i++) {
            boolean onTime = i < 67; 
            users.add(new User("S"+i, User.Type.STUDENT, onTime));
        }
        for (int i=0;i<20;i++) {
            boolean onTime = (80 + i) < 67; 
            users.add(new User("F"+i, User.Type.FACULTY, onTime));
        }

        Main.Simulation sim = new Main.Simulation(lib, users, 12345L);
        Main.Simulation.Stats stats = sim.run(365, 0.05, 0.08, 0.05, 0.02);
        // basic sanity checks
        assertTrue(stats.totalBorrows >= 0);
        assertTrue(stats.totalReturns >= 0);
        assertTrue(stats.totalFines >= 0.0);
    }
}
