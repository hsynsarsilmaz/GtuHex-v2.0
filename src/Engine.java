import java.util.ArrayList;
import java.util.Random;

enum statust{ empty, computer, player1, player2,xw,ow}; 

public class Engine implements HexGame,Cloneable{
    
    private Cell Gameboard[][];
    private int size;     //Size of the gameboard
    private int gametype; //Type of the game 0 for singleplayer 1 for multiplayer
    private char winner; //Variable for holding winner condition'0' for no winner 'x' for x 'o' for o
    private int score;  //Score of player in the cpu mode or player 2 in the multiplayer mode
    private int score1; // Score of player 1 in multiplayer mode
    
    Engine(int input_size, int input_gametype){
        size = input_size;
        gametype = input_gametype;
        Gameboard = new Cell[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                Gameboard[i][j] = new Cell();
                Cell a = new Cell();
                a.setStatus(statust.empty);
                a.setRow(i);
                a.setColumn(j);
                Gameboard[i][j] = a;
            }
        }
    }

    public Object Clone(){
        Engine temp = new Engine(size,gametype);
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                temp.getGameboard()[i][j].setStatus(Gameboard[i][j].getStatus());
            }
        }
        return temp;
    }

    public class Cell{
        Cell(){ status = statust.empty; }
        private statust status;
        private int row;
        private int column;
        public void setStatus(statust input){ status = input; }
        public statust getStatus(){ return status; }
        public void setColumn(int input){ column = input; }
        public int getColumn(){ return column; }
        public void setRow(int input){ row = input; }
        public int getRow(){ return row; }
    }
    
    public void save(){
        
    }
    
    public void print(){ //A test function for clone method
        char letter = 'A';
        int space = 3;
        System.out.print("   ");
        for(int i=0;i<size;i++){
            System.out.print(i+1 + " ");
        }
        System.out.print("\n");
        for(int i=0;i<size;i++){
            System.out.print(letter);
            for(int j=0;j<space;j++) System.out.print(" ");
            for(int j=0;j<size;j++){
                if(Gameboard[i][j].getStatus() == statust.empty) System.out.print(".");
                else if(Gameboard[i][j].getStatus() == statust.computer) System.out.print("x");
                else if(Gameboard[i][j].getStatus() == statust.player1) System.out.print("x");
                else if(Gameboard[i][j].getStatus() == statust.player2) System.out.print("o");
                else if(Gameboard[i][j].getStatus() == statust.xw) System.out.print("X");
                else if(Gameboard[i][j].getStatus() == statust.ow) System.out.print("Z");
                System.out.print(" ");
             }
            System.out.print("\n");
            letter++;
            space++;
        }
        System.out.print("\n");
    }

    public Cell [][] getGameboard(){
        return Gameboard;
    }
    
    public boolean playHuman(int x, int y, int type){
        if(type == 1){
            Gameboard[x][y].setStatus(statust.player2);
            for(int i=0;i<size;i++){
                checkwinnero(0,i,statust.player2);
                if(winner != 'o'){
                    for(int j=0;j<size;j++){
                        for(int k=0;k<size;k++){
                            if(Gameboard[j][k].getStatus() == statust.ow) Gameboard[j][k].setStatus(statust.player2); //clears the ow(markings for connected lines)
                        }
                    }
                }
            }
            if(winner == 'o') return true;
        }
        if(type == 2){
            Gameboard[x][y].setStatus(statust.player1);
            for(int i=0;i<size;i++){
                checkwinnerx(i,0,statust.player1);
                if(winner != 'x'){
                    for(int j=0;j<size;j++){
                        for(int k=0;k<size;k++){
                            if(Gameboard[j][k].getStatus() == statust.xw) Gameboard[j][k].setStatus(statust.player1); //clears the xw(markings for connected lines)
                        }
                    }
                }
            }
            if(winner == 'x') return true;
        }
        return false;
    }
    
    public boolean playCpu(Game a){
        Random random = new Random();
        int x,y;
        while(true){
            x = random.nextInt(size);
            y = random.nextInt(size);
            if(Gameboard[x][y].getStatus() == statust.empty){
                Gameboard[x][y].setStatus(statust.computer);
                a.setCpux(x);
                a.setCpuy(y);
                break;
            }

        }
        for(int i=0;i<size;i++){
            checkwinnerx(i,0,statust.computer);
            if(winner != 'x'){
                for(int j=0;j<size;j++){
                    for(int k=0;k<size;k++){
                        if(Gameboard[j][k].getStatus() ==  statust.xw) Gameboard[j][k].setStatus(statust.computer);
                    }
                }
            }
        }
        if(winner == 'x') return true;
        else return false;
    }

    public void checkwinnerx(int rinput, int cinput, statust winput){
        //Recursively counts the connected cells and if a line connects whole gameboard horizontally (winning condition of x) it assings the winner to winner value
    //Also calculates the score
    if (Gameboard[rinput][cinput].getStatus()  != winput) return;
    if (cinput == size-1){
        Gameboard[rinput][cinput].setStatus(statust.xw);
        score++;
        winner = 'x';
        return;
    }
    
    //East
    if(cinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput][cinput+1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput,cinput+1,winput);
        }
    }

    //North East
    if(cinput+1 < size && rinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput-1][cinput+1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput-1,cinput+1,winput);
        }
    }

    //South East
    if(rinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput+1][cinput].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput+1,cinput,winput);
        }
    }

    //West
    if(cinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput][cinput-1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput,cinput-1,winput);
        }
    }

    //North west
    if(rinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput-1][cinput].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput-1,cinput,winput);
        }
    }

    //South west
    if(cinput-1 >= 0 && rinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.xw) return;
        if(Gameboard[rinput+1][cinput-1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.xw);
            score++;
            checkwinnerx(rinput+1,cinput-1,winput);
        }
    }
    return;
    }
    
    public void checkwinnero(int rinput, int cinput, statust winput){
    //Recursively counts the connected cells and if a line connects whole Gameboard vertically (winning condition of o) it assings the winner to winner value
    //Also calculates the score
    if (Gameboard[rinput][cinput].getStatus() != winput) return;
    if (rinput == size-1){
        Gameboard[rinput][cinput].setStatus(statust.ow);
        score++;
        winner = 'o';
        return;
    }
    
    //East
    if(cinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput][cinput+1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.ow);
            score++;
            checkwinnero(rinput,cinput+1,winput);
        }
    }

    //North East
    if(cinput+1 < size && rinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput-1][cinput+1].getStatus()  == winput){
            Gameboard[rinput][cinput].setStatus(statust.ow);
            score++;
            checkwinnero(rinput-1,cinput+1,winput);
        }
    }

    //South East
    if(rinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput+1][cinput].getStatus()  == winput){
            score++;
            Gameboard[rinput][cinput].setStatus(statust.ow);;
            checkwinnero(rinput+1,cinput,winput);
        }
    }

    //West
    if(cinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput][cinput-1].getStatus()  == winput){
            score++;
            Gameboard[rinput][cinput].setStatus(statust.ow);
            checkwinnero(rinput,cinput-1,winput);
        }
    }

    //North west
    if(rinput-1 >= 0){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput-1][cinput].getStatus()  == winput){
            score++;
            Gameboard[rinput][cinput].setStatus(statust.ow);
            checkwinnero(rinput-1,cinput,winput);
        }
    }

    //South west
    if(cinput-1 >= 0 && rinput+1 < size){
        if(Gameboard[rinput][cinput].getStatus()  == statust.ow) return;
        if(Gameboard[rinput+1][cinput-1].getStatus()  == winput){
            score++;
            Gameboard[rinput][cinput].setStatus(statust.ow);
            checkwinnero(rinput+1,cinput-1,winput);
        }
    }
    return;
    }
    
    
    
    
}

