package org.ecando.engine;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.List;
import java.util.Random;

public class RandomBot implements Bot {
	private final Random rng = new Random();

	/**
	 * Makes random moves, unless a checkmate exists, takes that.
	 *
	 * @param board The board state
	 * @return The "best" move
	 */
	@Override
	public Move selectMove(Board board) {
		Board board1;

		List<Move> moves = board.legalMoves();
		Move bestMove = moves.get(rng.nextInt(moves.size()));

		for (Move m : moves) {
			board1 = board.clone();
			board1.doMove(m);
			if (board1.isMated()) {
				bestMove = m;
				break;
			}
		}

		return bestMove;
	}
}
