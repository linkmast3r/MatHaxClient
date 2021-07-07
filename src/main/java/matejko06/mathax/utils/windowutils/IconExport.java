package matejko06.mathax.utils.windowutils;

import matejko06.mathax.MatHaxClient;

import java.io.*;

public class IconExport {

    private static final File ICONS_FOLDER = new File(MatHaxClient.MCCONFIG_FOLDER, "Icons/Window");

    public static void reset() {
        File[] files = ICONS_FOLDER.exists() ? ICONS_FOLDER.listFiles() : new File[0];
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
                    file.delete();
                }
            }
        }
    }

    public static void init() {
        File[] files = ICONS_FOLDER.exists() ? ICONS_FOLDER.listFiles() : new File[0];
        File iconFile = null;
        File iconFile2 = null;
        File iconFile3 = null;
        File iconFile4 = null;
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
                    iconFile = file;
                    iconFile2 = file;
                    iconFile3 = file;
                    iconFile4 = file;
                    break;
                }
            }
        }

        if (iconFile == null) {
            try {
                iconFile = new File(ICONS_FOLDER, "icon16.png");
                iconFile.getParentFile().mkdirs();

                InputStream in = MatHaxClient.class.getResourceAsStream("/assets/mathax/textures/icons/window/icon16.png");
                OutputStream out = new FileOutputStream(iconFile);

                byte[] bytes = new byte[255];
                int read;
                while ((read = in.read(bytes)) > 0) out.write(bytes, 0, read);

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (iconFile2 == null) {
            try {

                iconFile2 = new File(ICONS_FOLDER, "icon32.png");
                iconFile2.getParentFile().mkdirs();

                InputStream in = MatHaxClient.class.getResourceAsStream("/assets/mathax/textures/icons/window/icon32.png");
                OutputStream out = new FileOutputStream(iconFile2);

                byte[] bytes = new byte[255];
                int read;
                while ((read = in.read(bytes)) > 0) out.write(bytes, 0, read);

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (iconFile3 == null) {
            try {

                iconFile3 = new File(ICONS_FOLDER, "icon64.png");
                iconFile3.getParentFile().mkdirs();

                InputStream in = MatHaxClient.class.getResourceAsStream("/assets/mathax/textures/icons/window/icon64.png");
                OutputStream out = new FileOutputStream(iconFile3);

                byte[] bytes = new byte[255];
                int read;
                while ((read = in.read(bytes)) > 0) out.write(bytes, 0, read);

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (iconFile4 == null) {
            try {

                iconFile4 = new File(ICONS_FOLDER, "icon128.png");
                iconFile4.getParentFile().mkdirs();

                InputStream in = MatHaxClient.class.getResourceAsStream("/assets/mathax/textures/icons/window/icon128.png");
                OutputStream out = new FileOutputStream(iconFile4);

                byte[] bytes = new byte[255];
                int read;
                while ((read = in.read(bytes)) > 0) out.write(bytes, 0, read);

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
