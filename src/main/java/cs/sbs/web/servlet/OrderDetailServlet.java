package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/order/*")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取Path Parameter: 订单ID
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            out.println("Error: Order ID is required in the URL (e.g., /order/1001)");
            return;
        }

        String orderId = path.substring(1); // 去掉开头的/

        // 查找订单
        Optional<Order> orderOpt = OrderCreateServlet.orderList.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            out.println("Order Detail");
            out.println("Order ID: " + order.getOrderId());
            out.println("Customer: " + order.getCustomer());
            out.println("Food: " + order.getFood());
            out.println("Quantity: " + order.getQuantity());
        } else {
            out.println("Error: Order with ID " + orderId + " not found.");
        }
    }
}
