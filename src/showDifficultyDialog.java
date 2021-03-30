import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class showDifficultyDialog implements ActionListener {
    private final JFrame jFrame = new JFrame("Choose difficulty");
    private final JComboBox<String> difficulty = new JComboBox<>();
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
        jFrame.setVisible(true);
        jFrame.pack();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (difficulty.getSelectedIndex() == 0){
            Main.refreshRate = 500;
        } else if (difficulty.getSelectedIndex() == 1){
            Main.refreshRate = 200;
        } else {
            Main.refreshRate = 100;
        }
        Main.canContinue = true;
        jFrame.setVisible(false);
    }
}
