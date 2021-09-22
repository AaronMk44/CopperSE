package copper.views.dialogs;

import java.io.IOException;

import copper.controllers.dialogs.SettingsController;
import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsView
{
    public void getWindow()
    {
        Stage window = new Stage();
        Parent root = null;

        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/SettingsViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/SettingsView.fxml"));
            }
            root = loader.load();

            SettingsController ctrl = loader.getController();
            ctrl.setWindow(window);
            ctrl.populate();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        window.setTitle("Settings");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        try
        {
            if(Configurations.getConfig("theme").equals("dark"))
            {
                Image icon = new Image(getClass().
                getResourceAsStream("/copper/assets/images/logoDark.png"));
                window.getIcons().add(icon);
            }else
            {
                Image icon = new Image(getClass().
                getResourceAsStream("/copper/assets/images/logoLight.png"));
                window.getIcons().add(icon);
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        window.show();
    }
}
