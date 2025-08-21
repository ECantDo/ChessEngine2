package org.ecando.engine;

import com.github.bhlangonijr.chesslib.*;

import static org.ecando.engine.Version1.PIECE_VALUES;

public class Tables {

	private static final int[][] PAWN_TABLE = new int[][]{
			{ // GENERAL
					0, 0, 0, 0, 0, 0, 0, 0,     // Rank 1 (A1–H1)
					100, 100, 100, 100, 100, 100, 100, 100,     // Rank 2
					50, 50, 60, 70, 70, 60, 50, 50,     // Rank 3
					20, 20, 30, 60, 60, 30, 20, 20,     // Rank 4
					10, 10, 20, 50, 50, 20, 10, 10,     // Rank 5
					5, 5, 10, 30, 30, 10, 5, 5,     // Rank 6
					0, 0, 0, -20, -20, 0, 0, 0,     // Rank 7
					0, 0, 0, 0, 0, 0, 0, 0      // Rank 8
			},
			{ // ENDGAME
					0, 0, 0, 0, 0, 0, 0, 0,     // Rank 1
					100, 100, 100, 100, 100, 100, 100, 100,     // Rank 2
					140, 140, 150, 160, 160, 150, 140, 140,     // Rank 3
					180, 180, 200, 220, 220, 200, 180, 180,     // Rank 4
					240, 240, 260, 300, 300, 260, 240, 240,     // Rank 5
					320, 320, 350, 400, 400, 350, 320, 320,     // Rank 6
					450, 450, 500, 600, 600, 500, 450, 450,     // Rank 7
					0, 0, 0, 0, 0, 0, 0, 0      // Rank 8
			}
	};

	private static final int[] KNIGHT_TABLE = {
			-50, -40, -30, -30, -30, -30, -40, -50,
			-40, -20, 0, 5, 5, 0, -20, -40,
			-30, 5, 10, 15, 15, 10, 5, -30,
			-30, 0, 15, 20, 20, 15, 0, -30,
			-30, 5, 15, 20, 20, 15, 5, -30,
			-30, 0, 10, 15, 15, 10, 0, -30,
			-40, -20, 0, 0, 0, 0, -20, -40,
			-50, -40, -30, -30, -30, -30, -40, -50
	};

	private static final int[] BISHOP_TABLE = {
			-20, -10, -10, -10, -10, -10, -10, -20,
			-10, 5, 0, 0, 0, 0, 5, -10,
			-10, 10, 10, 10, 10, 10, 10, -10,
			-10, 0, 10, 10, 10, 10, 0, -10,
			-10, 5, 5, 10, 10, 5, 5, -10,
			-10, 0, 5, 10, 10, 5, 0, -10,
			-10, 0, 0, 0, 0, 0, 0, -10,
			-20, -10, -10, -10, -10, -10, -10, -20,
	};

	private static final int[] ROOK_TABLE = {
			0, 0, 0, 5, 5, 0, 0, 0,
			-5, 0, 0, 0, 0, 0, 0, -5,
			-5, 0, 0, 0, 0, 0, 0, -5,
			-5, 0, 0, 0, 0, 0, 0, -5,
			-5, 0, 0, 0, 0, 0, 0, -5,
			-5, 0, 0, 0, 0, 0, 0, -5,
			5, 10, 10, 10, 10, 10, 10, 5,
			0, 0, 0, 0, 0, 0, 0, 0,
	};

	private static final int[] QUEEN_TABLE = {
			-20, -10, -10, -5, -5, -10, -10, -20,
			-10, 0, 0, 0, 0, 0, 0, -10,
			-10, 0, 5, 5, 5, 5, 0, -10,
			-5, 0, 5, 5, 5, 5, 0, -5,
			0, 0, 5, 5, 5, 5, 0, -5,
			-10, 5, 5, 5, 5, 5, 0, -10,
			-10, 0, 5, 0, 0, 0, 0, -10,
			-20, -10, -10, -5, -5, -10, -10, -20,
	};

	private static final int[][] KING_TABLE = {{
			-30, -40, -40, -50, -50, -40, -40, -30,
			-30, -40, -40, -50, -50, -40, -40, -30,
			-30, -40, -40, -50, -50, -40, -40, -30,
			-30, -40, -40, -50, -50, -40, -40, -30,
			-20, -30, -30, -40, -40, -30, -30, -20,
			-10, -20, -20, -20, -20, -20, -20, -10,
			20, 20, 0, 0, 0, 0, 20, 20,
			20, 30, 10, 0, 0, 10, 30, 20,
	}, {
			-50, -40, -30, -20, -20, -30, -40, -50,
			-30, -20, -10, 0, 0, -10, -20, -30,
			-30, -10, 20, 30, 30, 20, -10, -30,
			-30, -10, 30, 40, 40, 30, -10, -30,
			-30, -10, 30, 40, 40, 30, -10, -30,
			-30, -10, 20, 30, 30, 20, -10, -30,
			-30, -30, 0, 0, 0, 0, -30, -30,
			-50, -30, -30, -30, -30, -30, -30, -50,
	}};

	public static int getTableValue(boolean isEndgame, Square square, Piece piece) {
		PieceType pieceType = piece.getPieceType();
		System.out.println("Piece: " + piece + " PieceType: " + pieceType);
		if (pieceType == null || pieceType == PieceType.NONE)
			return 0;

		Side side = piece.getPieceSide();
		int endgameTable = isEndgame ? 1 : 0;

		int idx = square.ordinal();
		if (side == Side.BLACK)
			idx = flipIndex(idx);

		int result = switch (pieceType) {
			case PAWN -> PAWN_TABLE[endgameTable][idx];
			case KNIGHT -> KNIGHT_TABLE[idx];
			case BISHOP -> BISHOP_TABLE[idx];
			case ROOK -> ROOK_TABLE[idx];
			case QUEEN -> QUEEN_TABLE[idx];
			case KING -> KING_TABLE[endgameTable][idx];
			default -> 0;
		};

		if (side == Side.BLACK)
			return -result;
		return result;

	}

	public static boolean isEndgame(Board board) {
		int material = 0;

		for (Square square : Square.values()) {
			Piece piece = board.getPiece(square);
			if (piece != null && piece.getPieceType() != PieceType.KING && piece.getPieceType() != PieceType.NONE) {
				material += Math.abs(PIECE_VALUES.getOrDefault(piece, 0));
			}
		}

		return material < 2500; // If there's < 25 points of material (approx 2 rooks or a queen)
	}

	private static int flipIndex(int index) {
		int rank = index / 8;
		int file = index % 8;
		return (7 - rank) * 8 + file;
	}


}
