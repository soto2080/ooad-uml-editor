package tw.mhyang.ooad.project.Item;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public final class groupedObject extends basicObject {
    private ArrayList<basicObject> arrayList;
    public groupedObject(GraphicsContext gc,ArrayList<basicObject> arrayList){
        super(0,0,gc);
        this.arrayList = arrayList;
    }

    @Override
    public void drawGraph(){
        arrayList.forEach(obj->obj.drawGraph());
    }
    @Override
    public void drawName(){
        arrayList.forEach(obj->obj.drawName());
    }
    @Override
    public void drawConnectionPort(){
        arrayList.forEach(obj->obj.drawConnectionPort());
    }
    @Override
    public void setDiff(double dx,double dy){
        arrayList.forEach(obj->obj.setDiff(dx,dy));
    }
    @Override
    public boolean isOnClicked(double x,double y){
        boolean onClick=false;
        for(basicObject obj:arrayList){
            if(obj.isOnClicked(x,y)) onClick=true;
        }
        if(onClick){
            selected = true;
            return true;
        }
        selected = false;
        return false;
    }
    @Override
    public boolean isUnderDragged(double x1,double y1,double x2,double y2){
        // check the dragged event start and end is fully covered the graph
        // from left topside to right downside
        boolean allOnDragged = true;
        for(basicObject obj:arrayList){
            if(!obj.isUnderDragged(x1, y1, x2, y2))
                allOnDragged = false;
        }
        // from right downside to left topside
        if(allOnDragged){
            selected = true;
            return true;
        }
        selected = false;
        return false;
    }
    @Override
    public boolean isConnectable(){
        return false;
    }
    public ArrayList<basicObject> unwrap(){
        return arrayList;
    }
}
