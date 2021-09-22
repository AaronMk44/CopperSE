package copper.controllers.account;

import com.jfoenix.controls.JFXButton;

import copper.entities.User;
import copper.models.Configurations;
import copper.models.UserModel;
import copper.views.account.CurrentPwdView;
import copper.views.account.UpdateDetailsView;
import copper.views.dialogs.AgreementView;
import copper.views.dialogs.InfoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ManageAccountController
{

    private Stage window = null;
    private User user = null;
    private UserModel model = null;
    private boolean isDeveloper;
    
    public ManageAccountController()
    {
        this.model = new UserModel();
    }

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label email;

    @FXML
    private Label userGroup;

    @FXML
    private JFXButton changePwdBtn;

    @FXML
    private JFXButton updateDetailsBtn;

    @FXML
    private ImageView developerImg;

    @FXML
    private Label becomeDevLabel;

    @FXML
    private Label gender;

    @FXML
    void becomeDeveloper(MouseEvent event)
    {
        if (this.isDeveloper)
        {
            InfoView info = new InfoView("You are already a Developer.");
            info.getWindow();
        } else
        {
            AgreementView box = new AgreementView();
            box.getWindow(this.user, this);
        }
    }

    @FXML
    void changePwd(ActionEvent event)
    {
        CurrentPwdView view = new CurrentPwdView();
        view.getWindow();
    }

    @FXML
    void updateDetails(ActionEvent event)
    {
        UpdateDetailsView view = new UpdateDetailsView();
        view.getWindow(this);
    }

    public void populate()
    {
        this.firstName.setText(Configurations.getUser().getFirstName());
        this.lastName.setText(Configurations.getUser().getLastName());
        this.gender.setText(Configurations.getUser().getGender());
        this.email.setText(Configurations.getUser().getEmail());
        this.userGroup.setText(Configurations.getUser().getUserGroup());

        //For developer label
        this.isDeveloper = this.model.isDeveloper(Configurations.getUser().getUserID());
        if (isDeveloper)
        {
            this.becomeDevLabel.setText("You are a Developer.\nStatus: " + 
                    this.model.getStatus(Configurations.getUser().getUserID()));
        } else
        {
            this.becomeDevLabel.setText("Become a Developer");
        }
    }

    public void setData(Stage window)
    {
        this.window = window;
    }

}
