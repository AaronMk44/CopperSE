package copper.controllers.account;

import org.mindrot.jbcrypt.BCrypt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import copper.models.Configurations;
import copper.views.account.ChangePwdView;
import copper.views.dialogs.ErrorView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CurrentPwdController
{

    private Stage window = null;
    private String message = null;

    @FXML
    private JFXPasswordField currentPwdField;

    @FXML
    private JFXButton verifyBtn;

    @FXML
    void verifyPwd(ActionEvent event)
    {
        if (BCrypt.checkpw(this.currentPwdField.getText(), 
                Configurations.getUser().getUsrPassword()))
        {
            this.window.close();
            ChangePwdView view = new ChangePwdView();
            view.getWindow();
        } else
        {
            this.message = "Password entered doesn't match our records. \nTry again.";

            ErrorView box = new ErrorView(this.message);
            box.getWindow();
        }
    }

    public void setData(Stage window)
    {
        this.window = window;
    }
}
