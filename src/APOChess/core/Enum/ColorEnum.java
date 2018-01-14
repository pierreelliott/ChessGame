package APOChess.core.Enum;

public enum ColorEnum {

    WHITE("White"), BLACK("Black"), EMPTY("");

    private final String value;
    private ColorEnum(String value) {
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
    public static ColorEnum getEnum(String s){
        if(s.equalsIgnoreCase("White"))
            return ColorEnum.WHITE;
        if(s.equalsIgnoreCase("Black"))
            return ColorEnum.BLACK;
        return ColorEnum.EMPTY;
    }
}
