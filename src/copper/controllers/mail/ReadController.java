package copper.controllers.mail;

import copper.entities.Mail;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ReadController 
{
    @FXML
    private TextField from;

    @FXML
    private TextField title;

    @FXML
    private VBox contentContainer;
    
    public void setData(Mail obj)
    {
        this.from.setText(obj.getFromEmail());
        this.title.setText(obj.getTitle());
        Label body = new Label(obj.getBody());
        body.setWrapText(true);
        body.setPadding(new Insets(10));
        this.contentContainer.getChildren().add(body);
    }
}
