import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SearchButton extends JButton implements ActionListener {
    public SearchButton(String name){
        super(name);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setPreferredSize(new Dimension(350, 45));
        addActionListener(this);
        setBackground(new Color(0,0,128));
        setForeground(new Color(240, 240, 245));
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}
