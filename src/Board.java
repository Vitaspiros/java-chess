import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;

public class Board extends JPanel {
    public int SQUARE_SIZE;
    public int HEIGHT;
    public int WIDTH;

    public final int TILE_SIZE=35;

    public int[][] position={
        {1,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,8 | 1}
    };

    public Board(int width,int height) {
        super();
        HEIGHT=height;
        WIDTH=width;

        

        SQUARE_SIZE=WIDTH/8;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        drawGrid(g);
        drawColors(g,new Color(0xc0c0c0),new Color(0x592c2c));
        position=FENDecoder.fenToArray("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        drawPosition(g);
    }

    public void drawPosition(Graphics g) {
        int squareX=0;
        int squareY=0;
        for (int y=0;y<8*SQUARE_SIZE;y+=SQUARE_SIZE) {
            for (int x=0;x<8*SQUARE_SIZE;x+=SQUARE_SIZE) {
                int pieceId=position[squareY][squareX];

                BufferedImage asset=null;
                try {
                    switch (pieceId) {
                        case 1:
                            asset=ImageIO.read(new File("src/assets/king_white.png"));
                            break;
                        case 2:
                            asset=ImageIO.read(new File("src/assets/queen_white.png"));
                            break;
                        case 3:
                            asset=ImageIO.read(new File("src/assets/bishop_white.png"));
                            break;
                        case 4:
                            asset=ImageIO.read(new File("src/assets/knight_white.png"));
                            break;
                        case 5:
                            asset=ImageIO.read(new File("src/assets/rook_white.png"));
                            break;
                        case 6:
                            asset=ImageIO.read(new File("src/assets/pawn_white.png"));
                            break;

                        case 8 | 1:
                            asset=ImageIO.read(new File("src/assets/king_black.png"));
                            break;
                        case 8 | 2:
                            asset=ImageIO.read(new File("src/assets/queen_black.png"));
                            break;
                        case 8 | 3:
                            asset=ImageIO.read(new File("src/assets/bishop_black.png"));
                            break;
                        case 8 | 4:
                            asset=ImageIO.read(new File("src/assets/knight_black.png"));
                            break;
                        case 8 | 5:
                            asset=ImageIO.read(new File("src/assets/rook_black.png"));
                            break;
                        case 8 | 6:
                            asset=ImageIO.read(new File("src/assets/pawn_black.png"));
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                g.drawImage(asset,x,y,SQUARE_SIZE,SQUARE_SIZE,null);

                squareX++;
            }
            squareX=0;
            squareY++;
        }
    }

    public void drawGrid(Graphics g) {
        for (int x=0;x<8*SQUARE_SIZE;x+=SQUARE_SIZE) {
            g.drawLine(x,0,x,WIDTH);
        }

        for (int y=0;y<8*SQUARE_SIZE;y+=SQUARE_SIZE) {
            g.drawLine(0,y,HEIGHT,y);
        }
    }

    public void drawColors(Graphics g,Color whiteColor,Color blackColor) {
        boolean isWhite=true;
        for (int y=0;y<8*SQUARE_SIZE;y+=SQUARE_SIZE) {
            for (int x=0;x<8*SQUARE_SIZE;x+=SQUARE_SIZE) {
                if (isWhite) g.setColor(whiteColor);
                else g.setColor(blackColor);
                g.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);

                isWhite=!isWhite;
            }
            isWhite=!isWhite;
        }

        
        
    }
}
