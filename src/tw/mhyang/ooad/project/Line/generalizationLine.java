package tw.mhyang.ooad.project.Line;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import tw.mhyang.ooad.project.Item.basicObject;

public final class generalizationLine extends basicLine {
    private final int ARR_SIZE = 8;
    public generalizationLine(basicObject point1, int index1, basicObject point2, int index2, GraphicsContext gc){
        super(point1,index1,point2,index2,gc);
    }
    @Override
    public void draw(){
        gc.setLineWidth(2);
        gc.setStroke(Color.FIREBRICK);
        updatePos();
        calculateTransform();
        gc.setTransform(new Affine(transform));
        gc.strokeLine(0, 0, length, 0);
        gc.fillPolygon(new double[]{length, length - ARR_SIZE, length - ARR_SIZE, length}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0},4);
        gc.setTransform(new Affine());
    }
}