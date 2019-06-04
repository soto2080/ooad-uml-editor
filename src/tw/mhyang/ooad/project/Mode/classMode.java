package tw.mhyang.ooad.project.Mode;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import tw.mhyang.ooad.project.Item.classObject;

public class classMode extends Mode {
    public classMode(){}
    @Override
    public  void setHandler(){
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
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                //ObjectCreation
                objectArray.add(new classObject(e.getX(), e.getY(), gc));
            }
        }
    };
}
