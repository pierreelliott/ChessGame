package APOChess.gui.custom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomCell {

    private Color defaultColor;

    private Rectangle rectangle;

    private int col;

    private int row;

    private ImageView imageView;

    private Color color;

    public CustomCell(int col, int row, Rectangle rectangle, Color defaultColor, ImageView imageView){
        this.col = col;
        this.row = row;
        this.rectangle = rectangle;
        this.defaultColor = defaultColor;
        this.color = defaultColor;
        this.imageView = imageView;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setDefaultColor(){
        rectangle.setFill(defaultColor);
        color = defaultColor;
    }

    public CustomCell setColor(Color c) {
        rectangle.setFill(c);
        color = c;
        return this;
    }

    public CustomCell setImage(String s){
        imageView.setImage(
                new Image(
                        getClass().getResourceAsStream("../../res/" + s)));
        return this;
    }

    public Color getColor() {
        return color;
    }
}
