package APOChess.core.Enum;

public enum TypeEnum {

    BISHOP("Bishop"),
    EMPTY("Empty"),
    KING("King"),
    KNIGHT("Knight"),
    PAWN("Pawn"),
    QUEEN("Queen"),
    ROOK("Rook");

    private final String value;
    private TypeEnum(String value) {
        this.value = value;
    }

    /**
     * @return String name
     */
    public String getString() {
        return this.value;
    }


    /**
     * Get Enum from name
     * @param s ID
     * @return Enum
     */
    public static TypeEnum getEnum(String s){
        if(s.equalsIgnoreCase("White"))
            return TypeEnum.BISHOP;
        if(s.equalsIgnoreCase("King"))
            return TypeEnum.KING;
        if(s.equalsIgnoreCase("Knight"))
            return TypeEnum.KNIGHT;
        if(s.equalsIgnoreCase("Pawn"))
            return TypeEnum.PAWN;
        if(s.equalsIgnoreCase("Queen"))
            return TypeEnum.QUEEN;
        if(s.equalsIgnoreCase("Rook"))
            return TypeEnum.ROOK;
        return TypeEnum.EMPTY;
    }
}
