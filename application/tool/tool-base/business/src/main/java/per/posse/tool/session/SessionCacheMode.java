package per.posse.tool.session;

public enum SessionCacheMode {

    LOCAL(1), //
    REDIS(2);

    private Integer value;

    SessionCacheMode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static SessionCacheMode fromValue(Integer value) {
        for (SessionCacheMode mode : values()) {
            if (mode.getValue() == value) {
                return mode;
            }
        }
        return null;
    }
}
