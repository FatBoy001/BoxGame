import java.util.Scanner;

public class Game {
    private final static int MAP_WIDTH = 12;
    private final static int MAP_HEIGHT = 12;
    private final Map map;
    private Player player;

    public Game() {
        player = new Player(0, 0);
        map = new Map(MAP_WIDTH, MAP_HEIGHT, player);
        map.createInviroment(10);
        map.createPlayer(player);
        refreshScreen();
    }

    public boolean runGame() {
        Scanner input = new Scanner(System.in);
        String diraction;
        boolean endGame = true;
        map.display();
        while (true) {
            System.out.println("'w'->up 's'->down 'a'->right 'd'->right");
            System.out.print("Moving diraction('o' enter to exit 'r' to restart): ");
            diraction = input.next();
            if (diraction.equals("o"))
                break;
            else if (diraction.equals("r")) {
                endGame = false;
                break;
            }

            playerControl(diraction);
            refreshScreen();
            map.display();
            if (checkWinning()) {
                System.out.println("You win! Enter 'r' for try again others for exit.");
                if (input.next().equals("r"))
                    endGame = false;
                else
                    endGame = true;
                break;
            }
        }
        // input.close();
        return endGame;
    }

    private void playerControl(String diraction) {
        int x, y;
        switch (diraction) {
            case "w":
                x = 0;
                y = -1;
                placePlayer(x, y);
                break;
            case "a":
                x = -1;
                y = 0;
                placePlayer(x, y);
                break;
            case "s":
                x = 0;
                y = 1;
                placePlayer(x, y);
                break;
            case "d":
                x = 1;
                y = 0;
                placePlayer(x, y);
                break;
            default:
                System.out.println("Invailed input. 'w', 'a', 's', 'd' only.");
        }
    }

    private void placePlayer(int x, int y) {
        if (checkMovable(x, y)) {
            if (map.getMap()[player.getX() + x][player.getY() + y] == Map.BOX)
                map.moveBox(x, y);
            map.movePlayer(x, y);
        }

    }

    private boolean checkMovable(int x, int y) {

        if (map.getMap()[player.getX() + x][player.getY() + y] == Map.WALL)
            return false;
        if (map.getMap()[player.getX() + x][player.getY() + y] == Map.END_POINT)
            return false;
        if (map.getMap()[player.getX() + x][player.getY() + y] == Map.BOX
                && map.getMap()[map.getBox().getX() + x][map.getBox().getY() + y] == Map.WALL)
            return false;

        return true;
    }

    public boolean checkWinning() {
        if (map.getBox().getX() == map.getEndPoint().getX() && map.getBox().getY() == map.getEndPoint().getY()) {
            return true;
        }
        return false;
    }

    private void refreshScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}