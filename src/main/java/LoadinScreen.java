import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadinScreen {
    private BufferedImage hg;
    private int state;


    public LoadinScreen() {
        try {
            hg = ImageIO.read(new File("src/main/java/lbar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        Random r = new Random();
        int rdm = r.nextInt(5)+5;
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(()-> {
            if(state < 100) {
                state++;
                System.out.println(state);
            }
        },0,50, TimeUnit.MILLISECONDS );
    }

    public int getState() {
        return state;
    }

    public BufferedImage getHg() {
        return hg;
    }
}
