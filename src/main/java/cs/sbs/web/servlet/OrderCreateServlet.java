package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/order")
public class OrderCreateServlet extends HttpServlet {
    // 模拟订单存储
    public static final List<Order> orderList = new ArrayList<>();
    // 订单ID自增器
    private static final AtomicInteger orderIdGenerator = new AtomicInteger(1001);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取Form Parameter
        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String quantityStr = request.getParameter("quantity");

        // 异常处理
        if (customer == null || customer.isBlank() || food == null || food.isBlank() || quantityStr == null || quantityStr.isBlank()) {
            out.println("Error: All parameters (customer, food, quantity) are required.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                out.println("Error: quantity must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: quantity must be a valid number");
            return;
        }

        // 生成订单ID并保存订单
        String orderId = String.valueOf(orderIdGenerator.getAndIncrement());
        Order newOrder = new Order(orderId, customer, food, quantity);
        orderList.add(newOrder);

        // 返回成功结果
        out.println("Order Created: " + orderId);
    }
}