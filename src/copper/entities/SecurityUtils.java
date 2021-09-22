package copper.entities;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils
{

    public static String hashText(String plainText)
    {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static String generateUsername(User obj)
    {
        String username = "";
        String email = obj.getEmail();        
        for (int i = 0; i < email.length(); i++)
        {
            if (email.charAt(i) == '@')
                break;
      
            username += email.charAt(i);
        }
        
        return username + "@copper.dev";
    }

    public static String generatePassword(String key)
    {
        String password = "";
        String temp = SecurityUtils.hashText(key);
        for (int i = 0; i < 10; i++)
            password += temp.charAt(i);

        return password;
    }

}
