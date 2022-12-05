import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.util.*;
//import java.sql.*;

public class UserTicketPage implements ActionListener 
{
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  ArrayList<Ticket> userTickets;
  JPanel ticketPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JLabel ticketLabel = new JLabel("Tickets");
  DefaultTableModel tableModel;
  JTable movieTable;
  JScrollPane movieScroll;

  JLabel spacer = new JLabel();
  
  UserTicketPage(JFrame mainFrame, User user)
  {
    this.mainFrame = mainFrame;
    this.user = user;

    ticketMenuSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(ticketPage);
    mainFrame.validate();
  }

  private void tableSet() //PULL USER TICKET INFO FOR TABLE
  {
    TicketManager ticketM = TicketManager.getInstance();
    //userTickets = ticketM.getShowtimes();
    /*
    String[] columnNames = { "Movie", "Date", "Time", "Duration", "" };
    Object[][] data = new Object[showtimes.size()][5];
    for (int i = 0; i < showtimes.size(); i++) {
      data[i][0] = showtimes.get(i).getMTitle();
      data[i][1] = showtimes.get(i).getShowDateTime().toLocalDate().toString();
      data[i][2] = showtimes.get(i).getShowDateTime().toLocalTime().toString();
      data[i][4] = "Select";
      try {
        data[i][3] = Integer.toString((ticketM.getMovie(showtimes.get(i).getMTitle())).getDuration());
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
    */
 
    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(4).setMaxWidth(50);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
    movieScroll = new JScrollPane(movieTable);

    ButtonColumn buttonColumn = new ButtonColumn(movieTable, select, 4);
    buttonColumn.setMnemonic(KeyEvent.VK_D);
  }

  private void ticketMenuSetup() 
  {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    tableSet();

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(150, 30));
    ticketPage.add(main, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 3;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    ticketPage.add(spacer, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 1;
    ticketLabel.setPreferredSize(new Dimension(600, 30));
    ticketPage.add(ticketLabel, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 2;
    movieScroll.setPreferredSize(new Dimension(600, 400));
    ticketPage.add(movieScroll, gbc);
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == main)
    {
      new MainMenu(mainFrame, user);
    }
  }

  Action select = new AbstractAction()
  {
    public void actionPerformed(ActionEvent e)
    {
      int modelRow = Integer.valueOf(e.getActionCommand());
      JOptionPane.showMessageDialog(mainFrame, "Delete ticket " + modelRow); //REMOVE TICKET BASED ON INDEX
    }
  };
}