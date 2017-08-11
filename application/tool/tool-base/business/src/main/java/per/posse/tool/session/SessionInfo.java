package per.posse.tool.session;

import java.io.Serializable;

public abstract class SessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    // timestamp
    private Long createdAt;
    // unit: second
    private Long expiryTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{key: ").append(key).append(", createdAt: ").append(createdAt)
                .append(", expiryTime: ").append(expiryTime).append("}").toString();
    }
}
