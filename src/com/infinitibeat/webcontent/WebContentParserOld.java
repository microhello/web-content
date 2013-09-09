/**
 * Copyright (c) 2011 Eric Wei @ Gravity Groups Co.,Ltd.
 * All rights reserved.
 */
package com.infinitibeat.webcontent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.infinitibeat.crawler.HtmlTag;
import com.infinitibeat.crawler.WebCrawler;
import com.infinitibeat.io.FileUtils;
import com.infinitibeat.util.MapUtils;

/**
 * WebContent v1.0-release finised的版本。
 * @author 白昌永, Eric Wei @ Gravity Groups Co.,Ltd.
 * @version
 */
public class WebContentParserOld extends WebCrawler
{
	/**
	 * 要預先過濾掉的無用標籤。
	 */
	public static final NodeFilter[] TRIM_FILTER = { 
		new NodeClassFilter( HeadTag.class ),
		new NodeClassFilter( StyleTag.class ),
		new NodeClassFilter( ScriptTag.class ) };
	
	private static final String VISUAL_BLOCK = "Visual_Blcok";
	private static final String NORMAL_BLOCK = "Normal_Block";
	private static final String LINK_BLOCK = "Link_Block";
	private static final String INVALID_BLOCK = "Invalid_Block";
	private static final String ACTION_BLOCK = "Action_Block";
	
	private static final String URL_PATH = "dat/web_content/article_url";
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
//			"http://pianogreen.blogspot.com/2011/05/1000529.html";
//			"http://blog.xuite.net/m6624/blog/57708421-%25E8%258F%2581%25E6%25A1%2590,%25E7%25A6%258F%25E5%25AE%25B9,%25E5%25B9%25B3%25E6%25BA%25AA%25E4%25B8%2580%25E6%2597%25A5%25E9%2581%258A";
//			"http://www.kuobrothers.com/article-60.htm";
//			"http://www.cheers.com.tw/blog/blogTopic.action?id=105&nid=2327";
//			"http://lkk96tw.blogspot.tw/2010/12/blog-post.html";
//			"http://sophiefish.pixnet.net/blog/post/5188660-%25E2%2596%258C%25E8%25B6%25B4%25E8%25B6%25B4%25E8%25B5%25B0%25E2%2596%2590-%25E8%258F%2581%25E6%25A1%2590%25E8%2580%2581%25E8%25A1%2597%25E9%2581%258A%25E3%2580%2582";
//			"http://lkk96tw.blogspot.tw/2010/12/blog-post.html";
//			"http://kanema.pixnet.net/blog/post/25889381-090926-%25E8%258F%2581%25E6%25A1%2590%25E8%2580%2581%25E8%25A1%2597%25E4%25B9%258B%25E8%2580%2581%25E6%259C%25A8%25E5%25BC%25B7%25E8%25BA%25AB%25E4%25B9%258B%25E6%2597%2585";
//			"http://ad5897mstpc.pixnet.net/blog/post/24185838-%25E3%2580%2590%25E5%25B9%25B3%25E6%25BA%25AA%25E8%258F%2581%25E6%25A1%2590%25E3%2580%2591%25E6%258E%25A2%25E8%25A8%25AA%25E5%2581%25B6%25E5%2583%258F%25E5%258A%2587%25E6%2599%25AF%25E9%25BB%259E";
//			"http://blog.xuite.net/edward.lai/papato/10455002";
//			"http://blog.xuite.net/shufu88/blog/30884748";
//			"http://blog.xuite.net/wallaces528/blog/17840170";
//			"http://blog.xuite.net/daol/abu/9126504";
//			"http://infinite520visa.pixnet.net/blog/post/16757743-%25E6%2596%25B0%25E5%258C%2597%25E5%25B8%2582~%25E5%25B9%25B3%25E6%25BA%25AA~%25E8%258F%2581%25E6%25A1%2590%25E8%2580%2581%25E8%25A1%2597%25E2%2580%25A7%25E7%25BE%258E%25E9%25A3%259F";
		
			// NOTE: trimScript()過濾掉正文
//			"http://blog.xuite.net/peifang08/0315/16244716"; // FIXED! 
//			"http://www.wretch.cc/blog/shebw/27842179";
//			"http://blog.xuite.net/bruce588/myblog/40134090";
//		"http://blog.xuite.net/wallaces528/blog/17840170"; // FIXED!

			
			// NOTE: 2.後來js過濾有問題!!!! 正文都被過濾掉了!! 正要解決!!! i=145
//			"http://tw.news.yahoo.com/3%E6%8B%9B%E6%94%B9%E6%8E%89%E6%8B%96%E5%BB%B6%E5%A3%9E%E7%BF%92%E6%85%A3-110556823.html";

			// temp2.html
//			"http://stupid77.com/work-manager/%E8%A1%8C%E9%8A%B7%E7%AD%96%E7%95%A5%E5%88%B6%E5%AE%9A%E7%9A%84%E4%B8%89%E9%83%A8%E6%9B%B2.html";
//			"http://school.cw.com.tw/article/show/587?cname=INSIGHT&offset=2&max=1";
//			"http://www.libertytimes.com.tw/2012/new/jun/26/today-north5.htm";
//			"http://yurenju.blogspot.tw/2010/03/2010-pingsi-sky-lanterns.html"; // <div class='post-body entry-content'>
			// NOTE: 4.包含無法過濾乾淨的<script>document.write('<script type="text/javascript 'src="http://www.google.com/friendconnect/script/friendconnect.js"></script>'</script>
//			"http://tku-vois.blogspot.com/2010/10/2010.html"; // *<div class='post-body entry-content' id='post-body-5662388878345342738' itemprop='description articlebody'>
//		"http://www.cw.com.tw/blog/blogTopic.action?id=181&nid=2347"; // <div class="postdetail">
		// NOTE: 1.原先js過濾有問題 i=531!!
//		"http://blog.xuite.net/bibitsai/bibi/35951126-%25E8%258F%2581%25E6%25A1%2590%25E5%258D%2581%25E5%2588%2586%25E6%2590%25AD%25E7%2581%25AB%25E8%25BB%258A%25E8%25B6%25B4%25E8%25B6%25B4%25E8%25B5%25B0%25E4%25B9%258B%25E6%2597%2585%25E7%25AC%25AC%25E4%25B8%2580%25E7%25AB%2599%25EF%25BC%258C%25E8%258F%2581%25E6%25A1%2590%25E8%2580%2581%25E8%25A1%2597%25EF%25BC%258C%25E5%258D%2588%25E9%25A4%2590%40%25E6%25A5%258A%25E5%25AE%25B6%25E9%259B%259E%25E6%258D%25B2";
// "http://www.wretch.cc/blog/xalekd/4606692"; // NOTE: <div class="innertext">...是最小包含正文的區塊，但是還是包含連結區塊!
//		 "http://www.wretch.cc/blog/avav6004/30663865";
//		 "http://www.hksilicon.com/kb/articles/85863/12";
//		 "http://www.cool3c.com/article/62221";
			
		 // NOTE: 3.	這裡js過濾掉正文了!!
//		 "http://mrjamie.cc/2012/08/30/startup-poker/?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+MrJamie+%28MR+JAMIE%29&utm_content=FaceBook";
	
		// FIXME: "http://taiiwan.com/8896/japan-201207-travel-log-6#more-8896";
		// "dat/crawler/article/2.dat";
	
		
		try
		{
//			checkBugURL();
			Scanner input = new Scanner( System.in );
			File datFile = new File( URL_PATH );
			int i = 0;
			for ( String dat : datFile.list() )
			{
				System.out.printf("%d=%s\n", i, dat );
				i++;
			}
			System.out.print( "\t選擇一個dat檔來分析\n" );
			System.out.print("\t或直接輸入URL\n >> ");
			
			try
			{
				int dat = input.nextInt();
				parseArticle( datFile.list()[ dat ] );	
			}
			catch ( InputMismatchException e )
			{
				String url = input.next();
//				getVisualBlock( url );
//				String trimHtml = trimScript( url );
//				System.out.println( trimHtml ); System.out.println( trimHtml.contains( "<script" ) ); System.out.println( trimHtml.contains( "</script" ) ); System.out.println( trimHtml.contains( "var " ) );
				BlockProperties contentProp = parseWebContent( url ); 
				System.out.println( "\n正文: " ); contentProp.print();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private static void parseArticle( String datFile ) throws FileNotFoundException
	{
		List<String> urlList = FileUtils.inputAsStringList( URL_PATH + File.separator + datFile );
		PrintWriter output = new PrintWriter( new File( "output/web_content/" + datFile + ".html" ) );
		for ( int i = 0; i < urlList.size(); i++ )
		{
			try
			{
				String url = urlList.get( i );
				System.out.printf("%d. %s\n", i, url );
				output.println( i + ".<a href='"+ url + "' target='_blank'>" + url + "</a><br>" );
				
				BlockProperties contentProp = parseWebContent( url );
				contentProp.print();
				output.println( contentProp.getBlockText() + " <br> " );
				output.flush();
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
		if ( output != null )
			output.close();
	}
	
	private static void checkBugURL() throws FileNotFoundException
	{
		List<String> urlList = FileUtils.inputAsStringList( 
				"dat/web_content/filter-link-block-bug.txt" );
		( new File( "output/web_content" ) ).mkdirs();
		PrintWriter output = new PrintWriter( new File( "output/web_content/bug.html" ) );
		for ( int i = 0; i < urlList.size(); i++ )
		{
			try
			{
				String url = urlList.get( i );
				if ( !url.startsWith( "http" ) )
					continue;
				System.out.println("\n" + url );
				
//				output.println( i + ".<a href='"+ url + "'>" + url + "</a>" );
				BlockProperties contentProp = parseWebContent( url );
				System.out.println("\n--正文區块: "); contentProp.print();
//				output.println( contentProp.getBlockText() + " <br> " );
//				output.flush();
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		}
		if ( output != null )
			output.close();
	}
	
	/**
	 * 先找到文字彼此獨立的視覺區塊。
	 * 
	 * @param url
	 * @return
	 * @throws ParserException
	 */
	public static NodeList getVisualBlock( String url ) throws ParserException
	{
		List<String> nodeTextList = new ArrayList<String>();
		NodeList elementBlockNodeList = 
			Parser.createParser( trimScript( url.trim() ), "UTF-8" ).extractAllNodesThatMatch( new OrFilter( BLOCK_FILTER ) );
		
		NodeList visualBlockNodeList = new NodeList();
		
		if ( elementBlockNodeList.size() <= 0 )
			throw new NullPointerException("沒有找到任何<div>或<p>或<table>的標籤區塊!!");
		
		Node currentNode = elementBlockNodeList.elementAt( 0 );
		String currentHtml = currentNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
		String currentText = WebCrawler.filterSpecialSymbol( currentNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
		for ( int i = 1; i < elementBlockNodeList.size(); i++ )
		{
			Node nextNode = elementBlockNodeList.elementAt( i );
			
			String nextHtml = nextNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nextText = WebCrawler.filterSpecialSymbol( nextNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();

			if ( nextText.length() <= 0  )
				continue;
//			System.out.printf( "**Tag=%s\nHtml=%s\nText=%s\n", nextNode.getClass().getSimpleName(), nextHtml, nextText );

			else if ( nextText.equals( currentText ) && nextHtml.length() < currentHtml.length() )
			{
				currentNode = nextNode;
				currentHtml = nextHtml;
				currentText = nextText;
				nodeTextList.add( currentText );
			}
			else if ( !nextText.equals( currentText ) )
			{
				visualBlockNodeList.add( currentNode );
//				System.out.printf( "**Tag=%s\nHtml=%s\nText=%s\n", currentNode.getClass().getSimpleName(), currentHtml, currentText );
				currentNode = nextNode;
				currentHtml = nextHtml;
				currentText = nextText;
			}
		}
		return visualBlockNodeList;
	}
	
	public static BlockProperties parseWebContent( String url ) throws ParserException
	{
		NodeList visualBlockNodeList = getVisualBlock( url );
		
		NodeList linkNodeList = findLinkBlock( visualBlockNodeList );
		NodeList invalidNodeList = findInvalidBlock( visualBlockNodeList );
		NodeList actionNodeList = findActionBlock( visualBlockNodeList ); // NOTE: 因為動作標籤可能沒有包含文字，所以要獨立出來找
		
		Map<String, NodeList> blockNodeMap = new HashMap<String, NodeList>();
		blockNodeMap.put( VISUAL_BLOCK, visualBlockNodeList );
		blockNodeMap.put( LINK_BLOCK, linkNodeList );
		blockNodeMap.put( INVALID_BLOCK, invalidNodeList );
		blockNodeMap.put( ACTION_BLOCK, actionNodeList );
		
		/*
		for ( int i = 0; i < visualBlockNodeList.size(); i++ )
		{
			Node currentNode = visualBlockNodeList.elementAt( i );
			String currentHtml = currentNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String currentText = WebCrawler.filterSpecialSymbol( currentNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
//			System.out.printf( "Tag=%s\nHtml=%s\nText=%s\n", currentNode.getClass().getSimpleName(), currentHtml, currentText );
			System.out.println( "*" + currentHtml + "\n" + currentText );
			if ( linkNodeList.contains( currentNode ) )
				System.out.println( "\t" + LINK_BLOCK );
			else if ( invalidNodeList.contains( currentNode ) )
				System.out.println( "\t" + INVALID_BLOCK );
			else if ( actionNodeList.contains( currentNode ) )
				System.out.println( "\t" + ACTION_BLOCK );
			else
				System.out.println( "\tOK!");
			System.out.println("");
		}
		*/
		
		List<BlockProperties> blockPropertiesList = getBlockProperties( blockNodeMap );
		Map<BlockProperties, Double> propMap = new TreeMap<BlockProperties, Double>();
		for ( int i = 0; i < blockPropertiesList.size(); i++ )
		{
			BlockProperties blockProp = blockPropertiesList.get( i );
			
//			/*
			if ( blockProp.getProperties().equals( NORMAL_BLOCK ) && blockProp.getBlockText().length() <= 0 )
				continue;
			// v1: 0.3
			else if ( ( blockProp.getProperties().equals( LINK_BLOCK ) && blockProp.getBlockTextRatio() >= 0.4 ) || 
					blockProp.getSubLinkTextRatio() >= 0.45 )
				continue;
			// v1: 0.4
			else if ( ( blockProp.getProperties().equals( INVALID_BLOCK ) && blockProp.getBlockTextRatio() >= 0.4 ) ||
					blockProp.getSubInvalidTextRatio() >= 0.4 )
				continue;
			else if ( blockProp.getProperties().equals( ACTION_BLOCK ) || blockProp.getSubActionBlock() > 0 )
				continue;
//				*/
//			blockProp.print();
			
			// 找出正文區塊的計算特徵值公式
			double linkInvalidTextLen = blockProp.getSubInvalidTextLength() + blockProp.getSubLinkTextLength();
			double normalTextLen = blockProp.getBlockText().length() * ( 1.0 - blockProp.getBlockTextRatio() ) - linkInvalidTextLen;
			if ( linkInvalidTextLen <= 0 )
				linkInvalidTextLen = 1.0; // 為了除法，如果等於零要轉成1
			
			double linkInvalidSubBlockNum = blockProp.getSubLinkBlock() + blockProp.getSubInvalidBlock();
			double normalSubBlock = (double)( blockProp.getSubBlockNum() - linkInvalidSubBlockNum );
			if ( linkInvalidSubBlockNum <= 0 )
				linkInvalidSubBlockNum = 1.0;
			
			double weight = Math.pow( normalTextLen, 5 ) / (double)blockProp.getBlockText().length();
			weight /= Math.pow( 10.0, 5 );
			
			// 子區块都是連結或是無效區块
			if ( normalSubBlock == 0 && blockProp.getSubBlockNum() != 0 )
				weight /= ( 10.0 * Math.pow( blockProp.getSubBlockNum(), 2 ) );
			else if ( normalSubBlock != 0 && blockProp.getSubBlockNum() != 0 )
				weight *= ( normalSubBlock / Math.pow( blockProp.getSubBlockNum(), 2 ) );
			
			if ( blockProp.getProperties().equals( NORMAL_BLOCK ) )
				weight *= 2.0;
			else if ( blockProp.getProperties().equals( INVALID_BLOCK ) )
				weight *= 1.2;
			else if ( blockProp.getProperties().equals( LINK_BLOCK ) )
				weight *= 1.5;
			
			// 由視覺區块的class或id來判斷，包含article和content的字眼可提高權重值
			CompositeTag blockTag = (CompositeTag)blockProp.getBlockNode();
			String className = blockTag.getAttribute( "class" );
			String idName = blockTag.getAttribute( "id" );
			String checkName = null;
			if ( idName != null )
				checkName = idName;
			else if ( className != null )
				checkName = className;
			
			if ( checkName != null )
			{
				checkName = checkName.toLowerCase().trim();
				if ( !( checkName.contains( "footer" ) ||
						checkName.contains( "header" ) ||
						checkName.contains( "counter" ) ||
						checkName.contains( "banner" ) ) )
				{
//					System.out.println( "*Weight=" + weight );
					if ( checkName.contains( "content" ) &&
							checkName.contains( "article" ) )
						weight *= 100.0;
					else if ( checkName.contains( "innertext" ) ||
							checkName.contains( "content" ) )
						weight *= 50.0;
					else if ( checkName.contains( "article" ) ) 
						weight *= 10.0;
					else if ( checkName.contains( "text" ) )
						weight *= 5.0;
//					System.out.println( "Weight'=" + weight );

				}
			}
			
			propMap.put( blockProp, weight );
//			System.out.println( "\t*Weight=" + weight );
			propMap = MapUtils.sortByValue( propMap, true );
		}
		
		
		int count = 0; // 為了取得第一個BlockProperties用的
		BlockProperties contentProp = null;
		int commentIndex = 0; // 用來儲存回應的區塊索引，所有在回應以下的區塊都不能成為正文區块
		for ( BlockProperties prop : propMap.keySet() )
		{
			try
			{
				// 用來去掉回應區塊
				CompositeTag propNode = (CompositeTag)prop.getBlockNode();
				String className = propNode.getAttribute( "class" );
				String idName = propNode.getAttribute( "id" );
				if ( ( className != null &&
						( className.contains( "comment" ) || className.contains( "reply" ) ) ) ||
						( idName != null && ( idName.contains( "comment" ) || idName.contains( "reply" ) ) ) )
				{
					commentIndex = blockPropertiesList.indexOf( prop );
					continue;
				}
				// FIXME: 會過濾掉正文區塊
				/*
				else if ( propNode.toHtml().contains( "comment" ) || propNode.toHtml().contains( "reply" ) )
				{
					System.out.println( prop.getBlockHtml() );
					continue;
				}
				*/
			}
			catch ( NullPointerException e )
			{
				e.printStackTrace();
			}
			
			if ( commentIndex != 0 && blockPropertiesList.indexOf( prop ) > commentIndex )
				continue;
			
			if ( count == 0 )
			{
//				System.out.println( "\n\n--> Wegith= " + propMap.get( prop ) ); prop.print(); 
				contentProp = prop;
				count++;
			}
			else
				break;
		}
		
		// 找到正文區塊還有其子區块，判斷是否包含連結區塊或是特定class, id名稱，然後過濾掉。
		Node currentNode = contentProp.getBlockNode();
		String contentHtml = currentNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
		String contentText = WebCrawler.filterSpecialSymbol( currentNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() );
		String checkContentHtml = contentHtml; // check開頭的變數是給迴圈判斷正文區塊的子區塊用的
		String checkContentText = contentText; // 因為在迴圈中contentHtml和contentText的字串會變動，所以無法拿來判斷子區塊
		for ( int i = visualBlockNodeList.indexOf( contentProp.getBlockNode() ) + 1;
					i < visualBlockNodeList.size(); i++ )
		{
			CompositeTag nextNode = (CompositeTag)visualBlockNodeList.elementAt( i );
			String nextHtml = nextNode.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nextText = WebCrawler.filterSpecialSymbol( nextNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			
			boolean trim = false;
			if ( checkContentHtml.contains( nextHtml ) &&
					checkContentText.contains( nextText ) )
			{
//				System.out.println( "*" + nextHtml );
				if ( linkNodeList.contains( nextNode ) )
				{
					int linkTextLen = getLinkTextLength( nextNode );
					double linkTextRatio = (double)linkTextLen / (double)nextText.length();
					if ( linkTextRatio >= 0.65 )
					{
//						System.out.println( "\t" + linkTextRatio );
						trim = true;
					}
				}
				if ( containTrimClassID( nextNode ) )
				{
//					System.out.println("\t" + nextText );
					trim = true;
				}
				
				if ( trim )
				{
					contentHtml = contentHtml.replace( nextHtml, "" );
					contentText = contentText.replace( nextText, "" );
				}
			}
			else
				break;
		}
		
		contentProp.setBlockHtml( contentHtml.replaceAll( HtmlTag.BR_REPLACE_REGEX, " " ).replaceAll( HtmlTag.STYLE_REPLACE_REGEX, "" ) );
		contentProp.setBlockText( WebCrawler.filterSpecialSymbol( WebCrawler.filterHTMLTag( contentProp.getBlockHtml(), "" ) ) );
		return contentProp;
	}
	
	public static List<BlockProperties> getBlockProperties( Map<String, NodeList> blockNodeMap )
	{
		List<BlockProperties> blockPropList = new ArrayList<BlockProperties>();
		NodeList visualBlockList = blockNodeMap.get( VISUAL_BLOCK );
		NodeList linkBlockList = blockNodeMap.get( LINK_BLOCK );
		NodeList invalidBlockList = blockNodeMap.get( INVALID_BLOCK );
		NodeList actionBlockList = blockNodeMap.get( ACTION_BLOCK );
		for ( int i = 0; i < visualBlockList.size(); i++ )
		{
			Node block = visualBlockList.elementAt( i );
			BlockProperties blockProp = new BlockProperties( block );
			String blockHtml = block.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			blockProp.setBlockHtml( blockHtml );
			String blockText = WebCrawler.filterSpecialSymbol( block.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			blockProp.setBlockText( blockText );
//			System.out.printf("*%s\n%s\n#", mainHtml, mainText );
//			System.out.println( mainText.length() );
			
			if ( linkBlockList.contains( block ) )
			{
//				System.out.printf("*%s\n%s\n", mainHtml, mainText );
//				System.out.println( mainText.length() );
				blockProp.setProperties( LINK_BLOCK );
//				System.out.print( "\t" + LINK_BLOCK );
				int linkTextLen = getLinkTextLength( block );
//				System.out.printf(", #%d, R=%f\n", linkTextLen, (double)linkTextLen / (double)mainText.length() );
				blockProp.setBlockTextRatio( (double)linkTextLen / (double)blockText.length() );
			}
			else if ( invalidBlockList.contains( block ) )
			{
				blockProp.setProperties( INVALID_BLOCK );
//				System.out.print( "\t" + INVALID_BLOCK );
				int invalidTextLen = getInvalidTextLength( block );
//				System.out.printf(", #%d, R=%f\n", invalidTextLen, (double)invalidTextLen / (double)mainText.length() ); 
				blockProp.setBlockTextRatio( (double)invalidTextLen / (double)blockText.length() );
			}
			else if ( actionBlockList.contains( block ) )
			{
				blockProp.setProperties( ACTION_BLOCK );
//				System.out.println( "\t" + ACTION_BLOCK );
			}
			else
			{
				blockProp.setProperties( NORMAL_BLOCK );
//				System.out.println( "\t" + NORMAL_BLOCK );
				int linkTextLen = getLinkTextLength( block );
//				System.out.printf(", #%d, R=%f\n", linkTextLen, (double)linkTextLen / (double)mainText.length() );
				blockProp.setBlockTextRatio( (double)linkTextLen / (double)blockText.length() );
				
				
			}
			
			int subBlockNum = 0;
			int subLinkBlock = 0;
			int subLinkTextLen = 0;
			int subInvalidBlock = 0;
			int subInvalidTextLen = 0;
			int subActionBlock = 0;
			for ( int j = i + 1; j < visualBlockList.size() ; j++ )
			{
				Node blockToCheck = visualBlockList.elementAt( j );
				String checkHtml = blockToCheck.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
				String checkText = WebCrawler.filterSpecialSymbol( blockToCheck.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
				if ( blockHtml.contains( checkHtml ) &&
						blockText.contains( checkText ) )
				{
					subBlockNum++;
					
					if ( linkBlockList.contains( blockToCheck ) )
					{
						subLinkBlock++;
						subLinkTextLen += getLinkTextLength( blockToCheck );
					}
					else if ( invalidBlockList.contains( blockToCheck ) )
					{
						subInvalidBlock++;
						subInvalidTextLen += getInvalidTextLength( blockToCheck );
					}
					else if ( actionBlockList.contains( blockToCheck ) )
					{
						subActionBlock++;
					}
//						System.out.printf("\t%s\n\t%s\n\n", checkHtml, checkText );
				}
				else // 本身沒有子區塊
					break;
			}
			
			/*
			System.out.println( "\t子块=" + subBlockNum );
			System.out.println( "\t連結子區块=" + subLinkBlock );
			System.out.print( "\t子區块連結文字=" + subLinkTextLen );
			System.out.println( ", R=" + (double)subLinkTextLen / (double)mainText.length() );
			System.out.print( "\t無效子區块=" + subInvalidBlock );
			System.out.println( ", R=" + (double)subInvalidTextLen / (double)mainText.length() );
			System.out.println( "\t動作子區块=" + subActionBlock );
			*/
			blockProp.setSubBlockNum( subBlockNum );
			blockProp.setSubLinkBlock( subLinkBlock );
			blockProp.setSubLinkTextLength( subLinkTextLen );
			blockProp.setSubLinkTextRatio( (double)subLinkTextLen / (double)blockText.length() );
			blockProp.setSubInvalidBlock( subInvalidBlock );
			blockProp.setSubInvalidTextLength( subInvalidTextLen );
			blockProp.setSubInvalidTextRatio( (double)subInvalidTextLen / (double)blockText.length() );
			blockProp.setSubActionBlock( subActionBlock );
			
			blockPropList.add( blockProp );
		}
		
		return blockPropList;
	}

	public static NodeList findLinkBlock( NodeList blockNodeList ) throws ParserException
	{
		NodeList linkBlockNodeList = new NodeList();
		for ( int i = 0; i < blockNodeList.size(); i++ )
		{
			int linkTextLength = 0;
			Node node = blockNodeList.elementAt( i );
			String nodeName = node.getClass().getSimpleName();
			String nodeHtml = node.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nodeText = WebCrawler.filterSpecialSymbol( node.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
//			System.out.printf( "\nTag=%s\nHtml=%s\nText=%s\n%d, %d\n", nodeName, nodeHtml, nodeText, nodeText.length(), getLinkTextLength( node ) );
			
			try
			{
				NodeList childrenNodeList = node.getChildren(); 
				if ( childrenNodeList == null )
					continue;
				
				for ( int j = 0; j < childrenNodeList.size(); j++ )
				{
					Node childNode = childrenNodeList.elementAt( j );
					String childNodeName = childNode.getClass().getSimpleName();
					String childText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
					if ( childNodeName.equals( TextNode.class.getSimpleName() ) && childText.length() <= 0 )
						continue;
					
					// 找到第一層子結點是超連結的區塊
					if ( childNodeName.equals( LinkTag.class.getSimpleName() ) )
					{
						LinkTag linkNode = (LinkTag)childNode;
						linkTextLength += WebCrawler.filterSpecialSymbol( linkNode.getLinkText() ).length();
					}
					
//					System.out.println( "\t" + childNodeName );
					
					// 遞迴往下找超連結區塊
					boolean isChildLinkBlock = false;
					if ( ( childNodeName.equals( HeadingTag.class.getSimpleName() ) ||
							childNodeName.equals( BulletList.class.getSimpleName() ) ||
							childNodeName.equals( Bullet.class.getSimpleName() ) ||
							childNodeName.equals( Div.class.getSimpleName() ) ||
							childNodeName.equals( ParagraphTag.class.getSimpleName() ) ) )
						isChildLinkBlock = isChildrenLinkBlock( childNode, nodeText.length() );
					else if ( childNodeName.equals( Span.class.getSimpleName() ) )
					{
						if ( (double)getLinkTextLength( node ) / (double)nodeText.length() > 0.1 )
							isChildLinkBlock = true;
					}

					if ( isChildLinkBlock )
					{
						linkBlockNodeList.add( node );
//						System.out.print( " -link-block! R=" );
//						System.out.println( (double)getLinkTextLength( node ) / (double)nodeText.length() );
					}
					
					/*
					if ( childNodeName.equals( TagNode.class.getSimpleName() ) &&
							( (TagNode)childNode ).isEndTag() == true )
						System.out.println("-End_Tag");
						*/
				}
			}
			catch ( NullPointerException e )
			{
				e.printStackTrace();
				// 該視覺區塊沒有任何子標籤。
			}
			
			if ( (double)linkTextLength / (double)nodeText.length() >= 0.1 )
			{
				linkBlockNodeList.add( node );
//				System.out.println("--1st-LINK_BLOCK");
//				System.out.println( (double)getLinkTextLength( node ) / (double)nodeText.length() );
			}
		}
		return linkBlockNodeList;
	}
	
	/**
	 * 遞迴判斷該節點下所有子節點是否包含連結。
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isChildrenLinkBlock( Node node, int allTextLength )
	{
		double linkTextLen = (double)getLinkTextLength( node );
		if ( linkTextLen / (double) allTextLength >= 0.1 )
			return true;
		else 
			return false;
		
		// 上面程式碼是修正後的正確判斷方法。(用超連結文字長度所佔比例判斷)
		// 下面程式碼是在視覺區塊下找到一個link就回傳是連結區塊。
		/* 
		NodeList childrenNodeList = node.getChildren();
		for ( int i = 0; i < childrenNodeList.size(); i++ )
		{
			Node childNode = childrenNodeList.elementAt( i );
			String childNodeName = childNode.getClass().getSimpleName();
			if ( childNodeName.equals( LinkTag.class.getSimpleName() ) )
			{
				System.out.println( "Link-Text-Length: " + getChildrenLinkTextLength( node ) );
				return true;
			}
			
			boolean checkChildren = false;
			if ( childNode.getChildren() != null )
				checkChildren = isChildrenLinkBlock( childNode );
			
			if ( checkChildren == true )
			{
				System.out.println( "Link-Text-Length: " + getChildrenLinkTextLength( node ) );
				return true;
			}
		}
		*/
	}
	
	public static int getLinkTextLength( Node node )
	{
		int linkTextLength = 0;
		NodeList childrenNodeList = node.getChildren(); 
		if ( childrenNodeList == null )
			return linkTextLength;
		for ( int j = 0; j < childrenNodeList.size(); j++ )
		{
			Node childNode = childrenNodeList.elementAt( j );
			String childNodeName = childNode.getClass().getSimpleName();
			String childText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			if ( childNodeName.equals( TextNode.class.getSimpleName() ) && childText.length() <= 0 )
				continue;
			
			// 找到第一層子結點是超連結的區塊
			if ( childNodeName.equals( LinkTag.class.getSimpleName() ) )
			{
				LinkTag linkNode = (LinkTag)childNode;
				linkTextLength += WebCrawler.filterSpecialSymbol( linkNode.getLinkText() ).length();
			}
			
			if ( ( childNodeName.equals( HeadingTag.class.getSimpleName() ) ||
					childNodeName.equals( BulletList.class.getSimpleName() ) ||
					childNodeName.equals( Bullet.class.getSimpleName() ) ||
					childNodeName.equals( Div.class.getSimpleName() ) ||
					childNodeName.equals( ParagraphTag.class.getSimpleName() ) ||
					childNodeName.equals( Span.class.getSimpleName() ) ) )
				linkTextLength += getChildrenLinkTextLength( childNode );
		}
		return linkTextLength;
	}
	
	public static NodeList findInvalidBlock( NodeList blockNodeList ) throws ParserException
	{
		NodeList invalidBlockNodeList = new NodeList();
		for ( int i = 0; i < blockNodeList.size(); i++ )
		{
			Node node = blockNodeList.elementAt( i );
			String nodeName = node.getClass().getSimpleName();
			String nodeHtml = node.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nodeText = WebCrawler.filterSpecialSymbol( node.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
//			System.out.printf( "\nTag=%s\nHtml=%s\nText=%s\n", nodeName, nodeHtml, nodeText );
			
			try
			{
				NodeList childrenNodeList = node.getChildren(); 
				if ( childrenNodeList == null )
					continue;
				
				for ( int j = 0; j < childrenNodeList.size(); j++ )
				{
					Node childNode = childrenNodeList.elementAt( j );
					String childNodeName = childNode.getClass().getSimpleName();
					String childText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
					if ( childNodeName.equals( TextNode.class.getSimpleName() ) && childText.length() <= 0 )
						continue;
					
					// 找到第一層子結點是無效區塊
					String invalidNodeName = "";
					if ( childNodeName.equals( ScriptTag.class.getSimpleName() ) ||
							childNodeName.equals( StyleTag.class.getSimpleName() ) ||
							childNodeName.equals( HeadTag.class.getSimpleName() ) ||
							childNodeName.equals( DoctypeTag.class.getSimpleName() ) ||
							childNodeName.equals( SelectTag.class.getSimpleName() ) ||
							childNodeName.equals( OptionTag.class.getSimpleName() ) )
						invalidNodeName = childNodeName;
					else if ( childNodeName.equals( TagNode.class.getSimpleName() ) )
					{
						TagNode tag = (TagNode)childNode;
						String tagCode = tag.getText();
						if ( tagCode.equals( HtmlTag.HEADER_TAG ) || 
								tagCode.equals( HtmlTag.FOOTER_TAG ) ||
								tagCode.equals( HtmlTag.NAV_TAG ) )
							invalidNodeName = tag.getText();
					}
					// 遞迴往下找無效區塊
					else 
						invalidNodeName = isChildrenInvalid( childNode );
					
//					if ( invalidNodeName.length() > 0 )
//						invalidBlockNodeList.add( node );
					
//					System.out.print( "\t" + childNodeName );
					if ( invalidNodeName.length() > 0 )
					{
						invalidBlockNodeList.add( node );
//						System.out.println( " -InvalidTag=" + invalidNodeName );
					}
//					printChildrenNode( childNode, 1 );
					/*
					if ( childNodeName.equals( TagNode.class.getSimpleName() ) &&
							( (TagNode)childNode ).isEndTag() == true )
						System.out.println("-End_Tag");
					else
						System.out.println("");
						*/
				}
			}
			catch ( NullPointerException e )
			{
				e.printStackTrace();
				// 該視覺區塊沒有任何子標籤。
			}
		}
		return invalidBlockNodeList;
	}
	
	/**
	 * 遞迴判斷該節點下所有子節點是否包含連結。
	 * 
	 * @param node
	 * @return 空字串表示valid或是所包含invalid的標籤名稱
	 */
	public static String isChildrenInvalid( Node node )
	{
		NodeList childrenNodeList = node.getChildren();
		if ( childrenNodeList == null )
			return "";
		for ( int i = 0; i < childrenNodeList.size(); i++ )
		{
			Node childNode = childrenNodeList.elementAt( i );
			String childNodeName = childNode.getClass().getSimpleName();
			if ( childNodeName.equals( ScriptTag.class.getSimpleName() ) ||
					childNodeName.equals( StyleTag.class.getSimpleName() ) ||
					childNodeName.equals( HeadTag.class.getSimpleName() ) ||
					childNodeName.equals( DoctypeTag.class.getSimpleName() ) ||
					childNodeName.equals( SelectTag.class.getSimpleName() ) ||
					childNodeName.equals( OptionTag.class.getSimpleName() ) )
			{
				return childNodeName;
			}
			else if ( childNodeName.equals( TagNode.class.getSimpleName() ) )
			{
				TagNode tag = (TagNode)childNode;
				String tagCode = tag.getText();
				if ( tagCode.equals( HtmlTag.HEADER_TAG ) || 
						tagCode.equals( HtmlTag.FOOTER_TAG ) ||
						tagCode.equals( HtmlTag.NAV_TAG ) )
					return tagCode;
			}
			
			String checkChildren = "";
			if ( childNode.getChildren() != null )
				checkChildren = isChildrenInvalid( childNode );
			
			if ( checkChildren.length() > 0 )
			{
				return checkChildren;
			}
		}
		return "";
	}
	
	public static int getInvalidTextLength( Node node )
	{
		int invalidTextLen = 0;
		NodeList childrenNodeList = node.getChildren(); 
		if ( childrenNodeList == null )
			return invalidTextLen;
		
		for ( int j = 0; j < childrenNodeList.size(); j++ )
		{
			Node childNode = childrenNodeList.elementAt( j );
			String childNodeName = childNode.getClass().getSimpleName();
			String childText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			if ( childNodeName.equals( TextNode.class.getSimpleName() ) && childText.length() <= 0 )
				continue;
			
			// 找到第一層子結點是無效區塊
			if ( childNodeName.equals( ScriptTag.class.getSimpleName() ) ||
					childNodeName.equals( StyleTag.class.getSimpleName() ) ||
					childNodeName.equals( HeadTag.class.getSimpleName() ) ||
					childNodeName.equals( DoctypeTag.class.getSimpleName() ) ||
					childNodeName.equals( SelectTag.class.getSimpleName() ) ||
					childNodeName.equals( OptionTag.class.getSimpleName() ) ||
					( childNodeName.equals( TagNode.class.getSimpleName() ) &&
							childNode.getText().toLowerCase().equals( HtmlTag.BUTTON_TAG ) ) )
			{
				invalidTextLen += childText.length();
			}
			else if ( childNodeName.equals( TagNode.class.getSimpleName() ) )
			{
				TagNode tag = (TagNode)childNode;
				String tagCode = tag.getText();
				if ( tagCode.equals( HtmlTag.HEADER_TAG ) || 
						tagCode.equals( HtmlTag.FOOTER_TAG ) ||
						tagCode.equals( HtmlTag.NAV_TAG ) ||
						tagCode.contains( HtmlTag.BUTTON_TAG ) )
					invalidTextLen += childText.length();
			}
			// 遞迴往下找無效區塊
			else 
				invalidTextLen += getInvalidTextLength( childNode );
		}
		return invalidTextLen;
	}

	/**
	 * 找到表單動作的標籤區塊 (form, input)
	 * @param blockNodeList
	 * @return
	 */
	public static NodeList findActionBlock( NodeList blockNodeList )
	{
		NodeList actionBlockNodeList = new NodeList();
		for ( int i = 0; i < blockNodeList.size(); i++ )
		{
			Node node = blockNodeList.elementAt( i );
			String nodeName = node.getClass().getSimpleName();
			String nodeHtml = node.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nodeText = WebCrawler.filterSpecialSymbol( node.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
//			System.out.printf( "\nTag=%s\nHtml=%s\nText=%s\n", nodeName, nodeHtml, nodeText );
			
			try
			{
				NodeList childrenNodeList = node.getChildren(); 
				if ( childrenNodeList == null )
					continue;
				
				for ( int j = 0; j < childrenNodeList.size(); j++ )
				{
					Node childNode = childrenNodeList.elementAt( j );
					String childNodeName = childNode.getClass().getSimpleName();
					String childText = WebCrawler.filterSpecialSymbol( childNode.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
					if ( childNodeName.equals( TextNode.class.getSimpleName() ) && childText.length() <= 0 )
						continue;
					
					// 找到第一層子結點是無效區塊
					String actionNodeName = "";
					if ( childNodeName.equals( InputTag.class.getSimpleName() ) )
					{
						InputTag inputTag = (InputTag)childNode;
						String typeAttr = inputTag.getAttribute( "type" );
						if ( typeAttr != null && 
								( typeAttr.equals( "image" ) || typeAttr.equals( "hidden" ) ) )
							actionNodeName = isChildrenAction( childNode );
					}
					else if ( childNodeName.equals( InputTag.class.getSimpleName() ) ||
							childNodeName.equals( FormTag.class.getSimpleName() ) ||
							childNodeName.equals( TextareaTag.class.getSimpleName() ) )
						actionNodeName = childNodeName;
					else if ( childNodeName.equals( TagNode.class.getSimpleName() ) )
					{
						if ( childNode.getText().toLowerCase().contains( HtmlTag.BUTTON_TAG ) )
							actionNodeName = childNode.getText();
					}
					// 遞迴往下找無效區塊
					else 
						actionNodeName = isChildrenAction( childNode );
					
//					if ( invalidNodeName.length() > 0 )
//						invalidBlockNodeList.add( node );
					
//					System.out.print( "\t" + childNodeName );
					if ( actionNodeName.length() > 0 )
					{
						actionBlockNodeList.add( node );
//						System.out.println( " -InvalidTag=" + invalidNodeName );
					}
//					printChildrenNode( childNode, 1 );
					/*
					if ( childNodeName.equals( TagNode.class.getSimpleName() ) &&
							( (TagNode)childNode ).isEndTag() == true )
						System.out.println("-End_Tag");
					else
						System.out.println("");
						*/
				}
			}
			catch ( NullPointerException e )
			{
				e.printStackTrace();
				// 該視覺區塊沒有任何子標籤。
			}
		}
		return actionBlockNodeList;
	}
	
	public static String isChildrenAction( Node node )
	{
		NodeList childrenNodeList = node.getChildren();
		if ( childrenNodeList == null )
			return "";
		for ( int i = 0; i < childrenNodeList.size(); i++ )
		{
			Node childNode = childrenNodeList.elementAt( i );
			String childNodeName = childNode.getClass().getSimpleName();
			if ( childNodeName.equals( InputTag.class.getSimpleName() ) )
			{
				InputTag inputTag = (InputTag)childNode;
				String typeAttr = inputTag.getAttribute( "type" );
				if ( typeAttr != null && 
						( typeAttr.equals( "image" ) || typeAttr.equals( "hidden" ) ) )
					return "";
			}
			else if ( childNodeName.equals( InputTag.class.getSimpleName() ) ||
					childNodeName.equals( FormTag.class.getSimpleName() ) ||
					childNodeName.equals( TextareaTag.class.getSimpleName() ) )
				return childNodeName;
			else if ( childNodeName.equals( TagNode.class.getSimpleName() ) )
			{
				if ( childNode.getText().toLowerCase().contains( HtmlTag.BUTTON_TAG ) )
					return childNode.getText();
			}
			
			String checkChildren = "";
			if ( childNode.getChildren() != null )
				checkChildren = isChildrenAction( childNode );
			
			if ( checkChildren.length() > 0 )
				return checkChildren;
		}
		return "";
	}

/**
	 * 累加節點和遞迴累加該節點下的所有子節點連結文字的長度。
	 * 
	 * @param node
	 * @return
	 */
	private static int getChildrenLinkTextLength( Node node )
	{
		int linkTextLength = 0;
		NodeList childrenNodeList = node.getChildren();
		if ( childrenNodeList == null )
			return linkTextLength;
		for ( int i = 0; i < childrenNodeList.size(); i++ )
		{
			Node childNode = childrenNodeList.elementAt( i );
//			System.out.println( "\t" + childNode.getClass().getSimpleName() );
			String childNodeName = childNode.getClass().getSimpleName();
			if ( childNodeName.equals( LinkTag.class.getSimpleName() ) )
			{
				LinkTag linkTag = (LinkTag)childNode;
				linkTextLength += WebCrawler.filterSpecialSymbol( linkTag.getLinkText() ).length();
//				System.out.println("LINK_TEXT: " + linkTag.getLinkText().replaceAll( "\\s+", " " ).trim() );
			} 
			
			if ( childNode.getChildren() != null )
				linkTextLength += getChildrenLinkTextLength( childNode );
		}
		return linkTextLength;
	}

	/**
	 * 檢查標籤是否包含一些非正文相關的class或id。
	 * @param node
	 * @return
	 */
	private static boolean containTrimClassID( CompositeTag node )
	{
		boolean contains = false;
		List<String> trimClassIDList = new ArrayList<String>();
		trimClassIDList.add( "push" );
		trimClassIDList.add( "pull" );
		trimClassIDList.add( "fb" );
		trimClassIDList.add( "counter" );
		trimClassIDList.add( "comment" );
		trimClassIDList.add( "postcontent" );
		trimClassIDList.add( "widget" );
		trimClassIDList.add( "rank" );
		trimClassIDList.add( "posted" );
		trimClassIDList.add( "selectbar" );
		trimClassIDList.add( "keyw" );
		trimClassIDList.add( "footer" );
		trimClassIDList.add( "toolbar" );
		trimClassIDList.add( "header" );
		trimClassIDList.add( "claimstatement" );
		trimClassIDList.add( "credit_text" );
		trimClassIDList.add( "permanent_url" );
		
		String className = node.getAttribute( "class" );
		if ( className == null ) className = "";
		String idName = node.getAttribute( "id" );
		if ( idName == null ) idName = "";
		
		for ( String classID : trimClassIDList )
		{
			if ( className.contains( classID ) || idName.contains( classID ) )
			{
				contains = true;
				break;
			}
		}
		return contains;
	}

	private static Node printVisualBlock( String url ) throws ParserException, UnsupportedEncodingException
	{
		NodeList divParagraphNodeList = WebCrawler.getNodeList( url ); 
//			WebCrawler.getNodeList( url, new OrFilter( BLOCK_FILTER ) );

		// 歷遍所有視覺區塊的Node
		for ( int i = 0; i < divParagraphNodeList.size(); i++ )
		{
			Node node = divParagraphNodeList.elementAt( i );
			String nodeName = node.getClass().getSimpleName();
			String nodeHtml = node.toHtml().replaceAll( "\\s+", " " ).toLowerCase().trim();
			String nodeText = WebCrawler.filterSpecialSymbol( node.toPlainTextString().replaceAll( "\\s+", " " ).trim() ).trim();
			System.out.printf( "Tag=%s\nHtml=%s\nText=%s\n", nodeName, nodeHtml, nodeText );

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
				for ( int t = 1; t <= level; t++ )
					System.out.print( "    " );
				System.out.printf( "*Tag=%s\n", childNodeName );
				for ( int t = 1; t <= level; t++ )
					System.out.print( "    " );
				System.out.printf("Html=%s\n", childNode.getText().replaceAll( "\\s+", " " ).trim() );
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
