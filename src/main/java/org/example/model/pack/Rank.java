package org.example.model.pack;

public enum Rank {
    ALPHA('α', 8),
    BETA('β', 7),
    GAMMA('γ', 6),
    DELTA('δ', 5),
    EPSILON('ε', 4),
    ZETA('ζ', 3),
    ETA('η', 2),
    THETA('θ', 1),
    OMEGA('ω', 0);

    private final char symbol;
    private final int hierarchyLevel;

    Rank(char symbol, int hierarchyLevel) {
        this.symbol = symbol;
        this.hierarchyLevel = hierarchyLevel;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public Rank getLowerRank() {
        int i = ordinal();
        return i < values().length - 1 ? values()[i + 1] : OMEGA;
    }
}
