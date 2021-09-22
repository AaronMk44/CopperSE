package copper.controllers.mail;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import copper.entities.Mail;
import copper.models.MailModel;
import copper.views.dialogs.ErrorView;
import copper.views.dialogs.SuccessView;
import copper.views.mail.ReadView;
import copper.views.tables.SentTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class SentController implements Initializable
{
    private SentTable table;
    private TableView<Mail> t;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.table = new SentTable();
        this.t = this.table.getTable();
        this.mainLayout.setCenter(this.t);
    } 
    @FXML
    private BorderPane mainLayout;

    @FXML
    private JFXButton readBtn;

    @FXML
    private JFXButton reloadBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    void delete(ActionEvent event) 
    {
        TableView.TableViewSelectionModel<Mail> selected = 
            this.t.getSelectionModel();
        if(selected.isEmpty())
        {
            ErrorView box = new ErrorView("You did not select anything.");
            box.getWindow();
        }else
        {
            MailModel model = new MailModel();
            model.deleteForSender(selected.getSelectedItem().getId());
            
            SuccessView view = new SuccessView("Message successfully deleted.");
            view.getWindow();
            
            this.table.reload();
        }
    }


    @FXML
    void read(ActionEvent event) 
    {
        TableView.TableViewSelectionModel<Mail> selected = 
            this.t.getSelectionModel();
        if(selected.isEmpty())
        {
            ErrorView box = new ErrorView("You did not select anything.");
            box.getWindow();
        }else
        {
            ReadView read = new ReadView();
            read.getWindow(selected.getSelectedItem());            
        }
    }

    @FXML
    void reload(ActionEvent event) 
    {
        this.table.reload();
    }

}
