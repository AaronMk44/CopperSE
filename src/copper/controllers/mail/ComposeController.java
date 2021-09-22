package copper.controllers.mail;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import copper.entities.Mail;
import copper.models.Configurations;
import copper.models.MailModel;
import copper.views.dialogs.ErrorView;
import copper.views.dialogs.SuccessView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ComposeController 
{
    private Mail msg;
    private Stage window;
    
    @FXML
    private JFXTextField to;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextArea body;

    @FXML
    private JFXButton send;
    
    @FXML
    void sendMail(ActionEvent event) 
    {
        this.msg = new Mail();
        msg.setToEmail(this.to.getText());
        msg.setFromEmail(Configurations.getUser().getEmail());
        msg.setTitle(this.title.getText());
        msg.setBody(this.body.getText());
        
        if(msg.isValid())
        {
            MailModel model = new MailModel();
            model.sendMail(msg);
            
            SuccessView view = new SuccessView("Mail successfully sent.");
            view.getWindow();
            
            this.window.close();
        }else
        {
            ErrorView view = new ErrorView(msg.getValidationErrors());
            view.getWindow();
        }        
        
    }
    
    public void setData(Stage window)
    {
        this.window = window;
    }
}