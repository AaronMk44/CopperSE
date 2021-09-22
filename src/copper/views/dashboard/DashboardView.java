package copper.views.dashboard;

import java.io.IOException;

import copper.controllers.dashboard.DashboardController;
import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DashboardView
{
    private static Stage window = null;
    private static Scene scene = null;
    
    public void getWindow()
    {
        window = new Stage();
        Parent root = null;

        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dashboard/DashboardViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/dashboard/DashboardView.fxml"));
            }
            root = loader.load();

            DashboardController ctrl = loader.getController();
            ctrl.setData(window);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        scene = new Scene(root, 600, 646);

        window.setTitle("Copper - Dashboard");
        window.setScene(scene);
        window.setResizable(false);
        
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
    public static void reload()
    {
        Parent root = null;
        FXMLLoader loader = null;
        
        try
        {
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(DashboardView.class.
                getResource("/copper/views/dashboard/DashboardViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(DashboardView.class.
                    getResource("/copper/views/dashboard/DashboardView.fxml"));
            }
            root = loader.load();

            DashboardController ctrl = loader.getController();
            ctrl.setData(window);
            scene.setRoot(root);
            
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
    }
}
