package Cliente;


public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
