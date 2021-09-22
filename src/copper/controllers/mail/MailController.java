package copper.controllers.mail;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import copper.views.mail.BroadcastView;
import copper.views.mail.ComposeView;
import copper.views.mail.InboxView;
import copper.views.mail.SentView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class MailController implements Initializable
{
    BroadcastView bView = null;
    InboxView iView = null;
    SentView sView = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.bView = new BroadcastView();
        this.iView = new InboxView();
        this.sView = new SentView();
        
        this.scrollContent.setContent(bView.getView());
    } 
    
    @FXML
    private BorderPane mainLayout;

    @FXML
    private JFXButton composeBtn;

    @FXML
    private Label currentView;

    @FXML
    private JFXButton broadcastBtn;

    @FXML
    private JFXButton inboxBtn;

    @FXML
    private JFXButton sentBtn;
    
    @FXML
    private ScrollPane scrollContent;

    @FXML
    void composeMail(ActionEvent event) 
    {
        ComposeView view = new ComposeView();
        view.getWindow();
    }

    @FXML
    void loadBroadcastMail(ActionEvent event) 
    {
        this.currentView.setText("Broadcast");
        this.scrollContent.setContent(this.bView.getView());
    }

    @FXML
    void loadInboxMail(ActionEvent event) 
    {
        this.currentView.setText("Inbox");
        this.scrollContent.setContent(this.iView.getView());
    }

    @FXML
    void loadSentMail(ActionEvent event) 
    {
        this.currentView.setText("Sent");
        this.scrollContent.setContent(this.sView.getView());
    }

}
