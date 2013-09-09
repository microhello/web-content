/**
 * Copyright (c) 2011 Eric Wei @ Gravity Groups Co.,Ltd.
 * All rights reserved.
 */
package com.infinitibeat.webcontent;

import java.io.UnsupportedEncodingException;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.infinitibeat.crawler.HtmlTag;
import com.infinitibeat.crawler.WebCrawler;

/**
 * 
 * @author 白昌永, Eric Wei @ Gravity Groups Co.,Ltd.
 * @version
 */
public class VIPS extends WebCrawler
{
	/**
	 * 要預先過濾掉的無用標籤。
	 */
	public static final NodeFilter[] TRIM_FILTER = { 
		new NodeClassFilter( HeadTag.class ),
		new NodeClassFilter( StyleTag.class ),
		new NodeClassFilter( ScriptTag.class ) };
	
//	
//	based on HTML 4.01 specification, we classify the node into two categories
//	
	private static final String INLINE_NODE = "Inline_Node";
	private static final String LINE_BREAK_NODE = "Line_Break_Node";
	
//	
//	based on the appearance of the node on the browser and
//	the children properties of the node
//	
	private static final String VALID_NODE = "Valid_Node";
	private static final String INVALID_NODE = "Invalid_Node";
	private static final String TEXT_NODE = "Text_Node";
	private static final String VIRTUAL_TEXT_NODE = "Virtual_Text_Node";
	private static final String END_NODE = "End_Node"; // end tag
	
	public static NodeList findLinkBlock( String url )
	{
		NodeList linkRegionNodeList = new NodeList();
		
		return linkRegionNodeList;
	}
	
	public static void blockExtract( Node pNode )
	{
		
	}
	
	public static boolean isDividable( Node pNode )
	{
		boolean dividable = true;
		String nodeProperties = _getNodeProperties( pNode );
		NodeList cNodeList = pNode.getChildren();
		
//		Rule1
		if ( !nodeProperties.equals( TEXT_NODE ) &&
				cNodeList == null )
			dividable = false;
		
//		Rule2
		if ( cNodeList.size() == 1 && !_getNodeProperties( cNodeList.elementAt( 0 ) ).equals( TEXT_NODE ) )
			dividable = true;
		
//		Rule3
		
//		Rule4
		if ( cNodeList != null )
		{
			for ( int i = 0; i < cNodeList.size(); i++ )
			{
				Node cNode = cNodeList.elementAt( i );
				String cNodeProperties = _getNodeProperties( cNode );
				if ( !( cNodeProperties.equals( TEXT_NODE ) || cNodeProperties.equals( VIRTUAL_TEXT_NODE ) ) )
				{
					dividable = false;
					break;
				}
			}
			dividable = true;
		}
		
//		Rule 5
		if ( cNodeList != null )
		{
			for ( int i = 0; i < cNodeList.size(); i++ )
			{
				Node cNode = cNodeList.elementAt( i );
				String cNodeCategory = getNodeCategory( cNode );
				if ( cNodeCategory.equals( LINE_BREAK_NODE ) )
				{
					dividable = true;
					break;
				}
			}
			dividable = false;
		}
		
		
		return dividable;
	}
	
	public static boolean rule1( Node pNode )
	{
		return true;
	}
	
	public static String getNodeCategory( Node node )
	{
		String nodeHtmlText = node.getText().toLowerCase().trim();
		
		if ( HtmlTag.INLINE_TAG_LIST.contains( nodeHtmlText.split( "[ ]+" )[ 0 ] ) )
			return INLINE_NODE;
		else
			return LINE_BREAK_NODE;
	}
	
	public static String getNodeProperties( Node node )
	{
		if ( node.getClass().getSimpleName().equals( TagNode.class.getSimpleName() ) &&
				( ( (TagNode)node ).isEndTag() == true ) )
				return END_NODE;
		else if ( isTextNode( node ) )
			return TEXT_NODE;
		else if ( isVirtualTextNode( node ) )
			return VIRTUAL_TEXT_NODE;
		else if ( isValidNode( node ) )
			return VALID_NODE;
		else
			return INVALID_NODE;
		
	}
	
	/**
	 * @deprecated 判斷Virtual Text Node錯誤。
	 * @param node
	 * @return
	 */
	public static String _getNodeProperties( Node node )
	{
		String nodeName = node.getClass().getSimpleName();
		if ( nodeName.equals( TextNode.class.getSimpleName() ) &&
				( node.getParent() != null && !getNodeCategory( node.getParent() ).equals( INLINE_NODE ) ) )
			return TEXT_NODE;
		else if ( nodeName.equals( TagNode.class.getSimpleName() ) )
		{
			TagNode tag = (TagNode)node;
			if ( tag.isEndTag() )
				return END_NODE;
		}
		
		String nodeCategory = getNodeCategory( node );
		if ( nodeCategory.equals( INLINE_NODE ) )
		{
			NodeList childrenNodeList = node.getChildren();
			if ( childrenNodeList == null )
				return VALID_NODE;
			else
			{
				for ( int i = 0; i < childrenNodeList.size(); i++ )
				{
					Node childNode = childrenNodeList.elementAt( i );
					String childNodeName = childNode.getClass().getSimpleName();
					String childNodeCategory = getNodeCategory( childNode ); 
					if ( !childNodeName.equals( TextNode.class.getSimpleName() ) &&
							( (TagNode)childNode ).isEndTag() == false &&
							!childNodeCategory.equals( INLINE_NODE ) )
						return VALID_NODE;
					
					// base case
					if ( childNodeName.equals( TextNode.class.getSimpleName() ) ||
							( (TagNode)childNode ).isEndTag() == true )
						continue;
					// recursive step
					else if ( childNodeCategory.equals( INLINE_NODE ) )
					{
						String childNodeProperties = _getNodeProperties( childNode );
						if ( childNodeProperties.equals( TEXT_NODE ) ||
								childNodeProperties.equals( VALID_NODE ) )
							return VALID_NODE;
					}
				}
				return VIRTUAL_TEXT_NODE;
			}
		}
		else
		{
			if ( nodeName.equals( ScriptTag.class.getSimpleName() ) ||
					nodeName.equals( StyleTag.class.getSimpleName() ) )
				return INVALID_NODE;
			else
				return VALID_NODE;
		}
	}
	
	public static boolean isValidNode( Node node )
	{
		String nodeName = node.getClass().getSimpleName();
		if ( nodeName.equals( ScriptTag.class.getSimpleName() ) ||
				nodeName.equals( StyleTag.class.getSimpleName() ) ||
				nodeName.equals( RemarkNode.class.getSimpleName() ) ||
				nodeName.equals( DoctypeTag.class.getSimpleName() ) ||
				nodeName.equals( MetaTag.class.getSimpleName() ) ||
				nodeName.equals( HeadTag.class.getSimpleName() )  )
			return false;
		else
			return true;
	}
	
	public static boolean isTextNode( Node node )
	{
		if ( node.getClass().getSimpleName().equals( TextNode.class.getSimpleName() ) )
			return true;
		else
			return false;
	}
	
	public static boolean isVirtualTextNode( Node node )
	{
		boolean containText = false;
		if ( !getNodeCategory( node ).equals( INLINE_NODE ) || 
				node.getChildren() == null )
			return false;
		else
		{
			NodeList childrenNodeList = node.getChildren();
			for ( int i = 0; i < childrenNodeList.size(); i++ )
			{
				Node childNode = childrenNodeList.elementAt( i );
				if ( childNode.getClass().getSimpleName().equals( TagNode.class ) )
				{
					if ( ( (TagNode)childNode ).isEndTag() == true )
						continue;
					else
						return false;
				}
				
				if ( isTextNode( childNode ) )
					containText = true;
				else
				{
					if ( getNodeCategory( childNode ).equals( INLINE_NODE ) ) 
					{
						if ( !isVirtualTextNode( childNode ) )
							return false;
						else
							containText = true;
					}
					else
						return false;
				}
			}
		}
		return containText;
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		String url =
				// "http://tku-vois.blogspot.com/2010/10/2010.html";
			"http://gravity-groups.us.to/test.html";
		// "http://www.cw.com.tw/blog/blogTopic.action?id=181&nid=2347";
		// "http://blog.xuite.net/bibitsai/bibi/35951126-%25E8%258F%2581%25E6%25A1%2590%25E5%258D%2581%25E5%2588%2586%25E6%2590%25AD%25E7%2581%25AB%25E8%25BB%258A%25E8%25B6%25B4%25E8%25B6%25B4%25E8%25B5%25B0%25E4%25B9%258B%25E6%2597%2585%25E7%25AC%25AC%25E4%25B8%2580%25E7%25AB%2599%25EF%25BC%258C%25E8%258F%2581%25E6%25A1%2590%25E8%2580%2581%25E8%25A1%2597%25EF%25BC%258C%25E5%258D%2588%25E9%25A4%2590%40%25E6%25A5%258A%25E5%25AE%25B6%25E9%259B%259E%25E6%258D%25B2";
		// "http://www.wretch.cc/blog/xalekd/4606692";
		// "http://www.wretch.cc/blog/avav6004/30663865";
		// "http://www.hksilicon.com/kb/articles/85863/12"
		// "http://www.cool3c.com/article/62221"
		// "http://mrjamie.cc/2012/08/30/startup-poker/?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+MrJamie+%28MR+JAMIE%29&utm_content=FaceBook"
	
		// FIXME: "http://taiiwan.com/8896/japan-201207-travel-log-6#more-8896";
		// "dat/crawler/article/2.dat";
	
		try
		{
			printVisualBlock( url );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	
	}

	private static Node printVisualBlock( String url ) throws ParserException, UnsupportedEncodingException
	{
		NodeList divParagraphNodeList = WebCrawler.getNodeList( url,
				new OrFilter( BLOCK_FILTER ) );

		// 歷遍所有視覺區塊的Node
		for ( int i = 0; i < divParagraphNodeList.size(); i++ )
		{
			Node node = divParagraphNodeList.elementAt( i );
			String nodeName = node.getClass().getSimpleName();
			String nodeHtml = node.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nodeText = WebCrawler.filterSpecialSymbol( node.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			System.out.printf( "Tag=%s\nHtml=%s\nText=%s\n", nodeName, nodeHtml, nodeText );
//			System.out.println( "Category=" + WebContentParserV4.getNodeCategory( node ) );
//			System.out.println( "Properties=" + WebContentParserV4._getNodeProperties( node ) );

				printChildrenNode( node, 1 );
			System.out.println("");
		}
		return null;
	}
	
	private static void printChildrenNode( Node parentNode, int level )
	{
		try
		{
			NodeList childrenNodeList = parentNode.getChildren();
			for ( int i = 0; i < childrenNodeList.size(); i++ )
			{
				Node childNode = childrenNodeList.elementAt( i );
				String childNodeName = childNode.getClass().getSimpleName();
				String childNodeHtml = childNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
//				String childNodeText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
				for ( int t = 1; t <= level; t++ )
					System.out.print( "    " );
				System.out.printf( "*Tag=%s\n", childNodeName );
				for ( int t = 1; t <= level; t++ )
					System.out.print( "    " );
				System.out.printf("Html=%s\n", childNodeHtml );
				if ( childNode.getChildren() != null )
					printChildrenNode( childNode, ++level );
			}
		}
		catch ( NullPointerException e )
		{
			for ( int t = 1; t <= level; t++ )
				System.out.print("    ");
			System.out.println( "No Children!");
		}
	}
	
}
