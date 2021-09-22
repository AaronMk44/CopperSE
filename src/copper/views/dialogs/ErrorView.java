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

public class ErrorView
{

    private String errorMsg;

    public ErrorView(String errorMessage)
    {
        this.errorMsg = errorMessage;
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
                getResource("/copper/views/dialogs/ErrorViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/ErrorView.fxml"));
            }
            root = loader.load();

            BoxController ctrl = loader.getController();
            ctrl.setData(this.errorMsg, window);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);

        window.setTitle("Copper - Notification");
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
