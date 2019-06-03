package tw.mhyang.ooad.project.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.ArrayList;

public class basicObject {
    GraphicsContext gc;
    //繪圖參數
    double itemWidth = 150;
    double itemHeight = 75;
    private double portSize = 7.5;
    //座標
    private double x;
    private double y;

    //Z-Index
    private int z;
    //物件名稱
    private String name="Nobody";
    //是否被選取
    boolean selected;
    private int portOnClick=0;
    //Port position
    private ArrayList<Position> pos;
    basicObject(double x,double y,GraphicsContext gc){
        this.x=x;
        this.y=y;
        this.gc=gc;
        pos = updatePortPos();
        selected=false;
    }

    double getX() {
        return x;
    }
    double getY() {
        return y;
    }

    public void setDiff(double dx,double dy){
        this.x+=dx;
        this.y+=dy;
        pos = updatePortPos();
    }
    public int getPortOnClick(){return portOnClick;}
    public Position getPortPos(int index){return pos.get(index);}
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected(){return selected;}

    public boolean isOnClicked(double x,double y){
        if((this.x < x)&&(x < this.x+itemWidth)&&(this.y < y)&&(y < this.y+ itemHeight)){
            //Recording port which is clicked and set the object is selected.
            double tmp=distance(x,y,pos.get(0));
            portOnClick=0;
            for(int i=0;i<pos.size();++i){
                if(distance(x,y,pos.get(i))<tmp){
                    tmp=distance(x,y,pos.get(i));
                    portOnClick=i;
                }
            }
            selected = true;
            return true;
        }
        selected = false;
        return false;
    }
    public boolean isUnderDragged(double x1,double y1,double x2,double y2){
        // check the dragged event start and end is fully covered the graph
        // from left topside to right downside
        if((this.x > x1)&&(x2 > this.x+itemWidth)&&(this.y > y1)&&(y2 > this.y+ itemHeight)){
            selected = true;
            return true;
        }
        // from right downside to left topside
        else if((this.x > x2)&&(x1 > this.x+itemWidth)&&(this.y > y2)&&(y1 > this.y+ itemHeight)){
            selected = true;
            return true;
        }
        selected = false;
        return false;
    }

    public boolean isConnectable(){
        return true;
    }
    public void drawGraph(){}
    private ArrayList<Position> updatePortPos(){
        //updating ports' coordinate
        ArrayList<Position> tmp = new ArrayList<>();
        tmp.add(new Position(x,y+itemHeight/2));
        tmp.add(new Position(x+itemWidth/2,y+itemHeight));
        tmp.add(new Position(x+itemWidth,y+itemHeight/2));
        tmp.add(new Position(x+itemWidth/2,y));
        return tmp;
    }

    public void drawConnectionPort(){
        gc.setFill(Color.BLACK);
        pos.forEach(obj->{ gc.fillRect(obj.getX(),obj.getY(),portSize,portSize);});
    }

    private double distance(double x,double y,Position pos){
        return Math.sqrt(Math.pow(x-pos.getX(),2)+Math.pow(y-pos.getY(),2));
    }
    public void drawName(){
        gc.setFill(Color.INDIANRED);
        gc.fillText(name,x+itemWidth/4,y+itemWidth/4,300);
    }
}
