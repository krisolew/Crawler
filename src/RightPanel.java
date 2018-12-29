import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel{

    JTextArea text;
    JScrollPane scroll;

    public RightPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500,600));

        text = new JTextArea();
        text.setFont(new Font("Arial", 0, 20));
        text.setTabSize(4);
        text.setBackground(new Color(240, 240, 245));
        text.setEditable(false);

        scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
    }

    public void setTextContent(List <String> names, List <String> points){
        String name;
        String point;
        StringBuilder builder = new StringBuilder();
        int i = 1;

        builder.append("Nr\t" + "Pkt\t" + "Nazwisko i imie\n" );

        while (!names.isEmpty()) {
            name = names.remove(0);
            point = points.remove(0);
            if ( point.equals("---") ) break;
            builder.append(i + ".\t" + point.trim() + "\t" + name.trim() + "\n");
            i++;
        }

        name = builder.toString();
        text.setText(name);
    }

    public void setTextContent ( String content ){
        text.setText(content);
    }
}
