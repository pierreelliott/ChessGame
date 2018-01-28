package APOChess.gui.custom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomCell {

    /**
     * Default color of the cell
     */
    private Color defaultColor;

    /**
     * Rectangle linked to the cell
     */
    private Rectangle rectangle;

    /**
     * Column of the cell second to the board NOT the gui grid !
     */
    private int col;

    /**
     * Row of the cell second to the board NOT the gui grid !
     */
    private int row;

    /**
     * ImageView linked to the cell
     */
    private ImageView imageView;

    /**
     * Current color of the cell, which can differ from default color
     */
    private Color color;

    /**
     * Constructor
     * @param col int Column of the cell second to the board NOT the gui grid !
     * @param row int Row of the cell second to the board NOT the gui grid !
     * @param rectangle Rectangle linked to the cell
     * @param defaultColor Color default and current color of the cell
     * @param imageView ImageView linked to the cell
     */
    public CustomCell(int col, int row, Rectangle rectangle, Color defaultColor, ImageView imageView){
        this.col = col;
        this.row = row;
        this.rectangle = rectangle;
        this.defaultColor = defaultColor;
        this.color = defaultColor;
        this.imageView = imageView;
    }

    /**
     * Get colomn of the cell second to the board NOT the gui grid !
     * @return int
     */
    public int getCol() {
        return col;
    }

    /**
     * Get row of the cell second to the board NOT the gui grid !
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Apply default color to the cell
     */
    public void applyDefaultColor(){
        rectangle.setFill(defaultColor);
        color = defaultColor;
    }

    /**
     * Set current color
     * Return CustomCell updated
     * @param c Color
     * @return CustomCell
     */
    public CustomCell setColor(Color c) {
        rectangle.setFill(c);
        color = c;
        return this;
    }

    /**
     * Set image
     * Return CustomCell updated
     * @param s String
     * @return CustomCell
     */
    public CustomCell setImage(String s){
        imageView.setImage(
                new Image(
                        getClass().getResourceAsStream("../../res/" + s)));
        return this;
    }
}
