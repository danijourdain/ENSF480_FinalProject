package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;

import control;
import MainMenu;

public class LoginPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  JPanel loginPage = new JPanel(new GridBagLayout());
  JLabel emailLabel = new JLabel("Email: ");
  JTextField email = new JTextField(32);
  JLabel passwordLabel = new JLabel("Password: ");
  JPasswordField password = new JPasswordField(32);
  JButton signUp = new JButton("Sign Up");
  JButton login = new JButton("Login");

  JLabel spacer = new JLabel();

  LoginPage(JFrame mainFrame) {
    this.mainFrame = mainFrame;

    loginPageSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(loginPage);
    mainFrame.validate();
  }

  private void loginPageSetup() {
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    emailLabel.setPreferredSize(new Dimension(150, 30));
    loginPage.add(emailLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    email.addActionListener(this);
    email.setPreferredSize(new Dimension(225, 30));
    loginPage.add(email, gbc);

    gbc.insets = new Insets(10, 15, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    passwordLabel.setPreferredSize(new Dimension(150, 30));
    loginPage.add(passwordLabel, gbc);

    gbc.insets = new Insets(10, 5, 15, 15);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 1;
    password.addActionListener(this);
    password.setPreferredSize(new Dimension(225, 30));
    loginPage.add(password, gbc);

    gbc.anchor = GridBagConstraints.SOUTH;

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 2;
    signUp.addActionListener(this);
    signUp.setPreferredSize(new Dimension(225, 30));
    loginPage.add(signUp, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 2;
    spacer.setPreferredSize(new Dimension(225, 30));
    loginPage.add(spacer, gbc);

    gbc.insets = new Insets(30, 0, 0, 0);
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 2;
    login.addActionListener(this);
    login.setPreferredSize(new Dimension(225, 30));
    loginPage.add(login, gbc);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == signUp) {
      String userEmail = email.getText();
      String userPass = String.valueOf(password.getPassword());
      try {
        LoginRegisterManager login = LoginRegisterManager.getInstance();
        User user = login.createNewUser(userEmail, userPass);
        new MainMenu(mainFrame, user);
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
    if (e.getSource() == login) {
      String userEmail = email.getText();
      String userPass = String.valueOf(password.getPassword());
      try {
        LoginRegisterManager login = LoginRegisterManager.getInstance();
        User user = login.login(userEmail, userPass);
        new MainMenu(mainFrame, user);
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
  }
}
