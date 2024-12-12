package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BorrarSociosServlet", value = "/BorrarSociosServlet")
public class BorrarSociosServlet extends HttpServlet {

    private SocioDAO socioDAO = new SocioDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = null;
        Integer codigo = null;

        try {
            codigo = Integer.parseInt(request.getParameter("codigo"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (codigo != null){
            socioDAO.delete(codigo);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
            List<Socio> listado = this.socioDAO.getAll();
            request.setAttribute("listado", listado);
            dispatcher.forward(request, response);

        }else{
            response.sendRedirect("ListarSociosServlet?err-cod=1");
        }

    }
}
