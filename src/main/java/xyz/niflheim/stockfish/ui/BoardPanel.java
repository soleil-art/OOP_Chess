package xyz.niflheim.stockfish.ui;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import xyz.niflheim.stockfish.util.GameDTO;

import javax.swing.*;
import java.awt.*;


public class BoardPanel extends JPanel implements BoardEventListener {

    public static final int SQUARE_DIMENSION = 75;

    private final Board board;
    private boolean boardReversed;

    private JPanel boardPanel;
    private JPanel[][] squarePanels;
    private JLayeredPane boardLayeredPane;

    private JPanel promotionPanel;  // 프로모션 패널
    private Square promotionSquare;
    private boolean isWhitePromotion;
    Square[] values;
    int index =0;


    public BoardPanel(GameDTO gameDTO) {
        super(new BorderLayout());
        board = gameDTO.getBoard();
        board.addEventListener(BoardEventType.ON_MOVE,this);
        board.addEventListener(BoardEventType.ON_LOAD,this);
        board.addEventListener(BoardEventType.ON_UNDO_MOVE,this);

        this.boardReversed = gameDTO.isBoardReserved();
        values = Square.values();

        initializeBoardLayeredPanel();
        initializeSquares();
        initializePieces();
    }

    private void initializeSquares() {
        squarePanels = new JPanel[8][8];
        if(boardReversed) {
            for(int row =0; row <8; row++) {
                for(int col = 7; col >=0; col--) {
                    initializeSingleSquaresPanel(row,col);
                }
            }
        }else { // 정방향
            for (int row = 7; row >= 0; row--) {
                for (int col = 0; col < 8; col++) {
                    initializeSingleSquaresPanel(row,col);
                }
            }
        }
    }
    private void initializeSingleSquaresPanel(int row,int col) {
        JLayeredPane squareLayeredPane = new JLayeredPane();
        squareLayeredPane.setPreferredSize(new Dimension(SQUARE_DIMENSION,SQUARE_DIMENSION));

        //바닥 패널 설정
        JPanel jPanel = new JPanel(new GridLayout(1, 1));
        squarePanels[row][col] = new SquarePanel(new GridLayout(1,1));
        squarePanels[row][col].setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
        squarePanels[row][col].setBackground(row%2==col%2 ? Color.decode("#7D945D") : Color.decode("#EEEED5")); // 초록색 ,베이지색
        squarePanels[row][col].setName(Square.squareAt(index).toString());
        boardPanel.add(squarePanels[row][col]);
    }
    private void initializeBoardLayeredPanel() {
        boardPanel = new JPanel(new GridLayout(8,8));
        boardPanel.setBounds(0,0,8*SQUARE_DIMENSION,8*SQUARE_DIMENSION);

        boardLayeredPane = new JLayeredPane();
        boardLayeredPane.setPreferredSize(new Dimension(8*SQUARE_DIMENSION,8*SQUARE_DIMENSION));
        boardLayeredPane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);

        PieceDragAndDropListener pieceDragAndDropListener = new PieceDragAndDropListener(this);
        boardLayeredPane.addMouseListener(pieceDragAndDropListener);
        boardLayeredPane.addMouseMotionListener(pieceDragAndDropListener);
        boardLayeredPane.setVisible(true);

        this.add(boardLayeredPane,BorderLayout.CENTER);
    }
    private void initializePieces() {

        for (Square square : Square.values()) {

            if(board.getPiece(square)!=Piece.NONE) {
                char file = square.getFile().getNotation().charAt(0);
                int rank = Integer.parseInt(square.getRank().getNotation());

                JPanel squarePanel = getSquarePanel(file,rank);
                squarePanel.add(getPieceImageLabel(board.getPiece(square).toString()));
                squarePanel.repaint();
            }
        }
    }

    public void showPromotionPanel(Square promotionSquare, boolean isWhite) {
        this.promotionSquare = promotionSquare;
        this.isWhitePromotion = isWhite;

        promotionPanel = new JPanel();
        promotionPanel.setLayout(new GridLayout(1, 4));
        promotionPanel.setBounds(200, 200, 300, 100); // 원하는 위치와 크기로 설정

        String[] promotionOptions = {"Queen", "Rook", "Bishop", "Knight"};
        for (String option : promotionOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> promotePawn(option));
            promotionPanel.add(button);
        }

        boardLayeredPane.add(promotionPanel, JLayeredPane.POPUP_LAYER); // 가장 위에 패널 추가
        promotionPanel.setVisible(true);
    }

    private void promotePawn(String pieceType) {
        Piece promotedPiece;
        switch (pieceType) {
            case "Rook":
                promotedPiece = isWhitePromotion ? Piece.WHITE_ROOK : Piece.BLACK_ROOK;
                break;
            case "Bishop":
                promotedPiece = isWhitePromotion ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
                break;
            case "Knight":
                promotedPiece = isWhitePromotion ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
                break;
            default:
                promotedPiece = isWhitePromotion ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
        }

        // 프로모션 적용
        board.doMove(new Move(promotionSquare, promotionSquare, promotedPiece), true);
        boardLayeredPane.remove(promotionPanel);  // 프로모션 패널 제거
        boardLayeredPane.repaint();  // 리프레시
    }

    public JPanel getSquarePanel(char file,int rank) {
        if(file <'A' || file > 'H' || rank <1 || rank >8) {
            return null;
        }else {
            return squarePanels[rank-1][file-'A'];
        }
    }
    public void executeMove(Move move) {
        loadingBoard(this.board);
        System.out.println(board.toString());
        System.out.println(board.getFen());
    }
    public void executeUndo(MoveBackup undoMove) {
        Square from = undoMove.getMove().getFrom();
        Square moveTo = undoMove.getMove().getTo();

        JPanel originalSquarePanel = getSquarePanel(from.getFile().getNotation().charAt(0), Integer.parseInt(from.getRank().getNotation()));
        JPanel destinationSquarePanel = getSquarePanel(moveTo.getFile().getNotation().charAt(0), Integer.parseInt(moveTo.getRank().getNotation()));

        if(undoMove.getCapturedPiece()!=Piece.NONE) { // 기물을 잡았던 경우
            Piece capturedPiece = undoMove.getCapturedPiece();

            if(undoMove.getMove().getPromotion()!=Piece.NONE) { // 기물을 잡으면서 동시에 프로모션을 한 경우
                Piece movingPiece = undoMove.getMovingPiece();
                addComponentToPanel(originalSquarePanel,getPieceImageLabel(movingPiece.toString()));
                addComponentToPanel(destinationSquarePanel,getPieceImageLabel(capturedPiece.toString()));
            }else {
                addComponentToPanel(originalSquarePanel,destinationSquarePanel.getComponent(0));
                addComponentToPanel(destinationSquarePanel,getPieceImageLabel(capturedPiece.toString()));
            }
        }else { // 기물을 잡지 않은 경우
            if(undoMove.isCastleMove()) { // 캐스링한 경우
                Component orginComponent = originalSquarePanel.getComponent(0);
                Component destinationComponent = destinationSquarePanel.getComponent(0);

                addComponentToPanel(originalSquarePanel,destinationComponent);
                addComponentToPanel(destinationSquarePanel,orginComponent);

            }else if(undoMove.getMove().getPromotion()!=Piece.NONE){
                addComponentToPanel(originalSquarePanel,getPieceImageLabel(undoMove.getMovingPiece().toString()));

                destinationSquarePanel.removeAll();
                destinationSquarePanel.revalidate();
                destinationSquarePanel.repaint();
            }else {
                addComponentToPanel(originalSquarePanel,destinationSquarePanel.getComponent(0));

                destinationSquarePanel.removeAll();
                destinationSquarePanel.revalidate();
                destinationSquarePanel.repaint();
            }
        }

    }
    private void addComponentToPanel(JPanel panel,Component component) {
        panel.removeAll();
        panel.add(component);
        panel.revalidate();
        panel.repaint();
    }
    public void loadingBoard(Board board) {
        for(Square square :Square.values()) {
            if(square!=Square.NONE) {
                Piece boardPiece = board.getPiece(square);

                char file = square.getFile().getNotation().charAt(0);
                int rank = Integer.parseInt(square.getRank().getNotation());

                SquarePanel squarePanel = (SquarePanel)getSquarePanel(file, rank);
                if(boardPiece!=Piece.NONE) {

                    addComponentToPanel(squarePanel,getPieceImageLabel(boardPiece.toString()));
                }else {
                    squarePanel.removeAll();
                    squarePanel.revalidate();
                    squarePanel.repaint();

                }
            }

        }

    }

    private JLabel getPieceImageLabel(String piece) {
        String imagePath = "/pieces/";
        imagePath += piece.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = icon.getImage().getScaledInstance(SQUARE_DIMENSION, SQUARE_DIMENSION, Image.SCALE_SMOOTH);
        JLabel pieceImageLable = new JLabel(new ImageIcon(scaledImage));
        return pieceImageLable;
    }

    public boolean isBoardReversed() {
        return boardReversed;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void onEvent(BoardEvent event) {
        if(event.getType()== BoardEventType.ON_MOVE) {
            Move move = (Move) event;
            executeMove(move);
        }else if(event.getType()==BoardEventType.ON_UNDO_MOVE) {
            MoveBackup undoMove = (MoveBackup) event;
            executeUndo(undoMove);
        }else if(event.getType()==BoardEventType.ON_LOAD) {
            Board board = (Board) event;
            loadingBoard(board);
        }
    }
    public static void main(String[] args) {
        // Swing UI를 만들기 위한 메인 프레임 생성
        JFrame frame = new JFrame("Chess Game");

        // 종료 버튼 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 체스 보드 패널 생성 (정방향 설정)
        BoardPanel boardPanel = new BoardPanel(false); // false는 보드가 정방향임을 의미합니다.

        // 패널을 프레임에 추가
        frame.add(boardPanel);

        // 창 크기 및 위치 설정
        frame.setSize(8 * BoardPanel.SQUARE_DIMENSION, 8 * BoardPanel.SQUARE_DIMENSION);
        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.pack();

        // 프레임을 표시
        frame.setVisible(true);
    }
}
