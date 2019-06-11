package tw.mhyang.ooad.project.Mode;

public class modeFactory {
    public Mode getMode(String str){
        Mode mode = null;
        switch (str){
            case "selectBtn":
                mode = new selectMode();
                break;
            case "associationBtn":
                mode = new associationLineMode();
                break;
            case "generalizationBtn":
                mode = new generalizationLineMode();
                break;
            case "compositionBtn":
                mode = new compositionLineMode();
                break;
            case "classBtn":
                mode = new classMode();
                break;
            case "caseBtn":
                mode = new caseMode();
            default:
                break;
        }
        return mode;
    }
}
