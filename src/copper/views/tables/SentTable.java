package copper.views.tables;

import java.util.Arrays;
import java.util.List;

import copper.entities.Mail;
import copper.models.MailModel;
import copper.views.dialogs.ErrorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SentTable
{
    private MailModel model;
    private List<Mail> dataList;
    private TableView<Mail> table;
    private ObservableList<Mail> mail;

    public SentTable()
    {
        this.model = new MailModel();
        this.dataList = this.getMail("");
    }
    
    public TableView getTable()
    {
        this.table = new TableView<Mail>();
        
        ObservableList<Mail> mail = FXCollections.observableList(this.dataList);

        TableColumn numbColumn = new TableColumn("#");
        numbColumn.setCellValueFactory(new PropertyValueFactory("displayNumber"));
        
        TableColumn<Mail, String> titleColumn =
            new TableColumn<Mail, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        
        TableColumn<Mail, String> fromColumn = 
            new TableColumn<Mail, String>("To");
        fromColumn.setCellValueFactory(new PropertyValueFactory("toEmail"));
        
        TableColumn<Mail, String> readStatusColumn = 
            new TableColumn<>("Status");
        readStatusColumn.setCellValueFactory(new PropertyValueFactory("readStatus"));
        
        TableColumn<Mail, String> dateColumn = 
            new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory("created_at"));
        
        table.getColumns().add(numbColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(fromColumn);
        table.getColumns().add(readStatusColumn);
        table.getColumns().add(dateColumn);        
        
        table.setItems(mail);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-font-size: 12px;");
        table.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>()
        {
            @Override
            public Boolean call(TableView.ResizeFeatures param)
            {
                return true;
            }
        });
        
        return table;
    }
    
    private List<Mail> getMail(String query)
    {
        Mail[] mail = this.model.getSentMail(query);
        if(mail == null)
        {
            return null;
        }else
        {
           return Arrays.asList(mail);
        }      
    }
    
    public void reload()
    {
        this.dataList = this.getMail("");
        this.mail = FXCollections.observableList(this.dataList);
        this.table.setItems(mail);
    }
    
    public void filter(String query)
    {
       this.dataList = this.getMail(query);
       if(this.dataList == null)
       {
           ErrorView box = new ErrorView("No results were found matching\nyour query.");
           box.getWindow();
       }else
       {
           this.mail = FXCollections.observableList(this.dataList);
           this.table.setItems(mail);
       }       
    }
}
