package geneva.patrick.whale.util;

import geneva.patrick.whale.IChatListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jibble.pircbot.PircBot;

public class IRCListener extends PircBot implements EventListener {

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
    
    /**
     * Subscribes a listener to this source
     * @param listener The listener class that wants callbacks
     */
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
    
    /**
     * Handle error notices
     * This handles twitch replying that login was unsuccessful
     */
    @Override
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        // Loop through each listener
        for (IChatListener listener : listeners) {
            listener.handle_notice(sourceNick, notice);
        }
    }

}
