import java.util.*;
import java.io.*;

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Path implements Comparable<Path> {
    ArrayList<Coordinate> coors;
    int cx;
    int cy;
    char[][] maze;
    int total;

    public Path(int initialX, int initialY, char[][] maze) {
        coors = new ArrayList<>();
        cx = initialX;
        cy = initialY;
        this.maze = maze;
        total = maze[initialY][initialX] - '0';
    }

    public Path(Path p) {
        coors = new ArrayList<>();

        for (Coordinate c : p.coors) {
            coors.add(c);
        }
        cx = p.cx;
        cy = p.cy;

        this.maze = p.maze;
        total += maze[cy][cx] - '0';
    }

    public void add(int x, int y) {
        cx = x;
        cy = y;
        coors.add(new Coordinate(x, y));
    }

    @Override
    public int compareTo(Path p) {
        return total - p.total;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Path)) {
            return false;
        }

        Path p = (Path)o;

        for (int i = 0; i < p.coors.size(); i++) {
            if (p.coors.get(i).x != coors.get(i).x || p.coors.get(i).y != coors.get(i).y) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        for (Coordinate c : coors) {
            hash = hash * 19 + c.x;
            hash = hash * 19 + c.y;
        }

        return hash;
    }
}

public class FindPath {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("ShortestPath/maze_input_weights.txt"));

        final int SIZE = 41;
        char[][] maze = new char[SIZE][SIZE];

        // read the maze
        int i = 0;
        while (sc.hasNextLine()) {
            maze[i++] = sc.nextLine().toCharArray();
        }

        PriorityQueue<Path> paths = new PriorityQueue<>();

        Set<Path> visited = new HashSet<>();

        paths.add(new Path(0, 0, maze));

        // X is a column
        // Y is a row
        // maze[row][column]
        while (!paths.isEmpty()) {
            Path p = paths.poll();

            if (visited.contains(p)) {
                continue;
            }
            visited.add(p);

            if (p.cx == maze[0].length - 1 && p.cy == maze.length - 1) {
                paths.clear();
                paths.add(p);
                break;
            }
            
            // Going left
            if (p.cx - 1 >= 0 && maze[p.cy][p.cx - 1] != '#') {
                Path newP = new Path(p);
                newP.add(p.cx - 1, p.cy);
                paths.add(newP);
            }

            // Going right
            if (p.cx + 1 < maze[0].length && maze[p.cy][p.cx + 1] != '#') {
                Path newP = new Path(p);
                newP.add(p.cx + 1, p.cy);
                paths.add(newP);
            }

            // Going up
            if (p.cy - 1 >= 0 && maze[p.cy - 1][p.cx] != '#') {
                Path newP = new Path(p);
                newP.add(p.cx, p.cy - 1);
                paths.add(newP);
            }

            // Going down
            if (p.cy + 1 < maze.length && maze[p.cy + 1][p.cx] != '#') {
                Path newP = new Path(p);
                newP.add(p.cx, p.cy + 1);
                paths.add(newP);
            }
        } // end of while

        // Print the final maze
        for (Coordinate c : paths.poll().coors) {
            maze[c.y][c.x] = 'O';
        }

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] >= '0' && maze[row][col] <= '9') {
                    maze[row][col] = ' ';
                }
                System.out.print(maze[row][col]);
            }
            System.out.println();
        }
    }
}
