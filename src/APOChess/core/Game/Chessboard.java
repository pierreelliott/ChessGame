package APOChess.core.Game;

import APOChess.core.Pieces.*;

public class Chessboard {
    private Tile[][] board;

    public Chessboard() {
        this.board = new Tile[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Tile();
            }
        }
    }

    /**
     * Initialize the board for a new game, positioning the black pieces to the upper side (line number = 7) and the white pieces to the lower side
     */
    public void initialize() {
        int line = 7;
        for (String color = "black"; !color.equalsIgnoreCase("white"); color = "white", line = 0){
            this.board[0][line].setPiece(new Rook(color));
            this.board[1][line].setPiece(new Knight(color));
            this.board[2][line].setPiece(new Bishop(color));
            this.board[3][line].setPiece(new Queen(color));
            this.board[4][line].setPiece(new King(color));
            this.board[5][line].setPiece(new Bishop(color));
            this.board[6][line].setPiece(new Knight(color));
            this.board[7][line].setPiece(new Rook(color));

            line += color.equals("black") ? -1 : 1;

            for (int i = 0; i < 8; i++){
                this.board[i][line].setPiece(new Pawn(color));
            }
        }
    }

    /**
     * Initialize the board with a configuration from the 'filepath' parameter
     * @param filepath The path to the text file containing the configuration to load
     */
    public void initialize(String filepath) {
        File configFile;
        try {
            configFile = new File(filepath);

            this.board = configFile.getBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        configFile = null;
    }
}
