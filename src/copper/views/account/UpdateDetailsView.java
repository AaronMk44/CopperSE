package copper.views.account;

import java.io.IOException;

import copper.controllers.account.ManageAccountController;
import copper.controllers.account.UpdateDetailsController;
import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateDetailsView
{

    public void getWindow(final ManageAccountController temp)
    {
        Stage window = new Stage();
        Parent root = null;

        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/account/UpdateDetailsViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/account/UpdateDetailsView.fxml"));
            }
            root = loader.load();

            UpdateDetailsController ctrl = loader.getController();
            ctrl.setData(window, temp);
            ctrl.populate();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);

        window.setTitle("Update Details");
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
