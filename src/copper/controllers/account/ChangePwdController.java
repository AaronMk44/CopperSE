package copper.controllers.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import copper.entities.SecurityUtils;
import copper.models.Configurations;
import copper.models.UserModel;
import copper.views.dialogs.ErrorView;
import copper.views.dialogs.SuccessView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChangePwdController
{

    private Stage window = null;
    private UserModel model = null;
    private String message = null;

    public ChangePwdController()
    {
        this.message = "";
        this.model = new UserModel();
    }

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField comfNewPwd;

    @FXML
    private JFXButton changePwdBtn;

    @FXML
    void changePwd(ActionEvent event)
    {
        if (this.newPassword.getText().equals(this.comfNewPwd.getText()))
        {
            String newPass = SecurityUtils.hashText(this.comfNewPwd.getText());
            Configurations.getUser().setUsrPassword(newPass);
            this.model.update(Configurations.getUser());            
            this.message = "Password Successfully Changed.";
            this.window.close();

            SuccessView box = new SuccessView(this.message);
            box.getWindow();
        } else
        {
            this.message = "Passwords entered don't match. \nTry again.";

            ErrorView box = new ErrorView(this.message);
            box.getWindow();
        }
    }

    public void setData(Stage window)
    {
        this.window = window;
    }
}
