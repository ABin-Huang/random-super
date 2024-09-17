package cn.hb.enums;

/**
 * The enum Type enum.
 *
 * @Author: abin
 * @Date: 2024 /9/17 20:31
 * @Description: 类型枚举
 */
public enum TypeEnum {
    /**
     * Integer type enum.
     */
    INTEGER("int", "integer"),
    /**
     * String type enum.
     */
    STRING("string", "string"),

    /**
     * Id type enum.
     */
    ID("id", "id");

    TypeEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }

    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static TypeEnum ofType(String type) throws Exception {
        TypeEnum[] values = TypeEnum.values();
        for (TypeEnum typeEnum : values) {
            if (typeEnum.getName().equalsIgnoreCase(type)) {
                return typeEnum;
            }
        }
        throw new Exception("Plugin Exception");
    }
}
