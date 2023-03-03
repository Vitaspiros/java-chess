import javax.swing.JFrame;
import java.awt.Container;

public class App extends JFrame {
    Container cp;
    public static Board board;

    public static final int WIDTH=600;
    public static final int HEIGHT=600+37;
    public static void main(String[] args) throws Exception {
        App app=new App();
        app.setSize(WIDTH,HEIGHT);
        app.addWindowListener(new WindowKiller());
        app.setVisible(true);
        app.setResizable(false);
    }

    public App() {
        super("Chessboard");
        cp=getContentPane();
        board=new Board(WIDTH,HEIGHT);
        cp.add(board);
    }
}
