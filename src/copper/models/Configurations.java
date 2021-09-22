package copper.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import copper.entities.User;
import copper.views.dialogs.ErrorView;

public class Configurations
{
	static private JSONObject json = null;
    static private String configFileName = 
        "C:\\Users\\"+System.getProperty("user.name")+"\\cunfig.json";
    static private String hashFileName = 
            "C:\\Users\\"+System.getProperty("user.name")+"\\A50B7BA3-0E63-43F9-83F1-E4C038A4BF39.txt";
    static private String defaultConfig = "{\"server_ip_address\" : \"127.0.0.1\","
                                        + "\"theme\" : \"light\"}";
    static private User loggedUser = null;

    public static void intit()
    {
        System.out.println("asdnfsajdn");
        File configFile = new File(configFileName);
        try
        {
            JSONParser parser = new JSONParser();
            
            if(configFile.exists())
            {    
                //read in the object
                Scanner input = new Scanner(configFile); 
                String tmp = "";    
                while(input.hasNextLine())
                {
                    tmp += input.nextLine();
                }
                input.close();
                Object obj = parser.parse(tmp);
                json = (JSONObject) obj;
            }else
            {
                //create the config file and set the default configurations
                configFile.createNewFile();
                FileWriter writer = new FileWriter(configFile);
                writer.write(defaultConfig);
                writer.close();
                
                //intitialize the json object with defualt configurations
                Object obj = parser.parse(defaultConfig);
                json = (JSONObject) obj;
            }
            
        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } catch (ParseException ex)
        {
            ErrorView box = new ErrorView("The configuration file is corrupt."
                                        + "Contact the administrator to resolve.");
            box.getWindow();
        } catch (IOException ex)
        {
            Logger.getLogger(Configurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setConfig(String key, String value)
    {
        //write to json
        value.replace('"', ' ');
        json.put(key, value);
        
        //write changes to config file        
        File configFile = new File(configFileName);    
        
        try
        {
            if(configFile.exists())
            {
                StringWriter out = new StringWriter();
                try{ json.writeJSONString(out); } 
                catch (IOException ex){}

                FileWriter writer;
                try
                {
                    writer = new FileWriter(configFile);
                    writer.write(out.toString());
                    writer.close();
                } catch (IOException ex){ex.printStackTrace();}
            }else
            {
                //create the config file and set the current configurations
                configFile.createNewFile();
                
                StringWriter out = new StringWriter();
                try{ json.writeJSONString(out); } 
                catch (IOException ex){}

                FileWriter writer;
                try
                {
                    writer = new FileWriter(configFile);
                    writer.write(out.toString());
                    writer.close();
                } catch (IOException ex){ex.printStackTrace();}
 
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    public static String getConfig(String key)
    {
        return (String) json.get(key);
    }
    
    public static void setUser(User obj)
    {
        loggedUser = obj;
    }
    
    public static User getUser()
    {
        return loggedUser;
    }
    
    public static void authorise()
    {
    	File hashFile = new File(hashFileName);
    	try
    	{
    		if(hashFile.exists())
            {                
                hashFile.delete();
            }else
            {
    			hashFile.createNewFile();
    			FileWriter writer = new FileWriter(hashFile);
    			writer.write(defaultConfig);            
                writer.close();
            }    		
    	}catch (FileNotFoundException ex)
        {
            ex.printStackTrace();        
        } catch (IOException ex)
        {
            Logger.getLogger(Configurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
