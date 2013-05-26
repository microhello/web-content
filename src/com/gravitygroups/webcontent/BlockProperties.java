/**
 * Copyright (c) 2011 Eric Wei @ Gravity Groups Co.,Ltd.
 * All rights reserved.
 */
package com.gravitygroups.webcontent;

import org.htmlparser.Node;

/**
 * 表示一個網頁視覺區塊的相關資訊，例如：Html、文字、連結數...etc.
 * @author 白昌永, Eric Wei @ Gravity Groups Co.,Ltd.
 * @version
 */
public class BlockProperties implements Comparable<Object>
{
	private Node blockNode;
	private String blockHtml;
	private String blockText;

	/**
	 * 主要視覺區塊的屬性。
	 */
	private String propoperties;

	/**
	 * 如果主要視覺區塊是Link或Invalid區塊，則表示連結文字或無效文字佔整個區塊文字的長度比例。
	 */
	private double blockTextRatio;
	
	/**
	 * 子區塊的個數。
	 */
	private int subBlockNum = 0;

	/**
	 * 
	 */
	private int subLinkBlock = 0;
	
	private int subLinkTextLength = 0;
	private double subLinkTextRatio = 0;
	private int subInvalidBlock = 0;
	private int subInvalidTextLength = 0;
	private double subInvalidTextRatio = 0;
	private int subActionBlock = 0;

	/**
	 * @param blockNode
	 */
	public BlockProperties( Node blockNode )
	{
		super();
		this.blockNode = blockNode;
	}

	/**
	 * @return the blockNode
	 */
	public Node getBlockNode()
	{
		return blockNode;
	}

	/**
	 * @param blockNode the blockNode to set
	 */
	public void setBlockNode( Node blockNode )
	{
		this.blockNode = blockNode;
	}

	/**
	 * @return the blockHtml
	 */
	public String getBlockHtml()
	{
		return blockHtml;
	}

	/**
	 * @param blockHtml the blockHtml to set
	 */
	public void setBlockHtml( String blockHtml )
	{
		this.blockHtml = blockHtml;
	}

	/**
	 * @return the blockText
	 */
	public String getBlockText()
	{
		return blockText;
	}

	/**
	 * @param blockText the blockText to set
	 */
	public void setBlockText( String blockText )
	{
		this.blockText = blockText;
	}

	/**
	 * @return the mainBlockProp
	 */
	public String getProperties()
	{
		return propoperties;
	}

	/**
	 * @param mainBlockProp the mainBlockProp to set
	 */
	public void setProperties( String mainBlockProp )
	{
		this.propoperties = mainBlockProp;
	}

	/**
	 * @return the mainTextRatio
	 */
	public double getBlockTextRatio()
	{
		return blockTextRatio;
	}

	/**
	 * @param mainTextRatio the mainTextRatio to set
	 */
	public void setBlockTextRatio( double mainTextRatio )
	{
		this.blockTextRatio = mainTextRatio;
	}

	/**
	 * @return the subBlockNum
	 */
	public int getSubBlockNum()
	{
		return subBlockNum;
	}

	/**
	 * @param subBlockNum the subBlockNum to set
	 */
	public void setSubBlockNum( int subBlockNum )
	{
		this.subBlockNum = subBlockNum;
	}

	/**
	 * @return the subLinkBlock
	 */
	public int getSubLinkBlock()
	{
		return subLinkBlock;
	}

	/**
	 * @param subLinkBlock the subLinkBlock to set
	 */
	public void setSubLinkBlock( int subLinkBlock )
	{
		this.subLinkBlock = subLinkBlock;
	}

	/**
	 * @return the subLinkTextLength
	 */
	public int getSubLinkTextLength()
	{
		return subLinkTextLength;
	}

	/**
	 * @param subLinkTextLength the subLinkTextLength to set
	 */
	public void setSubLinkTextLength( int subLinkTextLength )
	{
		this.subLinkTextLength = subLinkTextLength;
	}

	/**
	 * @return the subInvalidTextLength
	 */
	public int getSubInvalidTextLength()
	{
		return subInvalidTextLength;
	}

	/**
	 * @param subInvalidTextLength the subInvalidTextLength to set
	 */
	public void setSubInvalidTextLength( int subInvalidTextLength )
	{
		this.subInvalidTextLength = subInvalidTextLength;
	}

	/**
	 * @return the subLinkTextRatio
	 */
	public double getSubLinkTextRatio()
	{
		return subLinkTextRatio;
	}

	/**
	 * @param subLinkTextRatio the subLinkTextRatio to set
	 */
	public void setSubLinkTextRatio( double subLinkTextRatio )
	{
		this.subLinkTextRatio = subLinkTextRatio;
	}

	/**
	 * @return the subInvalidBlock
	 */
	public int getSubInvalidBlock()
	{
		return subInvalidBlock;
	}

	/**
	 * @param subInvalidBlock the subInvalidBlock to set
	 */
	public void setSubInvalidBlock( int subInvalidBlock )
	{
		this.subInvalidBlock = subInvalidBlock;
	}

	/**
	 * @return the subInvalidTextRatio
	 */
	public double getSubInvalidTextRatio()
	{
		return subInvalidTextRatio;
	}

	/**
	 * @param subInvalidTextRatio the subInvalidTextRatio to set
	 */
	public void setSubInvalidTextRatio( double subInvalidTextRatio )
	{
		this.subInvalidTextRatio = subInvalidTextRatio;
	}

	/**
	 * @return the subActionBlock
	 */
	public int getSubActionBlock()
	{
		return subActionBlock;
	}

	/**
	 * @param subActionBlock the subActionBlock to set
	 */
	public void setSubActionBlock( int subActionBlock )
	{
		this.subActionBlock = subActionBlock;
	}

	public void print()
	{
		System.out.printf( "*%s\n%s\n", this.blockHtml, this.blockText );
		/*
		System.out.print( this.propoperties );
		System.out.printf( ", R=%f, All-Len=%d, Normal-Len=%f\n", this.blockTextRatio, 
				this.blockText.length(),
				this.blockText.length() * ( 1 - this.blockTextRatio ) - this.subInvalidTextLength - this.subLinkTextLength );
		System.out.println( "\t子塊=" + subBlockNum );
		System.out.printf( "\t連結子區塊=%d", subLinkBlock );
		System.out.printf( ", R=%f\n", this.subLinkTextRatio );
		System.out.print( "\t無效子區塊=" + subInvalidBlock );
		System.out.println( ", R=" + this.subInvalidTextRatio );
		*/
	}

	@Override
	public int compareTo( Object o )
	{
		return 0;
	}

}
