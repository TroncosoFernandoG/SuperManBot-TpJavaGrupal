import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            
            JFrame frame = new JFrame("SUPER MAN BOT"); 
            GamePanel gamePanel = new GamePanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            frame.setResizable(false);               
            frame.add(gamePanel);
            frame.pack();             
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            gamePanel.requestFocusInWindow();
        });
    }

}




