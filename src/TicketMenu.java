import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.util.*;
//import java.sql.*;

public class TicketMenu implements ActionListener {
  public static void main(String args[]) 
  {
    new TicketMenu();
  }

  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame = new JFrame("Depressed SENG Student Theatres"); //This class will return a panel for GUI to display not a new Jframe
  JPanel ticketPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JButton userTickets = new JButton("My Tickets");
  JLabel showtimeLabel = new JLabel("Showtimes");
  DefaultTableModel tableModel;
  JTable movieTable;
  JScrollPane movieScroll;

  JLabel spacer = new JLabel();

  TicketMenu()
  {
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainMenuSetup();

    mainFrame.add(ticketPage);

    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);
  }

  private void tableSet() {
    String[] columnNames = {"Movie", "Date", "Time", ""};
    Object[][] data =
    {
      {"Movie1", "Feb 14", "7:00 pm", "<>"},
      {"Movie2", "Feb 14", "7:00 pm", "<>"},
      {"Movie3", "Feb 14", "7:00 pm", "<>"},
      {"Movie4", "Feb 14", "7:00 pm", "<>"},
      {"Movie5", "Feb 14", "7:00 pm", "<>"},
      {"Movie6", "Feb 14", "7:00 pm", "<>"},
      {"Movie7", "Feb 14", "7:00 pm", "<>"},
      {"Movie8", "Feb 14", "7:00 pm", "<>"}, 
      {"Movie9", "Feb 14", "7:00 pm", "<>"},
      {"Movie10", "Feb 14", "7:00 pm", "<>"},
      {"Movie11", "Feb 14", "7:00 pm", "<>"},
      {"Movie12", "Feb 14", "7:00 pm", "<>"},
      {"Movie13", "Feb 14", "7:00 pm", "<>"},
      {"Movie14", "Feb 14", "7:00 pm", "<>"},
      {"Movie15", "Feb 14", "7:00 pm", "<>"},
      {"Movie16", "Feb 14", "7:00 pm", "<>"}, 
      {"Movie17", "Feb 14", "7:00 pm", "<>"},
      {"Movie18", "Feb 14", "7:00 pm", "<>"},
      {"Movie19", "Feb 14", "7:00 pm", "<>"},
      {"Movie20", "Feb 14", "7:00 pm", "<>"},
      {"Movie21", "Feb 14", "7:00 pm", "<>"},
      {"Movie22", "Feb 14", "7:00 pm", "<>"},
      {"Movie23", "Feb 14", "7:00 pm", "<>"},
      {"Movie24", "Feb 14", "7:00 pm", "<>"}, 
      {"Movie25", "Feb 14", "7:00 pm", "<>"},
      {"Movie26", "Feb 14", "7:00 pm", "<>"},
      {"Movie27", "Feb 14", "7:00 pm", "<>"},
      {"Movie28", "Feb 14", "7:00 pm", "<>"},
      {"Movie29", "Feb 14", "7:00 pm", "<>"},
      {"Movie30", "Feb 14", "7:00 pm", "<>"},
      {"Movie31", "Feb 14", "7:00 pm", "<>"},
      {"Movie32", "Feb 14", "7:00 pm", "<>"}
    };
 
    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(3).setMaxWidth(50);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
    movieScroll = new JScrollPane(movieTable);

    ButtonColumn buttonColumn = new ButtonColumn(movieTable, select, 3);
    buttonColumn.setMnemonic(KeyEvent.VK_D);
  }

  private void mainMenuSetup() {
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

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == main)
    {
      JOptionPane.showMessageDialog(mainFrame, "Return to main page");
    }
    else if (e.getSource() == userTickets)
    {
      JOptionPane.showMessageDialog(mainFrame, "Show user tickets");
    }
  }

  Action select = new AbstractAction()
  {
    public void actionPerformed(ActionEvent e)
    {
      int modelRow = Integer.valueOf( e.getActionCommand() );
      JOptionPane.showMessageDialog(mainFrame, "Selected :" + modelRow);
    }
  };
}