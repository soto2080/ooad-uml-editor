package tw.mhyang.ooad.project.Mode;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import tw.mhyang.ooad.project.Item.basicObject;
import tw.mhyang.ooad.project.Line.basicLine;

import java.util.ArrayList;

public class Mode{
    protected Canvas canvas;
    protected GraphicsContext gc;
    //物件陣列
    protected ArrayList<basicObject> objectArray;
    protected ArrayList<basicLine> lineArray;
    protected basicObject selectedObject;

    public Mode(){
    }
    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
    }
    public void setHandler(){}
    public void removeHandler(){}
    public void changeName(String s){}
    public void wrapObject(){}
    public void UnwrapObject(){}
    public void setObjectArray(ArrayList<basicObject> objectArray){this.objectArray = objectArray;}
    public void setLineArray(ArrayList<basicLine> lineArray){this.lineArray = lineArray;}

}
