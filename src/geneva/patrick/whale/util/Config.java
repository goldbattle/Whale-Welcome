package geneva.patrick.whale.util;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

public class Config {

    // Twitch chat username
    public static String username;
    // Twitch oath for chat, http://twitchapps.com/tmi/
    public static String password_oauth;
    // Stream id
    public static String stream_id;
    // Toggle sub alert
    public static boolean enable_sub_normal;
    // Toggle resub alert
    public static boolean enable_sub_resub;
    // Message for normal subs
    public static String chat_message_normal;
    // Message for resubs
    public static String chat_message_resub;
    
    /**
     * Read in config information
     * Store that information into the public variables
     */
    public void readConfig() {
        // Read in data
        String data = "";
        // Read in the file
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("config.json"));
            data = new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("[Error]["+getTime()+"]: Unable to Locate \"config.json\"");
            System.exit(1);
        }
        // Set our config information
        try {
            JSONObject json = new JSONObject(data);
            // Set data
            username = json.getString("username");
            password_oauth = json.getString("password_oauth");
            stream_id = json.getString("stream_id");
            enable_sub_normal = json.getBoolean("enable_sub_normal");
            enable_sub_resub = json.getBoolean("enable_sub_resub");
            chat_message_normal = json.getString("chat_message_normal");
            chat_message_resub = json.getString("chat_message_resub");
        } catch (Exception e) {
            System.out.println("[Error]: Invalid Config File");
            System.exit(1);
        }

    }
    
    /**
     * Returns a nice little helper to get the current time
     * Very nice for the logger
     */
    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
