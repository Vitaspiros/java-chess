import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();

        int squareX=Math.floorDiv(x,App.board.SQUARE_SIZE);
        int squareY=Math.floorDiv(y,App.board.SQUARE_SIZE);
        if (App.board.draggedPiece==0) {
            
            int piece=App.board.position[squareY][squareX];
            int pieceColor=(8 & piece) >> 3;
            if (pieceColor!=App.board.turn) return;
            App.board.draggedPiece=piece;

            App.board.position[squareY][squareX] = 0;
            App.board.updatePosition();
            
        } else {
            App.board.position[squareY][squareX]=App.board.draggedPiece;
            App.board.draggedPiece=0;

            App.board.turn=App.board.turn == 0 ? 1 : 0;
        }
        
    }
}
