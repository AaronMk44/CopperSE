package copper.views.dialogs;

import java.io.IOException;

import copper.controllers.dialogs.BoxController;
import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoView
{

    private String infoMsg;

    public InfoView(String infoMsg)
    {
        this.infoMsg = infoMsg;
    }

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
                getResource("/copper/views/dialogs/InfoViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/InfoView.fxml"));
            }
            root = loader.load();

            BoxController ctrl = loader.getController();
            ctrl.setData(this.infoMsg, window);

        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);

        window.setTitle("Copper - Notification");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.alwaysOnTopProperty();

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
