package be.ninedocteur.apare.api.mod;

public abstract class ApareMod {
    private String modName;
    private String modVersion;

    public ApareMod(String modName, String modVersion){
        this.modName = modName;
        this.modVersion = modVersion;
    }

    public abstract void loadMod();

    public String getModName() {
        return modName;
    }

    public String getModVersion() {
        return modVersion;
    }
}
