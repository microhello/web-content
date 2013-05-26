package com.gravitygroups.webcontent;

import org.htmlparser.Node;

import com.gravitygroups.crawler.WebCrawler;

/**
 * 用來儲存Match的相關資訊。
 *
 * @author 白昌永, Eric Wei @ Gravity Groups Co.,Ltd.
 * @version
 */
class ContentTitleMatch
{
	/**
	 * 
	 */
	public ContentTitleMatch()
	{
//		this( WebContentParserV1.NO_MATCH, WebContentParserV1.NO_MATCH, null );
	}

	/**
	 * @param index
	 * @param hits
	 * @param node
	 */
	public ContentTitleMatch( int index, int hits, Node node )
	{
		this.index = index;
		this.hits = hits;
		this.node = node;
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex( int index )
	{
		this.index = index;
	}

	/**
	 * @return the hits
	 */
	public int getHits()
	{
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits( int hits )
	{
		this.hits = hits;
	}

	/**
	 * @return the node
	 */
	public Node getNode()
	{
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode( Node node )
	{
		this.node = node;
	}
	
	public void print()
	{
		System.out.printf("** Match資訊: i=%s, hit=%s, text=%s\n",
				this.getIndex(), 
				this.getHits(),
				WebCrawler.filterSpecialSymbol( 
						this.getNode().toPlainTextString().replaceAll( "\\s+", " " ).trim() ) );
	}

	/**
	 * match的索引值。
	 */
	private int index;
	
	/**
	 * 比對文字相同的match數。
	 */
	private int hits;
	
	/**
	 * mathc到的HtmlTag Node。
	 */
	private Node node;
}