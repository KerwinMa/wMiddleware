//package com.witbooking.middleware.filters;
//
///**
// * Created by mongoose on 12/15/14.
// */
//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
//
//public class CORSFilter implements Filter {
//
//    public CORSFilter() { }
//
//    public void init(FilterConfig fConfig) throws ServletException { }
//
//    public void destroy() {	}
//
//    public void doFilter(
//            ServletRequest request, ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
//
//        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
//        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Credentials", "true");
//        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
//        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
//        chain.doFilter(request, response);
//    }
//}