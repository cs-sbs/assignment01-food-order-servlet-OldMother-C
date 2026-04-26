package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/menu")
public class MenuListServlet extends HttpServlet {
    // 模拟菜单数据
    private static final List<MenuItem> menu = new ArrayList<>();

    static {
        menu.add(new MenuItem("Fried Rice", 8));
        menu.add(new MenuItem("Fried Noodles", 9));
        menu.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nameParam = request.getParameter("name");
        List<MenuItem> filteredMenu;

        // 1. 如果有搜索关键词，过滤
        if (nameParam != null && !nameParam.trim().isEmpty()) {
            filteredMenu = menu.stream()
                    .filter(item -> item.getName().toLowerCase().contains(nameParam.trim().toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            // 2. 没有关键词，返回全部
            filteredMenu = menu;
        }

        // 3. 【关键！】如果过滤后为空，返回提示（测试需要！）
        if (filteredMenu.isEmpty()) {
            out.println("No menu items found");
        } else {
            out.println("Menu List:");
            for (int i = 0; i < filteredMenu.size(); i++) {
                out.println((i + 1) + ". " + filteredMenu.get(i));
            }
        }
    }
}