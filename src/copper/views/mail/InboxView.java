package copper.views.mail;

import java.io.IOException;

import copper.models.Configurations;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class InboxView
{
    public BorderPane getView()
    {
        BorderPane root = null;
        try
        {
            FXMLLoader loader = null;
            if(Configurations.getConfig("theme").equals("dark"))
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/mail/InboxViewDark.fxml"));
            }else
            {
                loader = new FXMLLoader(getClass().
                getResource("/copper/views/mail/InboxView.fxml"));
            }
            root = loader.load();        

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return root;
    }
}
