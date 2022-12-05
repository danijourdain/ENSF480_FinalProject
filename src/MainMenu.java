import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;

public class MainMenu implements ActionListener 
{
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  JPanel mainPage = new JPanel(new GridBagLayout());
  JLabel userType = new JLabel("UserType"); 
  JButton tickets = new JButton("Tickets");
  JLabel creditLabel = new JLabel("Credit: ");
  JTextField credit = new JTextField(32);
  JButton addCredit = new JButton("Add Credit");
  JTable movieTable;
  JScrollPane movieScroll;
  JButton register = new JButton("Register");
  JButton logout = new JButton("Logout");

  JLabel spacer = new JLabel();

  MainMenu(JFrame mainFrame, User user)
  {
    this.mainFrame = mainFrame;
    this.user = user;

    mainMenuSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(mainPage);
    mainFrame.validate();
  }

  private void tableSet() //This table is not needed, add only if we have time. It's just a list of the movies being played.  
  {
    String[] title = {"Playing", "Movies"};
    String[][] data = {{"movie1", "movie2"}, {"movie3", "movie4"}, {"movie5", "movie6"}, {"movie7", "movie8"}, {"movie9", "movie10"}};

    movieTable = new JTable(data, title);
    movieScroll = new JScrollPane(movieTable);
  }

  private void mainMenuSetup() 
  {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    tableSet();

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    userType.setPreferredSize(new Dimension(150, 30));
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
    mainPage.add(register, gbc);

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

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == tickets)
    {
      new TicketMenu(mainFrame, user);
    }
    else if(e.getSource() == register)
    {
      //Needs to be adjusted for registration
      new RegisterPage(mainFrame, user);
    }
    else if(e.getSource() == logout)
    {
      JOptionPane.showMessageDialog(mainFrame, "Logged Out");
      new LoginPage(mainFrame);
    }
  }
}