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
import java.util.Optional;

@WebServlet(name = "EditarSociosServlet", value = "/EditarSociosServlet")
public class EditarSociosServlet extends HttpServlet {

    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");

        Optional<Socio> socio = socioDAO.find(Integer.parseInt(req.getParameter("codigo")));
        if (socio.isPresent()) {
            req.setAttribute("socio", socio.get());
        } else {
            res.sendRedirect("ListarSociosServlet?err-cod=2");
        }

        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;

        Integer id = null;
        String nombre = null;
        Integer estatura = null;
        Integer edad = null;
        String localidad = null;

        try{
            id = Integer.parseInt(req.getParameter("id"));
            nombre = req.getParameter("nombre");
            estatura = Integer.parseInt(req.getParameter("estatura"));
            edad = Integer.parseInt(req.getParameter("edad"));
            localidad = req.getParameter("localidad");
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        if (id != null){
            socioDAO.update(new Socio(id, nombre, estatura, edad, localidad));
            dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
            List<Socio> listado = this.socioDAO.getAll();
            req.setAttribute("listado", listado);
            dispatcher.forward(req, res);
        }else{
            res.sendRedirect("ListarSociosServlet?error-code=1");
        }

    }
}
