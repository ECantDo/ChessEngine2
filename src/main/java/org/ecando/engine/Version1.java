package org.ecando.engine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Version1 implements Bot {

	private final static int MAX_DEPTH = 4;
	private final static int MIN_DEPTH = MAX_DEPTH - 3;
	public final static HashMap<Piece, Integer> PIECE_VALUES = new HashMap<>();
	private final static Random random = new Random();

	static {
		PIECE_VALUES.put(Piece.WHITE_KING, 0);
		PIECE_VALUES.put(Piece.WHITE_QUEEN, 900);
		PIECE_VALUES.put(Piece.WHITE_ROOK, 500);
		PIECE_VALUES.put(Piece.WHITE_BISHOP, 330);
		PIECE_VALUES.put(Piece.WHITE_KNIGHT, 320);
		PIECE_VALUES.put(Piece.WHITE_PAWN, 100);
		PIECE_VALUES.put(Piece.BLACK_KING, 0);
		PIECE_VALUES.put(Piece.BLACK_QUEEN, -900);
		PIECE_VALUES.put(Piece.BLACK_ROOK, -500);
		PIECE_VALUES.put(Piece.BLACK_BISHOP, -330);
		PIECE_VALUES.put(Piece.BLACK_KNIGHT, -320);
		PIECE_VALUES.put(Piece.BLACK_PAWN, -100);
	}

	@Override
	public Move selectMove(Board board) {
		HashMap<Long, Double> hashMap = new HashMap<>();

		Side nextSideToMove = board.getSideToMove().flip();
		double bestScore = Double.NEGATIVE_INFINITY;
		ArrayList<Move> bestMoves = new ArrayList<>();

		for (Move move : board.legalMoves()) {
			board.doMove(move);
			double score = negamax(board, MAX_DEPTH - 1, Double.NEGATIVE_INFINITY,
					Double.POSITIVE_INFINITY, nextSideToMove, hashMap);
			board.undoMove();

//			System.out.println("INFO: MOVE " + move + " SCORE " + score);
			if (score > bestScore) {
				bestScore = score;
				bestMoves.clear();
				bestMoves.add(move);
			} else if (score == bestScore)
				bestMoves.add(move);
		}
		return bestMoves.get(random.nextInt(bestMoves.size()));
	}

	/**
	 * @param board The board position
	 * @param depth The current depth
	 * @return
	 */
	private double negamax(Board board, int depth, double alpha, double beta, Side sideToMove,
	                       HashMap<Long, Double> hashMap) {

		long zobrist = board.getZobristKey();
		if (hashMap.containsKey(zobrist))
			return hashMap.get(zobrist);

		int offset = (MAX_DEPTH - depth) << 2;

		if (board.isMated())
			return 100_000 - offset;
		else if (board.isDraw())
			return 0;

		if (depth == 0)
			return evaluate(board, depth);

		double maxValue = Double.NEGATIVE_INFINITY;

		Side nextSideToMove = sideToMove.flip();
		for (Move move : board.legalMoves()) {
			board.doMove(move);
			double score = -negamax(board, depth - 1, -beta, -alpha, nextSideToMove, hashMap);
			board.undoMove();

			maxValue = Math.max(maxValue, score);
			alpha = Math.max(alpha, score);

//			if (alpha >= beta)
//				break;
		}

		hashMap.put(zobrist, maxValue); // Store after all children searched
		return maxValue;
	}

	private double evaluate(Board board, int depth) {
		int score = 0;
		boolean endgame = Tables.isEndgame(board);

		for (Square square : Square.values()) {
			Piece piece = board.getPiece(square);
			if ((piece == null) || (piece.getPieceType() == PieceType.NONE))
				continue;

			score += PIECE_VALUES.getOrDefault(piece, 0);
			score += Tables.getTableValue(endgame, square, piece);
		}

		return board.getSideToMove() == Side.WHITE ? -score : score;
	}
}
