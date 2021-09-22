package copper.entities;

public class Developer extends User
{
    private String developerID;
    private String username;
    private String password;
    private String status;
    private String accountsStatus;
    private int numberOfApps;
    private String hasReadLicence;
    private String devCreatedAt;
    
    public Developer()
    {
        this.developerID = "";
        this.username = "";
        this.password = "";
        this.status = "";
        this.accountsStatus = "";
        this.numberOfApps = 0;
        this.hasReadLicence = "";
        this.devCreatedAt = "";
    }
    
    public Developer(User user)
    {
        super(user);
        this.developerID = "";
        this.username = "";
        this.password = "";
        this.status = "";
        this.accountsStatus = "";
        this.numberOfApps = 0;
        this.hasReadLicence = "";
        this.devCreatedAt = "";
    }

    public String getDeveloperID()
    {
        return this.developerID;
    }

    public void setDeveloperID(String iD)
    {
        this.developerID = iD;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDevPassword()
    {
        return this.password;
    }

    public void setDevPassword(String password)
    {
        this.password = password;
    }
    
    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAccountsStatus()
    {
        return accountsStatus;
    }

    public void setAccountsStatus(String accountsStatus)
    {
        this.accountsStatus = accountsStatus;
    }
    
    public int getNumberOfApps()
    {
        return numberOfApps;
    }

    public void setNumberOfApps(int numberOfApps)
    {
        this.numberOfApps = numberOfApps;
    }   

    public String getHasReadLicence()
    {
        return hasReadLicence;
    }

    public void setHasReadLicence(String hasReadLicence)
    {
        this.hasReadLicence = hasReadLicence;
    }
       
    public String getDevCreatedAt()
    {
        return this.devCreatedAt;
    }

    public void setDevCreatedAt(String createdAt)
    {
        this.devCreatedAt = createdAt;
    }

}
