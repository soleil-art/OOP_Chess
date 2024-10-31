package xyz.niflheim.stockfish.util;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.engine.enums.Option;
import xyz.niflheim.stockfish.engine.enums.Variant;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;

public class GameDTO {
    private Board board;
    private StockfishClient stockfishClient;
    private GameMode gameMode;
    private boolean boardReserved;
    private String blackPlayer;
    private String whitePlayer;

    public GameDTO(Preference preference) throws StockfishInitException {
        gameMode = preference.getGameMode();
        createStockFishClient(preference);
        initializeBoard(preference);
    }


    private void createStockFishClient(Preference preference) throws StockfishInitException {
        if(gameMode==GameMode.HUMAN_VS_MACHINE || gameMode==GameMode.MACHINE_VS_HUMAN) {
            Elo elo = preference.getElo();
            stockfishClient = new StockfishClient.Builder().setVariant(Variant.BMI2).setInstances(1) // 초심자,중수,고수 분류에 따른 엔진 생성
                    .setOption(Option.Skill_Level,elo.getRating()).build();
        }
    }
    private void initializeBoard(Preference preference) {
        board = new Board();
        if(gameMode==GameMode.HUMAN_VS_MACHINE) {
            blackPlayer = preference.getUserName();
            whitePlayer = "StockFishEngin";
            boardReserved = true;
        }else if(gameMode==GameMode.MACHINE_VS_HUMAN) {
            blackPlayer = "StockFishEngin";
            whitePlayer = preference.getUserName();
        }else {
            blackPlayer = preference.getUserName()+"(1)";
            whitePlayer = preference.getUserName();
        }
    }
}
