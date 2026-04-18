package ecosystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

    private int width, height;
    private List<Organism> organisms;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.organisms = new ArrayList<>();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void addOrganism(Organism o) {
        organisms.add(o);
    }

    public void removeOrganism(Organism o) {
        organisms.remove(o);
    }

    public boolean isFree(int x, int y) {
        for (Organism o : organisms)
            if (o.getX() == x && o.getY() == y) return false;
        return true;
    }

    public Organism getOrganismAtPosition(int x, int y) {
        for (Organism o : organisms)
            if (o.getX() == x && o.getY() == y) return o;
        return null;
    }

    public int[] getRandomFreeAdjacentPosition(int x, int y) {
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        List<int[]> free = new ArrayList<>();
        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && isFree(nx, ny))
                free.add(new int[]{nx, ny});
        }
        if (free.isEmpty()) return null;
        return free.get(new Random().nextInt(free.size()));
    }

    public List<int[]> getAdjacentPositions(int x, int y) {
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        List<int[]> pos = new ArrayList<>();
        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (nx >=0 && nx < width && ny >=0 && ny < height)
                pos.add(new int[]{nx, ny});
        }
        return pos;
    }

    public void step() {
        List<Organism> snapshot = new ArrayList<>(organisms);

        for (Organism o : snapshot) {
            if (organisms.contains(o)) {
                o.step(this);
            }
        }

        organisms.removeIf(o -> {
            boolean deadByAge = o.isDeadByAge();
            boolean deadByEnergy = false;

            if (o instanceof Animal animal) {
                deadByEnergy = animal.getEnergy() <= 0;
            }

            return deadByAge || deadByEnergy;
        });
    }

    public void print() {
        System.out.println("+" + "-".repeat(width * 2) + "+");
        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                Organism o = getOrganismAtPosition(x, y);
                System.out.print((o == null ? "." : o.getSymbol()) + " ");
            }
            System.out.println("|");
        }
        System.out.println("+" + "-".repeat(width * 2) + "+");
    }

    public int countOrganismType(Class<?> type) {
        int count = 0;
        for (Organism o : organisms) {
            if (type.isInstance(o)) count++;
        }
        return count;
    }

    public boolean hasOrganismType(Class<?> type) {
        return countOrganismType(type) > 0;
    }
}