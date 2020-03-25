package chess.domain.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Position {
    private static final Map<String, Position> positions = new HashMap<>();

    private final Row row;
    private final Column column;

    private Position(final Row row, final Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(final Row row, final Column column) {
        return positions.get(key(row, column));
    }

    public static int rowGap(Position start, Position end) {
        return start.row() - end.row();
    }

    public static int columnGap(Position start, Position end) {
        return start.column() - end.column();
    }

    public boolean isOn(final Row row) {
        return this.row == row;
    }

    public boolean isOn(final Column column) {
        return this.column == column;
    }

    public int row() {
        return row.getValue();
    }

    public int column() {
        return column.getValue();
    }

    public static Collection<Position> getAllPositions() {
        return positions.values();
    }

    static {
        for (Row row : Row.values()) {
            loopThroughColumns(row);
        }
    }

    private static void loopThroughColumns(final Row row) {
        for (Column column : Column.values()) {
            positions.put(key(row, column), new Position(row, column));
        }
    }

    private static String key(final Row row, final Column column) {
        return row.toString() + column.toString();
    }
}
