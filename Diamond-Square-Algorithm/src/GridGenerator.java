public class GridGenerator {
    private int step;
    private double[][] currentGrid;
    private GridGenerator()
    {
        step = 0;
        generateStartingGrid();
    }

    public static double[][] generateGrid(int size){
        GridGenerator generator = new GridGenerator();
        for (int i = 0; i < size; i++){
            generator.increaseGridSize();
        }
        return generator.currentGrid;
    }

    private void generateStartingGrid()
    {
        currentGrid = new double[][]{{Math.random(), Math.random()},
                                    {Math.random(), Math.random()}};
    }

    private int getSideLength(int step)
    {
        return 1 + (int)Math.pow(2, step);
    }

    private double randomOffset(int step)
    {
        return (2*Math.random()-1) / Math.pow(Main.noise, step);
    }

    private void increaseGridSize()
    {
        int oldSideLength = getSideLength(step);
        int newSideLength = getSideLength(step+1);

        // -- Copy old Grid to new Grid -
        double[][] nextGrid = new double[newSideLength][newSideLength];
        for (int y = 0; y < oldSideLength; y++) {
            for (int x = 0; x < oldSideLength; x++) {
                nextGrid[y*2][x*2] = currentGrid[y][x];
            }
        }

        // -- Diamond Step --
        for (int y = 0; y < oldSideLength - 1; y++){
            for (int x = 0; x < oldSideLength - 1; x++){
                double sum = currentGrid[y][x]
                             + currentGrid[y+1][x]
                             + currentGrid[y+1][x+1]
                             + currentGrid[y][x+1];
                double average = sum / 4;
                nextGrid[2*y + 1][2*x + 1] = average + randomOffset(step+1);
            }
        }

        // -- Square Step --
        // Fill top Border
        for (int x = 1; x < nextGrid.length - 1; x += 2){
            double sum = nextGrid[0][x-1]
                    + nextGrid[0][x+1]
                    + nextGrid[1][x];
            double average = sum / 3;
            nextGrid[0][x] = average + randomOffset(step+1);
        }
        // Fill bottom border
        for (int x = 1; x < nextGrid.length - 1; x += 2){
            double sum = nextGrid[nextGrid.length-1][x-1]
                    + nextGrid[nextGrid.length-1][x+1]
                    + nextGrid[nextGrid.length-2][x];
            double average = sum / 3;
            nextGrid[nextGrid.length-1][x] = average + randomOffset(step+1);
        }
        // Fill left border
        for (int y = 1; y < nextGrid.length - 1; y += 2){
            double sum = nextGrid[y-1][0]
                    + nextGrid[y+1][0]
                    + nextGrid[y][1];
            double average = sum / 3;
            nextGrid[y][0] = average + randomOffset(step+1);
        }
        // Fill right border
        for (int y = 1; y < nextGrid.length - 1; y += 2){
            double sum = nextGrid[y-1][nextGrid.length - 1]
                    + nextGrid[y+1][nextGrid.length - 1]
                    + nextGrid[y][nextGrid.length - 2];
            double average = sum / 3;
            nextGrid[y][nextGrid.length - 1] = average + randomOffset(step+1);
        }

        // Fill center Diamonds
        for (int y = 1; y < nextGrid.length - 1; y++){
            for (int x = (y % 2) + 1; x < nextGrid.length - 1; x += 2){
                double sum = nextGrid[y-1][x]
                            + nextGrid[y][x-1]
                            + nextGrid[y+1][x]
                            + nextGrid[y][x+1];
                double average = sum / 4;
                nextGrid[y][x] = average + randomOffset(step+1);
            }
        }

        currentGrid = nextGrid;
        step++;
    }
}
