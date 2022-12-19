package ink.markidea.note.context.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpServletRequest request = (HttpServletRequest)req;
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        //这里通过判断请求的方法，判断此次是否是预检请求
        //如果是，立即返回一个204状态码标示，允许跨域；预检后，正式请求，这个方法参数就是我们设置的post
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            //HttpStatus.SC_NO_CONTENT = 204
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return ;
        }
        filterChain.doFilter(req, resp);
    }
}
