package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Locale;

public class CSVLoader {

    private static BufferedReader readerFor(String path, String fallbackResource) throws Exception {
        if (path != null) {
            File f = new File(path);
            if (f.exists()) {
                return Files.newBufferedReader(f.toPath());
            }
        }
        if (path != null) {
            File rel = new File(System.getProperty("user.dir"), path);
            if (rel.exists()) return Files.newBufferedReader(rel.toPath());
        }

        InputStream is = CSVLoader.class.getClassLoader().getResourceAsStream(fallbackResource);
        if (is != null) {
            return new BufferedReader(new InputStreamReader(is));
        }

        return null;
    }

    public static void loadBooks(Library lib, String path) throws Exception {
        BufferedReader br = readerFor(path, "books.csv");
        if (br == null) return;
        try (BufferedReader r = br) {
            r.readLine();
            String line;
            int idx = 1;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(";");
                String title = parts.length>0?parts[0].trim():"";
                String author = parts.length>1?parts[1].trim():"";
                String genre = parts.length>2?parts[2].trim():"";
                String publisher = parts.length>4?parts[4].trim():"";
                String id = String.format(Locale.ROOT, "B%04d", idx++);
                lib.addItem(new Book(id, title, author, genre, publisher));
            }
        }
    }

    public static void loadJournals(Library lib, String path) throws Exception {
        BufferedReader br = readerFor(path, "jlist.csv");
        if (br == null) return;
        try (BufferedReader r = br) {
            r.readLine();
            String line;
            int idx = 1;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(";");
                String title = parts.length>0?parts[0].trim():"";
                String eissn = parts.length>3?parts[3].trim():"";
                String publisher = parts.length>4?parts[4].trim():"";
                String latest = parts.length>6?parts[6].trim():"";
                String url = parts.length>12?parts[12].trim():"";
                String id = String.format(Locale.ROOT, "J%04d", idx++);
                lib.addItem(new Journal(id, title, eissn, publisher, latest, url));
            }
        }
    }

    public static void loadFilms(Library lib, String path) throws Exception {
        BufferedReader br = readerFor(path, "movies.csv");
        if (br == null) return;
        try (BufferedReader r = br) {
            r.readLine();
            String line;
            int idx = 1;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(";");
                String title = parts.length>1?parts[1].trim():"";
                String genre = parts.length>2?parts[2].trim():"";
                String director = parts.length>4?parts[4].trim():"";
                int year = 0;
                try { year = Integer.parseInt(parts.length>6?parts[6].trim():"0"); } catch(Exception ignored) {}
                int runtime = 0;
                try { runtime = Integer.parseInt(parts.length>7?parts[7].trim():"0"); } catch(Exception ignored) {}
                String rating = parts.length>8?parts[8].trim():"";
                String id = String.format(Locale.ROOT, "F%04d", idx++);
                lib.addItem(new Film(id, title, genre, director, year, runtime, rating));
            }
        }
    }
}
