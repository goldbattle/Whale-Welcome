package geneva.patrick;

import geneva.patrick.whale.Whale;
import geneva.patrick.whale.util.Config;
import geneva.patrick.whale.util.IRCListener;


public class Main {

    public static void main(String[] args) throws Exception {
        // Create the config
        Config config = new Config();
        config.readConfig();
        // Irc bot
        IRCListener irc_bot = new IRCListener(config);
        // Create the whale
        Whale whale = new Whale(config, irc_bot);
    }

}
