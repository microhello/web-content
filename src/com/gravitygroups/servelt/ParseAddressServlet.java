package com.gravitygroups.servelt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.htmlparser.util.ParserException;

import com.gravitygroups.webcontent.WebContentParser;

/**
 * Servlet implementation class ParseAddressServlet
 */
public class ParseAddressServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String url = request.getParameter( "url" );
		String addressPrefix = "";
		try
		{
			addressPrefix =	request.getParameter( "addressPrefix" );
		}
		catch ( NullPointerException e )
		{
		}
		
		try
		{
			List<String> addressList = new ArrayList<String>();
			if ( addressPrefix.length() > 0 )
				addressList = WebContentParser.parseAddressList( url, addressPrefix );
			else
				addressList = WebContentParser.parseAddressList( url );
			session.setAttribute( "addressList", addressList );
			request.getRequestDispatcher( "address.jsp" ).forward( request, response );
		}
		catch ( ParserException e )
		{
			e.printStackTrace();
		}
	}
}
