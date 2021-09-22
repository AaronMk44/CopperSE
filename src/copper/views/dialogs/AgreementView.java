package copper.views.dialogs;

import java.io.IOException;

import copper.controllers.account.ManageAccountController;
import copper.controllers.dialogs.AgreementController;
import copper.entities.User;
import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgreementView
{

    public void getWindow(User obj, ManageAccountController parentWin)
    {
        Stage window = new Stage();
        Parent root = null;

        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/AgreementViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dialogs/AgreementView.fxml"));
            }
            root = loader.load();

            AgreementController ctrl = loader.getController();
            ctrl.setData(obj, window, parentWin);
            ctrl.populate();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        window.setTitle("Agreement");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        
        if(Configurations.getConfig("theme").equals("dark"))
        {
            scene.getStylesheets().add(getClass().getResource("/copper/assets/dark.css")
            .toExternalForm());
        }
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
