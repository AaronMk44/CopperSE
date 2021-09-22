package copper.controllers.dialogs;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BoxController 
{
    private Stage window = null;

    @FXML
    private Label msgLabel;

    @FXML
    private JFXButton okayBtn;

    @FXML
    void closeWindow(ActionEvent event) 
    {
        this.window.close();
    }
    
    public void setData(String mgs, Stage window)
    {
        this.msgLabel.setText(mgs);
        this.msgLabel.setWrapText(true);
        this.window = window;
    }

}
