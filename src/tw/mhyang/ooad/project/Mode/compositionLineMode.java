package tw.mhyang.ooad.project.Mode;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import tw.mhyang.ooad.project.Item.basicObject;
import tw.mhyang.ooad.project.Line.compositionLine;

public class compositionLineMode extends Mode{
    public compositionLineMode(){

    }
    @Override
    public  void setHandler(){
        canvas.addEventFilter(MouseEvent.ANY,mouseHandler);
    }
    @Override
    public void removeHandler(){
        canvas.removeEventFilter(MouseEvent.ANY,mouseHandler);
    }
    private basicObject linePoint1;
    private basicObject linePoint2;
    EventHandler mouseHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            MouseEvent e = (MouseEvent) event;
            if(e.getEventType()==MouseEvent.MOUSE_CLICKED){
                if(linePoint1 !=null&&linePoint2!=null&& linePoint1 !=linePoint2) {
                    if(linePoint1.isConnectable()&&linePoint2.isConnectable()) {
                            lineArray.add(new compositionLine(linePoint1,linePoint1.getPortOnClick(), linePoint2,linePoint2.getPortOnClick(), gc));
                        //Clean Click Status
                        linePoint1 = null;
                        linePoint2 = null;
                    }
                    else
                        System.out.println("Grouped Object can't be connected");
                }
            }
            if(e.getEventType()==MouseEvent.MOUSE_PRESSED){
                //Object selection
                objectArray.forEach((obj)->{
                    // Object is on clicked
                    if(obj.isOnClicked(e.getX(), e.getY())){
                        //Line ModeSelect
                            linePoint1=linePoint2;
                            linePoint2=obj;
                        }
                    });
            }
        }
    };

}
