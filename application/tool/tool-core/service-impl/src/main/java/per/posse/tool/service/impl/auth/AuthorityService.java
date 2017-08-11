package per.posse.tool.service.impl.auth;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.posse.tool.dto.ToolUserDto;
import per.posse.tool.enums.status.ApiAuthorizationStatus;
import per.posse.tool.service.auth.IAuthorityService;
import per.posse.tool.session.Constant.CacheKeyFormatConstant;
import per.posse.tool.session.SessionCacheMode;
import per.posse.tool.session.SessionInfo;
import per.posse.tool.session.ToolUserSessionInfo;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by posse on 17-8-10.
 */
@Service
public class AuthorityService implements IAuthorityService {

    private static final String CACHE_MODE_KEY = "cache-mode";

    /**
     * If can't connect to redis server, open local session cache mode
     */
    private static final ConcurrentMap<String, SessionInfo> LOCAL_SESSION_CACHE = Maps.newConcurrentMap();

    /**
     * session global attributes
     * <p>
     *     default open redis session cache mode
     * </p>
     * @param session
     */

    private static final ConcurrentMap<String, Object> BUCKETS = Maps.newConcurrentMap();
    static {
        BUCKETS.put(CACHE_MODE_KEY, SessionCacheMode.REDIS);
    }


    @Autowired
    @Qualifier("redisSessionSerializationTemplate")
    private RedisTemplate<String, SessionInfo> sessionTemplate;
    @Autowired
    @Qualifier("redisStringTemplate")
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void updateSession(SessionInfo session) {

        session.setCreatedAt(System.currentTimeMillis());
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            LOCAL_SESSION_CACHE.put(session.getKey(), session);
            return;
        }

        try {
            sessionTemplate.opsForValue().set(session.getKey(), session);
            sessionTemplate.expire(session.getKey(), session.getExpiryTime(), TimeUnit.SECONDS);
        } catch (Exception e) {
            BUCKETS.put(CACHE_MODE_KEY, SessionCacheMode.LOCAL);
            LOCAL_SESSION_CACHE.put(session.getKey(), session);
        }
    }

    @Override
    public void extendSessionExpiryTime(String sessionId, Long userId) {
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            return;
        }

        try {
            SessionInfo sessionInfo = getSessionInfo(sessionId);
            if (sessionInfo == null) {
                return;
            }
            sessionTemplate.expire(sessionId, sessionInfo.getExpiryTime(), TimeUnit.SECONDS);
            if (userId != null) {
                redisTemplate.expire(CacheKeyFormatConstant.USER_LOGOUT_STATUS_KEY + userId,
                        sessionInfo.getExpiryTime(), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ApiAuthorizationStatus authority(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return ApiAuthorizationStatus.BLANK_TOKEN;
        }

        SessionInfo sessionInfo = getSessionInfo(sessionId);
        if (sessionInfo == null) {
            return ApiAuthorizationStatus.TOKEN_NOT_FOUND;
        }

        if (sessionInfo instanceof ToolUserSessionInfo) {
            ToolUserDto user = ((ToolUserSessionInfo) sessionInfo).getUserInfo();
            if (user != null && getLogoutStatus(sessionId, user.getId())) {
                return ApiAuthorizationStatus.LOGIN_IN_ANOTHER_PLACE;
            }
        }

        return ApiAuthorizationStatus.SUCCESS;
    }

    @Override
    public SessionInfo getSessionInfo(String sessionId) {
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            return LOCAL_SESSION_CACHE.get(sessionId);
        }

        SessionInfo sessionInfo = null;
        try {
            sessionInfo = sessionTemplate.opsForValue().get(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionInfo;
    }

    @Override
    public void removeSession(String sessionId) {
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            LOCAL_SESSION_CACHE.remove(sessionId);
            return;
        }

        try {
            sessionTemplate.delete(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SessionCacheMode getCacheMode() {
        return (SessionCacheMode) BUCKETS.get(CACHE_MODE_KEY);
    }

    @Override
    public void changeCacheMode(SessionCacheMode cacheMode) {
        BUCKETS.put(CACHE_MODE_KEY, cacheMode);
    }

    @Override
    public void removeUserSession(Long userId, SessionInfo newSession) {
        if (userId == null) {
            return;
        }

        ToolUserDto userInfo = null;
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            Set<String> keys = LOCAL_SESSION_CACHE.keySet();
            for (String key : keys) {
                SessionInfo session = LOCAL_SESSION_CACHE.get(key);
                if (session != null && session instanceof ToolUserSessionInfo
                        && (userInfo = ((ToolUserSessionInfo) session).getUserInfo()) != null
                        && userId.equals(userInfo.getId())) {
                    userInfo.setLogout(true);
                }
            }
            return;
        }

        String key = CacheKeyFormatConstant.USER_LOGOUT_STATUS_KEY + userId;
        try {
            redisTemplate.opsForValue().set(key, newSession.getKey());
            redisTemplate.expire(key, newSession.getExpiryTime(), TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean getLogoutStatus(String sessionId, Long userId) {
        if (getCacheMode() == SessionCacheMode.LOCAL) {
            SessionInfo session = LOCAL_SESSION_CACHE.get(sessionId);
            if (session != null && session instanceof ToolUserSessionInfo
                    && ((ToolUserSessionInfo) session).getUserInfo() != null) {
                return ((ToolUserSessionInfo) session).getUserInfo().getLogout();
            }
            return Boolean.FALSE;
        }

        String loginSession = redisTemplate.opsForValue().get(CacheKeyFormatConstant.USER_LOGOUT_STATUS_KEY + userId);

        return !sessionId.equals(loginSession);
    }
}
