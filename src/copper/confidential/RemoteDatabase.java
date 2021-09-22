package copper.confidential;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import copper.models.Configurations;
import copper.views.dashboard.DashboardView;
import copper.views.dialogs.ErrorView;
import copper.views.dialogs.ProgressView;
import javafx.application.Platform;

public class RemoteDatabase
{

    final private String DATABASE_NAME = "copper";
    private String SEVER_ADDRESS;
    final private String username = "root";
    final private String password = "";

    private String errorMsg;

    public RemoteDatabase()
    {
        this.SEVER_ADDRESS = Configurations.getConfig("server_ip_address");
        this.errorMsg = "";
    }

    public Connection getConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + this.SEVER_ADDRESS + 
                ":3306/" + this.DATABASE_NAME, this.username, this.password);

            return conn;

        } catch (ClassNotFoundException | SQLException e)
        {
        	this.errorMsg = "The connection to the Server failed. \n"
                    + "Configure the Server's IP Address.\n"
                    + "If the problem persists, \n"
                    + "contact the administrator's.";
        	Platform.runLater(() -> { // required to interact with javafx ui
                ProgressView.close();                  
                ErrorView notify = new ErrorView(this.errorMsg);
                notify.getWindow();
            });
            e.printStackTrace();                 
        }
        return conn;
    }

    public void closeConnection(Connection conn)
    {
        try
        {
            conn.close();
        } catch (SQLException e)
        {
        	this.errorMsg = "The connection to the Server failed. \n"
                    + "Configure the Server's IP Address.\n"
                    + "If the problem persists, \n"
                    + "contact the administrator's.";
        	Platform.runLater(() -> { // required to interact with javafx ui
                ProgressView.close();                  
                ErrorView notify = new ErrorView(this.errorMsg);
                notify.getWindow();
            });
            e.printStackTrace();
        }
    }
}
