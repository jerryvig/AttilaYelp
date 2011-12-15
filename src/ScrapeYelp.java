import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.html.HtmlHeading4;
import com.gargoylesoftware.htmlunit.html.HtmlAddress;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import java.net.URL;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import au.com.bytecode.opencsv.CSVReader;
import java.net.URLEncoder;

public class ScrapeYelp {
  public static void main( String[] args ) {
      scrapeGoogleListPages();
      getGOOGProfilePages();
      //scrapeListPages();
  }

  public static void scrapeGoogleListPages() {
   try {
     BufferedReader fh = new BufferedReader( new FileReader("./YelpCategories.csv") );
     BufferedWriter fh2 = new BufferedWriter( new FileWriter("./YelpScrapeDataMONTREAL.csv") );
     ArrayList<String> lines = new ArrayList<String>();
     String s;
     while ( (s = fh.readLine()) != null ) {
	 lines.add( s.trim() );
     }

     ArrayList<String[]> categories = new ArrayList<String[]>();
     for ( String line : lines ) {
	String[] cols = line.split(",");
        categories.add( cols ); 
     }

     WebClient wc = new WebClient( BrowserVersion.FIREFOX_3 );
     wc.setJavaScriptEnabled( false );     
     WebClient wcII = new WebClient( BrowserVersion.FIREFOX_3 );
     wcII.setJavaScriptEnabled( false );

     for ( String[] cats : categories ) {
       String searchString = cats[1].trim();
       for ( int i=0; i<800; i+=10 ) {
         try {
               Thread.sleep( 280 );
         }
         catch ( InterruptedException ie ) { ie.printStackTrace(); }

         String businessName = "";
	 String categoryString = "";
	 String phoneNumber = "";
	 String hoods = "";
	 String mediaStory = "";
	 String addressText = "";
         String bizLink = ""; 

         String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.ca%2Fsearch%3Fcflt%3D"+URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DMontr%25C3%25A9al%252C%2BQC%26start%3D"+Integer.toString(i);

         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DPittsburgh%252C%2BPA%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DRichmond%252C%2BVA%26start%3D"+Integer.toString(i);
	 // String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DOrlando%252C%2BFL%26cflt%3D"+URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DCharleston%252C%2BSC%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DBirmingham%252C%2BAL%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26find_dInteger.toString(i);esc%3D%26find_loc%3DSaint%2BLouis%252C%2BMO%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DMemphis%252C%2BTN%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Fcflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26find_desc%3D%26find_loc%3DBaton%2BRouge%252C%2BLA%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DDes%2BMoines%252C%2BIA%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DFresno%252C%2BCA%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DDenver%252C%2BCO%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_loc%3DHouston%252C%2BTX%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_loc%3DAustin%252C%2BTX%26cflt%3D"+java.net.URLEncoder.encode(searchString)+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_desc%3D%26find_loc%3DDallas%252C%2BTX%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_loc%3D"+java.net.URLEncoder.encode(searchString)+"%252C%2BTX%26cflt%3Dchinese%26start%3D"+Integer.toString(i);
         //String urlString = "http://google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_loc%3DTucson%252C%2BAZ%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
	 //String urlString = "http://www.google.com/gwt/x?u=http%3A%2F%2Fwww.yelp.com%2Fsearch%3Ffind_loc%3DSan%2BDiego%252C%2BCA%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
         //String urlString = "http://www.google.com/gwt/x?wsc=pr&source=wax&u=http%3A%2F%2Fwww.yelp.com/search%3Ffind_loc%3DPhoenix%252C%2BCA%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i);
	 //String urlString = "http://www.google.com/gwt/x?wsc=pr&wsi=9ed4d81435631c1b&source=wax&u=http%3A%2F%2Fwww.yelp.com/search%3Ffind_loc%3DLos%2BAngeles%252C%2BCA%26cflt%3D"+java.net.URLEncoder.encode(searchString.toLowerCase())+"%26start%3D"+Integer.toString(i)+"&ei=owy5Ts6FIIuerAOcrfjcBQ";
         System.out.println( urlString );        
         try {
	  HtmlPage page = wc.getPage( urlString );
          HtmlElement docElement = page.getDocumentElement();
          
          List<HtmlSpan> spanList = docElement.getHtmlElementsByTagName("span");
          for ( int j=0; j<spanList.size(); j++ ) {
	      HtmlSpan  span = spanList.get(j);
	    if ( span.getAttribute("style").equals("color:#6666cc;") ) {
	      String spanText = span.asText().trim();
              if ( spanText.contains("1.") || spanText.contains("2.") || spanText.contains("3.") || spanText.contains("4.") || spanText.contains("5.") || spanText.contains("6.") || spanText.contains("7.") || spanText.contains("8.") || spanText.contains("9.") || spanText.contains("0.") ) {
		  String[] pieces = spanText.split(Pattern.quote("."));
                  try {
		     businessName = pieces[1].trim();
                     HtmlElement anchor = (HtmlElement)(span.getParentNode());
                     bizLink = "http://www.google.com" + anchor.getAttribute("href").trim();
                  } catch ( IndexOutOfBoundsException ie ) { ie.printStackTrace(); }
              }
            }
            if ( span.getAttribute("style").equals("color:#555555;") ) {
	      String spanText = span.asText().trim();
              if ( spanText.startsWith("Category:") ) {
		 categoryString = span.getNextSibling().asText().trim();
              }
              if ( spanText.startsWith("(") ){
		  phoneNumber = spanText.trim();
                  addressText = spanList.get(j-2).asText().trim() + ", " + spanList.get(j-1).asText().trim();
                  System.out.println( "\"" + businessName + "\",\"" + categoryString + "\",\"" + hoods + "\",\"" + phoneNumber + "\",\"" + "\",\"" + addressText + "\",\"" + bizLink + "\"\n" );
                  try {
                   fh2.write( "\"" + businessName + "\",\"" + categoryString + "\",\"" + hoods + "\",\"" + phoneNumber + "\",\"" + "\",\"" + addressText + "\",\"" + bizLink + "\"\n" );                  
                  } catch ( IOException ioe ) { ioe.printStackTrace(); }

                  businessName = "";
	          categoryString = "";
	          phoneNumber = "";
	          hoods = "";
	          mediaStory = "";
	          addressText = "";
                  bizLink = ""; 
              } 
            }
            if ( span.getAttribute("style").equals("color:#555555;") ) {
	      String spanText = span.asText().trim();
              if ( spanText.startsWith("Neighborhood:") ) {
		  hoods = span.getNextSibling().asText().trim();
              }
            }
              
          }
         

         } catch ( Exception e ) { e.printStackTrace(); }
       }
     }
     
     fh2.close();
     fh.close();
   } catch ( IOException ioe ) { ioe.printStackTrace(); }
  }

  public static void scrapeListPages() {
   try {
     BufferedReader fh = new BufferedReader( new FileReader("./YelpCategories.csv") );
     BufferedWriter fh2 = new BufferedWriter( new FileWriter("./YelpScrapeDataLA.csv") );
     
     ArrayList<String> lines = new ArrayList<String>();
     String s;
     while ( (s = fh.readLine()) != null ) {
	 lines.add( s.trim() );
     }

     ArrayList<String[]> categories = new ArrayList<String[]>();
     for ( String line : lines ) {
	String[] cols = line.split(",");
        categories.add( cols ); 
     }

     WebClient wc = new WebClient( BrowserVersion.FIREFOX_3 );
     //DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) wc.getCredentialsProvider();
     //credentialsProvider.addProxyCredentials("anonymous", "anonymous");
     wc.setJavaScriptEnabled( false );     
     WebClient wcII = new WebClient( BrowserVersion.FIREFOX_3 );
     wcII.setJavaScriptEnabled( false );

     for ( String[] cats : categories ) {
       String searchString = cats[1].trim();
       for ( int i=0; i<1000; i+=40 ) {
	 
	  String urlString =  "http://www.yelp.com.nyud.net/search/snippet?attrs=&cflt=" + searchString + "&find_desc=&find_loc=Los+Angeles,+CA&main_places=CA:Los_Angeles::Downtown,CA:Los_Angeles::Hollywood,CA:Los_Angeles::Pasadena,CA:Los_Angeles::Glendale&mapsize=small&rpp=40&show_filters=0&sortby=best_match&start=" + Integer.toString(i);
	 //String urlString = "http://www.yelp.com.nyud.net/search/snippet?attrs=&cflt=&find_desc=" + searchString + "&find_loc=Las+Vegas,+NV&main_places=NV:Las_Vegas::Southeast,NV:Las_Vegas::Westside,NV:Las_Vegas::Eastside,NV:Las_Vegas::Spring_Valley&mapsize=small&rpp=40&show_filters=1&sortby=best_match&start=" + Integer.toString(i);

         System.out.println( urlString );
         HtmlPage page = null;
         try {

	    page = wc.getPage( urlString );
            String jsonString = page.getWebResponse().getContentAsString().trim();
            JSONObject jsonObj = new JSONObject( jsonString );
            JSONArray nameArray = jsonObj.names();
            JSONArray valArray = jsonObj.toJSONArray( nameArray );
          
            BufferedWriter outfile = new BufferedWriter( new FileWriter("./output.html") );
            outfile.write( "<html><body>"+valArray.getString(0).trim()+"</body></html>");
            outfile.close();            

            HtmlPage htmPage = wcII.getPage( "file:///tmp/AttilaParajdi/Yelp/ant/output.html" );
            List<HtmlDivision> divList = htmPage.getDocumentElement().getHtmlElementsByTagName("div");
            for ( HtmlDivision div : divList ) {
	      if ( div.getAttribute("class").equals("businessresult clearfix") ) {
		String businessName = "";
		String categoryString = "";
		String phoneNumber = "";
		String hoods = "";
		String mediaStory = "";
		String addressText = "";
                String bizLink = ""; 

                List<HtmlHeading4> h4List = div.getHtmlElementsByTagName("h4");
                for ( HtmlHeading4 h4 : h4List ) {
		  if ( h4.getAttribute("class").equals("itemheading") ) {
		     String itemHeading = h4.asText().trim();
		     businessName = itemHeading.split(Pattern.quote("."))[1].trim();
                     List<HtmlAnchor> aList = h4.getHtmlElementsByTagName("a");
                     for ( HtmlAnchor a : aList ) {
		       if ( a.getAttribute("id").contains("bizTitleLink") ) {
			  bizLink = a.getHrefAttribute().trim();
                       }   
                     }
		  }
                }
                List<HtmlDivision> childDivList = div.getHtmlElementsByTagName("div");
                for ( HtmlDivision childDiv : childDivList ) {
		  if ( childDiv.getAttribute("class").equals("itemcategories") ) {
		    categoryString = childDiv.asText().split(Pattern.quote(":"))[1];
		  }
                  if ( childDiv.getAttribute("class").equals("phone") ) {
		      phoneNumber = childDiv.asText().trim();
		  }
		  if ( childDiv.getAttribute("class").equals("itemneighborhoods") ) {
		      hoods = childDiv.asText().trim();
		  }
		  /* if ( childDiv.getAttribute("class").equals("media-story") ) {
		      mediaStory = childDiv.asText().trim();
		      } */
                }
                List<HtmlAddress> addyList = div.getHtmlElementsByTagName("address");
                for ( HtmlAddress addy : addyList ) {
		    addressText = addy.asText().trim();
                }
 
               System.out.println( "\"" + businessName + "\",\"" + categoryString + "\",\"" + hoods + "\",\"" + phoneNumber + "\",\"" + "\",\"" + addressText + "\",\"" + bizLink + "\"\n" );
               fh2.write( "\"" + businessName + "\",\"" + categoryString + "\",\"" + hoods + "\",\"" + phoneNumber + "\",\"" + "\",\"" + addressText + "\",\"" + bizLink + "\"\n" );
              }
            }          
         } catch ( Exception e ) { e.printStackTrace(); }
        
       }
     }

     fh2.close();
     fh.close();
   } catch ( IOException ioe ) { ioe.printStackTrace(); }
  }

  public static void getProfilePages() {
   try {
     CSVReader fh = new CSVReader( new FileReader("./YelpScrapeData4.csv") ); 
     WebClient wc = new WebClient( BrowserVersion.FIREFOX_3 );
     wc.setJavaScriptEnabled( false );
     BufferedWriter fh2 = new BufferedWriter( new FileWriter("./YelpDataWithLinks4.csv") );

     String[] cols;
     while ((cols = fh.readNext())!=null) {
        Thread.sleep( 333 );
	String businessUrl = "";
        String urlString = "http://www.yelp.com"+cols[6].trim();
	//String urlString = "http://www.zendproxy.com/includes/process.php?action=update";
        WebRequestSettings rSettings = new WebRequestSettings( new URL(urlString), HttpMethod.GET );
        //rSettings.setRequestParameters( new ArrayList() );
        //rSettings.getRequestParameters().add( new NameValuePair("u","http://yelp.com"+cols[6].trim()) );
        //rSettings.getRequestParameters().add( new NameValuePair("encodeURL","on") );
	// rSettings.getRequestParameters().add( new NameValuePair("allowCookies","on") );
        //rSettings.getRequestParameters().add( new NameValuePair("stripJS","on") );

        HtmlPage profilePage = null;
        try {
          profilePage = wc.getPage( rSettings );
        } catch ( com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException fhsce ) { fhsce.printStackTrace(); }
        List<HtmlDivision> divList = profilePage.getDocumentElement().getHtmlElementsByTagName("div");
        for ( HtmlDivision div : divList ) {
	  if ( div.getAttribute("id").equals("bizUrl") ) {
	     List<HtmlAnchor> aList = div.getHtmlElementsByTagName("a");
             for ( HtmlAnchor a : aList ) {
	       if ( a.getAttribute("class").equals("url") ) {
		  businessUrl = a.asText().trim();
	       }
             }
          }  
        }
        System.out.println( "\"" + cols[0] + "\",\"" + cols[1] + "\",\"" + cols[2] + "\",\"" + cols[3] + "\",\"" + cols[4] + "\",\"" + cols[5] + "\",\"" + cols[6] + "\",\"" + businessUrl + "\"" );
        fh2.write( "\"" + cols[0] + "\",\"" + cols[1] + "\",\"" + cols[2] + "\",\"" + cols[3] + "\",\"" + cols[4] + "\",\"" + cols[5] + "\",\"" + cols[6] + "\",\"" + businessUrl + "\"\n" );

     }
			    
     fh.close();
   } catch ( IOException ioe ) { ioe.printStackTrace(); }
   catch ( InterruptedException ie ) { ie.printStackTrace(); }
  }

 public static void getGOOGProfilePages() {
	try {
	   CSVReader fh = new CSVReader( new FileReader("./YelpScrapeDataMONTREAL.csv") );
	   WebClient wc = new WebClient( BrowserVersion.FIREFOX_3 );
	   wc.setJavaScriptEnabled( false );
	   BufferedWriter fh2 = new BufferedWriter( new FileWriter("./YelpScrapeDataWithLinksMONTREAL.csv") );
	   String[] cols;
	   while ((cols = fh.readNext())!=null) {
		try {
		Thread.sleep( 285 );
		String businessUrl = "";
                String urlString = cols[6].trim();
		HtmlPage profilePage = null;
		try {
		    profilePage = (HtmlPage) wc.getPage( urlString );
		} catch ( com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException fhsce ) { fhsce.printStackTrace(); }
		catch ( ClassCastException cce ) { cce.printStackTrace(); continue; }
		List<HtmlAnchor> aList = profilePage.getDocumentElement().getHtmlElementsByTagName("a");
		for ( HtmlAnchor a : aList ) {
		    if ( a.getHrefAttribute().contains("/gwt/x") ) {
			String aText = a.asText().trim();
			if ( aText.startsWith("www.") ) {
			    businessUrl = aText.trim();
			}
		    }
		}
		System.out.println( "\"" + cols[0] + "\",\"" + cols[1] + "\",\"" + cols[2] + "\",\"" + cols[3] + "\",\"" + cols[4] + "\",\"" + cols[5] + "\",\"" + cols[6] + "\",\"" + businessUrl + "\"" );
		fh2.write( "\"" + cols[0] + "\",\"" + cols[1] + "\",\"" + cols[2] + "\",\"" + cols[3] + "\",\"" + cols[4] + "\",\"" + cols[5] + "\",\"" + cols[6] + "\",\"" + businessUrl + "\"\n" );
		} catch ( java.net.MalformedURLException me ) { me.printStackTrace(); }
	    }
            fh2.close();
	} catch ( IOException ioe ) { ioe.printStackTrace(); }
	catch ( InterruptedException ie ) { ie.printStackTrace(); }
    }
}