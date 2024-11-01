package xyz.niflheim.stockfish;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.engine.enums.Option;
import xyz.niflheim.stockfish.engine.enums.Query;
import xyz.niflheim.stockfish.engine.enums.QueryType;
import xyz.niflheim.stockfish.engine.enums.Variant;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.Elo;

public class hello {
    public static void main(String[] args) throws StockfishInitException {

        Board board = new Board();
        String fen = board.getFen();
        System.out.println(fen);

        StockfishClient client = new StockfishClient.Builder().setVariant(Variant.BMI2).setInstances(1).build();

        Elo beginner = Elo.BEGINNER;
        StockfishClient client1 = new StockfishClient.Builder().setVariant(Variant.BMI2).setInstances(1).setOption(Option.Skill_Level,beginner.getRating()).build();

        Query query = new Query.Builder(QueryType.Best_Move,fen)
                .build();
        client.submit(query,result -> {
            System.out.println(result);
            String substring = result.toUpperCase().substring(0, 2);
            String substring1 = result.toUpperCase().substring(2, 4);

            Move move = new Move(Square.valueOf(substring), Square.valueOf(substring1));
            boolean b = board.doMove(move);
            System.out.println("is move success ? : " + b);
            if(b) {
                System.out.println(board.getFen());
            }else {
                System.out.println("실패");
            }

        });
    }
}
