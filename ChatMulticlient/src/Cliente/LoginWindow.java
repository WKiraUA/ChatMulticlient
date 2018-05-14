package Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by jim on 4/24/17.
 */
public class LoginWindow extends JFrame {
    private final ChatClient client;
    JTextField loginField = new JTextField();
    JButton loginButton = new JButton("Login");

    public LoginWindow() throws IOException {
        super("Login");
        
        this.client = new ChatClient("localhost", 8818);
        client.connect();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(loginField);
        p.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    doLogin();
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getContentPane().add(p, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin() throws IOException {
        String login = loginField.getText();

        if (client.login(login)) {
            // bring up the user list window
            UserListPane userListPane = new UserListPane(client);
            JFrame frame = new JFrame("User List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 600);
            
            frame.getContentPane().add(userListPane, BorderLayout.CENTER);
            frame.setVisible(true);
            
            setVisible(false);
        } else {
            // show error message
            JOptionPane.showMessageDialog(this, "Invalid login.");
        }
    }

    public static void main(String[] args) throws IOException {
        LoginWindow loginWin = new LoginWindow();
        loginWin.setVisible(true);
    }
}
