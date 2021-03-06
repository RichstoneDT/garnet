package com.richstonedt.garnet.interceptory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.richstonedt.garnet.model.Token;
import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.TokenCriteria;
import com.richstonedt.garnet.service.RouterGroupService;
import com.richstonedt.garnet.service.TokenService;
import com.richstonedt.garnet.service.UserCredentialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;


public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    private static final String LOGIN_STATUS_FALSE = "false";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RouterGroupService routerGroupService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //如果是映射到方法，验证登录
        /*if (handler instanceof HandlerMethod) {
            System.out.println("request url: " + request.getRequestURL());
            System.out.println("request params: " + request.getParameterMap());
            return true;
        } else{
            System.out.println("logInterceptor false...");
            return false;
        }*/
        return false;
    }

    /**
     * 检查Token的有效性
     * @param tokenWithAppCode
     * @param request
     * @param response
     * @return*/
    private boolean checkToken(String tokenWithAppCode, HttpServletRequest request, HttpServletResponse response) {
        //如果token存在
        if(!StringUtils.isEmpty(tokenWithAppCode) && !"null".equals(tokenWithAppCode)){
            Map<String, Claim> tokenParams = null;
            //从前端传来的token中获取真正的token。关键步骤！！！
            String[] tokenParams1 = tokenWithAppCode.split("#");
            //真正的token
            String token = tokenParams1[0];

            //从token中获取userName
            String userName = JWT.decode(token).getAudience().get(0);
            //通过userName获取密码
            UserCredential userCredential = userCredentialService.getCredentialByUserName(userName);
            if (ObjectUtils.isEmpty(userCredential)) {
                return userNotExist(request, response);
            }

            //根据password解密
            String password = userCredential.getCredential();
            try {
                tokenParams = JwtToken.verifyToken(token, password);
            } catch (Exception e) {
                //无法解密，token不正确
                return tokenError(request, response);
            }

            //根据userName从数据库中拿出token
            TokenCriteria tokenCriteria = new TokenCriteria();
            tokenCriteria.createCriteria().andUserNameEqualTo(userName);
            Token token1 = tokenService.selectSingleByCriteria(tokenCriteria);
            if (ObjectUtils.isEmpty(token1)) {
                return haveNotToken(request, response);
            }

            //将token和数据库中的token对比
            String tokenFromDB = token1.getToken();
            if (!token.equals(tokenFromDB)) {
                return tokenError(request, response);
            }

            //验证token是否过期
            Long expiredTime = token1.getExpireTime();
            if (System.currentTimeMillis() > expiredTime) {
                return tokenExpired(request, response);
            }

            return true;
        } else{
            //token为空
            return haveNotToken(request, response);
        }
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) {return annotation;}
        return handler.getMethodAnnotation(annotationType);
    }

    /**
     * 以JSON格式输出
     * @param response*/
    protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private boolean haveNotToken(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("请先登录");
        loginMessage.setLoginStatus(LOGIN_STATUS_FALSE);
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
        return false;
    }

    private boolean tokenExpired(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("登录失效，请重新登录");
        loginMessage.setLoginStatus(LOGIN_STATUS_FALSE);
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
        return false;
    }

    private boolean tokenError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("TOKEN验证错误，请重新登录或确保您有权限进行此操作");
        loginMessage.setLoginStatus(LOGIN_STATUS_FALSE);
        loginMessage.setCode(403);
        responseOutWithJson(response, loginMessage);
        return false;
    }

    private boolean userNotExist(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("用户不存在，请重新登录");
        loginMessage.setLoginStatus(LOGIN_STATUS_FALSE);
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
        return false;
    }

}
