import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.client.main.Main;

public class Start {

    public static void main(String[] args) {
        try {

            if (args == null) {
                args = new String[]{};
            }

            Main.main(concat(new String[]{"--version", "tuskevich", "--accessToken", "0",
                    "--assetIndex", "1.12", "--userProperties", "{}"
            }, args));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
