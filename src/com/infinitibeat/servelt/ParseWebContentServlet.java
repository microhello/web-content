package com.infinitibeat.servelt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.htmlparser.util.ParserException;

import com.infinitibeat.webcontent.BlockProperties;
import com.infinitibeat.webcontent.WebContentParser;

/**
 * Servlet implementation class ParseWebContentServlet
 */
public class ParseWebContentServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		try
		{
			BlockProperties contentProp = WebContentParser.parseWebContent( request.getParameter( "url" ) );
			session.setAttribute( "webContent", contentProp );
			request.getRequestDispatcher( "content.jsp" ).forward( request, response );
		}
		catch ( ParserException e )
		{
			// TODO: error handling!!
			e.printStackTrace();
		}
		catch ( NullPointerException e )
		{
			session.setAttribute( "webContent", null );
			request.getRequestDispatcher( "content.jsp" ).forward( request, response );
		}
	}

}
