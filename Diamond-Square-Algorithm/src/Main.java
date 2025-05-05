public class Main {
    public final static String pathToObjFile = "./data/landscape.obj";
    public final static Vec3 scaling = new Vec3(15,6, 15);
    public final static double noise = 1.7; // A bigger value means less noise

    public static void main(String[] args) {
        double[][] grid = GridGenerator.generateGrid(6);

        ObjFileWriter.writeGridToObjFile(grid, pathToObjFile, scaling);
    }
}