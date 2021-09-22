package copper.controllers.dialogs;

import com.jfoenix.controls.JFXButton;

import copper.controllers.account.ManageAccountController;
import copper.entities.Developer;
import copper.entities.SecurityUtils;
import copper.entities.User;
import copper.models.Configurations;
import copper.models.DeveloperModel;
import copper.models.LegalModel;
import copper.views.dialogs.InfoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AgreementController
{

    private Stage window = null;
    private User user = null;
    private LegalModel model = null;
    private ManageAccountController parentWin;

    public AgreementController()
    {
        this.model = new LegalModel();
    }
    
    @FXML
    private JFXButton agreeBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private Label headerTitle;

    @FXML
    private Label messageLabel;

    @FXML
    void cancel(ActionEvent event)
    {
        this.window.close();
    }

    @FXML
    void iAgree(ActionEvent event)
    {
        Developer newGuy = new Developer();
        newGuy.setUserID(Configurations.getUser().getUserID());
        newGuy.setIsDeveloper(true);
        newGuy.setUsername(SecurityUtils.generateUsername(Configurations.getUser()));
        newGuy.setDevPassword(SecurityUtils.generatePassword(Configurations.getUser().getUsrPassword()));
        newGuy.setStatus("pending");
        newGuy.setAccountsStatus("not_created");
        newGuy.setHasReadLicence("Yes");

        DeveloperModel model = new DeveloperModel();
        model.insert(newGuy); // password is hashed from model

        InfoView box = new InfoView("Thank you for joing the community.\n"
            + "Your request is being processed.\n"
            + "You will be sent a comfirmation mail.");
        box.getWindow();
        this.parentWin.populate();
        this.window.close();
    }

    public void populate()
    {
        this.messageLabel.setText(this.model.getLegalMessage("developerAgreement"));
    }
    
    public void setData(User temp, Stage window, ManageAccountController parentWin)
    {
        this.user = temp;
        this.window = window;
        this.parentWin = parentWin;
    }
}
