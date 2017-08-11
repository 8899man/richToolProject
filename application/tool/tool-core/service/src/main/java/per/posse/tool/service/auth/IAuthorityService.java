package per.posse.tool.service.auth;

import per.posse.tool.enums.status.ApiAuthorizationStatus;
import per.posse.tool.session.SessionCacheMode;
import per.posse.tool.session.SessionInfo;

/**
 * Created by posse on 17-8-10.
 */
public interface IAuthorityService {

    void updateSession(SessionInfo session);

    void extendSessionExpiryTime(String sessionId, Long userId);

    ApiAuthorizationStatus authority(String sessionId);

    SessionInfo getSessionInfo(String sessionId);

    void removeSession(String sessionId);

    SessionCacheMode getCacheMode();

    void changeCacheMode(SessionCacheMode cacheMode);

    void removeUserSession(Long userId, SessionInfo session);

    Boolean getLogoutStatus(String sessionId, Long userId);
}
