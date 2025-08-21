package org.ecando;

import com.frequentlymisseddeadlines.chessuci.UciListener;
import com.frequentlymisseddeadlines.chessuci.UciProtocol;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import org.ecando.engine.Bot;
import org.ecando.engine.Version1;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Board board = new Board();
		Bot bot = new Version1();

		while (in.hasNextLine()) {
			String line = in.nextLine().trim();

			if (line.equals("uci")) {
				// Identify the engine
				System.out.println("id name NegamaxBot2");
				System.out.println("id author ECanDo");
				System.out.println("uciok");
				System.out.flush();

			} else if (line.equals("isready")) {
				// Engine is ready to go
				System.out.println("readyok");
				System.out.flush();

			} else if (line.startsWith("position")) {
				if (line.contains("startpos")) {
					board = new Board(); // official startpos FEN
				}
				if (line.contains("fen")) {
					String fen = line.substring(line.indexOf("fen") + 4).trim();
					if (fen.contains("moves")) {
						fen = fen.substring(0, fen.indexOf("moves")).trim();
					}
					board.loadFromFen(fen);
				}
				if (line.contains("moves")) {
					String moves = line.substring(line.indexOf("moves") + 6).trim();
					for (String mv : moves.split(" ")) {
						if (mv.isBlank()) continue;

						// UCI format: e2e4, g1f3, e7e8q
						Square from = Square.fromValue(mv.substring(0, 2).toUpperCase());
						Square to = Square.fromValue(mv.substring(2, 4).toUpperCase());

						Move move;
						if (mv.length() == 5) {
							// promotion
							Side side = board.getSideToMove();
							PieceType promo = switch (mv.charAt(4)) {
								case 'q' -> PieceType.QUEEN;
								case 'r' -> PieceType.ROOK;
								case 'b' -> PieceType.BISHOP;
								case 'n' -> PieceType.KNIGHT;
								default -> throw new IllegalArgumentException();
							};
							move = new Move(from, to, Piece.make(side, promo));
						} else {
							move = new Move(from, to);
						}

						board.doMove(move);
					}
				}
			} else if (line.startsWith("go")) {
				Move move = bot.selectMove(board);
				System.out.println("bestmove " + move.toString());
				System.out.flush();

			} else if (line.equals("quit")) {
				// Exit cleanly
				break;
			} else if (line.equals("stop")) {
				// Stop search if running (stubbed)
				// You’d set a flag in a real searcher
			}
		}

		in.close();
		System.exit(0);
	}


}