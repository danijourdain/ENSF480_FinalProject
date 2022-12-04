import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.sql.*;

public class MainMenu implements ActionListener {
  public static void main(String args[]) 
  {
    new MainMenu();
  }

  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame = new JFrame("Depressed SENG Student Theatres"); //This class will return a panel for GUI to display not a new Jframe
  JPanel loginPage = new JPanel(new GridBagLayout());
  JLabel userType = new JLabel("UserType"); //Will be changed with strategy
  JButton tickets = new JButton("Tickets");
  JLabel creditLabel = new JLabel("Credit: ");
  JTextField credit = new JTextField(32);
  JButton addCredit = new JButton("Add Credit");
  JTable movieTable;
  JScrollPane movieScroll;
  JButton register = new JButton("Register"); //Won't be shown based on strategy
  JButton logout = new JButton("Logout");

  JLabel spacer = new JLabel();

  MainMenu()
  {
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainMenuSetup();

    mainFrame.add(loginPage);

    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);
  }

  private void tableSet() {
    String[] title = {"Playing", "Movies"};
    String[][] data = {{"movie1", "movie2"}, {"movie3", "movie4"}, {"movie5", "movie6"}, {"movie7", "movie8"}, {"movie9", "movie10"}};

    movieTable = new JTable(data, title);
    movieScroll = new JScrollPane(movieTable);
  }

  private void mainMenuSetup() {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    tableSet();

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    userType.setPreferredSize(new Dimension(150, 30));
    loginPage.add(userType, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    loginPage.add(spacer, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 0;
    tickets.addActionListener(this);
    tickets.setPreferredSize(new Dimension(150, 30));
    loginPage.add(tickets, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    creditLabel.setPreferredSize(new Dimension(50, 30));
    loginPage.add(creditLabel, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 1;
    credit.setPreferredSize(new Dimension(50, 30));
    credit.setEditable(false);
    loginPage.add(credit, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 1;
    spacer.setPreferredSize(new Dimension(225, 30));
    loginPage.add(spacer, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 1;
    addCredit.addActionListener(this);
    addCredit.setPreferredSize(new Dimension(150, 30));
    loginPage.add(addCredit, gbc);

    gbc.insets = new Insets(15, 0, 0, 0);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 2;
    movieScroll.setPreferredSize(new Dimension(225, 200));
    loginPage.add(movieScroll, gbc);

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 3;
    register.addActionListener(this);
    register.setPreferredSize(new Dimension(150, 30));
    loginPage.add(register, gbc);

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 3;
    spacer.setPreferredSize(new Dimension(225, 30));
    loginPage.add(spacer, gbc);

    gbc.insets = new Insets(50, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 3;
    logout.addActionListener(this);
    logout.setPreferredSize(new Dimension(150, 30));
    loginPage.add(logout, gbc);
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == tickets)
    {
      JOptionPane.showMessageDialog(mainFrame, "Show tickets page");
    }
    else if (e.getSource() == addCredit)
    {
      JOptionPane.showMessageDialog(mainFrame, "Show add credits");
    }
    else if (e.getSource() == register)
    {
      JOptionPane.showMessageDialog(mainFrame, "Pop up confirmation");
    }
    else if (e.getSource() == logout)
    {
      JOptionPane.showMessageDialog(mainFrame, "Confirmation then login page");
    }
  }
}