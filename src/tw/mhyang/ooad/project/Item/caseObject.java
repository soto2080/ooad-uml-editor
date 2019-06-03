package tw.mhyang.ooad.project.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class caseObject extends basicObject {
    public caseObject(double x, double y, GraphicsContext gc){
        super(x,y,gc);
        drawGraph();
    }

    @Override
    public void drawGraph(){
        gc.setLineWidth(4);
        gc.setStroke(Color.ANTIQUEWHITE);
        //Draw Stroke Oval
        gc.strokeOval(getX(), getY(), itemWidth, itemHeight);
    }
}
