public interface HexGame{
    //int getSize();
    boolean playCpu(Game a);
    boolean playHuman(int rinput, int cinput, int tinput);
    //void playGame();
    void checkwinnerx(int rinput, int cinput, statust winput);
    void checkwinnero(int rinput, int cinput, statust winput);
    //int getScore();
    //void setScore(int input);
    //void incScore(int input);
    //int getScore1();
    //void setScore1(int input);
    //void incScore1(int input);
    //void inputmove(int *rowinput, int *columninput);
    //void commands();
    //void save();
    //void load();
}