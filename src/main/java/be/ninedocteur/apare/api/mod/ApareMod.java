package be.ninedocteur.apare.api.mod;

import be.ninedocteur.apare.ApareAPI;

public abstract class ApareMod {
    private String modName;
    private String modId;
    private String modVersion;
    private String modAuthor;
    private String modCredits;
    private String modDescription;

    public ApareMod(String modName, String modId, String modVersion, String modAuthor, String modCredits, String modDescription){
        this.modName = modName;
        this.modId = modId;
        this.modVersion = modVersion;
        this.modAuthor = modAuthor;
        this.modCredits = modCredits;
        this.modDescription = modDescription;
    }

    public abstract void init();

    public String getModName() {
        return modName;
    }

    public String getModVersion() {
        return modVersion;
    }
}
