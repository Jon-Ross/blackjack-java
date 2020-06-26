import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GamePresenterTest {

    @Mock
    private GameScreenContract.View view;

    @Mock
    private Game game;

    private GameScreenContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        presenter = new GamePresenter(game);
        presenter.bind(view);
    }

    @Test
    public void GivenNewGame_WhenOnStartScreen_ThenShowStartingInstructions() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();

        // then
        verify(view).showStartingInstructions(startingInstructions);
    }

    @Test
    public void WhenOnStartBlackJackGame_ThenShowPlayerHandAndShowGameInstructions() {
        // given
        final int playerCard1 = 8;
        final int playerCard2 = 9;
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        // when
        presenter.onStartBlackJackGame();

        // then
        InOrder orderVerifier = Mockito.inOrder(view);
        orderVerifier.verify(view).showHandPlayer(playerHand);
        orderVerifier.verify(view).showGameInstructions(gameInstructions);
    }

    // TODO: Create onTwist() method in GamePresenter and update tests

    @Test
    public void name() {

//        game.addValue(10);
//        game.addValue(11);
//        final int playerCard1 = game.dealCard();
//        final int playerCard2 = game.dealCard();
//        final Hand playerHand = new Hand();
//        playerHand.addValue(playerCard1);
//        playerHand.addValue(playerCard2);
    }
}
