import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;

public class RegisterPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  JPanel registerPage = new JPanel(new GridBagLayout());
  JLabel FnameLabel = new JLabel("First Name: ");
  JTextField Fname = new JTextField(32);
  JLabel LnameLabel = new JLabel("Last Name: ");
  JTextField Lname = new JTextField(32);
  JLabel addressLabel = new JLabel("Address: ");
  JTextField address = new JTextField(32);
  JLabel creditCardLabel = new JLabel("Credit Card: ");
  JTextField creditCard = new JTextField(32);
  JButton main = new JButton("Main Page");
  JButton register = new JButton("Register");

  JLabel spacer = new JLabel();

  RegisterPage(JFrame mainFrame, User user) {
    this.mainFrame = mainFrame;
    this.user = user;

    loginPageSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(registerPage);
    mainFrame.validate();
  }

  private void loginPageSetup() {
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    FnameLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(FnameLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    Fname.addActionListener(this);
    Fname.setPreferredSize(new Dimension(225, 30));
    registerPage.add(Fname, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    LnameLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(LnameLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 1;
    Lname.addActionListener(this);
    Lname.setPreferredSize(new Dimension(225, 30));
    registerPage.add(Lname, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 2;
    addressLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(addressLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 2;
    address.addActionListener(this);
    address.setPreferredSize(new Dimension(225, 30));
    registerPage.add(address, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 3;
    creditCardLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(creditCardLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 3;
    creditCard.addActionListener(this);
    creditCard.setPreferredSize(new Dimension(225, 30));
    registerPage.add(creditCard, gbc);

    gbc.anchor = GridBagConstraints.SOUTH;

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 4;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(225, 30));
    registerPage.add(main, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 4;
    spacer.setPreferredSize(new Dimension(225, 30));
    registerPage.add(spacer, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 4;
    register.addActionListener(this);
    register.setPreferredSize(new Dimension(225, 30));
    registerPage.add(register, gbc);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == register) {
      String userFName = Fname.getText();
      String userLName = Lname.getText();
      String userAddress = address.getText();
      String userCreditCard = creditCard.getText();
      try {
        LoginRegisterManager login = LoginRegisterManager.getInstance();
        login.registerUser(user, userFName, userLName, userCreditCard, userAddress);
        JOptionPane.showMessageDialog(mainFrame, "Welcome Registered User");
        new MainMenu(mainFrame, user);
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
    if (e.getSource() == main) {
      new MainMenu(mainFrame, user);
    }
  }
}