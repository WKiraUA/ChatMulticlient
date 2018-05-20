package Cliente;

import javax.swing.*;
import java.awt.*;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jim on 4/21/17.
 */
public class MessagePane extends JPanel implements MessageListener {

    private final ChatClient client;
    private final String login;

    private DefaultListModel<String> listModel;
    private JList<String> messageList;
    private JTextField inputField = new JTextField();

    public MessagePane(ChatClient client, String login) {
        this.client = client;
        this.login = login;

        client.addMessageListener(this);

        listModel = new DefaultListModel<>();
        
        
        if(!(client.getHistorico() == null)){
            ArrayList<String> array = null;
            for(String s : client.getHistorico().keySet()){
                array = client.getHistorico().get(s);
            }
            if(!(array == null)){
                array.forEach((s) -> {
                   listModel.addElement(s);
                });
            }
        }
        
        messageList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    client.msg(login, text);
                    String[] s = { "msg" , "You" , text, login};
                    client.loadHistory(s);
                    listModel.addElement("You: " + text);
                    inputField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        if (login.equalsIgnoreCase(fromLogin)) {
            String line = fromLogin + ": " + msgBody;
            listModel.addElement(line);
        }
    }
}
