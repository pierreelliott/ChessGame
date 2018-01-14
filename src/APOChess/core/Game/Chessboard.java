package APOChess.core.Game;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Pieces.*;

import java.util.ArrayList;
import java.util.logging.Level;

public class Chessboard {

    /**
     * The board
     */
    private Tile[][] board;

    /**
     * Main pointer
     */
    private Main main;

    public Chessboard(Main main) {
        this.main = main;
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
        int line = 0;
        ColorEnum color = ColorEnum.BLACK;
        this.board[0][line].setPiece(new PieceRook(color));
        this.board[1][line].setPiece(new PieceKnight(color));
        this.board[2][line].setPiece(new PieceBishop(color));
        this.board[3][line].setPiece(new PieceQueen(color));
        this.board[4][line].setPiece(new PieceKing(color));
        this.board[5][line].setPiece(new PieceBishop(color));
        this.board[6][line].setPiece(new PieceKnight(color));
        this.board[7][line].setPiece(new PieceRook(color));
        line = 1;
        for (int i = 0; i < 8; i++){
            this.board[i][line].setPiece(new PiecePawn(color));
        }
        line = 7;
        color = ColorEnum.WHITE;
        this.board[0][line].setPiece(new PieceRook(color));
        this.board[1][line].setPiece(new PieceKnight(color));
        this.board[2][line].setPiece(new PieceBishop(color));
        this.board[3][line].setPiece(new PieceQueen(color));
        this.board[4][line].setPiece(new PieceKing(color));
        this.board[5][line].setPiece(new PieceBishop(color));
        this.board[6][line].setPiece(new PieceKnight(color));
        this.board[7][line].setPiece(new PieceRook(color));
        line = 6;
        for (int i = 0; i < 8; i++){
            this.board[i][line].setPiece(new PiecePawn(color));
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

    /**
     * Get the Tile at a positon
     * @param p The position of the Tile
     * @return Tile
     */
    public Tile getTile(Position p){
        return getTile(p.getPosX(), p.getPosY());
    }

    /**
     * Get the Tile at a position expressed by columns and rows
     * @param col Columns
     * @param row Row
     * @return Tile
     */
    public Tile getTile(int col, int row){
        return board[col][row];
    }

    /**
     * <em>True</em> if there is a Piece not empty a the position
     * @param position Position The Position of the Piece
     * @return bool
     */
    public boolean isOccuped(Position position){
        main.logger.log(Level.FINER, "Position isOccuped " + position.toString());
        if(!isOnGrid(position)){
            main.logger.log(Level.SEVERE, "isOccuped tested out of the board");
            return false;
        }
        return board[position.getPosX()][position.getPosY()].isOccuped();
    }

    /**
     * <em>True</em> if the position is on the grid.
     * @param position Position
     * @return bool
     */
    public boolean isOnGrid(Position position){
        return position.getPosX() >= 0 && position.getPosX() < 8 && position.getPosY() >= 0 && position.getPosY() < 8;
    }

    /**
     * Return a list of positions where the piece can be moved.
     * Need the position of the piece picked.
     * @param col Grid's colomn where is placed the piece.
     * @param row Grdi's row where is placed the piece.
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getAvailableMoves(int col, int row){
        ArrayList<Position> positions = new ArrayList<>();

        if(!isOnGrid(new Position(col, row))){
            main.logger.log(Level.WARNING, "Not on grid " + (new Position(col, row)).toString());
            return positions;
        }

        positions = board[col][row].getPiece().getPossibleMoves(new Position(col, row), this);
        return positions;
    }

    /**
     * Return a list of positions where the piece can be moved thanks to special moves.
     * Need the position of the piece picked.
     * @param col Grid's colomn where is placed the piece.
     * @param row Grdi's row where is placed the piece.
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getSpecialeMoves(int col, int row){
        ArrayList<Position> positions = new ArrayList<>();

        if(!isOnGrid(new Position(col, row))){
            main.logger.log(Level.WARNING, "Not on grid " + (new Position(col, row)).toString());
            return positions;
        }

        positions = board[col][row].getPiece().getSpecialMoves(new Position(col, row), this);
        return positions;
    }
}
