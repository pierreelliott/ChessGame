package APOChess.core.Enum;

public enum ColorEnum {

    WHITE("White"), BLACK("Black"), EMPTY("");

    private final String value;
    private ColorEnum(String value) {
        this.value = value;
    }

    /**
     * @return ID de l'Enum
     */
    public String getString() {
        return this.value;
    }


    /**
     * Retourne l'Enum correspondant a l'ID
     * @param s ID
     * @return Enum
     */
    public static ColorEnum getEnum(String s){
        if(s.equalsIgnoreCase("White"))
            return ColorEnum.WHITE;
        if(s.equalsIgnoreCase("Black"))
            return ColorEnum.BLACK;
        return ColorEnum.EMPTY;
    }
}
