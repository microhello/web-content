package com.gravitygroups.webcontent;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.gravitygroups.io.FileUtils;

public class WebContentParserTest
{
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
//		getEmptyResult();
		checkDiff();
	}
	
	private static void getEmptyResult()
	{
		try
		{
			File datFile = new File( "dat/web_content/article_url/2.dat" );
			List<String> visitedURLList = new ArrayList<String>();
			List<String> urlList = FileUtils.inputAsStringList( datFile.getAbsolutePath() );
			PrintWriter output = new PrintWriter( new File( "output/web_content/2-old-empty.html" ) );
			for ( int i = 0; i < urlList.size(); i++ )
			{
				String url = urlList.get( i );
				if ( visitedURLList.contains( url ) )
					continue;
				try
				{
					System.out.printf("%d. %s\n", i, url );
					visitedURLList.add( url );
					
					BlockProperties oldContentProp = WebContentParserOld.parseWebContent( url );
					if ( oldContentProp.getBlockText().isEmpty() )
					{
						oldContentProp.print();
						
						output.println( i + ".<a href='"+ url + "' target='_blank'>" + url + "</a><br>" );
						output.println( oldContentProp.getBlockText() + " <br> " );
						output.flush();
					}
					System.out.println("");
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
			if ( output != null )
				output.close();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private static void checkDiff()
	{
		try
		{
			File datFile = new File( "dat/web_content/article_url/2.dat" );
			List<String> visitedURLList = new ArrayList<String>();
			List<String> urlList = FileUtils.inputAsStringList( datFile.getAbsolutePath() );
			PrintWriter output = new PrintWriter( new File( "output/web_content/2-test-new-new-new.html" ) );
			for ( int i = 0; i < urlList.size(); i++ )
			{
				String url = urlList.get( i );
				if ( visitedURLList.contains( url ) )
					continue;
				try
				{
					System.out.printf("%d. %s\n", i, url );
					visitedURLList.add( url );
					
					BlockProperties oldContentProp = WebContentParserOld.parseWebContent( url );
					BlockProperties contentProp = WebContentParser.parseWebContent( url );
					if ( !oldContentProp.getBlockText().equals( contentProp.getBlockText() ) &&
							oldContentProp.getBlockText().replaceAll( "\\s+", "" ).trim().length() !=
								contentProp.getBlockText().replaceAll( "\\s+", "" ).trim().length() )
					{
						System.out.println("舊:");
						oldContentProp.print();
						System.out.println("新:");
						contentProp.print();
						
						output.println( i + ".<a href='"+ url + "' target='_blank'>" + url + "</a><br>" );
						output.println( "OLD:" + oldContentProp.getBlockText() + " <br> " );
						output.println( "NEW:" + contentProp.getBlockText() + " <br> " );
						output.flush();
					}
					System.out.println("");
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
			if ( output != null )
				output.close();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
