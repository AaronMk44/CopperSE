package copper.views.dialogs;

import java.io.IOException;

import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressView 
{
    private static Stage window = new Stage();
    public static void init()
    {
        Parent root = null;

        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(ProgressView.class.
                getResource("/copper/views/dialogs/ProgressViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(ProgressView.class.
                getResource("/copper/views/dialogs/ProgressView.fxml"));
            }
            root = loader.load();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        window.setTitle("Processing");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        
        try
        {
            if(Configurations.getConfig("theme").equals("dark"))
            {
                Image icon = new Image(ProgressView.class.
                getResourceAsStream("/copper/assets/images/logoDark.png"));
                window.getIcons().add(icon);
            }else
            {
                Image icon = new Image(ProgressView.class.
                getResourceAsStream("/copper/assets/images/logoLight.png"));
                window.getIcons().add(icon);
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        window.initStyle(StageStyle.UNDECORATED);
    }
    
    public static void show()
    {
        window.show();
    }
    
    public static void close()
    {
        window.close();
    }
}
