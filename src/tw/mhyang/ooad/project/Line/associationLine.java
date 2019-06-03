package tw.mhyang.ooad.project.Line;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.transform.Affine;
import tw.mhyang.ooad.project.Item.basicObject;

public final class associationLine extends basicLine {
    public associationLine(basicObject point1, int index1, basicObject point2, int index2, GraphicsContext gc){
        super(point1,index1,point2,index2,gc);
    }

    @Override
    public void draw(){
        gc.setLineWidth(2);
        gc.setStroke(Color.GOLD);
        updatePos();
        calculateTransform();
        gc.setTransform(new Affine(transform));
        gc.strokeLine(0, 0, length, 0);
        gc.setTransform(new Affine());
    }
}
