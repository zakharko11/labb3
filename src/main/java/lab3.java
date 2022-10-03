import gamepkg.Game;

class Lab3
{
    public static void main(String[] args)
    {
        Game game = new Game();
        game.droidCreation();
        game.matchCreation();
        game.readBattleFromFile("battle.txt");
    }
}