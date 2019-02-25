package play.ai.dragonrealm.geiloutils.new_configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.discord.DiscordConfig;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Optional;

public class JsonManager {

    private static File configLocation = new File(Loader.instance().getConfigDir() + File.separator + GeiloUtils.MODID);
    private static Gson json = new GsonBuilder().setPrettyPrinting().create();

    public static DiscordConfig discordConfig;


    public static void initializeConfigs(){

        if(!configLocation.exists()) {
            configLocation.mkdirs();
        }

        readFilesToRuntime();
    }


    public static void writeToFiles() {
        Field[] fields;

        try {
            fields = JsonManager.class.getDeclaredFields();
        }catch (SecurityException e) {
            GeiloUtils.getLogger().fatal("Unable to reflect config files. Aborting load!");
            return;
        }

        for(Field field : fields){
            try {
                if (IJsonFile.class.isAssignableFrom(field.getType())) {
                    IJsonFile file = (IJsonFile) field.getType().newInstance();
                    File configFile = getFileFromString(file.getFileName());
                    writeFile(configFile, file);

                }
            } catch (IllegalAccessException | InstantiationException e){
                GeiloUtils.getLogger().fatal("Unable to load field reflection for: " + field.toString());
            }
        }
    }

    public static void readFilesToRuntime(){
        Field[] fields;

        try {
            fields = JsonManager.class.getDeclaredFields();
        }catch (SecurityException e) {
            GeiloUtils.getLogger().fatal("Unable to reflect config files. Aborting load!");
            return;
        }

        for(Field field : fields){
            try {
                if (IJsonFile.class.isAssignableFrom(field.getType())) {
                    IJsonFile file = (IJsonFile) field.getType().newInstance();
                    File configFile = getFileFromString(file.getFileName());

                    if(configFile.createNewFile() || configFile.length() == 0) {
                        writeFile(configFile, file.getDefaultJson());
                        field.set(null, file.getDefaultJson());
                    } else {
                        IJsonFile loadFile = loadFile(configFile, file.getClass());
                        field.set(null, loadFile);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | IOException e){
                GeiloUtils.getLogger().fatal("Unable to load field reflection for: " + field.toString());
            }
        }
    }



    private static File getFileFromString(String fileName){
        return new File(configLocation + File.separator + "new_" + fileName);
    }


    /**
     * Takes in a string path and a class type. Uses GSON to read the path type,
     * and returns the model, if it exists. Null otherwise.
     *
     * @param file Files you want to read from.
     * @param type the class type for GSON to reflect
     * @param <T> Type for GSON to load from
     * @return Object of type T, or null on any IOException
     */
    public static <T extends IJsonFile> T loadFile(File file, Class<T> type) {
        Reader reader;

        try {
            reader = new FileReader(file);
            T objInstance = json.fromJson(reader, type);
            reader.close();
            return objInstance;
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * Writes a file to the disk.
     * @param file the file that's to be written.
     * @param object the IJsonData object to write to the file.
     */
    public static void writeFile(File file, IJsonFile object) {
        String fileText = json.toJson(object, object.getClass());
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(fileText);
        } catch (IOException e) {

        }
    }
}
