package APOChess.core.Game;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Pieces.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class File {
    private ArrayList<String> white;
    private ArrayList<String> black;
    private Tile[][] board;
    
    /**
     *  Used to determine if the pieces created follow chess rules (ie, 8 Pawns, 2 Rooks, ...)
     *  Unities represents the number of Pawns, ...
     *  */
    private int piecesCounter;

    /**
     * 
     * @param filepath The relative path to the file which will be loaded
     * @throws IOException If an error occurs while reading the file
     * @throws Exception If the file contains incoherent data
     */
    public File(String filepath) throws IOException, Exception {
        BufferedReader file = new BufferedReader(new FileReader(filepath));
        white = new ArrayList<>();
        black = new ArrayList<>();

        String line, color = null;
        while ((line = file.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            } else {
                if(line.equalsIgnoreCase("white")){
                    /* 'color' corresponds to which arrayList will be used to store the data in */
                    color = "white";
                } else if (line.equalsIgnoreCase("black")){
                    color = "black";
                } else {
                    if(color == null) {
                        throw new Exception("Error file");
                    }

                    if(color.equalsIgnoreCase("white")) {
                        white.add(line);
                    } else if(color.equalsIgnoreCase("black")) {
                        black.add(line);
                    } else {
                        /* If the line hasn't been recognized, then throw an Exception */
                        throw new Exception("Error file");
                    }
                }
            }
        }
    }

    /**
     * 
     * @return A new board created with the configuration file (the one passed in the constructor's parameters)
     * @throws Exception If an error occurs while creating the board
     */
    public Tile[][] getBoard() throws Exception {
        /* Create the board */
        createBoard();
        
        return this.board;
    }

    /**
     * 
     * @throws Exception 
     */
    private void createBoard() throws Exception {
        this.board = new Tile[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = new Tile();
            }
        }

        int length = white.size(), i;
        piecesCounter = 0;
        String[] line;
        for (i = 0; i < length; i++) {
            line = white.get(i).split("\t");
            line[1] = line[1].split("\n")[0];
            addPiece(createPiece(line[0], ColorEnum.WHITE), line[1]);
        }
        if(piecesCounter != 112228) {
            throw new Exception("Error number of chess pieces");
        }

        length = black.size();
        piecesCounter = 0;
        for (i = 0; i < length; i++) {
            line = black.get(i).split("\t");
            line[1] = line[1].split("\n")[0];
            addPiece(createPiece(line[0], ColorEnum.WHITE), line[1]);
        }
        if(piecesCounter != 112228) {
            throw new Exception("Error number of chess pieces");
        }
    }

    /**
     * Add the passed chess piece in the board (or not, if its position is 0)
     * @param piece The chess piece to add in the board
     * @param pos A string containing the position where to add the piece. 0 if the piece isn't in the board, [a-h][0-7] otherwise
     * @throws Exception
     */
    private void addPiece(Piece piece, String pos) throws Exception {
        int posX, posY;

        /* Just to be sure the letter in the position is in lower case, for the later modulo */
        pos = pos.toLowerCase();

        if(pos.length() == 1) {

            if(!pos.equalsIgnoreCase("0")) {
                /* If the only character for the position isn't set to 0, there's an error in the file */
                throw new Exception("Error file");
            }

            /* If the position is 0, we do nothing (the piece isn't on the board anymore) */

        } else if(pos.length() == 2) {

            posX = ( (int)pos.charAt(0) )%'a';
            posY = Integer.parseInt(pos.substring(1,2))-1;

            if(posX < 0 || posY > 7 || posY < 0 || posY > 7) {
                /* If the position parsed is out of bound of the board, throw an error */
                throw new Exception("Error out of bound position");
            }

            this.board[posX][posY].setPiece(piece);

        } else {
            /* If the position isn't 1 or 2 characters, there's an error in the file */
            throw new Exception("Error file");
        }
    }

    /**
     * Create a chess piece parsing the type parameter
     * @param type A string containing type's character(s) (one or two) of the chess piece
     * @param color A ColorEnum containing the color of the chess piece
     * @return Returns the piece created with the previous parameters
     * @throws Exception If the piece isn't initialized before return, the file contains an error, which this method doesn't handle
     */
    private Piece createPiece(String type, ColorEnum color) throws Exception {
        Piece piece = null;

        if(type.length() > 1) {
            if (type.matches("k.")) {
                /* If the type of the piece is 'k' followed by a number, it means it is a PieceKnight
                 * So we change its type to be a unique letter (different from the PieceKing's one) */
                type = "n";
            } else {
                type = type.substring(0,1);
            }
        }

        /* Create the piece depending of the type of the piece */
        switch (type) {
            case "p":
                piece = new PiecePawn(color);
                piecesCounter += 1;
                break;
            case "t":
                piece = new PieceRook(color);
                piecesCounter += 10;
                break;
            case "n":
                piece = new PieceKnight(color);
                piecesCounter += 100;
                break;
            case "b":
                piece = new PieceBishop(color);
                piecesCounter += 1000;
                break;
            case "k":
                piece = new PieceKing(color);
                piecesCounter += 10000;
                break;
            case "q":
                piece = new PieceQueen(color);
                piecesCounter += 100000;
                break;
        }

        /* If the piece hasn't been initialized, there's an error in the file */
        if (piece == null) {
            throw new Exception("Error file");
        }

        return piece;
    }
}
