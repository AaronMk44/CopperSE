package copper.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import copper.confidential.RemoteDatabase;
import copper.entities.User;

public class UserModel
{

    private final String prefix = "zictc_intra_";
    private final String TABLE_NAME = this.prefix + "users";

    public UserModel()
    {
    }

    public User getUser(String email)
    {
        User obj = new User();

        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "SELECT * FROM " + this.TABLE_NAME + " WHERE email = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                obj.setUserID(results.getInt("id") + "");
                obj.setFirstName(results.getString("first_name"));
                obj.setLastName(results.getString("last_name"));
                obj.setEmail(results.getString("email"));
                obj.setGender(results.getString("gender"));
                obj.setUsrPassword(results.getString("password"));
                obj.setUserGroup(results.getString("user_group"));
                obj.setIsDeveloper(this.isDeveloper(results.getInt("id") + ""));
            }
            stmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        rdb.closeConnection(conn);
        return obj;
    }

    public boolean update(User obj)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "UPDATE " + this.TABLE_NAME + 
            " SET first_name = ?, last_name = ?, password = ?"
            + "WHERE id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getFirstName());
            stmt.setString(2, obj.getLastName());
            stmt.setString(3, obj.getUsrPassword());
            stmt.setInt(4, Integer.parseInt(obj.getUserID()));

            stmt.execute();
            stmt.close();
            rdb.closeConnection(conn);
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        rdb.closeConnection(conn);
        return false;
    }

    public boolean delete(String email)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();
        
        String sql = "DELETE FROM " + this.TABLE_NAME + " WHERE email = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            stmt.execute();
            stmt.close();
            rdb.closeConnection(conn);
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        rdb.closeConnection(conn);
        return false;
    }
    
    public boolean isDeveloper(String ID)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "SELECT developer_status FROM zictc_intra_developers "
            + "WHERE user_id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ID);

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

    public String getStatus(String ID)
    {
        String status = null;

        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "SELECT developer_status FROM zictc_intra_developers "
            + "WHERE user_id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ID);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                status = results.getString("developer_status");
            }
            
            stmt.close();
            rdb.closeConnection(conn);
            return status;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        rdb.closeConnection(conn);
        return status;
    }

    public String getUserID(String email)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();
        
        String sql = "SELECT id FROM " + this.TABLE_NAME + " WHERE email = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                return results.getInt("id") + "";
            }
            stmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
