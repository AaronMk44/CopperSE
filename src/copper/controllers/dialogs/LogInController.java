package copper.controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import copper.models.Configurations;
import copper.models.LogInModel;
import copper.models.UserModel;
import copper.views.dashboard.DashboardView;
import copper.views.dialogs.ErrorView;
import copper.views.dialogs.InfoView;
import copper.views.dialogs.ProgressView;
import copper.views.dialogs.SettingsView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogInController
{

    private Stage window = null;
    private String errorMsg;
    private LogInModel userData = null;

    public LogInController()
    {
        errorMsg = "";
        userData = new LogInModel();
    }

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField paaswordField;

    @FXML
    private JFXButton logInBtn;

    @FXML
    private Label lackAccLable;

    @FXML
    private Button settingsBtn;

    @FXML
    private ImageView settingsImg;

    @FXML
    void loadLackAcc(MouseEvent event)
    {
        InfoView box = new InfoView("Contact the administrator to create an \naccount.");
        box.getWindow();
    }

    @FXML
    void verifyCredentials(ActionEvent event)
    {
        ProgressView.show();
        Task task = new Task<Void>()  // required to create separate thread to interact with database
        {
            @Override public Void call() 
            {
                if (userData.checkUser(emailField.getText(), 
                        paaswordField.getText()))
                {

                    UserModel model = new UserModel();
                    Configurations.setUser(model.getUser(emailField.getText()));
                    
                    Platform.runLater(() -> { // Required to interact with JavaFX UI
                        try 
                        {
                            window.close();
                            ProgressView.close();
                            DashboardView nextView = new DashboardView();
                            nextView.getWindow();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    
                } else
                {
                    Platform.runLater(() -> { // Required to interact with JavaFX UI
                        try 
                        {
                            ProgressView.close();
                            errorMsg = "The email or password entered does \n"
                                + "not match our records. Try again.";

                            ErrorView notify = new ErrorView(errorMsg);
                            notify.getWindow();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });                    
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    void loadSettings(MouseEvent event)
    {
        System.out.println("loding settings");
        SettingsView view = new SettingsView();
        view.getWindow();
    }

    public void setData(Stage window)
    {
        this.window = window;
    }
}
