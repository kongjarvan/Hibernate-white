package site.metacoding.white.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<HelloFilter> jwtAuthenticationFilterRegister() { // 서버 실행시 IoC 등록
		log.debug("디버그 : 인증필터 등록");
		FilterRegistrationBean<HelloFilter> bean = new FilterRegistrationBean<>(new HelloFilter());
		bean.addUrlPatterns("/hello");
		return bean;
	}
}

@Slf4j
class HelloFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException { // 주소 요청시
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (req.getMethod().equals("POST")) {
			log.debug("디버그 : HelloFilter 실행됨");
		} else {
			log.debug("디버그 : POST 요청이 아니어서 실행 할 수 없습니다.");
		}

		// chain.doFilter(req, resp); // 필터 체인 전부 실행된 후 컨트롤러 실행
	}

}
