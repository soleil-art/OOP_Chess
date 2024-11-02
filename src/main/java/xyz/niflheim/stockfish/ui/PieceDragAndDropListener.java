package xyz.niflheim.stockfish.ui;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import xyz.niflheim.stockfish.ui.board.BoardPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PieceDragAndDropListener implements MouseListener, MouseMotionListener {

    private final BoardPanel boardPanel;
    private List<Move> possibleMoves;

    private Square fromPiece;
    private char originFile;
    private int originRank;

    public PieceDragAndDropListener(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        this.possibleMoves = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        originFile = calculateFile(e);
        originRank = calculateRank(e);
        Square clickedSquare;

        Board board = boardPanel.getBoard();
        String notation = originFile + "" + originRank;
        clickedSquare = Square.valueOf(notation);
        SquarePanel clickedSquarePanel = (SquarePanel) boardPanel.getSquarePanel(clickedSquare.getFile().getNotation().charAt(0), Integer.parseInt(clickedSquare.getRank().getNotation()));


        if (clickedSquarePanel.isHighlight()) {
            removeHighlightSquare();
            // 말의 이동 처리
            boolean isLegalMove = board.isMoveLegal(new Move(fromPiece, clickedSquare), true);
            boolean isPromotionWhite = board.isMoveLegal(new Move(fromPiece,clickedSquare,Piece.BLACK_QUEEN),true);
            boolean isPromotionBlack = board.isMoveLegal(new Move(fromPiece,clickedSquare,Piece.WHITE_QUEEN),true);
            if(isLegalMove || isPromotionBlack || isPromotionWhite) {
                if(isPromotionBlack) {
                    Move move = new Move(fromPiece, clickedSquare, Piece.BLACK_QUEEN);
                    //board.doMove(move,true);
                    boardPanel.processUserMove(move);
                }else if(isPromotionWhite) {
                    Move move = new Move(fromPiece, clickedSquare, Piece.WHITE_QUEEN);
                    //board.doMove(move,true);
                    boardPanel.processUserMove(move);
                }else {
                    //board.doMove(new Move(fromPiece,clickedSquare),true);
                    boardPanel.processUserMove(new Move(fromPiece,clickedSquare));
                }

                fromPiece = clickedSquare;

            }else {

            }
        }
        Piece piece = board.getPiece(clickedSquare);

        if(board.getPiece(clickedSquare)!=Piece.NONE && isTurn(piece)) {
            removeHighlightSquare();

            List<Move> legalMoves = MoveGenerator.generateLegalMoves(board);
            possibleMoves = legalMoves.stream().filter(move -> move.getFrom()==clickedSquare)
                    .collect(Collectors.toList());

            possibleMoves.forEach(move -> { // 이동가능한 곳 하이라이트
                Square targetSquare = move.getTo();
                SquarePanel squarePanel = (SquarePanel) boardPanel.getSquarePanel(targetSquare.getFile().getNotation().charAt(0), Integer.parseInt(targetSquare.getRank().getNotation()));
                squarePanel.setHighlight(true);
                System.out.print(move.getTo() + " ");
                System.out.println(move.getPromotion());
            });
            System.out.println();
            possibleMoves.stream().findFirst().ifPresent(fromMove -> this.fromPiece = fromMove.getFrom());
        }
    }
    public void removeHighlightSquare() {
        possibleMoves.forEach(move -> {
            Square moveTo = move.getTo();
            SquarePanel squarePanel = (SquarePanel) boardPanel.getSquarePanel(moveTo.getFile().getNotation().charAt(0), Integer.parseInt(moveTo.getRank().getNotation()));
            squarePanel.setHighlight(false);
        });
        possibleMoves.clear();
    }
    private boolean isTurn(Piece piece) {
        String[] fenParts = boardPanel.getBoard().getFen().split(" ");

        String pieceColor = piece.name().substring(0, 5);

        if(pieceColor.equals("BLACK") && fenParts[1].equals("b")) {
            return true;
        }else if(pieceColor.equals("WHITE") && fenParts[1].equals("w")) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    private char calculateFile(MouseEvent e) {
        if (boardPanel.isBoardReversed()) {
            return (char) ('H' - e.getPoint().x / boardPanel.SQUARE_DIMENSION);
        } else {
            return (char) ('A' + e.getPoint().x / boardPanel.SQUARE_DIMENSION);
        }
    }
    private int calculateRank(MouseEvent e) { // 0~ 7
        if(boardPanel.isBoardReversed()) {
            return 1 + e.getPoint().y / boardPanel.SQUARE_DIMENSION;
        }else {
            return 8 - e.getPoint().y / boardPanel.SQUARE_DIMENSION;
        }
    }
}
