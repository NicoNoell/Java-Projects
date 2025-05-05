import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ObjFileWriter {
    private static File getEmptyFile(String path) throws IOException
    {
        File destinationFile = new File(path);
        if (!destinationFile.exists()) {
            destinationFile.getParentFile().mkdirs();
            destinationFile.createNewFile();
        }
        return destinationFile;
    }

    /**
     * @param grid A grid of heights to form a mesh out of
     * @param path Path to the obj-file that is to be created
     * @return {@code true} if obj-file has successfully been created, {@code false} otherwise.
     */
    public static boolean writeGridToObjFile(double[][] grid, String path, Vec3 scaling){
        File destinationFile;
        try {
            destinationFile = getEmptyFile(path);
        } catch (IOException e){
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, false))) {
            writeVertices(grid, writer, scaling);
            writeFaces(grid, writer);
        } catch (IOException e){
            return false;
        }

        return true;
    }

    private static void writeFaces(double[][] grid, BufferedWriter writer) throws IOException
    {
        int width = grid[0].length;
        int depth = grid.length;
        for (int y = 0; y < depth - 1; y++){
            for (int x = 0; x < width - 1; x++){
                int a = y * width + x + 1;
                writeSquareFace(a, a+1, a+width, a+width+1, writer);
            }
        }
    }

    /**
     * Writes two adjacent triangles into the obj-file such that they fill the specified square.
     * @param a Top-Left
     * @param b Top-Right
     * @param c Bottom-Left
     * @param d Bottom-Right
     */
    private static void writeSquareFace(int a, int b, int c, int d, BufferedWriter writer) throws IOException
    {
        String face1 = "f " + a + " " + b + " " + c + "\n";
        String face2 = "f " + b + " " + d + " " + c + "\n";
        writer.write(face1 + face2);
    }

    private static void writeVertices(double[][] grid, BufferedWriter writer, Vec3 scaling) throws IOException
    {
        double xSpacing = (double)2/(grid[0].length-1);
        double zSpacing = (double)2/(grid.length-1);
        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[0].length; x++){
                double xPos = x * xSpacing - 1;
                double zPos = y * zSpacing - 1;
                writeVertex(xPos * scaling.x, grid[y][x] * scaling.y, zPos * scaling.z, writer);
            }
        }
        writer.newLine();
    }

    private static void writeVertex(double x, double y, double z, BufferedWriter writer) throws IOException
    {
        String line = "v " + x + " " + y + " " + z + "\n";
        writer.write(line);
    }
}
