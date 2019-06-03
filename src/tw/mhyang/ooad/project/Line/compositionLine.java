package tw.mhyang.ooad.project.Line;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import tw.mhyang.ooad.project.Item.basicObject;

public final class compositionLine extends basicLine {
    private final int RECT_SIZE = 8;
    public compositionLine(basicObject point1, int index1, basicObject point2, int index2, GraphicsContext gc){
        super(point1,index1,point2,index2,gc);
    }
    @Override
    public void draw(){
        gc.setLineWidth(2);
        gc.setStroke(Color.GAINSBORO);
        updatePos();
        calculateTransform();
        gc.setTransform(new Affine(transform));
        gc.strokeLine(0, 0, length, 0);
        gc.fillRect(0,-RECT_SIZE/2,RECT_SIZE,RECT_SIZE);
        gc.fillRect(length-RECT_SIZE/2,-RECT_SIZE/2,RECT_SIZE,RECT_SIZE);
        gc.setTransform(new Affine());
    }
}