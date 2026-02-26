package pl.morawski.lego_store.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.morawski.lego_store.service.RequestCounterService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestCounterFilter extends OncePerRequestFilter {

    private final RequestCounterService counterService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        counterService.increment();
        filterChain.doFilter(request, response);
    }
}
