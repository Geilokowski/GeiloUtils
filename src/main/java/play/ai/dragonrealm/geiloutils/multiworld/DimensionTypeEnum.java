package play.ai.dragonrealm.geiloutils.multiworld;

public enum DimensionTypeEnum {
    VOID ("VOID"),
    OVERWORLD ("OVERWORLD"),
    MININGWORLD ("MININGWORLD");

    private final String name;

    DimensionTypeEnum(String dimensionType) {
        name = dimensionType;
    }

    public boolean equalsDimensionType(String dimensionType){
        return name.equals(dimensionType);
    }

    public String toString(){
        return name;
    }
}
