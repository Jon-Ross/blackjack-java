package game_screen;

public class DummyChild extends Dummy {

    @Override
    public void coolFunc() {
        super.coolFunc();
        System.out.println("dummy child code");
    }

    @Override
    public void abstractFunc() {
        System.out.println("dummy code " + value);
    }

    public void childFunc() {
        System.out.println("my own child code");
    }
}
