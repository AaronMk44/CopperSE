package copper.controllers.mail;

import java.net.URL;
import java.util.ResourceBundle;

import copper.entities.Mail;
import copper.views.dialogs.ErrorView;
import copper.views.mail.ReadView;
import copper.views.tables.BroadcastTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class BroadcastController implements Initializable
{
    private BroadcastTable table;
    private TableView<Mail> t;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.table = new BroadcastTable();
        this.t = this.table.getTable();
        this.mainLayout.setCenter(this.t);
    }    
    
    @FXML
    private BorderPane mainLayout;
    
    @FXML
    void readMail(ActionEvent event) 
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
}
