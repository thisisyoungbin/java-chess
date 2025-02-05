package chess.domain;

import chess.domain.board.Board;
import chess.domain.board.Point;
import chess.domain.board.Square;
import chess.domain.board.Team;
import chess.domain.gamestate.GameState;
import chess.domain.gamestate.Ready;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChessGame {
    private static final int SOURCE_INDEX = 1;
    private static final int DESTINATION_INDEX = 2;
    private static final int MOVE_ARGUMENTS_COUNT = 3;

    private final Turn turn;
    private Board board;
    private GameState state;

    public ChessGame(Board board) {
        this.board = board;
        this.state = new Ready(board);
        this.turn = new Turn();
    }

    public void start() {
        state = state.start();
    }

    public void end() {
        state = state.end();
    }

    public void status() {
        state = state.status();
    }

    public void move(List<String> arguments) {
        validateMoveArgument(arguments);

        Point source = Point.of(arguments.get(SOURCE_INDEX));
        Point destination = Point.of(arguments.get(DESTINATION_INDEX));
        state = state.move(source, destination, turn.now());
        turn.nextTurn();

        board = state.board();
    }

    private void validateMoveArgument(List<String> arguments) {
        if (arguments.size() != MOVE_ARGUMENTS_COUNT) {
            throw new IllegalArgumentException("정확한 위치를 입력해주세요.");
        }
    }

    public boolean isRunning() {
        return state.isRunning();
    }

    public double score(Team team) {
        return board.score(team);
    }

    public GameState state() {
        return state;
    }

    public Map<Point, Square> board() {
        return board.board();
    }

    public String winner() {
        return state.winner();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(turn, chessGame.turn) && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }
}
