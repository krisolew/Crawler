package Crawler.GUI;

import Crawler.GUILogic.MainFrameLogic;
import Crawler.GUILogic.RaceType;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrameLogic logic;
    public RightPanel rightPanel;
    public LeftPanel leftPanel;
    private SearchButton button;
    private SearchButton lButton;
    private SearchButton mButton;

    public MainFrame(){
        super("Alpine Skiing Informator");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000,600));
        setResizable(false);
        setLocation(450,200);
        ImageIcon icon = new ImageIcon("img/fis.png");
        setIconImage(icon.getImage());

        logic = new MainFrameLogic(this);

        leftPanel = new LeftPanel(this);
        add(leftPanel,BorderLayout.WEST);

        rightPanel = new RightPanel();
        add(rightPanel,BorderLayout.EAST);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        JPanel searchRow = new JPanel();
        searchRow.setLayout(new GridLayout());

        lButton = logic.createLadiesButton();
        searchRow.add(lButton);

        mButton = logic.createManButton();
        searchRow.add(mButton);

        searchPanel.add(searchRow,BorderLayout.PAGE_START);

        searchRow = new JPanel();
        searchRow.setLayout(new GridLayout());

        button = logic.createSearchButton(RaceType.SL);
        searchRow.add(button);

        button = logic.createSearchButton(RaceType.GS);
        searchRow.add(button);

        button = logic.createSearchButton(RaceType.SG);
        searchRow.add(button);

        button = logic.createSearchButton(RaceType.DH);
        searchRow.add(button);
        searchPanel.add(searchRow);

        add(searchPanel,BorderLayout.PAGE_END);

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void resetSettings(){

        leftPanel.resetSettings();
        rightPanel.setTextContent("");
    }

    public void setGenderButtonOnClickProperties(JButton button, boolean man)
    {
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(new Color(0));

        if(man)
        {
            lButton.setBackground(new Color(0,0,128));
            lButton.setForeground(new Color(255,255,255));
        }
        else
        {
            mButton.setBackground(new Color(0,0,128));
            mButton.setForeground(new Color(255,255,255));
        }
    }

    public void setSpecialButtonSettings(SearchButton button)
    {
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(new Color(0));
    }
}
