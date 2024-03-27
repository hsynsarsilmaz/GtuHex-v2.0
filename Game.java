import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;


public class Game extends JFrame implements ActionListener,MouseInputListener{
    private int size;
    private int gametype;
    private int cpu_x = 0;
    private int cpu_y = 0;
    private int turn = 0;
    private int buttonsize;
    private Formatter file;
    private Scanner fileread;
    private FileWriter filewrite;
    ImageIcon gtulogo;
    JPanel gamepanel;
    JButton gameboard[][];
    JLabel gamelogo;
    JLabel textcommands;
    JLabel textwinner1;
    JLabel textwinner2;
    JLabel textnotempty;
    JButton buttonsave;
    JButton buttonload;
    JButton buttonreset;
    JButton buttonundo;
    JButton buttonplayagain;
    String backupmoves; // for save-load to co work with undo
    Engine engine;
    
    Game(int s,int t){
        size = s;
        gametype = t;
        engine = new Engine(size,gametype);
        gtulogo = new ImageIcon("Picture/gtulogo.png");
        fpanel();
        flogo();
        fcreatemoves();
        fframe();
        fcell();
        fcommands();
        finvisible();
        if(gametype == 2) turn = 1;
        this.setVisible(true);
    }

    public int buttonsizer(){
        for(int i=26;i>4;i--){
            if(size == i) return 15 + (36-i);
        }
        return 0;
    }

    public void setCpux(int a){
        cpu_x = a;
    }

    public void setCpuy(int a){
        cpu_y = a;
    }

    public void fpanel(){
        gamepanel = new JPanel();
        gamepanel.setBounds(370,0,996,768);
        gamepanel.setBackground(Color.black);
        gamepanel.setBorder(BorderFactory.createEmptyBorder());
        gamepanel.setLayout(null);
        this.add(gamepanel);
    }

    public void flogo(){
        gamelogo = new JLabel();
        gameboard = new JButton[size][size];
        ImageIcon gamelogoi = new ImageIcon("Picture/gamelogo.png");
        gamelogo.setIcon(gamelogoi);
        gamelogo.setBounds(10,25,350,60);
        this.add(gamelogo);
    }

    public void fcreatemoves(){
        try{
            file = new Formatter("moves.txt");
        }
        catch(FileNotFoundException fnfe){
            System.err.println("Moves file cannot be created");
        }
        file.close();
    }

    public void fcell(){
        buttonsize = buttonsizer();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                gameboard[i][j]= new JButton();
                gameboard[i][j].setFocusable(false);
                gameboard[i][j].setBackground(Color.white);
                gameboard[i][j].setBounds(15 + j *(buttonsize) + i*(buttonsize/2),15 + i*(buttonsize),buttonsize,buttonsize);
                gameboard[i][j].addActionListener(this);
                gameboard[i][j].addMouseListener(this);
                gameboard[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
                gamepanel.add(gameboard[i][j]);
            }
        }   
    }

    public void finvisible(){
        textwinner1 = new JLabel();
        textwinner1.setBounds(40,500,400,50);
        textwinner1.setForeground(Color.white);
        textwinner1.setFont(new Font("Arial",Font.BOLD,18));
        textwinner1.setVisible(false);
        textwinner2 = new JLabel();
        textwinner2.setBounds(40,565,400,50);
        textwinner2.setForeground(Color.white);
        textwinner2.setFont(new Font("Arial",Font.BOLD,20));
        textwinner2.setVisible(false);
        textnotempty = new JLabel("This cell is not empty");
        textnotempty.setBounds(40,100,250,50);
        textnotempty.setForeground(Color.white);
        textnotempty.setVisible(false);
        textnotempty.setFont(new Font("Arial",Font.BOLD,20));
        buttonplayagain = new JButton();
        buttonplayagain.addActionListener(this);
        buttonplayagain.setText("Play again");
        buttonplayagain.setFont(new Font("Arial",Font.BOLD,25));
        buttonplayagain.setVerticalTextPosition(JButton.CENTER);
        buttonplayagain.setHorizontalTextPosition(JButton.CENTER);
        buttonplayagain.setFocusable(false);
        buttonplayagain.setBounds(50,650,250,40);
        buttonplayagain.setBackground(Color.white);
        buttonplayagain.setBorder(BorderFactory.createEmptyBorder());
        buttonplayagain.setVisible(false);
        this.add(buttonplayagain);
        this.add(textwinner1);
        this.add(textwinner2);
        this.add(textnotempty);
    }

    public void fframe(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("GTUHEX v2.0");
        this.setIconImage(gtulogo.getImage());
        this.setSize(1366,768);
        this.getContentPane().setBackground(new Color(0,0,0));
        this.setLayout(null);
        this.setResizable(false);
    }

    public void fcommands(){
        textcommands = new JLabel();
        textcommands.setText("Commands:");
        textcommands.setBounds(40,150,350,50);
        textcommands.setForeground(Color.white);
        textcommands.setFont(new Font("Arial",Font.BOLD,40));
        buttonsave = new JButton();
        buttonsave.addActionListener(this);
        buttonsave.setText("Save");
        buttonsave.setFont(new Font("Arial",Font.BOLD,25));
        buttonsave.setVerticalTextPosition(JButton.CENTER);
        buttonsave.setHorizontalTextPosition(JButton.CENTER);
        buttonsave.setFocusable(false);
        buttonsave.setBounds(100,225,125,40);
        buttonsave.setBackground(Color.white);
        buttonsave.setBorder(BorderFactory.createEmptyBorder());
        buttonload = new JButton();
        buttonload.addActionListener(this);
        buttonload.setText("Load");
        buttonload.setFont(new Font("Arial",Font.BOLD,25));
        buttonload.setVerticalTextPosition(JButton.CENTER);
        buttonload.setHorizontalTextPosition(JButton.CENTER);
        buttonload.setFocusable(false);
        buttonload.setBounds(100,285,125,40);
        buttonload.setBackground(Color.white);
        buttonload.setBorder(BorderFactory.createEmptyBorder());
        buttonload.setEnabled(false);
        buttonreset = new JButton();
        buttonreset.addActionListener(this);
        buttonreset.setText("Reset");
        buttonreset.setFont(new Font("Arial",Font.BOLD,25));
        buttonreset.setVerticalTextPosition(JButton.CENTER);
        buttonreset.setHorizontalTextPosition(JButton.CENTER);
        buttonreset.setFocusable(false);
        buttonreset.setBounds(100,345,125,40);
        buttonreset.setBackground(Color.white);
        buttonreset.setBorder(BorderFactory.createEmptyBorder());
        buttonundo = new JButton();
        buttonundo.addActionListener(this);
        buttonundo.setText("Undo");
        buttonundo.setFont(new Font("Arial",Font.BOLD,25));
        buttonundo.setVerticalTextPosition(JButton.CENTER);
        buttonundo.setHorizontalTextPosition(JButton.CENTER);
        buttonundo.setFocusable(false);
        buttonundo.setBounds(100,405,125,40);
        buttonundo.setBackground(Color.white);
        buttonundo.setBorder(BorderFactory.createEmptyBorder());
        this.add(textcommands);
        this.add(buttonsave);
        this.add(buttonload);
        this.add(buttonreset);
        this.add(buttonundo);
    }
    
    public void gamefinisher(int wins){
        buttonsave.setEnabled(false);
        buttonload.setEnabled(false);
        buttonreset.setEnabled(false);
        buttonundo.setEnabled(false);
        if(wins == 1) textwinner1.setText("A.i supremacy over human brain");
        if(wins == 2) textwinner1.setText("You have won against A.I");
        if(wins == 3) textwinner1.setText("Congratulations Red Player");
        if(wins == 4) textwinner1.setText("Congratulations Blue Player");
        if(wins == 1) textwinner2.setText("THE EARTH IS OURS NOW !!");
        if(wins == 2) textwinner2.setText("We're safe (for now...)");
        if(wins == 3) textwinner2.setText("You have WON !");
        if(wins == 4) textwinner2.setText("You have WON !");
        buttonplayagain.setVisible(true);
        textwinner1.setVisible(true);
        textwinner2.setVisible(true);
    }
    
    public void mouseEntered(MouseEvent e){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(e.getSource() == gameboard[i][j]){
                    if(engine.getGameboard()[i][j].getStatus() == statust.empty)
                        if(gametype == 2 && turn == 2) gameboard[i][j].setBackground(new Color(75,75,225));
                        else gameboard[i][j].setBackground(new Color(225,75,75));

                    else{
                        textnotempty.setVisible(true);
                    }
                }
            }
        }
    }
    
    public void mouseExited(MouseEvent e){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(e.getSource() == gameboard[i][j]){
                    if(engine.getGameboard()[i][j].getStatus() == statust.empty)
                        gameboard[i][j].setBackground(Color.white);
                    else{
                        textnotempty.setVisible(false);
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e){
        //Deliberately empty
    }

    public void mousePressed(MouseEvent e){
        //Deliberately empty
    }

    public void mouseClicked(MouseEvent e){
        //Deliberately empty
    }

    public void mouseMoved(MouseEvent e){
        //Deliberately empty
    }

    public void mouseDragged(MouseEvent e){
        //Deliberately empty
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonsave){
            String temp = new String();
            try {
                fileread = new Scanner(new File("moves.txt"));
            }
            catch(Exception excpt){
                System.err.println("Could not found movesfile");
            }
            while(fileread.hasNext()){
                temp += fileread.next();
                temp += " ";
            }
            fileread.close();
            backupmoves = temp;
            try{
                file = new Formatter("save.txt");
            }
            catch(FileNotFoundException fnfe){
                System.err.println("Save file cannot be created");
            }
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    if(engine.getGameboard()[i][j].getStatus() == statust.empty) file.format("%s", "e "); // e: empty
                    else if(engine.getGameboard()[i][j].getStatus() == statust.computer) file.format("%s", "c ");
                    else if(engine.getGameboard()[i][j].getStatus() == statust.player1) file.format("%s", "f ");
                    else if(engine.getGameboard()[i][j].getStatus() == statust.player2) file.format("%s", "p ");
                }
            }
            file.close();
            buttonload.setEnabled(true);
        }
        
        else if(e.getSource() == buttonload){
            try{
            fileread = new Scanner(new File("save.txt"));
            }
            catch(Exception excpt){
                System.err.println("Could not found save file");
            }
            String temp;
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    temp = fileread.next();
                    if(temp.equals("e")) engine.getGameboard()[i][j].setStatus(statust.empty);
                    else if(temp.equals("c")) engine.getGameboard()[i][j].setStatus(statust.computer);
                    else if(temp.equals("f")) engine.getGameboard()[i][j].setStatus(statust.player1);
                    else if(temp.equals("p")) engine.getGameboard()[i][j].setStatus(statust.player2);
                }
            }
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    if(engine.getGameboard()[i][j].getStatus() == statust.empty){
                        gameboard[i][j].setBackground(Color.white);
                        gameboard[i][j].setEnabled(true);
                    } 
                    else if(engine.getGameboard()[i][j].getStatus() == statust.computer) gameboard[i][j].setBackground(Color.blue);
                    else if(engine.getGameboard()[i][j].getStatus() == statust.player1) gameboard[i][j].setBackground(Color.blue);
                    else if(engine.getGameboard()[i][j].getStatus() == statust.player2) gameboard[i][j].setBackground(Color.red);
                }
            }
            fileread.close();
            try{
                filewrite = new FileWriter("moves.txt");
                filewrite.write(backupmoves);
                filewrite.close();
            }
            catch(IOException ioex){
                System.err.println("error while writing move");
            }
        }

        else if(e.getSource() == buttonreset){
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    engine.getGameboard()[i][j].setStatus(statust.empty);
                    gameboard[i][j].setBackground(Color.white);
                    gameboard[i][j].setEnabled(true);
                }
            }
            if(gametype == 2) turn = 1;
        }

        else if(e.getSource() == buttonundo){
            String movecheck = new String();
            String storemoves = new String();
            int x=0,y=0,amount;
            if(gametype == 1) amount = 4;
            else amount = 2;
            try{
                fileread = new Scanner(new File("moves.txt"));
                }
            catch(Exception excpt){
                    System.err.println("Could not found save file");
            }
            if(fileread.hasNext()){
                for(int i=0;i<amount;i++){
                    if(fileread.hasNext()){
                        if(i== 0 || i == 2){
                            movecheck = fileread.next();
                            x = Integer.parseInt(movecheck);
                        }
                        else{
                            movecheck = fileread.next();
                            y = Integer.parseInt(movecheck);
                            gameboard[x][y].setBackground(Color.white);
                            gameboard[x][y].setEnabled(true);
                            engine.getGameboard()[x][y].setStatus(statust.empty);
                        } 
                    }
                }
                while(fileread.hasNext()){
                    movecheck = fileread.next();
                    storemoves += movecheck;
                    storemoves += " ";  
                }
                fileread.close();
                try{
                    filewrite = new FileWriter("moves.txt");
                    filewrite.write(storemoves);
                    filewrite.close();
                }
                catch(IOException ioex){
                    System.err.println("error while writing move");
                }
            }
            if(gametype == 2 && turn == 1) turn++;
            else if(gametype == 2 && turn == 2) turn--;
            
        }
        
        else if(e.getSource() == buttonplayagain){
            this.dispose();
            new StartScreen();
        }
        
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(e.getSource() == gameboard[i][j]){
                    String append = new String();
                    String move = new String();
                    if(gametype == 1){
                        gameboard[i][j].setBackground(Color.red);
                        gameboard[i][j].setEnabled(false);
                        try{
                            fileread = new Scanner(new File("moves.txt"));
                            }
                            catch(Exception excpt){
                                System.err.println("Could not found save file");
                        }
                        while(fileread.hasNext()){
                            append += fileread.next();
                            append += " ";
                        }
                        move += Integer.toString(i);
                        move += " ";
                        move += Integer.toString(j);
                        move += " ";
                        fileread.close();
                        try{
                            filewrite = new FileWriter("moves.txt");
                            filewrite.write(move);
                            filewrite.close();
                            }
                        catch(IOException ioex){
                            System.err.println("error while writing move");
                        }
                        try{
                        filewrite = new FileWriter("moves.txt",true);
                        filewrite.write(append);
                        filewrite.close();
                        }
                        catch(IOException ioex){
                            System.err.println("error while appending");
                        }
                        if(engine.playHuman(i, j, 1)){
                            for(int k=0;k<size;k++){
                                for(int q=0;q<size;q++){
                                    gameboard[k][q].setEnabled(false);
                                    if(engine.getGameboard()[k][q].getStatus() == statust.ow) gameboard[k][q].setBackground(new Color(200,0,0));
                                }
                            }
                            gamefinisher(2);
                            break;
                        }
                        if(engine.playCpu(this)){
                            for(int k=0;k<size;k++){
                                for(int q=0;q<size;q++){
                                    gameboard[k][q].setEnabled(false);
                                    if(engine.getGameboard()[k][q].getStatus() == statust.xw) gameboard[k][q].setBackground(new Color(0,0,200));
                                }
                            }
                            gamefinisher(1);
                            break;
                        }
                        gameboard[cpu_x][cpu_y].setBackground(Color.blue);
                        gameboard[cpu_x][cpu_y].setEnabled(false);
                        append = new String();
                        move = new String();
                        try{
                            fileread = new Scanner(new File("moves.txt"));
                            }
                            catch(Exception excpt){
                                System.err.println("Could not found save file");
                        }
                        while(fileread.hasNext()){
                            append += fileread.next();
                            append += " ";
                        }
                        move += Integer.toString(cpu_x);
                        move += " ";
                        move += Integer.toString(cpu_y);
                        move += " ";
                        fileread.close();
                        try{
                            filewrite = new FileWriter("moves.txt");
                            filewrite.write(move);
                            filewrite.close();
                            }
                        catch(IOException ioex){
                            System.err.println("error while writing move");
                        }
                        try{
                        filewrite = new FileWriter("moves.txt",true);
                        filewrite.write(append);
                        filewrite.close();
                        }
                        catch(IOException ioex){
                            System.err.println("error while appending");
                        }
                        break;
                    }
                    if(gametype == 2 && turn == 1){
                        gameboard[i][j].setBackground(Color.red);
                        gameboard[i][j].setEnabled(false);
                        append = new String();
                        move = new String();
                        try{
                            fileread = new Scanner(new File("moves.txt"));
                            }
                            catch(Exception excpt){
                                System.err.println("Could not found save file");
                        }
                        while(fileread.hasNext()){
                            append += fileread.next();
                            append += " ";
                        }
                        move += Integer.toString(i);
                        move += " ";
                        move += Integer.toString(j);
                        move += " ";
                        fileread.close();
                        try{
                            filewrite = new FileWriter("moves.txt");
                            filewrite.write(move);
                            filewrite.close();
                            }
                        catch(IOException ioex){
                            System.err.println("error while writing move");
                        }
                        try{
                        filewrite = new FileWriter("moves.txt",true);
                        filewrite.write(append);
                        filewrite.close();
                        }
                        catch(IOException ioex){
                            System.err.println("error while appending");
                        }
                        if(engine.playHuman(i, j, 1)){
                            for(int k=0;k<size;k++){
                                for(int q=0;q<size;q++){
                                    gameboard[k][q].setEnabled(false);
                                    if(engine.getGameboard()[k][q].getStatus() == statust.ow) gameboard[k][q].setBackground(new Color(200,0,0));
                                }
                            }
                            gamefinisher(3);
                            break;
                        }
                        turn = 2;
                        break;
                    }
                    if(gametype == 2 && turn == 2){
                        gameboard[i][j].setBackground(Color.blue);
                        gameboard[i][j].setEnabled(false);
                        append = new String();
                        move = new String();
                        try{
                            fileread = new Scanner(new File("moves.txt"));
                            }
                            catch(Exception excpt){
                                System.err.println("Could not found save file");
                        }
                        while(fileread.hasNext()){
                            append += fileread.next();
                            append += " ";
                        }
                        move += Integer.toString(i);
                        move += " ";
                        move += Integer.toString(j);
                        move += " ";
                        fileread.close();
                        try{
                            filewrite = new FileWriter("moves.txt");
                            filewrite.write(move);
                            filewrite.close();
                            }
                        catch(IOException ioex){
                            System.err.println("error while writing move");
                        }
                        try{
                        filewrite = new FileWriter("moves.txt",true);
                        filewrite.write(append);
                        filewrite.close();
                        }
                        catch(IOException ioex){
                            System.err.println("error while appending");
                        }
                        if(engine.playHuman(i, j, 2)){
                            for(int k=0;k<size;k++){
                                for(int q=0;q<size;q++){
                                    gameboard[k][q].setEnabled(false);
                                    if(engine.getGameboard()[k][q].getStatus() == statust.xw) gameboard[k][q].setBackground(new Color(0,0,200));
                                }
                            }
                            gamefinisher(4);
                            break;
                        }
                        turn = 1;
                        break;
                    }
                }
            }
        }
    }
}