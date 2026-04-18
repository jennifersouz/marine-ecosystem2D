package ecosystem;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // INITIALIZATION PROBABILITIES
    private static final double PROB_ALGA = 0.30;           // 30%
    private static final double PROB_ZOOPLANKTON = 0.10;    // 10%
    private static final double PROB_JELLYFISH = 0.05;      // 5%
    private static final double PROB_TURTLE = 0.02;         // 2%
    // The remaining percentage stays empty

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Grid dimensions
        int width = 20;
        int height = 20;

        Grid grid = new Grid(width, height);

        // INITIALIZATION BY PROBABILITY
        System.out.println("=== INITIALIZING MARINE ECOSYSTEM ===");
        initializeByProbabilities(grid, width, height);

        System.out.println("Initial Grid:");
        grid.print();
        showStatistics(grid, 0);

        // EXECUTION CONTROLS
        boolean running = true;
        int stepCount = 0;

        while (running) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║         SIMULATION CONTROLS           ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1. Run Step-by-Step");
            System.out.println("2. Run N Steps");
            System.out.println("3. Run until a species goes extinct");
            System.out.println("4. Exit");
            System.out.print("\nChoose an option: ");

            int option = sc.nextInt();

            switch (option) {
                case 1:
                    stepCount++;
                    executeStep(grid, stepCount);
                    if (checkExtinction(grid)) {
                        System.out.println("\nA SPECIES HAS GONE EXTINCT!");
                        showExtinctSpecies(grid);
                        running = false;
                    }
                    break;

                case 2:
                    System.out.print("How many steps do you want to run? ");
                    int n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        stepCount++;
                        executeStep(grid, stepCount);
                        if (checkExtinction(grid)) {
                            System.out.println("\nA SPECIES WENT EXTINCT AT STEP " + stepCount + "!");
                            showExtinctSpecies(grid);
                            running = false;
                            break;
                        }
                        sleep(200); // Visual pause
                    }
                    break;

                case 3:
                    System.out.println("Running until extinction...\n");
                    while (!checkExtinction(grid)) {
                        stepCount++;
                        executeStep(grid, stepCount);
                        sleep(100);

                        // Safety limit
                        if (stepCount > 2000) {
                            System.out.println("\nSimulation stopped after 2000 steps.");
                            break;
                        }
                    }
                    System.out.println("\nA SPECIES WENT EXTINCT AT STEP " + stepCount + "!");
                    showExtinctSpecies(grid);
                    running = false;
                    break;

                case 4:
                    running = false;
                    System.out.println("\nSimulation ended. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }

        sc.close();
    }

    private static void initializeByProbabilities(Grid grid, int width, int height) {
        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double prob = rand.nextDouble();

                if (prob < PROB_ALGA) {
                    grid.addOrganism(new Algae(x, y));
                } else if (prob < PROB_ALGA + PROB_ZOOPLANKTON) {
                    grid.addOrganism(new Zooplankton(x, y));
                } else if (prob < PROB_ALGA + PROB_ZOOPLANKTON + PROB_JELLYFISH) {
                    grid.addOrganism(new Jellyfish(x, y));
                } else if (prob < PROB_ALGA + PROB_ZOOPLANKTON + PROB_JELLYFISH + PROB_TURTLE) {
                    grid.addOrganism(new SeaTurtle(x, y));
                }
            }
        }
    }

    private static void executeStep(Grid grid, int stepNumber) {
        clearConsole();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║            STEP " + String.format("%4d", stepNumber) + "                  ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        grid.print();
        grid.step();
        showStatistics(grid, stepNumber);
    }

    private static boolean checkExtinction(Grid grid) {
        return !grid.hasOrganismType(Algae.class) ||
                !grid.hasOrganismType(Zooplankton.class) ||
                !grid.hasOrganismType(Jellyfish.class) ||
                !grid.hasOrganismType(SeaTurtle.class);
    }

    private static void showExtinctSpecies(Grid grid) {
        System.out.println("\nExtinct Species:");
        if (!grid.hasOrganismType(Algae.class)) System.out.println("- Algae (A)");
        if (!grid.hasOrganismType(Zooplankton.class)) System.out.println("- Zooplankton (Z)");
        if (!grid.hasOrganismType(Jellyfish.class)) System.out.println("- Jellyfish (J)");
        if (!grid.hasOrganismType(SeaTurtle.class)) System.out.println("- Sea Turtle (T)");
    }

    private static void showStatistics(Grid grid, int stepNumber) {
        int algae = grid.countOrganismType(Algae.class);
        int zooplankton = grid.countOrganismType(Zooplankton.class);
        int jellyfish = grid.countOrganismType(Jellyfish.class);
        int turtles = grid.countOrganismType(SeaTurtle.class);
        int total = algae + zooplankton + jellyfish + turtles;

        System.out.println("  STATISTICS (Step " + String.format("%4d", stepNumber) + ")");
        System.out.println("    Algae (A)      : " + String.format("%4d", algae));
        System.out.println("    Zooplankton (Z): " + String.format("%4d", zooplankton));
        System.out.println("    Jellyfish (J)  : " + String.format("%4d", jellyfish));
        System.out.println("    Sea Turtle (T) : " + String.format("%4d", turtles));
        System.out.println("    TOTAL          : " + String.format("%4d", total));
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}