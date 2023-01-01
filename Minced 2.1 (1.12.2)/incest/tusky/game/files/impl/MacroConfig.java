package incest.tusky.game.files.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.cmd.macro.Macro;
import incest.tusky.game.files.FileManager;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class MacroConfig extends FileManager.CustomFile {

    public MacroConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    public void loadFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.getFile().getAbsolutePath());
            DataInputStream in = new DataInputStream(fileInputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String bind = curLine.split(":")[0];
                String value = curLine.split(":")[1];
                if (tuskevich.instance.macroManager != null) {
                    tuskevich.instance.macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value));
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
            for (Macro m : tuskevich.instance.macroManager.getMacros()) {
                if (m != null) {
                    out.write(Keyboard.getKeyName(m.getKey()) + ":" + m.getValue());
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception ignored) {

        }
    }
}
