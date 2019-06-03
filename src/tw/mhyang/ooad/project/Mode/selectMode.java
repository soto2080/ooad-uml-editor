package tw.mhyang.ooad.project.Mode;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import tw.mhyang.ooad.project.Item.basicObject;
import tw.mhyang.ooad.project.Item.groupedObject;


import java.util.ArrayList;

public class selectMode extends Mode {
    //temp
    protected MouseEvent preEvent;
    protected double startX,startY,dx,dy;
    protected boolean draggingLayout;
    protected boolean isDraggingObject;

    public selectMode(){
    }
    @Override
    public void setHandler(){
        canvas.addEventFilter(MouseEvent.ANY,mouseHandler);
    }
    @Override
    public void removeHandler(){
        canvas.removeEventFilter(MouseEvent.ANY,mouseHandler);
    }
    EventHandler mouseHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            MouseEvent e = (MouseEvent) event;
            if(e.getEventType()==MouseEvent.MOUSE_PRESSED){
                draggingLayout = false;
                startX = e.getX();
                startY = e.getY();

                //Object selection
                objectArray.forEach((obj)->{
                    // Object is on clicked
                    if(obj.isOnClicked(e.getX(), e.getY())){
                        selectedObject = obj;
                        isDraggingObject = true;
                        preEvent=e;
                    }
                });
            }
            if(e.getEventType()==MouseEvent.DRAG_DETECTED){
                draggingLayout = true;
            }
            if(e.getEventType()==MouseEvent.MOUSE_DRAGGED){
                drawPortsOnDragged(e);
                if(isDraggingObject) {
                    calculateDiff(e);
                    drawMovingObject();
                    preEvent=e;
                }
            }
            if(e.getEventType()==MouseEvent.MOUSE_RELEASED){
                isDraggingObject = false;
                if(!draggingLayout){
                    drawPortsOnClicked(e);
                }
            }
        }
    };

    private void drawPortsOnClicked(MouseEvent e){
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
    private void drawPortsOnDragged(MouseEvent e){
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
    private void drawMovingObject(){
            selectedObject.setDiff(dx,dy);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            objectArray.forEach((obj)->{
                obj.drawGraph();
                obj.drawName();
            });
            //Draw Lines
            lineArray.forEach((obj->{obj.draw();}));
    }
    @Override
    public void changeName(String s){
        selectedObject.setName(s);
    }
    @Override
    public void wrapObject(){
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
    @Override
    public void UnwrapObject(){
            try {
                groupedObject groupedObject = (groupedObject) selectedObject;
                objectArray.addAll(groupedObject.unwrap());
                objectArray.remove(selectedObject);
                System.out.println("Unwrap");
            }catch (ClassCastException e){
                System.out.println("You sucks, it's not grouped.");
            }

    }
    private void calculateDiff(MouseEvent e){
        dx = e.getX() - preEvent.getX();
        dy = e.getY() - preEvent.getY();
    }
}
