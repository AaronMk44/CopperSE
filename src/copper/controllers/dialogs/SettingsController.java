package copper.controllers.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import copper.models.Configurations;
import copper.views.dashboard.DashboardView;
import copper.views.dialogs.SuccessView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SettingsController implements Initializable
{
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {       
        ToggleGroup themeGroup = new ToggleGroup();
        this.lightTheme.setToggleGroup(themeGroup);
        this.darkTheme.setToggleGroup(themeGroup);
    }
    private Stage window = null;
    private String successMsg;

    @FXML
    private JFXTextField serverAddr;
    
    @FXML
    private RadioButton lightTheme;

    @FXML
    private RadioButton darkTheme;

    @FXML
    void save(ActionEvent event)
    {
        String theme = null;        
        if(this.lightTheme.isSelected())
        {
            theme = "light";
        }else if(this.darkTheme.isSelected())
        {
            theme = "dark";
        }
        Configurations.setConfig("server_ip_address", this.serverAddr.getText());
        Configurations.setConfig("theme", theme);
        this.successMsg = "Settings successfully updated.";
        
        if(Configurations.getUser() != null) //ensures reload only if user is logged
        {
            DashboardView.reload();
        }
        
        SuccessView notify = new SuccessView(this.successMsg);
        notify.getWindow();
        this.window.close();
    }

    public void populate()
    {
        this.serverAddr.setText(Configurations.getConfig("server_ip_address"));
        String theme = Configurations.getConfig("theme");
        if(theme.equals("light"))
        {
            this.lightTheme.setSelected(true);
        }else
        {
            this.darkTheme.setSelected(true);
        }
    }
    
    public void setWindow(Stage window)
    {
        this.window = window;
    }
}
