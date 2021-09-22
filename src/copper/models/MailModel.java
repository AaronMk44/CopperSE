package copper.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import copper.confidential.RemoteDatabase;
import copper.entities.Mail;

public class MailModel
{
    private final String prefix = "zictc_intra_";
    private final String TABLE_NAME = this.prefix + "mail";
    
    public MailModel()
    {        
    }
    
    public boolean sendMail(Mail obj)
    {
        RemoteDatabase dbr = new RemoteDatabase();
        UserModel model = new UserModel();
        Connection conn = dbr.getConnection();

        String sql = "INSERT INTO " + this.TABLE_NAME + 
            " (from_user_id, to_user_id, title, body, read_status, "
            + "delete_status_receiver, delete_status_sender) "
            + "VALUES(?,?,?,?,?,?,?)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, model.getUserID(obj.getFromEmail()));
            stmt.setString(2, model.getUserID(obj.getToEmail()));
            stmt.setString(3, obj.getTitle());
            stmt.setString(4, obj.getBody());
            stmt.setString(5, "unread");
            stmt.setString(6, "not_deleted");
            stmt.setString(7, "not_deleted");

            stmt.executeUpdate();
            stmt.close();
            dbr.closeConnection(conn);
            return true;

        } catch (SQLException e)
        {
            dbr.closeConnection(conn);
            e.printStackTrace();
        }
        dbr.closeConnection(conn);
        return false;
    }
    
    public Mail[] getBroadcastMail(String query)
    {     
        Mail[] data = null;
        
        if(query.equals(""))
        {
            /*This block will execute when fetching all mail*/ 
            RemoteDatabase dbr = new RemoteDatabase();
            Connection conn = dbr.getConnection();

            data = new Mail[this.getNumberOfBroadcastMails()];
            String sql = "SELECT * FROM zictc_intra_broadcast_mail "
                + "ORDER BY created_at DESC";
            try
            {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet results = stmt.executeQuery();

                int counter = 0;
                while (results.next())
                {
                    data[counter] = new Mail();
                    data[counter].setId(results.getInt("id") + "");
                    data[counter].setDisplayNumber(counter + 1);
                    data[counter].setFromEmail("admin");
                    data[counter].setTitle(results.getString("title"));
                    data[counter].setBody(results.getString("body"));
                    data[counter].setCreatedAt(results.getString("created_at"));                    

                    counter++;
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            
            dbr.closeConnection(conn);
            return data;
            
        }else
        {   /*otherwise, it means we want to get user by the query*/
            int numberOfMailByQuery = this.getNumberOfBroadcastMailsBy(query);            
            if(numberOfMailByQuery == 0)
            {
                return null;
            }else
            {
                /*This block will be executed when the search query is defined 
                  and results will be returned*/
  
                RemoteDatabase dbr = new RemoteDatabase();
                Connection conn = dbr.getConnection();

                data = new Mail[numberOfMailByQuery];
                String sql = "SELECT * FROM zictc_intra_broadcast_mail" + 
                    " WHERE title LIKE ? OR body LIKE ? ORDER BY created_at DESC";
                try
                {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, "%"+query+"%");
                    stmt.setString(2, "%"+query+"%");
 
                    ResultSet results = stmt.executeQuery();

                    int counter = 0;
                    while (results.next())
                    {
                        data[counter] = new Mail();
                        data[counter].setId(results.getInt("id") + "");
                        data[counter].setDisplayNumber(counter + 1);
                        data[counter].setFromEmail("admin");
                        data[counter].setTitle(results.getString("title"));
                        data[counter].setBody(results.getString("body"));
                        data[counter].setCreatedAt(results.getString("created_at"));

                        counter++;
                    }

                } catch (SQLException e)
                {
                    e.printStackTrace();
                }

                dbr.closeConnection(conn);                
                return data;
            }
        }     
    }
    
    public Mail[] getInboxMail(String query)
    {     
        Mail[] data = null;
        
        if(query.equals(""))
        {
            /*This block will execute when fetching all mail*/ 
            RemoteDatabase dbr = new RemoteDatabase();
            Connection conn = dbr.getConnection();

            data = new Mail[this.getNumberOfInboxMails()];
            String sql = "SELECT zictc_intra_mail.id, zictc_intra_mail.title,zictc_intra_mail.body,"
                + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                + "zictc_intra_users.email FROM (zictc_intra_mail "
                + "INNER JOIN zictc_intra_users ON from_user_id = zictc_intra_users.id)"
                + "WHERE to_user_id = ? AND zictc_intra_mail.delete_status_receiver = 'not_deleted' "
                + "ORDER BY created_at DESC ";
            try
            {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, Configurations.getUser().getUserID());
                ResultSet results = stmt.executeQuery();
                int counter = 0;
                while (results.next())
                {
                    data[counter] = new Mail();
                    data[counter].setId(results.getInt("zictc_intra_mail.id") + "");
                    System.out.println(data[counter].getId());
                    data[counter].setDisplayNumber(counter + 1);
                    data[counter].setFromEmail(results.getString("email"));
                    data[counter].setTitle(results.getString("title"));
                    data[counter].setBody(results.getString("body"));
                    data[counter].setReadStatus(results.getString("read_status"));
                    data[counter].setCreatedAt(results.getTimestamp("created_at").
                        toString());     
                    System.out.println(data[counter].getCreateAt());
                    counter++;
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            
            dbr.closeConnection(conn);
            return data;
            
        }else
        {   /*otherwise, it means we want to get mail by the query*/
            int numberOfMailByQuery = this.getNumberOfInboxMailsBy(query);            
            if(numberOfMailByQuery == 0)
            {
                return null;
            }else
            {
                /*This block will be executed when the search query is defined 
                  and results will be returned*/
  
                RemoteDatabase dbr = new RemoteDatabase();
                Connection conn = dbr.getConnection();

                data = new Mail[numberOfMailByQuery];
                String sql = "SELECT zictc_intra_mail.id, zictc_intra_mail.title,zictc_intra_mail.body,"
                            + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                            + "zictc_intra_users.email FROM (zictc_intra_mail "
                            + "INNER JOIN zictc_intra_users ON from_user_id = zictc_intra_users.id)"
                            + "WHERE zictc_intra_mail.to_user_id = ? AND zictc_intra_mail.delete_status_receiver = 'not_deleted' "
                            + "AND (body LIKE ? OR title LIKE ? OR zictc_intra_users.email LIKE ?)";
                
                try
                {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, Configurations.getUser().getUserID());
                    stmt.setString(2, "%"+query+"%");
                    stmt.setString(3, "%"+query+"%");
                    stmt.setString(4, "%"+query+"%");

                    ResultSet results = stmt.executeQuery();

                    int counter = 0;
                    while (results.next())
                    {
                        data[counter] = new Mail();
                        data[counter].setId(results.getInt("zictc_intra_mail.id") + "");
                        System.out.println(data[counter].getId());
                        data[counter].setDisplayNumber(counter + 1);
                        data[counter].setFromEmail(results.getString("email"));
                        data[counter].setTitle(results.getString("title"));
                        data[counter].setBody(results.getString("body"));
                        data[counter].setReadStatus(results.getString("read_status"));
                        data[counter].setCreatedAt(results.getTimestamp("created_at").
                            toString());

                        counter++;
                    }

                } catch (SQLException e)
                {
                    e.printStackTrace();
                }

                dbr.closeConnection(conn);                
                return data;
            }
        }     
    }
    
    public Mail[] getSentMail(String query)
    {     
        Mail[] data = null;
        
        if(query.equals(""))
        {
            /*This block will execute when fetching all users*/ 
            RemoteDatabase dbr = new RemoteDatabase();
            Connection conn = dbr.getConnection();

            data = new Mail[this.getNumberOfSentMails()];
            String sql = "SELECT zictc_intra_mail.id, zictc_intra_mail.title,zictc_intra_mail.body,"
                + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                + "zictc_intra_users.email FROM (zictc_intra_mail "
                + "INNER JOIN zictc_intra_users ON zictc_intra_mail.to_user_id = zictc_intra_users.id)"
                + "WHERE zictc_intra_mail.from_user_id = ? AND "
                + "zictc_intra_mail.delete_status_sender = 'not_deleted' "
                + "ORDER BY created_at DESC ";
            try
            {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, Configurations.getUser().getUserID());
                ResultSet results = stmt.executeQuery();

                int counter = 0;
                while (results.next())
                {
                    data[counter] = new Mail();
                    data[counter].setId(results.getInt("zictc_intra_mail.id") + "");                        
                    data[counter].setDisplayNumber(counter + 1);
                    data[counter].setToEmail(results.getString("email"));
                    data[counter].setTitle(results.getString("title"));
                    data[counter].setBody(results.getString("body"));
                    data[counter].setReadStatus(results.getString("read_status"));
                    data[counter].setCreatedAt(results.getTimestamp("created_at").
                        toString());   

                    counter++;
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            
            dbr.closeConnection(conn);
            return data;
            
        }else
        {   /*otherwise, it means we want to get user by the query*/
            int numberOfMailByQuery = this.getNumberOfSentMailsBy(query);            
            if(numberOfMailByQuery == 0)
            {
                return null;
            }else
            {
                /*This block will be executed when the search query is defined 
                  and results will be returned*/
  
                RemoteDatabase dbr = new RemoteDatabase();
                Connection conn = dbr.getConnection();

                data = new Mail[numberOfMailByQuery];
                String sql = "SELECT zictc_intra_mail.id, zictc_intra_mail.title,zictc_intra_mail.body,"
                            + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                            + "zictc_intra_users.email FROM (zictc_intra_mail "
                            + "INNER JOIN zictc_intra_users ON to_user_id = zictc_intra_users.id)"
                            + "WHERE zictc_intra_mail.from_user_id = ? AND "
                            + "zictc_intra_mail.delete_status_sender = 'not_deleted' "
                            + "AND (body LIKE ? OR title LIKE ? OR zictc_intra_users.email LIKE ?)";
                
                try
                {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, Configurations.getUser().getUserID());
                    stmt.setString(2, "%"+query+"%");
                    stmt.setString(3, "%"+query+"%");
                    stmt.setString(4, "%"+query+"%");

                    ResultSet results = stmt.executeQuery();

                    int counter = 0;
                    while (results.next())
                    {
                        data[counter] = new Mail();
                        data[counter].setId(results.getInt("zictc_intra_mail.id") + "");
                        data[counter].setDisplayNumber(counter + 1);
                        data[counter].setToEmail(results.getString("email"));
                        data[counter].setTitle(results.getString("title"));
                        data[counter].setBody(results.getString("body"));
                        data[counter].setReadStatus(results.getString("read_status"));
                        data[counter].setCreatedAt(results.getTimestamp("created_at").
                            toString()); 

                        counter++;
                    }

                } catch (SQLException e)
                {
                    e.printStackTrace();
                }

                dbr.closeConnection(conn);                
                return data;
            }
        }     
    }
    
    public boolean alterReadStatus(String ID)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "UPDATE " + this.TABLE_NAME + " SET read_status = ?"
                   + "WHERE id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "read");
            stmt.setString(2, ID);
            
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
    
    public boolean deleteForSender(String ID)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "UPDATE " + this.TABLE_NAME + " SET delete_status_sender = ?"
                   + "WHERE id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "deleted");
            stmt.setString(2, ID);
            
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
    
    public boolean deleteForReciever(String ID)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "UPDATE " + this.TABLE_NAME + " SET delete_status_receiver = ?"
                   + "WHERE id = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "deleted");
            stmt.setString(2, ID);
            
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
    
    public int getNumberOfBroadcastMails()
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT * FROM zictc_intra_broadcast_mail";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet results = stmt.executeQuery();

            results.last();
            
            size = results.getRow();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }
    
    public int getNumberOfBroadcastMailsBy(String query)
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();
        
        String sql = "SELECT COUNT(*) FROM zictc_intra_broadcast_mail" + 
            " WHERE title LIKE ? OR body LIKE ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%"+query+"%");
            stmt.setString(2, "%"+query+"%");
                        
            ResultSet results = stmt.executeQuery();

            results.next();
            
            size = results.getInt("COUNT(*)");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }
    
    public int getNumberOfInboxMails()
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT zictc_intra_mail.title,zictc_intra_mail.body,"
                + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                + "zictc_intra_users.email FROM (zictc_intra_mail "
                + "INNER JOIN zictc_intra_users ON from_user_id = zictc_intra_users.id)"
                + "WHERE to_user_id = ? AND zictc_intra_mail.delete_status_receiver = 'not_deleted' ";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Configurations.getUser().getUserID());
            ResultSet results = stmt.executeQuery();

            results.last();
            
            size = results.getRow();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }
    
    public int getNumberOfInboxMailsBy(String query)
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT zictc_intra_mail.title,zictc_intra_mail.body,"
                + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                + "zictc_intra_users.email FROM (zictc_intra_mail "
                + "INNER JOIN zictc_intra_users ON from_user_id = zictc_intra_users.id)"
                + "WHERE zictc_intra_mail.to_user_id = ? AND zictc_intra_mail.delete_status_receiver = 'not_deleted' "
                + "AND (body LIKE ? OR title LIKE ? OR zictc_intra_users.email LIKE ?)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Configurations.getUser().getUserID());
            stmt.setString(2, "%"+query+"%");
            stmt.setString(3, "%"+query+"%");
            stmt.setString(4, "%"+query+"%");
            ResultSet results = stmt.executeQuery();

            results.last();
            
            size = results.getRow();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }  
   
    public int getNumberOfSentMails()
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT zictc_intra_mail.title,zictc_intra_mail.body,"
                + "zictc_intra_mail.read_status, zictc_intra_mail.created_at, "
                + "zictc_intra_users.email FROM (zictc_intra_mail "
                + "INNER JOIN zictc_intra_users ON zictc_intra_mail.to_user_id = "
                + "zictc_intra_users.id)"
                + "WHERE zictc_intra_mail.from_user_id = ? AND "
                + "zictc_intra_mail.delete_status_sender = 'not_deleted' ";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Configurations.getUser().getUserID());
            ResultSet results = stmt.executeQuery();

            results.last();
            
            size = results.getRow();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }
    
    public int getNumberOfSentMailsBy(String query)
    {
        int size = 0;

        RemoteDatabase dbr = new RemoteDatabase();
        Connection conn = dbr.getConnection();

        String sql = "SELECT zictc_intra_mail.title, zictc_intra_mail.body, "
            + "zictc_intra_mail.read_status, zictc_intra_mail.created_at , "
            + "zictc_intra_users.email FROM (zictc_intra_mail "
            + "INNER JOIN zictc_intra_users ON to_user_id = zictc_intra_users.id) "
            + "WHERE zictc_intra_mail.from_user_id = ? AND "
            + "zictc_intra_mail.delete_status_sender = 'not_deleted' AND "
            + "(body LIKE ? OR title LIKE ? OR zictc_intra_users.email LIKE ?)";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Configurations.getUser().getUserID());
            stmt.setString(2, "%"+query+"%");
            stmt.setString(3, "%"+query+"%");
            stmt.setString(4, "%"+query+"%");
            ResultSet results = stmt.executeQuery();

            results.last();
            
            size = results.getRow();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        dbr.closeConnection(conn);
        return size;
    }
}
