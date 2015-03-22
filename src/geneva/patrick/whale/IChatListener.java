package geneva.patrick.whale;

public interface IChatListener {
    
    // Callback method for irc notices
    public void handle_notice(String sender, String message);
    
    // Callback method for chat messages
    public void handle_message(String channel, String sender, String message);
    
}
