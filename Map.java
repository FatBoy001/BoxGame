import java.security.SecureRandom;

public class Map {
    class EndPoint {
        private int x;
        private int y;

        public EndPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    class Box {
        private int x;
        private int y;

        public Box(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    private final int[][] map;
    public final static int WALL = 1;
    public final static int FLOOR = 0;
    public final static int PLAYER = -1;
    public final static int END_POINT = 2;
    public final static int BOX = -2;
    private int mapWidth;
    private int mapHeight;
    private Player player;
    private EndPoint endPoint;
    private Box box;

    public Map(int mapWidth, int mapHeight, Player player) {
        this.mapWidth = mapWidth - 1;
        this.mapHeight = mapHeight - 1;
        this.player = player;
        this.map = new int[mapWidth][mapHeight];
        createBorder();
    }

    // getter
    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

    public int[][] getMap() {
        return this.map;
    }

    public Box getBox() {
        return box;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }
    // getter

    private void placItemInMap(int x, int y, int item) {
        map[x][y] = item;
    }

    public void movePlayer(int x, int y) {
        placItemInMap(player.getX() + x, player.getY() + y, Map.PLAYER);
        placItemInMap(player.getX(), player.getY(), Map.FLOOR);
        player.setX(player.getX() + x);
        player.setY(player.getY() + y);

    }

    public void moveBox(int x, int y) {
        placItemInMap(box.getX() + x, box.getY() + y, Map.BOX);
        box.setX(box.getX() + x);
        box.setY(box.getY() + y);
    }

    // [11,11]
    // (0,0) y->++ (0,11)
    // (0,11) x->++ (11,11)
    // (11,11) y->--(11,0)
    // (11,0) x->-- (0,0)

    private void createBorder() {
        for (int x = 0; x <= getMapWidth(); x++) {
            if (x == 0 || x == getMapWidth()) {
                for (int y = 0; y <= getMapHeight(); y++) {
                    map[x][y] = Map.WALL;
                }
                continue;
            }
            map[x][0] = Map.WALL;
            map[x][getMapHeight()] = Map.WALL;
        }
    }

    public void createInviroment(int roadblockNumber) {
        for (int i = 0; i < roadblockNumber;) {
            if (createItem(Map.WALL))
                i++;
        }
        while (!createItem(Map.END_POINT))
            ;
        while (true) {
            int x = randomNumber(mapWidth);
            int y = randomNumber(mapHeight);
            if (map[x + 1][y] != Map.WALL && map[x - 1][y] != Map.WALL && map[x][y + 1] != Map.WALL
                    && map[x][y - 1] != Map.WALL) {
                createItem(x, y, Map.BOX);
                break;
            }
        }
        System.out.println("Hello");
        for (int y = 0; y <= getMapHeight(); y++) {
            for (int x = 0; x <= getMapWidth(); x++) {
                if (map[x][y] == Map.END_POINT)
                    endPoint = new EndPoint(x, y);
                if (map[x][y] == Map.BOX)
                    box = new Box(x, y);
            }
        }

    }

    public void createPlayer(Player player) {
        while (true) {
            int x = randomNumber(mapWidth);
            int y = randomNumber(mapHeight);
            if (map[x][y] != Map.FLOOR)
                continue;
            if (map[x][y + 1] == Map.WALL && map[x + 1][y] == Map.WALL && map[x - 1][y] == Map.WALL
                    && map[x - 1][y] == Map.WALL)
                continue;
            placItemInMap(x, y, Map.PLAYER);
            player.setX(x);
            player.setY(y);
            return;
        }

    }

    private int randomNumber(int bound) {
        SecureRandom secureRandom = new SecureRandom();
        int min = 1;
        bound = bound - 1;
        int result = secureRandom.nextInt(bound - min + 1) + min;
        return result;
    }

    private boolean createItem(int item) {
        int x = randomNumber(mapWidth);
        int y = randomNumber(mapHeight);

        if (map[x][y] != Map.FLOOR)
            return false;
        if (map[x][y + 1] == Map.WALL && map[x + 1][y] == Map.WALL && map[x - 1][y] == Map.WALL
                && map[x - 1][y] == Map.WALL)
            return false;

        placItemInMap(x, y, item);
        return true;
    }

    private boolean createItem(int x, int y, int item) {
        placItemInMap(x, y, item);
        return true;
    }

    public void display() {
        for (int y = 0; y <= getMapHeight(); y++) {
            for (int x = 0; x <= getMapWidth(); x++) {
                switch (map[x][y]) {
                    case 2:
                        System.out.print("X");
                        break;
                    case 1:
                        System.out.print("&");
                        break;
                    case 0:
                        System.out.print("*");
                        break;
                    case -1:
                        System.out.print("P");
                        break;
                    case -2:
                        System.out.print("B");
                        break;
                }
            }

            System.out.println();
        }
    }
}
