package copper.controllers.account;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import copper.models.Configurations;
import copper.models.UserModel;
import copper.views.dialogs.SuccessView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class UpdateDetailsController
{

    private Stage window = null;
    private UserModel model = null;
    private String message = null;
    private ManageAccountController mac = null;

    public UpdateDetailsController()
    {
        this.message = "";
        this.model = new UserModel();
    }

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXButton updateBtn;

    @FXML
    void update(ActionEvent event)
    {
        Configurations.getUser().setFirstName(this.firstName.getText());
        Configurations.getUser().setLastName(this.lastName.getText());
        this.model.update(Configurations.getUser());
        this.message = "Credentials Successfully Updated.";
        this.window.close();
        //Reload the Manage Account Window after closing the Update Details Window
        this.mac.populate();

        SuccessView box = new SuccessView(this.message);
        box.getWindow();
    }

    public void populate()
    {
        this.firstName.setText(Configurations.getUser().getFirstName());
        this.lastName.setText(Configurations.getUser().getLastName());
    }

    public void setData(Stage window, ManageAccountController mac)
    {
        this.window = window;
        this.mac = mac;
    }
}
