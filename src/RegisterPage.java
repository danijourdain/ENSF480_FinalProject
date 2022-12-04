import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;

public class RegisterPage implements ActionListener 
{
  GridBagConstraints gbc = new GridBagConstraints();
  
  JFrame mainFrame;
  User user;
  JPanel registerPage = new JPanel(new GridBagLayout());
  JLabel nameLabel = new JLabel("Name: ");
  JTextField name = new JTextField(32);
  JLabel addressLabel = new JLabel("Address: ");
  JTextField address = new JTextField(32);
  JLabel creditCardLabel = new JLabel("Credit Card: ");
  JTextField creditCard = new JTextField(32);
  JButton main = new JButton("Main Page");
  JButton register = new JButton("Register");

  JLabel spacer = new JLabel();

  RegisterPage(JFrame mainFrame, User user)
  {
    this.mainFrame = mainFrame;
    this.user = user;

    loginPageSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(registerPage);
    mainFrame.validate();
  }

  private void loginPageSetup() 
  {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    nameLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(nameLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    name.addActionListener(this);
    name.setPreferredSize(new Dimension(225, 30));
    registerPage.add(name, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    addressLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(addressLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 1;
    address.addActionListener(this);
    address.setPreferredSize(new Dimension(225, 30));
    registerPage.add(address, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 2;
    creditCardLabel.setPreferredSize(new Dimension(150, 30));
    registerPage.add(creditCardLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 2;
    creditCard.addActionListener(this);
    creditCard.setPreferredSize(new Dimension(225, 30));
    registerPage.add(creditCard, gbc);

    gbc.anchor = GridBagConstraints.SOUTH; 

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 3;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(225, 30));
    registerPage.add(main, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 3;
    spacer.setPreferredSize(new Dimension(225, 30));
    registerPage.add(spacer, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 3;
    register.addActionListener(this);
    register.setPreferredSize(new Dimension(225, 30));
    registerPage.add(register, gbc);
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == register)
    {
      String userName;
      String userAddress;
      String userCreditCard;
      userName = name.getText();
      userAddress = address.getText();
      userCreditCard = creditCard.getText();

      if(userName.equalsIgnoreCase("test") && userAddress.equalsIgnoreCase("test") && userCreditCard.equalsIgnoreCase("1234")) 
      {
        JOptionPane.showMessageDialog(null, "Account Exists with Given Information");
      } 
      else 
      {
        JOptionPane.showMessageDialog(mainFrame, "Welcome Registered User");
        new MainMenu(mainFrame, user);
      }
    }
    if(e.getSource() == main)
    {
      new MainMenu(mainFrame, user);
    }
  }
}