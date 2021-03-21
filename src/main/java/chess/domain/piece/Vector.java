package chess.domain.piece;

import chess.domain.board.Point;
import chess.domain.board.Row;

import java.util.Arrays;
import java.util.List;

public enum Vector {
    EAST(1, 0),
    WEST(-1, 0),
    SOUTH(0, -1),
    NORTH(0, 1),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),

    FIRST_PAWN_UP(0, 2),
    FIRST_PAWN_DOWN(0, -2),

    NNE(1, 2),
    NEE(2, 1),
    SEE(2, -1),
    SSE(1, -2),
    SSW(-1, -2),
    SWW(-2, -1),
    NWW(-2, 1),
    NNW(-1, 2);

    private final int horizon;
    private final int vertical;

    Vector(int horizon, int vertical) {
        this.horizon = horizon;
        this.vertical = vertical;
    }

    public static List<Vector> firstWhitePawnVectors() {
        return Arrays.asList(NORTH, FIRST_PAWN_UP);
    }

    public static List<Vector> firstBlackPawnVectors() {
        return Arrays.asList(NORTH, FIRST_PAWN_DOWN);
    }

    public static List<Vector> everyVectors() {
        return Arrays.asList(EAST, WEST, SOUTH, NORTH, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST);
    }

    public static List<Vector> diagonalVectors() {
        return Arrays.asList(NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST);
    }

    public static List<Vector> axisVectors() {
        return Arrays.asList(EAST, WEST, SOUTH, NORTH);
    }

    public static List<Vector> knightVectors() {
        return Arrays.asList(NNE, NEE, SEE, SSE, SSW, SWW, NWW, NNW);
    }

    public static boolean hasNotVector(int horizon, int vertical) {
        return Arrays.stream(Vector.values())
            .noneMatch(vector -> (vector.horizon == horizon)
                && (vector.vertical == vertical));
    }

    public static Vector get(int horizon, int vertical) {
        return Arrays.stream(Vector.values())
            .filter(vector -> (vector.horizon == horizon)
                && (vector.vertical == vertical))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isWhitePawnsStraight(Point source) {
        if (source.isRow(Row.TWO)) {
            return firstWhitePawnVectors().contains(this);
        }
        return this == NORTH;
    }

    public boolean isBlackPawnsStraight(Point source) {
        if (source.isRow(Row.SEVEN)) {
            return firstBlackPawnVectors().contains(this);
        }
        return this == SOUTH;
    }

    public boolean isWhiteDiagonalVector() {
        return this == NORTHEAST || this == NORTHWEST;
    }

    public boolean isBlackDiagonalVector() {
        return this == SOUTHEAST || this == SOUTHWEST;
    }

    public boolean isSameDirection(int horizon, int vertical) {
        if (isOppositeDirection(horizon, vertical)) {
            return false;
        }
        return this.horizon * vertical == this.vertical * horizon;
    }

    private boolean isOppositeDirection(int horizon, int vertical) {
        return (this.horizon * horizon < 0) || (this.vertical * vertical < 0);
    }

    public Vector opposite() {
        return get((-1) * horizon, (-1) * vertical);
    }

    public int getHorizon() {
        return horizon;
    }

    public int getVertical() {
        return vertical;
    }
}
