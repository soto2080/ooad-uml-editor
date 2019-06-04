package tw.mhyang.ooad.project;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import tw.mhyang.ooad.project.Item.basicObject;
import tw.mhyang.ooad.project.Line.basicLine;
import tw.mhyang.ooad.project.Mode.*;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    //物件陣列
    private final ArrayList<basicObject> objectArray = new ArrayList<>();
    private final ArrayList<basicLine> lineArray = new ArrayList<>();
    private final ArrayList<Button> btnArray = new ArrayList<>();
    private Mode mode;
    @FXML private Canvas canvas;
    @FXML private Button selectBtn;
    @FXML private Button associationBtn ;
    @FXML private Button generalizationBtn;
    @FXML private Button compositionBtn;
    @FXML private Button classBtn;
    @FXML private Button caseBtn;
    @FXML private MenuItem changeNameBtn;
    @FXML private MenuItem unGroupBtn;
    @FXML private MenuItem groupBtn;
    @FXML private MenuItem closeBtn;
    public Controller(){
    }

    @FXML
    public void initialize() {
        BtnAddList();
        closeBtn.setOnAction(e->closeButtonAction());
        changeNameBtn.setOnAction(e->changeNameDialog());
        groupBtn.setOnAction(e->mode.wrapObject());
        unGroupBtn.setOnAction(e->mode.UnwrapObject());
    }

    @FXML
    private void BtnAddList(){
        btnArray.add(selectBtn);
        btnArray.add(associationBtn);
        btnArray.add(generalizationBtn);
        btnArray.add(compositionBtn);
        btnArray.add(classBtn);
        btnArray.add(caseBtn);
        btnArray.forEach(btn->{
            btn.setOnAction(e->OnBtnClick(btn));
        });
    }
    @FXML
    private void OnBtnClick(Button btn){
        // 清除所有按鈕的CSS
        btnArray.forEach(button->{
            button.getStyleClass().remove("theChosenOne");
        });
        // 選中按鈕上色
        btn.getStyleClass().add("theChosenOne");
        // Remove Handler before change to new mode
        if(mode !=null)
            mode.removeHandler();
        switch (btn.getId()){
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
        mode.setCanvas(canvas);
        mode.setLineArray(lineArray);
        mode.setObjectArray(objectArray);
        mode.setHandler();
    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) selectBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void changeNameDialog(){
        TextInputDialog dialog = new TextInputDialog("Named Object");
        dialog.setTitle("命名基本物件");
        dialog.setHeaderText("重新命名");
        dialog.setContentText("請輸入名子:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> mode.changeName(name));
    }
}
