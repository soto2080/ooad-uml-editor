package tw.mhyang.ooad.project.Manager;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import tw.mhyang.ooad.project.Item.basicObject;
import tw.mhyang.ooad.project.Item.caseObject;
import tw.mhyang.ooad.project.Item.classObject;
import tw.mhyang.ooad.project.Item.groupedObject;
import tw.mhyang.ooad.project.Line.associationLine;
import tw.mhyang.ooad.project.Line.basicLine;
import tw.mhyang.ooad.project.Line.compositionLine;
import tw.mhyang.ooad.project.Line.generalizationLine;
import tw.mhyang.ooad.project.Mode;

import java.util.ArrayList;

public final class ObjectManager {
    private Mode mode;
    private final Canvas canvas;
    private final GraphicsContext gc;

    //物件陣列
    private final ArrayList<basicObject> objectArray;
    private final ArrayList<basicLine> lineArray;
    //temp
    private MouseEvent preEvent;
    private double startX,startY,dx,dy;
    private boolean draggingLayout;
    private boolean isDraggingObject;

    private basicObject linePoint1;
    private basicObject linePoint2;
    private basicObject selectedObject;

    public ObjectManager(Canvas canvas){
        this.canvas=canvas;
        gc = canvas.getGraphicsContext2D();
        objectArray = new ArrayList<>();
        lineArray = new ArrayList<>();
        init();
    }

    private void init(){
        //Catch mouse events on Canvas
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
            //ObjectCreation
            if(mode==Mode.CLASS){
                objectArray.add(new classObject(e.getX(), e.getY(),gc));
            }else if(mode==Mode.USECASE){
                objectArray.add(new caseObject(e.getX(), e.getY(),gc));
            }else{
                if(linePoint1 !=null&&linePoint2!=null&& linePoint1 !=linePoint2) {
                    if(linePoint1.isConnectable()&&linePoint2.isConnectable()) {
                        if(mode == Mode.ASSOCIATION)
                            lineArray.add(new associationLine(linePoint1,linePoint1.getPortOnClick(), linePoint2,linePoint2.getPortOnClick(), gc));
                        if(mode == Mode.GENERALIZATION)
                            lineArray.add(new generalizationLine(linePoint1,linePoint1.getPortOnClick(), linePoint2,linePoint2.getPortOnClick(), gc));
                        if(mode == Mode.COMPOSITION)
                            lineArray.add(new compositionLine(linePoint1,linePoint1.getPortOnClick(), linePoint2,linePoint2.getPortOnClick(), gc));
                        //Clean Click Status
                        linePoint1 = null;
                        linePoint2 = null;
                    }
                    else
                        System.out.println("Grouped Object can't be connected");
                }
            }
        });

        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED,e->{
            //Select Mode
            if(mode==Mode.SELECT) {
                draggingLayout = false;
                startX = e.getX();
                startY = e.getY();
            }
            //Object selection
            objectArray.forEach((obj)->{
                // Object is on clicked
                if(obj.isOnClicked(e.getX(), e.getY())){
                    //Line Mode
                    if(mode==Mode.ASSOCIATION||mode==Mode.COMPOSITION||mode==Mode.GENERALIZATION) {
                        linePoint1=linePoint2;
                        linePoint2=obj;
                    }
                    selectedObject = obj;
                    isDraggingObject = true;
                    preEvent=e;
                }
            });
        });

        // Detect Canvas Dragging event
        canvas.addEventFilter(MouseEvent.DRAG_DETECTED,e->{
            if(mode==Mode.SELECT) { draggingLayout = true;}
        });

        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED,e->{
            if(mode==Mode.SELECT){
                drawPortsOnDragged(e);
            }
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED,e->{
            isDraggingObject = false;
            if(mode==Mode.SELECT&&!draggingLayout){
                drawPortsOnClicked(e);
            }
        });
        //Catch object move event
        canvas.setOnMouseDragged(e->{
            if(mode==Mode.SELECT&&isDraggingObject) {
                calculateDiff(e);
                drawMovingObject();
                preEvent=e;
            }
        });
    }

    public void setMode(Mode mode){
        this.mode=mode;
        //Clean LinePoint selections
        if(mode==Mode.ASSOCIATION||mode==Mode.COMPOSITION||mode==Mode.GENERALIZATION) {
            linePoint1=null;
            linePoint2=null;
        }
    }

    private void drawPortsOnClicked(MouseEvent e){
        if(mode==Mode.SELECT) {
            // Must redrawing everything here
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            objectArray.forEach((obj) -> {
                // Drawing basic graph
                obj.drawGraph();
                obj.drawName();
                // Object is selected, then drawing ports
                if (obj.isOnClicked(e.getX(), e.getY())) {
                    selectedObject = obj;
                    obj.drawConnectionPort();
                }
            });
            //Draw Lines
            lineArray.forEach((obj->{obj.draw();}));
        }
    }
    private void drawPortsOnDragged(MouseEvent e){
        if(mode==Mode.SELECT){
            // Must redrawing everything here
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            objectArray.forEach((obj)->{
                // Drawing basic graph
                obj.drawGraph();
                obj.drawName();
                // Object is selected, then drawing ports
                if(obj.isUnderDragged(startX,startY,e.getX(), e.getY()))
                    obj.drawConnectionPort();
            });
            //Draw Lines and ports
            lineArray.forEach((obj->{obj.draw();}));
        }
    }
    private void drawMovingObject(){
        if(mode==Mode.SELECT){
        selectedObject.setDiff(dx,dy);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        objectArray.forEach((obj)->{
            obj.drawGraph();
            obj.drawName();
        });
        //Draw Lines
        lineArray.forEach((obj->{obj.draw();}));
        }
    }
    public void changeName(String s){
        if(mode==Mode.SELECT){ selectedObject.setName(s);}
    }
    public void wrapObject(){
        if(mode==Mode.SELECT) {
            ArrayList<basicObject> tmpArray = new ArrayList<>();
            objectArray.forEach((obj) -> {
                if (obj.isSelected())
                    tmpArray.add(obj);
            });
            tmpArray.forEach((obj) -> {
                objectArray.remove(obj);
            });
            objectArray.add(new groupedObject(gc, tmpArray));
            System.out.println("Wrap");
        }
    }
    public void UnwrapObject(){
        if(mode==Mode.SELECT){
            try {
                groupedObject groupedObject = (groupedObject) selectedObject;
                objectArray.addAll(groupedObject.unwrap());
                objectArray.remove(selectedObject);
                System.out.println("Unwrap");
            }catch (ClassCastException e){
                System.out.println("You sucks, it's not grouped.");
            }
        }
    }
    private void calculateDiff(MouseEvent e){
        dx = e.getX() - preEvent.getX();
        dy = e.getY() - preEvent.getY();
    }
}
