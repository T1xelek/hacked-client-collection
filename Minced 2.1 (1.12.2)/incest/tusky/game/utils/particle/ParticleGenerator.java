package incest.tusky.game.utils.particle;

import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleGenerator {
    private final List<Particle> particles = new ArrayList<>();

    private final int amount;

    private int prevWidth;

    private int prevHeight;

    public ParticleGenerator(int amount) {
        this.amount = amount;
    }

    public void draw(int mouseX, int mouseY) {
        if (this.particles.isEmpty() || this.prevWidth != (Minecraft.getMinecraft()).displayWidth || this.prevHeight != (Minecraft.getMinecraft()).displayHeight) {
            this.particles.clear();
            create();
        }
        this.prevWidth = (Minecraft.getMinecraft()).displayWidth;
        this.prevHeight = (Minecraft.getMinecraft()).displayHeight;
        for (Particle particle : this.particles) {
            particle.fall();
            particle.interpolation();
            int range = 110;
            boolean mouseOver = (mouseX >= particle.x - range && mouseY >= particle.y - range && mouseX <= particle.x + range && mouseY <= particle.y + range);
            if (mouseOver)
                this.particles.stream()
                        .filter(part -> (part.getX() > particle.getX() && part.getX() - particle.getX() < range && particle.getX() - part.getX() < range && ((part.getY() > particle.getY() && part.getY() - particle.getY() < range) || (particle.getY() > part.getY() && particle.getY() - part.getY() < range))))

                        .forEach(connectable -> particle.connect(connectable.getX(), connectable.getY()));
            RenderUtils.drawCircle(particle.getX(), particle.getY(),1,new Color(255,255,255).getRGB());

            RenderUtils.drawBlurredShadow(particle.getX(), particle.getY() - 2, particle.size, 4,6,new Color(255,255,255));
        }
    }

    private void create() {
        Random random = new Random();
        for (int i = 0; i < this.amount; i++)
            this.particles.add(new Particle(random.nextInt((Minecraft.getMinecraft()).displayWidth), random.nextInt((Minecraft.getMinecraft()).displayHeight)));
    }
}
