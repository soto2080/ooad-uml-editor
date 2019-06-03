package tw.mhyang.ooad.project;


import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import tw.mhyang.ooad.project.Manager.ObjectManager;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    private ArrayList<Button> btnArray = new ArrayList<>();
    private ObjectManager objectManager;
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
        groupBtn.setOnAction(e->objectManager.wrapObject());
        unGroupBtn.setOnAction(e->objectManager.UnwrapObject());
        objectManager = new ObjectManager(canvas);
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
            button.getStyleClass().remove("theChooseOne");
        });
        // 選中按鈕上色
        btn.getStyleClass().add("theChooseOne");
        switch (btn.getId()){
            case "selectBtn":
                objectManager.setMode(Mode.SELECT);
                break;
            case "associationBtn":
                objectManager.setMode(Mode.ASSOCIATION);
                break;
            case "generalizationBtn":
                objectManager.setMode(Mode.GENERALIZATION);
                    break;
            case "compositionBtn":
                objectManager.setMode(Mode.COMPOSITION);
                break;
            case "classBtn":
                objectManager.setMode(Mode.CLASS);
                break;
            case "caseBtn":
                objectManager.setMode(Mode.USECASE);
             default:
                    break;
        }
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
        result.ifPresent(name -> objectManager.changeName(name));
    }
}
