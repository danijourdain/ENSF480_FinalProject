package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import controller.*;
import object.*;

public class TicketMenu implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  ArrayList<Showtime> showtimes;
  JPanel ticketPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JButton userTickets = new JButton("Account");
  JLabel showtimeLabel = new JLabel("Showtimes");
  DefaultTableModel tableModel;
  JTable movieTable;
  JScrollPane movieScroll;

  JLabel spacer = new JLabel();

  TicketMenu(JFrame mainFrame, User user) {
    this.mainFrame = mainFrame;
    this.user = user;

    ticketMenuSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(ticketPage);
    mainFrame.validate();
  }

  private void tableSet() {
    TicketManager ticketM = TicketManager.getInstance();
    showtimes = ticketM.getShowtimes();

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

    tableModel = new DefaultTableModel(data, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        if (column == 4)
          return true;
        return false;
      }
    };

    movieTable = new JTable(tableModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        if (column == 4)
          return true;
        return false;
      }
    };
    movieTable.getTableHeader().setReorderingAllowed(false);
    movieTable.getTableHeader().setResizingAllowed(false);
    movieTable.getColumnModel().getColumn(4).setMinWidth(50);
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
    gbc.gridwidth = 2;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    ticketPage.add(spacer, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 0;
    userTickets.addActionListener(this);
    userTickets.setPreferredSize(new Dimension(150, 30));
    ticketPage.add(userTickets, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 1;
    showtimeLabel.setPreferredSize(new Dimension(600, 30));
    ticketPage.add(showtimeLabel, gbc);

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
    } else if (e.getSource() == userTickets) {
      new UserTicketPage(mainFrame, user);
    }
  }

  Action select = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      int modelRow = Integer.valueOf(e.getActionCommand());
      new SeatingPage(mainFrame, user, showtimes.get(modelRow));
    }
  };
}