package com.richstonedt.garnet.common.handler;

import com.google.gson.Gson;
import com.richstonedt.garnet.common.utils.Result;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b><code>GarnetExceptionHandler</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 18:10
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Component
public class GarnetExceptionHandler implements HandlerExceptionResolver {

    /**
     * The Constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(GarnetExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        if (ex instanceof UnauthorizedException) {
            try {
                String json = new Gson().toJson(Result.error(HttpStatus.SC_FORBIDDEN, "no permission"));
                response.getWriter().print(json);
            } catch (Throwable t) {
                LOG.error("异常",t);
            }
        }
       LOG.error("Exceptin",ex);
        return new ModelAndView();
    }
}
