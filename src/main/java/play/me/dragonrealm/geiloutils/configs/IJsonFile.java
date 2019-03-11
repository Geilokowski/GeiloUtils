package play.me.dragonrealm.geiloutils.configs;

import javax.annotation.Nonnull;

public interface IJsonFile {

    /**
     * The name of this file as a json.
     * @return all lower case name of files, strictly in format: *.json
     */
    @Nonnull
    public String getFileName();


    @Nonnull
    public IJsonFile getDefaultJson();


}
