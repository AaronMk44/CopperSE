package copper.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import copper.confidential.RemoteDatabase;

public class LegalModel
{

    private final String prefix = "zictc_intra_";
    private final String TABLE_NAME = this.prefix + "legal";

    public LegalModel()
    {
    }

    public String getLegalMessage(String property)
    {
        RemoteDatabase rdb = new RemoteDatabase();
        Connection conn = rdb.getConnection();

        String sql = "SELECT * FROM " + this.TABLE_NAME + " WHERE property = ?";
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, property);

            ResultSet results = stmt.executeQuery();

            while (results.next())
            {
                return results.getString("value");
            }           
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        rdb.closeConnection(conn);
        return "";
    }
}
