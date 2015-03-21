package geneva.patrick.whale;

public interface IChatListener {
    public void handle_notice(String sender, String message);
    public void handle_message(String channel, String sender, String message);
}
