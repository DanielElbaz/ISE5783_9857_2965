package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
/**
 * Testing Camera Class
 *
 */
public class ImageWriterTests {

    @Test
    public void testWriteToImage() {
        Color color1 = new Color(255,0,127);
        Color color2 = new Color(0,0,255);

        ImageWriter writer = new ImageWriter("test", 801,501);
        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                if(i%50 == 0 || j%50 == 0){
                    writer.writePixel(i,j,color1);
                }
                else {
                    writer.writePixel(i,j,color2);
                }
            }
        }
        writer.writeToImage();
    }


}
