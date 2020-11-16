package blackjack_console;

import blackjack_core.GameScreenContract;

public class ConsoleStringProvider implements GameScreenContract.StringProvider {

    @Override
    public String getGameInstructions() {
        return null;
    }

    @Override
    public String getStartingInstructions() {
        return null;
    }

    @Override
    public String getPlayAgainInstructions() {
        return null;
    }

    @Override
    public String getPlayerBustAlert() {
        return null;
    }

    @Override
    public String getUnderMinThresholdAlert() {
        return null;
    }

    @Override
    public String getHouseBustAlert() {
        return null;
    }

    @Override
    public String getHouseAtLeastThresholdAlert() {
        return null;
    }
}
