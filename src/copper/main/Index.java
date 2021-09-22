package copper.main;

import copper.models.Configurations;
import copper.views.dialogs.LogInView;
import copper.views.dialogs.ProgressView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Index extends Application
{
    @Override
    public void start(Stage primaryWindow)
    {
        LogInView initialView = new LogInView();
        primaryWindow = initialView.getWindow();
        ProgressView.init();
    }

    public static void main(String args[])
    {
        Configurations.intit();        
        launch(args);
    }
}
