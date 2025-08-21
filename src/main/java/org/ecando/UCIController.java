package org.ecando;

import com.frequentlymisseddeadlines.chessuci.GoParameters;
import com.frequentlymisseddeadlines.chessuci.UciListener;
import com.frequentlymisseddeadlines.chessuci.UciProtocol;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;
import org.ecando.engine.Bot;

import java.util.Arrays;

public class UCIController implements UciListener {
	private final Board board;
	private final Bot bot;

	public UCIController(Bot bot) {
		this.bot = bot;
		this.board = new Board();
	}

	/**
	 * Return your Chess Engine Name. It may be displayed in the GUI.
	 *
	 * @return chess engine name
	 */
	@Override
	public String getEngineName() {
		return "RandoTron";
	}

	/**
	 * Return your name. It may be displayed in the GUI.
	 *
	 * @return your name
	 */
	@Override
	public String getAuthorName() {
		return "ECanDo";
	}

	/**
	 * This method is called when the position has changed. It provides the initial position and all moves done from it.
	 *
	 * @param initialPosition the initial position in <a href="https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">FEN notation</a>
	 * @param moves           an array of String containing all moves done from the starting position. The moves are in origin-destination notation.
	 *                        For example:
	 *                        <ul>
	 *                        <li>
	 *                            e2e4
	 *                        </li>
	 *                        <li>
	 *                            e5e7
	 *                        </li>
	 */
	@Override
	public void setPosition(String initialPosition, String[] moves) {
		try {
			// Load FEN if given, otherwise reset to startpos
			if (initialPosition != null && !initialPosition.isEmpty()) {
				board.loadFromFen(initialPosition);
			} else {
				board.loadFromFen(UciProtocol.STARTING_POSITION_FEN);
			}

			// Apply all moves from the GUI
			Arrays.stream(moves).forEach(m -> {
				try {
					board.doMove(new Move(m, board.getSideToMove()));
				} catch (MoveException e) {
					System.err.println("Invalid move from GUI: " + m);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called to tell the engine it should search for a move to play. Once it found one, it must
	 * return it as a origin-destination String.
	 *
	 * @param parameters the search parameters
	 * @return
	 */
	@Override
	public String go(GoParameters parameters) {
		// Ask your bot for a move
		Move move = bot.selectMove(board);
		if (move == null) {
			return "0000"; // UCI "null move" if stuck
		}
		board.doMove(move);
		return move.toString(); // Must be in UCI format, e.g. e2e4
	}
}
