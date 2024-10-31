package xyz.niflheim.stockfish.util;

public enum Elo {
    BEGINNER(1320),INTERMEDIATE(1600),ADVANCED(2000);

    private final int rating;

    Elo(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
