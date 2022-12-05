import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.util.*;
//import java.sql.*;

public class UserTicketPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  ArrayList<Ticket> userTickets;
  JPanel ticketPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JLabel ticketLabel = new JLabel("Tickets");
  DefaultTableModel tableModel;
  JTable movieTable;
  JScrollPane movieScroll = new JScrollPane();

  JLabel spacer = new JLabel();

  UserTicketPage(JFrame mainFrame, User user) {
    this.mainFrame = mainFrame;
    this.user = user;

    ticketMenuSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(ticketPage);
    mainFrame.validate();
  }

  private void tableSet() // PULL USER TICKET INFO FOR TABLE
  {
    TicketManager ticketM = TicketManager.getInstance();
    userTickets = ticketM.getUserTickets(user);

    String[] columnNames = { "Movie", "Date", "Time", "Seat", "" };
    Object[][] data = new Object[userTickets.size()][5];
    for (int i = 0; i < userTickets.size(); i++) {
      data[i][0] = userTickets.get(i).getShowtime().getMTitle();
      data[i][1] = userTickets.get(i).getShowtime().getShowDateTime().toLocalDate().toString();
      data[i][2] = userTickets.get(i).getShowtime().getShowDateTime().toLocalTime().toString();
      data[i][3] = userTickets.get(i).getTicketNo();
      data[i][4] = "Cancel";
    }

    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(4).setMaxWidth(50);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
    movieScroll = new JScrollPane(movieTable);

    ButtonColumn buttonColumn = new ButtonColumn(movieTable, select, 4);
    buttonColumn.setMnemonic(KeyEvent.VK_D);
  }

  private void ticketMenuSetup() {
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

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == main) {
      new MainMenu(mainFrame, user);
    }
  }

  Action select = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      int ticketNum = Integer.valueOf(e.getActionCommand());
      try {
        TicketManager ticket = TicketManager.getInstance();
        ticket.RefundTicket(userTickets.get(ticketNum), user);
        JOptionPane.showMessageDialog(mainFrame, "Ticket Refunded");
        new UserTicketPage(mainFrame, user);
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
  };
}