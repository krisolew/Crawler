import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {
    JLabel text;

    public ErrorFrame(String message){
        super("Error");
        setPreferredSize(new Dimension(600,300));
        setLocation(650,350);
        setLayout(new BorderLayout());
        setResizable(false);

        text = new JLabel();
        text.setFont(new Font("Arial",Font.PLAIN,24));
        text.setText(message);
        text.setHorizontalAlignment(0);
        text.setVerticalAlignment(0);
        add(text);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
