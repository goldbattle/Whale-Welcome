package geneva.patrick.whale;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jibble.pircbot.NickAlreadyInUseException;

import geneva.patrick.whale.util.Config;
import geneva.patrick.whale.util.IRCListener;

public class Whale implements IChatListener {

    // Config
    private Config config;

    // Data
    private IRCListener bot;

    /** 
     * Default constructor
     * This creates the connection the twitch servers
     * Also joins the channel
     * @param config Config class
     * @param irc_bot The irc bot that is connected to chat
     */
    public Whale(Config config, IRCListener irc_bot) {
        // Set the config
        this.config = config;
        // Set the bot
        this.bot = irc_bot;
        // Add this class as a listener
        bot.addListener(this);
        // Disable debugging output.
        bot.setVerbose(false);
        // Display enable/disabled status
        if(config.enable_sub_normal) {
            System.out.println("[Info]["+getTime()+"]: Normal Subscriptions: Enabled");
        } else {
            System.out.println("[Info]["+getTime()+"]: Normal Subscriptions: Disabled");
        }
        // Display enable/disabled status
        if(config.enable_sub_resub) {
            System.out.println("[Info]["+getTime()+"]: Resub Subscriptions: Enabled");
        } else {
            System.out.println("[Info]["+getTime()+"]: Resub Subscriptions: Disabled");
        }
        try {
            // Connect to the IRC server.
            System.out.println("[Info]["+getTime()+"]: Connecting to Twitch Servers");
            bot.connect("irc.twitch.tv", 6667, config.password_oauth);
        } catch (NickAlreadyInUseException e1) {
            System.out.println("[ERROR]["+getTime()+"]: IRC Bot Nick Already In Use");
            System.exit(1);
        } catch (Exception e1) {
            System.out.println("[ERROR]["+getTime()+"]: Unable to Connect to Twitch Servers");
            System.exit(1);
        }
        // Success
        System.out.println("[Info]["+getTime()+"]: Successfully Connected to Twitch Servers");
        // Tell twitch we want api messages
        bot.sendRawLine("TWITCHCLIENT 3");
        try {
            // Join our welcome channel
            System.out.println("[Info]["+getTime()+"]: Trying to Join " + config.stream_id);
            bot.joinChannel("#"+Config.stream_id);
        } catch(Exception e){
            System.out.println("[Error]["+getTime()+"]: Unable to join stream");
            System.exit(1);
        }
        // Success
        System.out.println("[Info]["+getTime()+"]: Successfully Connected to " + config.stream_id);
    }
    
    /**
     * Call back for chat messages
     * If the chat message if from twitch notify then handle it
     * If it is a subscription then figure out what to do with it
     */
    @Override
    public void handle_message(String channel, String sender, String message) {
        // Handle if we get a message from twitch
        if(sender.equals("twitchnotify")) {
            // Split based on spaces
            String[] message_split = message.split(" ");
            // Normal subscription
            if(message_split.length == 3) {
                // Data
                String username = message_split[0];
                // Debug
                System.out.println("[Info]["+getTime()+"]: Subscription "  + username + " (1 month)");
                // If enabled send message
                if(config.enable_sub_normal) {
                    // Debug
                    System.out.println("[Info]["+getTime()+"]: Sending Message");
                    // Send
                    String send_message = config.chat_message_normal.replace("{username}", username);
                    bot.sendMessage("#"+config.stream_id, send_message);
                }
            }
            // Resub subscriptions
            else if(message_split.length == 8) {
                // Data
                String username = message_split[0];
                String months = message_split[3];
                // Debug
                System.out.println("[Info]["+getTime()+"]: Subscription "  + username + " (" + months + " months)");
                // If enabled send message
                if(config.enable_sub_resub) {
                    // Debug
                    System.out.println("[Info]["+getTime()+"]: Sending Message");
                    // Send
                    String send_message = config.chat_message_resub.replace("{username}", username);
                    send_message = send_message.replace("{months}", months);
                    bot.sendMessage("#"+config.stream_id, send_message);
                }
            }
            // Welcome back messages
            else {
                System.out.println("[Info]["+getTime()+"]: " + message);
            }
        }
    }
    
    /**
     * Handle notices
     * For now, just print the error, and exit
     */
    @Override
    public void handle_notice(String sender, String message) {
        System.out.println("[Error]["+getTime()+"]: "+message);
        System.exit(1);
    }
    
    /**
     * Returns a nice little helper to get the current time
     * Very nice for the logger
     */
    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
