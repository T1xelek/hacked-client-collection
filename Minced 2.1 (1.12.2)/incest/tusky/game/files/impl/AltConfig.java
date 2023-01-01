package incest.tusky.game.files.impl;



import incest.tusky.game.files.FileManager;

import java.io.BufferedReader;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */
/*    */ public class AltConfig extends FileManager.CustomFile {
    /*    */   public AltConfig(String name, boolean loadOnStart) {
        /* 12 */     super(name, loadOnStart);
        /*    */   }
    /*    */
    /*    */   public void loadFile() throws IOException {
        /* 16 */     BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
        /*    */
        /*    */     String line;
        /* 19 */     while ((line = bufferedReader.readLine()) != null) {
            /* 20 */       String[] arrayOfString = line.split(":");
            /*    */     }
        /*    */
        /*    */
        /* 24 */     bufferedReader.close();
        /*    */   }
    /*    */
    /*    */   public void saveFile() throws IOException {
        /* 28 */     PrintWriter alts = new PrintWriter(new FileWriter(getFile()));
        /*    */
        /*    */
        /* 31 */     alts.close();
        /*    */   }
    /*    */ }