public class Main {
    public static void main(String[] args) {
        Game game;
        do {
            game = new Game();
        } while (!game.runGame());
        System.out.println("------------");
        System.out.println("-Game Over.-");
        System.out.println("------------");
    }
}