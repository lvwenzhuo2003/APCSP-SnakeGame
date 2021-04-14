import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This will show difficulty select dialog if no arguments is passed to the program.
 * @author SteveLyu03 (lvwenzhuo2003@126.com)
 */
public class showDifficultyDialog implements ActionListener {
    private final JFrame jFrame = new JFrame("Choose difficulty");
    private final JComboBox<String> difficulty = new JComboBox<>();
    private Logger logger = Logger.getLogger("showDifficultyDialog");

    /**
     * the dialog allows user to choose difficulty. The controller is Main.refreshRate
     * Also, this dialog defaults shows in the middle of the screen
     */
    public showDifficultyDialog(){
        JLabel title = new JLabel("Choose difficulty: ");
        difficulty.addItem("Easy");
        difficulty.addItem("Hard");
        difficulty.addItem("Expert");
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(title,BorderLayout.NORTH);
        jFrame.add(difficulty,BorderLayout.CENTER);
        jFrame.add(confirmButton,BorderLayout.SOUTH);
        int windowWidth = jFrame.getWidth();
        int windowHeight = jFrame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setVisible(true);
        jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        logger.log(Level.INFO, "showed showing difficulty dialog");
    }

    /**
     * Invoked when button is clicked (triggered).
     * refreshRate = 500 is easy (index = 0)
     * refreshRate = 200 is hard (index = 1)
     * refreshRate = 100 is expert (index = 2)
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (difficulty.getSelectedIndex() == 0){
            Main.refreshRate = 500;
            logger.log(Level.CONFIG, "User selected easy");
        } else if (difficulty.getSelectedIndex() == 1){
            Main.refreshRate = 200;
            logger.log(Level.CONFIG, "User selected hard");
        } else {
            Main.refreshRate = 100;
            logger.log(Level.CONFIG, "User selected expert");
        }
        Main.canContinue = true;
        logger.log(Level.INFO,"Hide difficulty dialog");
        jFrame.setVisible(false);
    }
}
