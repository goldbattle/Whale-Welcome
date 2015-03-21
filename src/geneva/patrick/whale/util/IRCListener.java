package geneva.patrick.whale.util;

import geneva.patrick.whale.IChatListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jibble.pircbot.PircBot;

public class IRCListener extends PircBot implements EventListener, IChatListener {

    // Config
    private Config config;

    // Listeners
    private List<IChatListener> listeners;

    // Data
    private HashMap<String, String> users_color;
    private HashMap<String, String> users_emotes;

    /**
     * Main Constructor for this IRC bot
     */
    public IRCListener(Config config) {
        // Set the config
        this.config = config;
        // Listener
        listeners = new ArrayList<IChatListener>();
        // Create our data hashes
        users_color = new HashMap<String, String>();
        users_emotes = new HashMap<String, String>();
        // Set our bot username
        this.setName(config.username);
    }

    public void addListener(IChatListener listener) {
        listeners.add(listener);
    }

    /**
     * Handle the incoming messages
     */
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        // Split the message up
        String[] temp = message.split(" ");
        // Test for message types
        switch(temp[0]){
        case "USERCOLOR":
            //users_color.put(temp[1], temp[2]);
            break;
        case "EMOTESET":
            //users_emotes.put(temp[1], temp[2]);
            break;
        case "SPECIALUSER":
            break;
        case "HOSTTARGET":
            break;
        case "CLEARCHAT":
            break;
        case "HISTORYEND":
            break;
        default:
            // Return if null, remove hashtag
            if(channel == null)
                return;
            // Loop through each listener
            for (IChatListener listener : listeners) {
                // Fire an event, remove the channel '#' sign
                listener.handle_message(channel.substring(1), sender, message);
            }

        }
    }

    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        // Loop through each listener
        for (IChatListener listener : listeners) {
            listener.handle_notice(sourceNick, notice);
        }
    }
    /**
     * Debug output
     */
    public void handle_message(String channel, String sender, String message) {
        // Create a row
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("channel", channel);
        json.put("user_name", sender);
        json.put("user_color", users_color.get(sender));
        json.put("user_emotes", users_emotes.get(sender));
        json.put("user_message", message);
        json.put("date_created", new Date());

        // Debug output, uncomment to see console spam
        System.out.println(json.toString());

    }

    @Override
    public void handle_notice(String sender, String message) {
        // TODO Auto-generated method stub

    }

}
