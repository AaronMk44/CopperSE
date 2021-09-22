package copper.controllers.dashboard;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import copper.models.Configurations;
import copper.views.account.ManageAccountView;
import copper.views.dialogs.InfoView;
import copper.views.dialogs.LogInView;
import copper.views.dialogs.SettingsView;
import copper.views.mail.MailView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController
{
    private Stage window = null;

    @FXML
    private ImageView inboxImg;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private VBox browserCont;

    @FXML
    private VBox manageAccCont;

    @FXML
    private VBox helpCont;

    @FXML
    private VBox settingsCont;

    @FXML
    private Label usernameLabel;

    @FXML
    void loadBrowser(MouseEvent event)
    {
        InfoView box = new InfoView("Please wait while we set up the Copper"
            + "\nBrowser.");
        box.getWindow();
        
        Configurations.authorise();
    	Runtime runTime = Runtime.getRuntime();
		try {
			Process process = runTime.exec("H:\\Projects\\Copper\\Adapters\\BrowserAdapter.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}        
    }

    @FXML
    void loadHelp(MouseEvent event)
    {
        InfoView box = new InfoView("Open the Copper Browser and go to:\n"
            + "zictc.help-desk.info");
        box.getWindow();
    }

    @FXML
    void loadManageAccount(MouseEvent event)
    {
        ManageAccountView view = new ManageAccountView();
        view.getWindow();
    }

    @FXML
    void loadSettings(MouseEvent event)
    {
        SettingsView view = new SettingsView();
        view.getWindow();
    }

    @FXML
    void logOut(ActionEvent event)
    {
        this.window.close();
        LogInView initialView = new LogInView();
        initialView.getWindow();
        Configurations.setUser(null);
    }

    @FXML
    void openInbox(MouseEvent event)
    {
        MailView view = new MailView();
        view.getWindow();
    }
    
    public void setData(Stage window)
    {
        this.usernameLabel.setText(Configurations.getUser().getFirstName() + " " + 
            Configurations.getUser().getLastName());
        this.window = window;
    }
}
