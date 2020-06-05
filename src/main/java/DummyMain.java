import game_screen.Dummy;
import game_screen.DummyChild;

public class DummyMain {

    public static void main(String[] args) {
        Dummy dummy = new DummyChild();
        dummy.coolFunc();
        dummy.abstractFunc();

        dummy = new DummyChild2();
        dummy.abstractFunc();
        //dummy.childFunc();

        final DummyInterface dummyInterface = new DummyInterfaceChild();
        dummyInterface.coolFunc();
    }
}
