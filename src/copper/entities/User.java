package copper.entities;

public class User
{

    private String userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String userGroup;
    private boolean isDeveloper;
    private String usrCreatedAt;

    public User()
    {
        this.userID = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.email = "";
        this.password = "";
        this.userGroup = "";
        this.isDeveloper = false;
        this.usrCreatedAt = "";
    }
    
    public User(User user)
    {
        this.userID = user.getUserID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.password = user.getUsrPassword();
        this.userGroup = user.getUserGroup();
        this.isDeveloper = user.getIsDeveloper();
        this.usrCreatedAt = user.getUsrCreatedAt();
    }

    public String getUserID()
    {
        return this.userID;
    }

    public void setUserID(String iD)
    {
        this.userID = iD;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsrPassword()
    {
        return this.password;
    }

    public void setUsrPassword(String password)
    {
        this.password = password;
    }

    public String getUserGroup()
    {
        return this.userGroup;
    }

    public void setUserGroup(String userGroup)
    {
        this.userGroup = userGroup;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public boolean getIsDeveloper()
    {
        return isDeveloper;
    }

    public void setIsDeveloper(boolean isDeveloper)
    {
        this.isDeveloper = isDeveloper;
    }
    
    public String getUsrCreatedAt()
    {
        return this.usrCreatedAt;
    }

    public void setUsrCreatedAt(String createdAt)
    {
        this.usrCreatedAt = createdAt;
    }

}
