import javax.swing.JFrame;

public class GUITest {
  public static void main(String args[]) 
  {
    JFrame mainFrame = new JFrame("Depressed SENG Student Theatres");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);

    new LoginPage(mainFrame);
  }
}
