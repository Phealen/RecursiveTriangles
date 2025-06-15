package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;
    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + "size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.black);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.drawString("Triangle goes here", border * 2, border * 4);

        // base triangle
        Polygon triangle = new Polygon();

        // middlepoint
        int x0 = size.width / 2;
        int y0 = size.height - border - 20;

        int length = size.width - (border * 2 + 20);
        double height = Math.sqrt(3) / 2 * length;

        triangle.addPoint(x0, (int) (y0 - height));
        triangle.addPoint(x0 - length / 2, y0);
        triangle.addPoint(x0 + length / 2, y0);

        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        Color randomColor = new Color(red, green, blue);

        gBuffer.setColor(randomColor);
        gBuffer.fillPolygon(triangle);
        drawTriangleRecursive(gBuffer, 8, triangle);

        return bufferedImage;
    }
        /* OLD VERSION
        //Triangle drawing
        int xa = (size.width/2);
        int xb = (size.width/2+30);
        int xc = (size.width/2-30);
        int ya = 50;
        int ybc = 100;
        int x[]={xa,xb,xc};
        int y[]={ya,ybc,ybc};
        gBuffer.drawPolygon(x,y , 3);

        //loop to create multiple triangles

        for (int i = 0; i < 10;i++){
            int x2[]={xa, xb, xc};
            int y2[]={50+(i*100),100+(i*50),100+(i*50)};
            gBuffer.drawPolygon(x2,y2,3);
        }
        return bufferedImage;
    }*/
    private void drawTriangleRecursive(Graphics2D gBuffer, int depth, Polygon triangle) {
        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        Color randomColor = new Color(red, green, blue);

        if (depth == 0) {
            gBuffer.setColor(randomColor);
            gBuffer.fillPolygon(triangle);
        } else {
            int x1 = (triangle.xpoints[0] + triangle.xpoints[1]) / 2;
            int y1 = (triangle.ypoints[0] + triangle.ypoints[1]) / 2;

            int x2 = (triangle.xpoints[1] + triangle.xpoints[2]) / 2;
            int y2 = (triangle.ypoints[1] + triangle.ypoints[2]) / 2;

            int x3 = (triangle.xpoints[2] + triangle.xpoints[0]) / 2;
            int y3 = (triangle.ypoints[2] + triangle.ypoints[0]) / 2;

            Polygon triangle1 = new Polygon();
            Polygon triangle2 = new Polygon();
            Polygon triangle3 = new Polygon();
            Polygon triangle4 = new Polygon();

            triangle1.addPoint(triangle.xpoints[0], triangle.ypoints[0]);
            triangle1.addPoint(x1, y1);
            triangle1.addPoint(x3, y3);

            triangle2.addPoint(triangle.xpoints[1], triangle.ypoints[1]);
            triangle2.addPoint(x2, y2);
            triangle2.addPoint(x1, y1);

            triangle3.addPoint(triangle.xpoints[2], triangle.ypoints[2]);
            triangle3.addPoint(x3, y3);
            triangle3.addPoint(x2, y2);

            triangle4.addPoint(x1, y1);
            triangle4.addPoint(x2, y2);
            triangle4.addPoint(x3, y3);

            red = rand.nextFloat();
            green = rand.nextFloat();
            blue = rand.nextFloat();
            randomColor = new Color(red, green, blue);
            gBuffer.setColor(randomColor);
            gBuffer.fillPolygon(triangle4);

            drawTriangleRecursive(gBuffer, depth - 1, triangle1);
            drawTriangleRecursive(gBuffer, depth - 1, triangle2);
            drawTriangleRecursive(gBuffer, depth - 1, triangle3);
        }
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
