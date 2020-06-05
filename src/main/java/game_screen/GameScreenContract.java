package game_screen;

public interface GameScreenContract {

    interface View {

    }

    interface Presenter {

        void bind(final View view);
        void unbind();
    }
}
