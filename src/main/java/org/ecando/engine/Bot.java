package org.ecando.engine;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public interface Bot {
	Move selectMove(Board board);
}
