import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.*;

public class Options extends JFrame {
  JButton signButton = new JButton("Sign up");
  JButton loginButton = new JButton("Login");
  JPanel optionsPanel = new JPanel();

  public Options() {
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    signButton.addActionListener(new ClickOnSign());
    loginButton.addActionListener(new ClickOnLogin());
    optionsPanel.add(signButton);
    optionsPanel.add(loginButton);
    add(optionsPanel);
    setVisible(true);
  }

  class ClickOnSign implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      Sign sign = new Sign();

    }

  }

  class ClickOnLogin implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      Login login = new Login();

    }

  }
}
