package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.*;
import object.*;

public class UserTicketPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  ArrayList<Ticket> userTickets;
  ArrayList<Credit> userCredits;
  JPanel ticketPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JLabel ticketLabel = new JLabel("Tickets");
  DefaultTableModel ticketTableModel;
  JLabel creditLabel = new JLabel("Credits");
  DefaultTableModel credittableModel;
  JTable ticketTable;
  JTable creditTable;
  JScrollPane ticketScroll = new JScrollPane();
  JScrollPane creditScroll = new JScrollPane();

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

    String[] columnNames1 = { "Movies", "Date", "Time", "Seat", "" };
    Object[][] data1 = new Object[userTickets.size()][5];
    for (int i = 0; i < userTickets.size(); i++) {
      data1[i][0] = userTickets.get(i).getShowtime().getMTitle();
      data1[i][1] = userTickets.get(i).getShowtime().getShowDateTime().toLocalDate().toString();
      data1[i][2] = userTickets.get(i).getShowtime().getShowDateTime().toLocalTime().toString();
      data1[i][3] = userTickets.get(i).getTicketNo();
      data1[i][4] = "Cancel";
    }

    ticketTableModel = new DefaultTableModel(data1, columnNames1) {
      @Override
      public boolean isCellEditable(int row, int column) {
        if (column == 4)
          return true;
        return false;
      }
    };
    ticketTable = new JTable(ticketTableModel);
    ticketTable.getColumnModel().getColumn(4).setMaxWidth(50);
    ticketTable.getColumnModel().getColumn(0).setMinWidth(200);
    ticketScroll = new JScrollPane(ticketTable);

    ButtonColumn buttonColumn = new ButtonColumn(ticketTable, select, 4);
    buttonColumn.setMnemonic(KeyEvent.VK_D);

    userCredits = user.getCredits();
    String[] columnNames2 = { "Issued", "Expired", "Amount" };
    Object[][] data2 = new Object[userCredits.size()][3];
    for (int i = 0; i < userCredits.size(); i++) {
      data2[i][0] = userCredits.get(i).getIssueDate().toString();
      data2[i][1] = userCredits.get(i).getExpiryDate().toString();
      data2[i][2] = String.valueOf(userCredits.get(i).getCreditAmount());
    }

    credittableModel = new DefaultTableModel(data2, columnNames2) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    creditTable = new JTable(credittableModel);
    creditScroll = new JScrollPane(creditTable);
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
    ticketScroll.setPreferredSize(new Dimension(600, 300));
    ticketPage.add(ticketScroll, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 3;
    creditLabel.setPreferredSize(new Dimension(600, 30));
    ticketPage.add(creditLabel, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 4;
    creditScroll.setPreferredSize(new Dimension(600, 300));
    ticketPage.add(creditScroll, gbc);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == main) {
      new MainPage(mainFrame, user);
    }
  }

  Action select = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      int n = JOptionPane.showConfirmDialog(null, "Confirm Cancellation", "", JOptionPane.YES_NO_OPTION);
      if (n == JOptionPane.YES_OPTION) {
        int ticketNum = Integer.valueOf(e.getActionCommand());
        try {
          TicketManager ticket = TicketManager.getInstance();
          ticket.refundTicket(userTickets.get(ticketNum), user);
          JOptionPane.showMessageDialog(mainFrame, "Ticket Refunded");
          new UserTicketPage(mainFrame, user);
        } catch (Exception f) {
          JOptionPane.showMessageDialog(mainFrame, f.getMessage());
        }
      }
    }
  };
}