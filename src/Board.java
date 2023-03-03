import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

public class Board extends JPanel {
    public int SQUARE_SIZE;
    public int HEIGHT;
    public int WIDTH;

    public int[][] position=new int[8][8];

    public int draggedPiece=0;

    public int turn=0;

    public Map<Integer,String> assetMap;

    public Board(int width,int height) {
        super();

        assetMap=Map.ofEntries(
            Map.entry(1, "king_white"),
            Map.entry(2, "queen_white"),
            Map.entry(3, "bishop_white"),
            Map.entry(4, "knight_white"),
            Map.entry(5, "rook_white"),
            Map.entry(6, "pawn_white"),

            Map.entry(8 | 1, "king_black"),
            Map.entry(8 | 2, "queen_black"),
            Map.entry(8 | 3, "bishop_black"),
            Map.entry(8 | 4, "knight_black"),
            Map.entry(8 | 5, "rook_black"),
            Map.entry(8 | 6, "pawn_black")
        );

        HEIGHT=height;
        WIDTH=width;

        addMouseListener(new MouseHandler());

        position=FENDecoder.fenToArray("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        SQUARE_SIZE=(int)(((getSize().getWidth()+getSize().getHeight())/2)/8);
        setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        drawGrid(g);
        drawColors(g,new Color(0xc0c0c0),new Color(0x592c2c));

        drawPosition(g);
        drawText(g);
        if (draggedPiece!=0) calculateDrag(g);
    }

    public void drawPosition(Graphics g) {
        int squareX=0;
        int squareY=0;
        for (int y=0;y<8*SQUARE_SIZE;y+=SQUARE_SIZE) {
            for (int x=0;x<8*SQUARE_SIZE;x+=SQUARE_SIZE) {
                int pieceId=position[squareY][squareX];

                BufferedImage asset=null;
                try {
                    if (pieceId!=0) asset=ImageIO.read(new File("src/assets/"+assetMap.get(pieceId)+".png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pieceId!=0) g.drawImage(asset,x,y,SQUARE_SIZE,SQUARE_SIZE,null);

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

    public void updatePosition() {
        repaint();
    }

    public void calculateDrag(Graphics g) {
        Point screenLocation=MouseInfo.getPointerInfo().getLocation();
        Point windowLocation=new Point(screenLocation.x-this.getLocationOnScreen().x,screenLocation.y-this.getLocationOnScreen().y);

        BufferedImage image=null;
        try {
            image = ImageIO.read(new File("src/assets/"+assetMap.get(draggedPiece)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image,windowLocation.x-SQUARE_SIZE/2,windowLocation.y-SQUARE_SIZE/2,SQUARE_SIZE,SQUARE_SIZE,null);

        repaint();
    }

    public void drawText(Graphics g) {
        Color color=Color.BLACK;
        char c='1';
        for (int y=0;y<SQUARE_SIZE*8;y+=SQUARE_SIZE) {
            g.setColor(color);
            g.drawString(String.valueOf(c),0,y+SQUARE_SIZE);
            color =  color == Color.BLACK ? Color.WHITE : Color.BLACK;
            c++;
        }

        color=Color.WHITE;
        c='a';

        for (int x=0;x<SQUARE_SIZE*8;x+=SQUARE_SIZE) {
            g.setColor(color);
            g.drawString(String.valueOf(c),x+SQUARE_SIZE/2,getHeight());
            color =  color == Color.BLACK ? Color.WHITE : Color.BLACK;
            c++;
        }
    }
}
