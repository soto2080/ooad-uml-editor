package tw.mhyang.ooad.project.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class classObject extends basicObject{
    public classObject(double x, double y, GraphicsContext gc){
        super(x,y,gc);
        drawGraph();
    }

    @Override
    public void drawGraph(){
        gc.setLineWidth(4);
        gc.setStroke(Color.AQUAMARINE);
        //Draw Stroke Rectangle
        gc.strokeRect(getX(), getY(), itemWidth, itemHeight);
    }
}
