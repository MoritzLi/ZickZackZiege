package network;

public interface NetzwerkListener {
    void onPlayersChanged();

    void onGameStarted(int spielerCount, int myID);

    void onFieldSet(int id, int x, int y);

    void onYourTurn();
}