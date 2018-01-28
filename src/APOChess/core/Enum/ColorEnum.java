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

    
    public static ColorEnum getOpposite(ColorEnum color) {
        if(color.equals(ColorEnum.WHITE))
            return ColorEnum.BLACK;
        if(color.equals(ColorEnum.BLACK))
            return ColorEnum.WHITE;

        return ColorEnum.EMPTY;
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
