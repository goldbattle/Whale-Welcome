package geneva.patrick.whale;

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
     * @param config
     * @param irc_bot 
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
            System.out.println("[Info]: Normal Subscriptions: Enabled");
        } else {
            System.out.println("[Info]: Normal Subscriptions: Disabled");
        }
        // Display enable/disabled status
        if(config.enable_sub_resub) {
            System.out.println("[Info]: Resub Subscriptions: Enabled");
        } else {
            System.out.println("[Info]: Resub Subscriptions: Disabled");
        }
        try {
            // Connect to the IRC server.
            System.out.println("[Info]: Connecting to Twitch Servers");
            bot.connect("irc.twitch.tv", 6667, config.password_oauth);
        } catch (NickAlreadyInUseException e1) {
            System.out.println("[ERROR]: IRC Bot Nick Already In Use");
            System.exit(1);
        } catch (Exception e1) {
            System.out.println("[ERROR]: Unable to Connect to Twitch Servers");
            System.exit(1);
        }
        // Success
        System.out.println("[Info]: Successfully Connected to Twitch Servers");
        // Tell twitch we want api messages
        bot.sendRawLine("TWITCHCLIENT 3");
        try {
            // Join our welcome channel
            System.out.println("[Info]: Trying to Join " + config.stream_id);
            bot.joinChannel("#"+Config.stream_id);
        } catch(Exception e){
            System.out.println("[Error]: Unable to join stream");
            System.exit(1);
        }
        // Success
        System.out.println("[Info]: Successfully Connected to " + config.stream_id);
    }

    @Override
    public void handle_message(String channel, String sender, String message) {
        // Handle if we get a message from twitch
        if(sender.equals("twitchnotify")) {
            // Split based on spaces
            String[] message_split = message.split(" ");
            // Normal subscription
            if(message_split.length == 3 && config.enable_sub_normal) {
                System.out.println("[Info]: Subscription "  + message_split[0] + " (1 month)");
                // If enabled send message
                if(config.enable_sub_normal) {
                    String send_message = config.chat_message_normal.replaceAll("{username}", message_split[0]);
                    System.out.println("[Info]: " + send_message);
                }
            }
            // Resub subscriptions
            else if(message_split.length == 8) {
                System.out.println("[Info]: Subscription "  + message_split[0] + " (" + message_split[3] + " months)");
                // If enabled send message
                if(config.enable_sub_resub) {
                    String send_message = config.chat_message_normal.replaceAll("{username}", message_split[0]);
                    send_message = message.replaceAll("{months}", message_split[3]);
                    System.out.println("[Info]: " + send_message);
                }
            }
            // Welcome back messages
            else {
                System.out.println(message);
            }
        }
    }

    @Override
    public void handle_notice(String sender, String message) {
        System.out.println("[Error]: "+message);
        System.exit(1);
    }

}
