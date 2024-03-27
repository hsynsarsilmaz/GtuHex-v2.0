import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.NumberFormatException;


public class StartScreen extends JFrame implements ActionListener{
    private int size = 0;
    private int gametype = 0;
    JButton sizesubmit;
    JButton beginbutton;
    ImageIcon gtulogo;
    JLabel gamelogo;
    JLabel textsize;
    JLabel texttype;
    JLabel textsizeinfo;
    JLabel texttypeinfo;
    JLabel wrongsize;
    JTextField sizeinput;
    JRadioButton singleplayer;
    JRadioButton multiplayer;
    ButtonGroup typebuttons;
    
    StartScreen(){
        typebuttons = new ButtonGroup();
        gtulogo = new ImageIcon("Picture/gtulogo.png");
        flogo();
        fframe();
        fsize();
        ftype();
        fstarter();
        this.setVisible(true);
    }
    
    public void fsize(){
        textsize = new JLabel();
        sizesubmit = new JButton();
        sizeinput = new JTextField();
        textsize.setText("Size");
        textsize.setForeground(Color.white);
        textsize.setFont(new Font("Arial",Font.BOLD,45));
        textsize.setBounds(65,150,200,50);
        textsize.setVerticalTextPosition(JButton.CENTER);
        textsize.setHorizontalTextPosition(JButton.CENTER);
        sizesubmit.addActionListener(this);
        sizesubmit.setText("Submit");
        sizesubmit.setFont(new Font("Arial",Font.BOLD,18));
        sizesubmit.setVerticalTextPosition(JButton.CENTER);
        sizesubmit.setHorizontalTextPosition(JButton.CENTER);
        sizesubmit.setBackground(Color.white);
        sizesubmit.setFocusable(false);
        sizesubmit.setBounds(50,245,125,20);
        sizesubmit.setBorder(BorderFactory.createEmptyBorder());
        sizeinput.setBounds(50,210,125,25);
        sizeinput.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        this.add(sizeinput);
        this.add(textsize);
        this.add(sizesubmit);
    }
        
    public void fframe(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("GTUHEX v2.0");
        this.setIconImage(gtulogo.getImage());
        this.setSize(450,450);
        this.getContentPane().setBackground(new Color(0,0,0));
        this.setLayout(null);
        this.setResizable(false);
    }
    
    public void flogo(){
        gamelogo = new JLabel();
        ImageIcon gamelogoi = new ImageIcon("Picture/gamelogo.png");
        gamelogo.setIcon(gamelogoi);
        gamelogo.setBounds(50,25,350,75);
        this.add(gamelogo);
    }
    
    public void ftype(){
        texttype = new JLabel();
        singleplayer = new JRadioButton("Singleplayer");
        multiplayer = new JRadioButton("Multiplayer");
        texttype.setText("Type");
        texttype.setForeground(Color.white);
        texttype.setFont(new Font("Arial",Font.BOLD,45));
        texttype.setBounds(275,150,200,50);
        texttype.setVerticalTextPosition(JButton.CENTER);
        texttype.setHorizontalTextPosition(JButton.CENTER);
        singleplayer.setBounds(260,210,150,20);
        singleplayer.setBackground(Color.black);
        singleplayer.setForeground(Color.white);
        singleplayer.addActionListener(this);
        singleplayer.setFocusable(false);
        singleplayer.setFont(new Font("Arial",Font.BOLD,17));
        multiplayer.setBounds(260,240,150,20);
        multiplayer.setBackground(Color.black);
        multiplayer.setForeground(Color.white);
        multiplayer.setFont(new Font("Arial",Font.BOLD,17));
        multiplayer.addActionListener(this);
        multiplayer.setFocusable(false);
        typebuttons.add(singleplayer);
        typebuttons.add(multiplayer);
        this.add(singleplayer);
        this.add(multiplayer);
        this.add(texttype);
    }

    public void fstarter(){
        textsizeinfo = new JLabel();
        texttypeinfo = new JLabel();
        beginbutton = new JButton();
        wrongsize = new JLabel();
        textsizeinfo.setText("Size: No input");
        textsizeinfo.setForeground(Color.white);
        textsizeinfo.setFont(new Font("Arial",Font.BOLD,20));
        textsizeinfo.setBounds(165,275,150,50);
        textsizeinfo.setVerticalTextPosition(JButton.CENTER);
        textsizeinfo.setHorizontalTextPosition(JButton.CENTER);
        texttypeinfo.setText("Gametype: Not Selected");
        texttypeinfo.setForeground(Color.white);
        texttypeinfo.setFont(new Font("Arial",Font.BOLD,20));
        texttypeinfo.setBounds(110,300,250,50);
        texttypeinfo.setVerticalTextPosition(JButton.CENTER);
        texttypeinfo.setHorizontalTextPosition(JButton.CENTER);
        beginbutton.addActionListener(this);
        beginbutton.setText("BEGIN");
        beginbutton.setFont(new Font("Arial",Font.BOLD,25));
        beginbutton.setForeground(Color.black);
        beginbutton.setVerticalTextPosition(JButton.CENTER);
        beginbutton.setHorizontalTextPosition(JButton.CENTER);
        beginbutton.setBackground(new Color(25,25,25));
        beginbutton.setFocusable(false);
        beginbutton.setBounds(125,350,200,50);
        beginbutton.setBorder(BorderFactory.createEmptyBorder());
        beginbutton.setEnabled(false);
        wrongsize.setVisible(false);
        wrongsize.setForeground(Color.white);
        wrongsize.setFont(new Font("Arial",Font.BOLD,18));
        wrongsize.setBounds(35,100,390,50);
        wrongsize.setVerticalTextPosition(JButton.CENTER);
        wrongsize.setHorizontalTextPosition(JButton.CENTER);
        this.add(beginbutton);
        this.add(texttypeinfo);
        this.add(textsizeinfo);
        this.add(wrongsize);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == sizesubmit){
            try{
            size = Integer.parseInt(sizeinput.getText());
            }
            catch(java.lang.NumberFormatException nfexcp){
                wrongsize.setText("Please input an integer");
                wrongsize.setVisible(true);
                return;
            }
            if(size<5 || size > 26){
                wrongsize.setText("Size must be in range 5-26 inclusive");
                wrongsize.setVisible(true);
                beginbutton.setBackground(new Color(25,25,25));
                beginbutton.setEnabled(false);
            }
            else{
                wrongsize.setText("Size must be in range 5-26 inclusive");
                wrongsize.setVisible(false);
                textsizeinfo.setText("Size: " + size);
                if(gametype != 0){
                    beginbutton.setBackground(new Color(210,0,0));
                    beginbutton.setEnabled(true);
                }
                else{
                    beginbutton.setBackground(new Color(25,25,25));
                    beginbutton.setEnabled(false);
                }
            }
        }
        else if(e.getSource() == singleplayer){
            gametype = 1;
            texttypeinfo.setText("Gametype: Singleplayer");
            if(gametype != 0 && size > 4 && size < 27){
                beginbutton.setBackground(new Color(210,0,0));
                beginbutton.setEnabled(true);
            }
            else{
                beginbutton.setBackground(new Color(25,25,25));
                beginbutton.setEnabled(false);
            }
        }
        else if(e.getSource() == multiplayer){
            gametype = 2;
            texttypeinfo.setText("Gametype: Multiplayer");
            if(gametype != 0  && size > 4 && size < 27){
                beginbutton.setBackground(new Color(210,0,0));
                beginbutton.setEnabled(true);
            }
            else{
                beginbutton.setBackground(new Color(25,25,25));
                beginbutton.setEnabled(false);
            }
        }
        else if(e.getSource() == beginbutton){
            this.dispose();
            JOptionPane.showMessageDialog(null,"Red will connect up to down\nBlue will connect left to right","Information",JOptionPane.INFORMATION_MESSAGE);
            Game game = new Game(size,gametype);
        }
    }
}