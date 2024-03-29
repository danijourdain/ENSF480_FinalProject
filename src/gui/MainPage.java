package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.*;
import object.*;

public class MainPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  JPanel mainPage = new JPanel(new GridBagLayout());
  JLabel userType = new JLabel("UserType");
  JButton tickets = new JButton("Tickets");
  JLabel creditLabel = new JLabel("Credit: ");
  JButton register = new JButton("Register");
  JButton logout = new JButton("Logout");

  JLabel spacer = new JLabel();

  /**
   * @param mainFrame GUI main display
   * @param user      The user currently logged it
   *
   * Setup and display main page
   * If a user is of type "Expired", a pop-up will be generated
   * asking the user if they would like to renew their registration
   */
  public MainPage(JFrame mainFrame, User user) {
    this.mainFrame = mainFrame;
    this.user = user;
    if (user.getType().equals("Expired")) {
      int n = JOptionPane.showConfirmDialog(mainFrame,
          "Would you like to renew your registration?\nYou will be charged 20 dollars", "", JOptionPane.YES_NO_OPTION);

      try {
        if (n == JOptionPane.YES_OPTION) {
          int res = LoginRegisterManager.getInstance().renewUser(user);
          System.out.println("here" + res);
          if (res >= 0) {
            JOptionPane.showMessageDialog(mainFrame, "Registration Purchased\nCard Charged: " + res);
            new MainPage(mainFrame, user);
          }
        } else {
          LoginRegisterManager deregister = LoginRegisterManager.getInstance();
          deregister.deregisterUser(user);
          new MainPage(mainFrame, user);
        }
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }

    mainMenuSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(mainPage);
    mainFrame.validate();
  }

  /**
   * Add all elements to the JPanel which will be displayed
   */
  private void mainMenuSetup() {
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    userType.setPreferredSize(new Dimension(150, 30));

    userType = new JLabel(user.getType());
    mainPage.add(userType, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    mainPage.add(spacer, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 0;
    tickets.addActionListener(this);
    tickets.setPreferredSize(new Dimension(150, 30));
    mainPage.add(tickets, gbc);

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    register.addActionListener(this);
    register.setPreferredSize(new Dimension(150, 30));
    System.out.println(user.getType());
    if (user.getType().equals("Registered")) {
      mainPage.add(spacer, gbc);
    } else {
      mainPage.add(register, gbc);
    }

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 1;
    spacer.setPreferredSize(new Dimension(225, 30));
    mainPage.add(spacer, gbc);

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 1;
    logout.addActionListener(this);
    logout.setPreferredSize(new Dimension(150, 30));
    mainPage.add(logout, gbc);
  }

  /**
   * @param e Trigger of an event
   *
   * Based on the trigger, perform a function
   * tickets - Display ticket page
   * register - Display registration page
   * logout - Display logout page (no user object passed)
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == tickets) {
      new TicketPage(mainFrame, user);
    } else if (e.getSource() == register) {
      new RegisterPage(mainFrame, user);
    } else if (e.getSource() == logout) {
      JOptionPane.showMessageDialog(mainFrame, "Logged Out");
      new LoginPage(mainFrame);
    }
  }
}