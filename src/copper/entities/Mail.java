package copper.entities;

import copper.models.UserModel;

public class Mail
{
    private String id; 
    private int displayNumber;
    private String fromEmail;
    private String toEmail;
    private String title;
    private String body;
    private String readStatus;
    private String created_at;
    private String validationErrors;

    public Mail()
    {
        this.id = "";
        this.displayNumber = 0;
        this.fromEmail = "";
        this.toEmail = "";
        this.title = "";
        this.body = "";
        this.readStatus = "";
        this.created_at = "";
        this.validationErrors = "";
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getDisplayNumber()
    {
        return displayNumber;
    }

    public void setDisplayNumber(int displayNumber)
    {
        this.displayNumber = displayNumber;
    }
    
    public String getFromEmail()
    {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail)
    {
        this.fromEmail = fromEmail;
    }

    public String getToEmail()
    {
        return toEmail;
    }

    public void setToEmail(String toEmail)
    {
        this.toEmail = toEmail;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getReadStatus()
    {
        return readStatus;
    }

    public void setReadStatus(String readStatus)
    {
        this.readStatus = readStatus;
    }

    public String getCreateAt()
    {
        return created_at;
    }

    public void setCreatedAt(String createAt)
    {
        this.created_at = createAt;
    }

    public boolean isValid()
    {
        boolean status = true;        
        
        UserModel model = new UserModel();
        if(model.getUserID(this.toEmail).equals(""))
        {
            this.validationErrors += "~ Email not found.\n";
            status = false;
        }
        if(this.title.equals(""))
        {
            this.validationErrors += "~ Enter a title.\n";
            status = false;
        }
        if (this.body.equals(""))
        {
            this.validationErrors += "~ Enter a message.\n";
            status = false;
        }
        
        return status;
    }
    
    public String getValidationErrors()
    {
        return this.validationErrors;
    }
}
