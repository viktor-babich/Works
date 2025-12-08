package library;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Library lib = new Library();

        String booksPath;
        String journalsPath;
        String filmsPath;

        if (args.length >= 3) {
            booksPath = args[0];
            journalsPath = args[1];
            filmsPath = args[2];
        } else {

            booksPath = "books.csv";
            journalsPath = "jlist.csv";
            filmsPath = "movies.csv";
        }

        CSVLoader.loadBooks(lib, booksPath);
        CSVLoader.loadJournals(lib, journalsPath);
        CSVLoader.loadFilms(lib, filmsPath);

        List<User> users = new ArrayList<>();
        for (int i=0;i<80;i++) {
            boolean onTime = i < 67;
            users.add(new User("S"+i, User.Type.STUDENT, onTime));
        }
        for (int i=0;i<20;i++) {
            users.add(new User("F"+i, User.Type.FACULTY, true));
        }

        //use the current time so each run produces different results
        long seed;
        if (args.length >= 4) {
            try {
                seed = Long.parseLong(args[3]);
            } catch (NumberFormatException ex) {
                seed = System.currentTimeMillis();
            }
        } else {
            seed = System.currentTimeMillis();
        }
        System.out.println("using RNG seed: " + seed);
        Simulation sim = new Simulation(lib, users, seed);
        Simulation.Stats stats = sim.run(365, 0.05, 0.08, 0.05, 0.02);

        String out = "output/simulation_report.txt";
        ReportWriter.writeSummary(stats, lib, users, out);
        System.out.println("simulation finished, report written to: " + out);
    }

    public static class ReportWriter {
        public static void writeSummary(Simulation.Stats stats, Library lib, java.util.List<User> users, String path) throws Exception {
            File outFile = new File(path);
            File parent = outFile.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(outFile))) {
                bw.write("library simulation report\n");
                bw.write(String.format("total borrows: %d\n", stats.totalBorrows));
                bw.write(String.format("total returns: %d\n", stats.totalReturns));
                bw.write(String.format("total fines collected: $%.2f\n", stats.totalFines));
                bw.write(String.format("overdue returns: %d\n", stats.overdueReturns));

                int booksOnLoan=0, journalsOnLoan=0, filmsOnLoan=0;
                for (LibraryItem it : lib.getAllItems()) {
                    if (it.isOnLoan()) {
                        if (it instanceof Book) booksOnLoan++;
                        else if (it instanceof Journal) journalsOnLoan++;
                        else if (it instanceof Film) filmsOnLoan++;
                    }
                }
                bw.write(String.format("books on loan: %d\n", booksOnLoan));
                bw.write(String.format("journals on loan: %d\n", journalsOnLoan));
                bw.write(String.format("films on loan: %d\n", filmsOnLoan));

                int usersWithLoans=0;
                for (User u : users) if (!u.getBorrowed().isEmpty()) usersWithLoans++;
                bw.write(String.format("users with outstanding loans: %d\n", usersWithLoans));

                bw.write("\nend of report\n");
            }
        }
    }

    public static class Simulation {
        private final Library library;
        private final java.util.List<User> users;
        private final java.util.Random rnd;

        public static class Stats {
            public int totalBorrows = 0;
            public int totalReturns = 0;
            public double totalFines = 0.0;
            public int overdueReturns = 0;
        }

        public Simulation(Library library, java.util.List<User> users, long seed) {
            this.library = library;
            this.users = users;
            this.rnd = new java.util.Random(seed);
        }

        public Stats run(int days, double alphaBook, double alphaJournal, double alphaFilm, double beta) {
            Stats s = new Stats();
            for (int day = 1; day <= days; day++) {
                final int currentDay = day;
                for (User u : users) {
                    java.util.List<LibraryItem> borrowed = new java.util.ArrayList<>(u.getBorrowed());
                    for (LibraryItem it : borrowed) {
                        boolean returned = false;
                        if (u.isOnTime()) {
                            int dueDate = it.borrowDate + it.loanPeriodDaysFor(u);
                            if (currentDay == dueDate) {
                                double fine = library.returnItem(it.getId(), currentDay);
                                s.totalReturns++;
                                s.totalFines += fine;
                                if (fine > 0) s.overdueReturns++;
                                returned = true;
                            }
                        }
                        if (!returned) {
                            if (rnd.nextDouble() < beta) {
                                double fine = library.returnItem(it.getId(), currentDay);
                                s.totalReturns++;
                                s.totalFines += fine;
                                if (fine > 0) s.overdueReturns++;
                            }
                        }
                    }

                    if (rnd.nextDouble() < alphaBook) {
                        library.pickRandomAvailable(Book.class, rnd).ifPresent(it -> {
                            if (library.borrowItem(it.getId(), u, currentDay)) s.totalBorrows++;
                        });
                    }
                    if (rnd.nextDouble() < alphaJournal) {
                        library.pickRandomAvailable(Journal.class, rnd).ifPresent(it -> {
                            if (library.borrowItem(it.getId(), u, currentDay)) s.totalBorrows++;
                        });
                    }
                    if (rnd.nextDouble() < alphaFilm) {
                        library.pickRandomAvailable(Film.class, rnd).ifPresent(it -> {
                            if (library.borrowItem(it.getId(), u, currentDay)) s.totalBorrows++;
                        });
                    }
                }
            }
            return s;
        }
    }
}
