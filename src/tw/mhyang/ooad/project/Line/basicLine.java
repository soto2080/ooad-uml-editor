package tw.mhyang.ooad.project.Line;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Transform;
import tw.mhyang.ooad.project.Item.Position;
import tw.mhyang.ooad.project.Item.basicObject;

import java.util.ArrayList;

public class basicLine {
    protected final GraphicsContext gc;
    protected Transform transform;
    private final basicObject point1,point2;
    //tmp position
    private int index1,index2;
    private Position pos1,pos2;
    protected double length,angle;

    public basicLine(basicObject point1, int index1, basicObject point2, int index2, GraphicsContext gc){
        this.gc = gc;
        this.point1 = point1;
        this.point2 = point2;
        this.index1 = index1;
        this.index2 = index2;
        draw();
    }
    protected void updatePos(){
        pos1 = point1.getPortPos(index1);
        pos2 = point2.getPortPos(index2);

    }
    public void draw(){
        updatePos();
        calculateTransform();
    }

    protected void calculateTransform(){
        double dx=pos2.getX()-pos1.getX(),dy=pos2.getY()-pos1.getY();
        angle = Math.atan2(dy,dx);
        length = Math.sqrt(dx*dx+dy*dy);
        transform = Transform.translate(pos1.getX(),pos1.getY());
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
    }
}
