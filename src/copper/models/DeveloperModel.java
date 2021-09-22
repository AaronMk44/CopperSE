package copper.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import copper.confidential.RemoteDatabase;
import copper.entities.Developer;
import copper.entities.SecurityUtils;

public class DeveloperModel
{

    private final String prefix = "zictc_intra_";
    private final String TABLE_NAME = this.prefix + "developers";

   public boolean insert(Developer obj)
    {
        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();
        int dev_id = -1;
        
        String sqlCreateDeveloper = "INSERT INTO " + this.TABLE_NAME + 
            " (user_id, username, password, has_read_licence, developer_status, "
            + "db_account_status) VALUES(?,?,?,?,?,?)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sqlCreateDeveloper, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, obj.getUserID());
            stmt.setString(2, obj.getUsername());
            stmt.setString(3, SecurityUtils.hashText(obj.getDevPassword()));
            stmt.setString(4, obj.getHasReadLicence());
            stmt.setString(5, obj.getStatus());
            stmt.setString(6, obj.getAccountsStatus());
           
            stmt.executeUpdate();
            //In the futute, process the returned value
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next())
            {
                dev_id = generatedKeys.getInt(1);
            }
            
            stmt.close();
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        String sqlDevLegder = "INSERT INTO zictc_intra_developer_ledger "
            + "(developer_id, password) VALUES(?,?)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sqlDevLegder);
            stmt.setString(1, ""+dev_id);
            stmt.setString(2, obj.getDevPassword());
           
            stmt.executeUpdate();
            stmt.close();
            dbr.closeConnection(conn);
            return true;
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
     
        dbr.closeConnection(conn);
        return false;
    }

     public Developer getDeveloper(String email)
    {
        Developer dev = null;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT * FROM " + this.TABLE_NAME + ""
            + "INNER JOIN zictc_intra_users ON zictc_intra_developers.user_id = "
            + "zictc_intra_users.id "
            + "WHERE email = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                dev = new Developer();
                dev.setUserID(results.getInt("zictc_intra_users.id") + "");
                dev.setFirstName(results.getString("first_name"));
                dev.setLastName(results.getString("last_name"));
                dev.setGender(results.getString("gender"));
                dev.setEmail(results.getString("email"));
                dev.setUsrPassword(results.getString("password"));
                dev.setUserGroup(results.getString("user_group"));
                dev.setIsDeveloper(true);
                dev.setUsrCreatedAt(results.getTimestamp("created_at").toString());
                dev.setDeveloperID(results.getInt("zictc_intra_developers.id") + "");
                dev.setUsername(results.getString("username"));
                dev.setDevPassword(results.getString("password"));
                dev.setStatus(results.getString("developer_status"));
                dev.setHasReadLicence(results.getString("has_read_licence"));
                dev.setDevCreatedAt(results.getTimestamp("created_at").toString());
            }
            stmt.close();
        } catch (SQLException e)
        {

            e.printStackTrace();
        }

        dbr.closeConnection(conn);
        return dev;
    }
     
     public boolean isUsernameTaken(String username)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "SELECT developer_status FROM " + this.TABLE_NAME + 
            " WHERE username = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                if (results.getString("developer_status").equals(""))
                {
                    stmt.close();
                    rdb.closeConnection(conn);
                    return false;
                } else
                {
                    stmt.close();
                    rdb.closeConnection(conn);
                    return true;
                }
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        rdb.closeConnection(conn);
        return false;
    }
     
}
