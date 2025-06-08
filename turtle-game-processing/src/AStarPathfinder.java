import java.util.*;


public class AStarPathfinder {
    private final Field field;

    public AStarPathfinder(Field field) {
        this.field = field;
    }

    private static class Node {
        int x, y;
        Node parent;
        int g;
        int f;

        Node(int x, int y, Node parent, int g, int h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.f = g + h;
        }
    }

    public List<int[]> findPath(int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        boolean[][] closed = new boolean[field.getSize()][field.getSize()];

        Node start = new Node(startX, startY, null, 0, heuristic(startX, startY, goalX, goalY));
        open.add(start);

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            closed[current.y][current.x] = true;

            for (int[] dir : directions()) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (!field.isInBounds(nx, ny) || closed[ny][nx] || !field.isSafe(nx, ny)) continue;

                int newG = current.g + 1;
                Node neighbor = new Node(nx, ny, current, newG, heuristic(nx, ny, goalX, goalY));
                open.add(neighbor);
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(int x, int y, int goalX, int goalY) {
        return Math.abs(goalX - x) + Math.abs(goalY - y);
    }

    private List<int[]> reconstructPath(Node node) {
        LinkedList<int[]> path = new LinkedList<>();
        while (node.parent != null) {
            path.addFirst(new int[]{node.x, node.y});
            node = node.parent;
        }
        return path;
    }

    private int[][] directions() {
        return new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
    }
}
