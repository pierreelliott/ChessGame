package APOChess.gui.controller;

import APOChess.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends MainController {
    @FXML
    VBox vBoxID;

    @FXML
    GridPane gridID;

    @FXML
    AnchorPane anchorID;

    @FXML
    BorderPane borderPane;
    @FXML
    Pane paneTop;
    @FXML
    Pane paneLeft;
    @FXML
    Pane paneBottom;
    @FXML
    Pane paneRight;

    public GameController(Main main) {
        super(main);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.WHITE;
                else color = Color.BLACK;
                square.setFill(color);

                gridID.add(square, col, row);

                ImageView imgv = new ImageView(new Image(getClass()
                        .getResourceAsStream("../../res/BlackBishop.png")));
                gridID.add(imgv, col, row);
                square.widthProperty().bind(gridID.widthProperty().divide(size));
                square.heightProperty().bind(gridID.heightProperty().divide(size));

                imgv.fitWidthProperty().bind(gridID.widthProperty().divide(size));
                imgv.fitHeightProperty().bind(gridID.heightProperty().divide(size));

            }
        }

        anchorID.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                resizeGrid();
            }
        });
        anchorID.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                resizeGrid();
            }
        });

    }

    public void resizeGrid(){
        if(anchorID.getWidth() > anchorID.getHeight()){
            AnchorPane.setLeftAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
            AnchorPane.setRightAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
        } else {
            AnchorPane.setTopAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
            AnchorPane.setBottomAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
        }

        System.out.println("Width: " + anchorID.getWidth() + "Height: " + anchorID.getHeight());
    }

    @FXML
    public void showMenu(ActionEvent event){
        main.showMenu();
    }

}
