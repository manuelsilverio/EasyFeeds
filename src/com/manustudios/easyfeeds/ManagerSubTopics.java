package com.manustudios.easyfeeds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


import android.app.Activity;

public class ManagerSubTopics extends Activity{

	public static final String KEY_IMG_SUBTOPIC = "img";
	public static final String KEY_URL_SUBTOPIC = "url";
	public static final String GOOGLE_API_LOAD = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=";
	public static final String TAG = "EasyNews TAG";
	
	protected JSONObject jsonData = null;
	
	
	//HashMap<String, String> mTech = new HashMap<String, String>();
	
	
		
	String[] mTechFeedUrl = {
				"http://www.theverge.com/tech/rss/index.xml",			//THE VERGE
			    "http://www.engadget.com/rss.xml",						//ENGADGET
			   // "http://www.lifehack.org/feed",							//LIFEHACK
			    "http://feeds.gawker.com/gizmodo/full",					//GIZMODO
			    "https://www.yahoo.com/tech/rss",						//YAHOO
			    "http://www.wired.com/feed/",							//WIRED
			    "http://feeds.mashable.com/Mashable",					//MASHABLE
			    "http://feeds.gawker.com/lifehacker/full",				//LIFEHACKER
			    "http://feeds2.feedburner.com/thenextweb",				//THENEXTWEB
			    "http://feeds.arstechnica.com/arstechnica/index/",		//ARSTECHNICA
			    "http://readwrite.com/main/feed/articles.xml"			//READWRITE
				};
		
	String[] mTechImgUrl = {
			"http://cdn0.vox-cdn.com/community_logos/35024/verge-logo-xl.jpg",											//THE VERGE
		    "http://img.engadget.com/common/images/0254949859537138.gif",												//ENGADGET
		    //"http://quotes.lifehack.org/static/images/share_logo.png",												//LIFEHACK
		    "http://www.userlogos.org/files/logos/Han%20Hong/gizmodo.gif",												//GIZMODO
		    "https://pbs.twimg.com/profile_images/378800000416597131/80143484834bddab9a2808c0da5b3fe4.jpeg",			//YAHOO
		    "http://cdni.wired.co.uk/620x413/w_z/wired_620x413.jpg",													//WIRED
		    "http://tabtimes.com/sites/default/files/mashable.png",														//MASHABLE
		    "http://cdn.marketplaceimages.windowsphone.com/v8/images/2bdb2c3c-1722-489e-be84-fd4855d68a72?imageType=ws_icon_large",			//LIFEHACKER
		    "http://www.padgadget.com/wp-content/uploads/2012/10/The-Next-Web-Logo.jpeg",								//THENEXTWEB
		    "https://www.privacyinternational.org/sites/privacyinternational.org/files/styles/media_article_pub_logo/public/media-articles/publisher-logo/ars-logo.png",			//ARSTECHNICA
		    "http://old.shareasimage.com/images/readwriteweb.jpg"														//READWRITE
			};

	String[] mGamingFeedUrl = {
			"http://www.polygon.com/rss/index.xml",		//Polygon
			"http://feeds.gawker.com/kotaku/full",		//Kotaku
			"http://www.joystiq.com//rss.xml",			//Joystiq
			"http://indiegames.com/atom.xml",			//IndieGames
			"http://www.rockpapershotgun.com/feed/"		//Rock,Paper,Shotgun
			};

	String[] mGamingImgUrl = {
			"http://images.eurogamer.net/2012/articles//a/1/5/2/3/2/9/7/eurogamer-51yuqx.jpg",		//Polygon
			"http://gamasutra.com/db_area/images/news2001/38236/kotakuLogo.gif",					//Kotaku
			"http://www.userlogos.org/files/logos/dk00111/Joystiq(Glow).png",						//Joystiq
			"http://xona.com/2009/05/images/indiegamesblog_logo.png",								//IndieGames
			"http://www.gamepolitics.com/files/blogimages/rps_2.jpg"								//Rock,Paper,Shotgun
			};

	String[] mMoviesFeedUrl = {
			"http://www.empireonline.com/rss/rss.asp",			//Empire News
			"http://feeds.feedburner.com/comingsoonnet30",		//comingsoon.net
			"http://feeds.feedburner.com/themovieboxnet/",		//The movie box
			};

	String[] mMoviesImgUrl = {
			"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR8kmdESzzir2lfW2dJE4wL_qb0H12xubFLox_laPALqEn6qrQZ",			//Empire News
			"http://www.userlogos.org/files/logos/Rog/comingsoon.com-02.png",													//comingsoon.net
			"http://img.themoviebox.net/zplace/upage.jpg",																		//The movie box
			};

	String[] mInspirationFeedUrl = {
			"http://tedxtalks.ted.com/feed/magnify.rss/recent",		//TEDTalks
			"http://zenhabits.net/feed/",							//Zenhabits
			"http://www.fubiz.net/en/feed/",						//Fubiz
			"http://feedproxy.google.com/brainpickings/rss",		//Brain Pickings
			"http://feeds.labnol.org/labnol",						//Digital Inspiration Tech Blog
			"http://www.thisiscolossal.com/feed/",					//Colossal
			"http://99u.com/feed"									//99u
			};		
			
	String[] mInspirationImgUrl = {
			"http://techmaharaja.com/wp-content/uploads/2014/01/ted_talks_logo.jpg",										//TEDTalks
			"http://www.lifeofbrian.ca/wp-content/uploads/2010/10/SP32-20101026-160057.png",								//Zenhabits
			"http://d1r5i20o8cadcu.cloudfront.net/designs/images/111116/original/logofubiz-qaljds.jpg",						//Fubiz
			"http://www.brainpickings.org/wp-content/uploads/2011/05/brainpickings_250x250.png",							//Brain Pickings
			"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQI1EzPcUtJLk6cZ4Ep611ZNY6rZtj9a_n4yG5l9LrxC3PXIf-H",		//Digital Inspiration Tech Blog
			"http://static.tumblr.com/9fdbd0fdeaeeadced46dae6ef06d3397/tl1lb4o/RmUn3c2xi/tumblr_static_tumblr.png",			//Colossal
			"http://t2ps.com/thepossibilityplace/wp-content/uploads/2012/09/99u-200x200.jpeg"								//99u
			};				
			
	String[] mFashionFeedUrl = {
			"http://www.stylebubble.co.uk/feed",				//Style Bubble
			"http://www.theblondesalad.com/feed",				//The Blonde Salad
			"http://www.fashionsquad.com/en/feed/",				//Fashion Squad
			"http://feeds.feedburner.com/fashionistacom",		//Fashionista
			"http://www.vogue.nl/rss",							//The Vogue Blog
			"http://feeds.dailycandy.com/dailycandyew",			//DailyCandy
			"http://becauseimaddicted.net/feed/atom",			//Because I'm addicted
			"http://www.coolhunting.com/index.xml",				//Cool Hunting
			"http://feeds2.feedburner.com/TheArtOfManliness",	//The Art of Manliness
			"http://feeds.feedburner.com/uncrate"				//Uncrate
			};	

	String[] mFashionImgUrl = {
			"http://www.bonigala.com/images/blog/images/Style-Bubble-logo-design.jpg",										//Style Bubble
			"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSMR3YUh_qTu23OoPf1QkpuGnKV68mwUMEgUl-GK5-gagy44xVIGg",	//The Blonde Salad
			"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTTeeJslYVYVUnQzhW-FdNQGVKk-djxAeqvYJYh1hMu_kxUZXVZ",		//Fashion Squad
			"http://www.melissajoymanning.com/blog/wp-content/uploads/fashionista-logo.jpg",								//Fashionista
			"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSulwf0OpVykmNHgKMw15L7oy0ZvWKdjwXtD5P5CkqSt6lNWKl5",		//The Vogue Blog
			"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwS9f1evJNQlEP4MY-OiAzPC7Y3LodpdfKN9gdUYMJf3A-LuT-",		//DailyCandy
			"http://becauseimaddicted.net/wp-content/themes/because/images/logo.png",										//Because I'm addicted
			"http://blog.mailchimp.com/wp-content/uploads/2012/02/cool_hunting_logo.jpg",									//Cool Hunting
			"http://imglogo.podbean.com/image-logo/184724/aomlogo.jpg",														//The Art of Manliness
			"http://www.userlogos.org/files/logos/ugn/uncrate.png"															//Uncrate
			};						
			
	String[] mScienceFeedUrl = {
			"http://feeds.nationalgeographic.com/ng/News/News_Main",	//National Geographic news
			"http://www.wired.com/feed/",								//Wired Science
			"http://www.sciencemag.org/rss/current.xml",				//Science mag
			"http://feeds.newscientist.com/science-news",				//New Scientist - Online News
			"http://www.popsci.com/galleries/feed",						//Popular Science
			"http://feeds.feedburner.com/realclimate/HYVV",				//Real Climate
			"http://rss.sciam.com/ScientificAmerican-Global"			//Scientific American content: Global
			};	
	
	String[] mScienceImgUrl = {
			"http://images.nationalgeographic.com/wpf/sites/common/i/presentation/NGLogo560x430-cb1343821768.png",	//National Geographic news
			"http://ilovenewwork.com/wp-content/uploads/2007/10/wired_science.jpg",								//Wired Science
			"https://www.aegeanconferences.org/aegeanconferences/filemanager/SponsorLogos/science.gif",				//Science mag
			"https://airingnews.s3.amazonaws.com/publishers/images/5a29f72f560440ba86462f0f0e28791e_original.jpg",				//New Scientist - Online News
			"http://www.dickjones.com/sites/default/files/styles/featured_in/public/Popular-science-Logo1.jpg",						//Popular Science
			"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcT-jqd45UM4qSupHGc-scfN_FKx2vVS-npLA7xwijrlHQDFqAL6",				//Real Climate
			"http://www.stern.nyu.edu/cons/groups/content/documents/webasset/con_032572.jpg"			//Scientific American content: Global
			};

	String[] mCookingFeedUrl = {
			"http://feeds.feedburner.com/smittenkitchen",			//Smitten Kitchen 
			"http://feedproxy.google.com/elise/simplyrecipes",		//Simply Recipes
			"http://feeds.feedburner.com/seriouseats/recipes",		//Serious Eats 
			"http://blog.foodnetwork.com/fn-dish/feed/",			//Food Network Blog
			"https://food52.com/blog.rss",							//Food 52 
			"http://chocolateandzucchini.com/feed/",				//Chocolate & Zucchini
			"http://thepioneerwoman.com/cooking/feed/",				//The Pioneer Woman Cooks 
			"http://www.loveandoliveoil.com/feed"					//Love and Olive Oil 
			};	

	String[] mCookingImgUrl = {
			"http://smittenkitchen.com/uploads/smittenkitchentrademarkedlogo.jpg",											//Smitten Kitchen 
			"https://ideasinspiringinnovation.files.wordpress.com/2010/03/logo_simply-recipes_us-1.jpg?w=140&h=140",		//Simply Recipes
			"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSGZ2CqwJ16Bgn9V6hZRn-gCD5yyce-yE0BvMSw7_MK2yZwiUFkgw",	//Serious Eats 
			"http://foodnetwork.sndimg.com/etc/designs/food/clientlib/img/fn-logo.png",										//Food Network Blog
			"http://s3.amazonaws.com/lerer-ventures/company/4/Food52_Logo_lowres_small.png",								//Food 52 
			"http://chocolateandzucchini.com/wp-content/themes/candz/images/logo.png",										//Chocolate & Zucchini
			"http://weelicious.com/wp-content/uploads/2012/03/The-Pioneer-Woman-Cooks.jpg",									//The Pioneer Woman Cooks 
			"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSM_NwzOx2TzsCzMo6CRAwnXf68WfaDgbtAJprt4CwNf7r2pY7q"		//Love and Olive Oil 
			};	

	String[] mBusinessFeedUrl = {
			"http://sethgodin.typepad.com/seths_blog/atom.xml",		//Seth Godin  
			"http://feeds.harvardbusiness.org/harvardbusiness",		//HBR.org (Harvard Business)
			"http://feeds2.feedburner.com/businessinsider",			//Business Insider 
			"http://www.fastcompany.com/rss.xml",					//Fast Company
			"http://feeds.feedburner.com/TheAtlantic",				//Business: The Atlantic 
			"http://feeds.venturebeat.com/VentureBeat",				//VentureBeat
			"http://feeds.inc.com/home/updates"						//Inc.com 
			};

	String[] mBusinessImgUrl = {
			"http://blog.hubspot.com/Portals/249/images/seth-godin.png",											//Seth Godin  
			"http://selfcontrol.psych.lsa.umich.edu/wp-content/uploads/2014/02/Harvard-Business-Review-Logo.jpg",	//HBR.org (Harvard Business)
			"http://www.technologywoman.com/wp-content/uploads/2011/03/business-insider.jpg",						//Business Insider 
			"http://mb4mh.files.wordpress.com/2011/12/fast-company-logo.gif",										//Fast Company
			"http://salliebingham.com/wp-content/uploads/TheAtlantic.jpg",											//Business: The Atlantic 
			"http://karmaplatform.com/wp-content/uploads/2013/11/VB_logo.jpg",										//VentureBeat
			"http://network.intuit.com/wp-content/uploads/2012/04/Inc-Magazine-Logo1.jpg"							//Inc.com 
			};


	String[] mDoItYourselfFeedUrl = {
			"http://makezine.com/feed/",										//Make  
			"http://www.ikeahackers.net/feed",									//Ikea Hackers 
			"http://feeds2.feedburner.com/hackaday/LgoM",						//Hackaday 
			"http://www.instructables.com/tag/type-id/featured-true/rss.xml",	//Instructables
			//"http://feeds.feedburner.com/doityourselfdiy",						//DoItYourself.com
			"http://www.adafruit.com/blog/feed/",								//Adafruit Industries Blog
			"http://ohhappyday.com/feed/"										//Oh Happy Day!
			};

	String[] mDoItYourselfImgUrl = {
			"http://baselandscape.com/wp-content/uploads/2012/03/makezine-logo.jpg",													//Make  
			"http://designforeveryone.howest.be/input/images/stories/ikea_hacker.jpg",													//Ikea Hackers 
			"http://hackadaycom.files.wordpress.com/2008/09/hackaday_large.png",																				//Hackaday 
			"http://www.latestlesson.org/wp-content/uploads/2013/04/instructables-300x225.jpeg",										//Instructables
			//"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/t1.0-1/p160x160/48115_10151382851888494_1385386507_a.jpg",			//DoItYourself.com
			"http://www.gotron.be/media/catalog/category/ADAlogo.jpg",																	//Adafruit Industries Blog
			"http://2.bp.blogspot.com/-wWdiqt0DQFg/Tor1oAozDeI/AAAAAAAACsQ/swMI4P2a2WA/s1600/Amanda+Jane+Jones+for+Oh+Happy+Day.png"	//Oh Happy Day!
			};	
	
	public ArrayList<HashMap<String, String>> getSubTopics(int position){
		ArrayList<HashMap<String, String>> subTopicsList = new ArrayList<HashMap<String, String>>();
		switch (position) {
		case 0:
			subTopicsList =  getTechSubTopics();
			break;
		case 1:
			subTopicsList =  getScienceSubTopics();
			break;
		case 2:
			subTopicsList =  getInspirationSubTopics();
			break;
		case 3:
			subTopicsList =  getBusinessSubTopics();
			break;
		case 4:
			subTopicsList =  getMoviesSubTopics();
			break;
		case 5:
			subTopicsList =  getGamingSubTopics();
			break;
		case 6:
			subTopicsList =  getCookingSubTopics();
			break;
		case 7:
			subTopicsList =  getDoItYourselfSubTopics();
			break;	
		case 8:
			subTopicsList =  getFashionSubTopics();
			break;	
		default:
			break;
		}
		
		return subTopicsList;
	}
	
	
	private ArrayList<HashMap<String, String>> getTechSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mTechFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mTechFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mTechImgUrl[i]);
			mSections.add(subTopicMap);
		}
	
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getScienceSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mScienceFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mScienceFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mScienceImgUrl[i]);
			mSections.add(subTopicMap);
		}
	
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getInspirationSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mInspirationFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mInspirationFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mInspirationImgUrl[i]);
			mSections.add(subTopicMap);
		}
	
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getBusinessSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mBusinessFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mBusinessFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mBusinessImgUrl[i]);
			mSections.add(subTopicMap);
		}
	
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getMoviesSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mMoviesFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mMoviesFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mMoviesImgUrl[i]);
			mSections.add(subTopicMap);
		}
	
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getGamingSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mGamingFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mGamingFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mGamingImgUrl[i]);
			mSections.add(subTopicMap);
		}
		
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getCookingSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mCookingFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mCookingFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mCookingImgUrl[i]);
			mSections.add(subTopicMap);
		}
		
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getDoItYourselfSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mDoItYourselfFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mDoItYourselfFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mDoItYourselfImgUrl[i]);
			mSections.add(subTopicMap);
		}
		
		return mSections;
	}
	
	private ArrayList<HashMap<String, String>> getFashionSubTopics(){
		ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < mFashionFeedUrl.length; i++) {
			HashMap<String, String> subTopicMap = new HashMap<String, String>();
			subTopicMap.put(KEY_URL_SUBTOPIC, mFashionFeedUrl[i]);
			subTopicMap.put(KEY_IMG_SUBTOPIC, mFashionImgUrl[i]);
			mSections.add(subTopicMap);
		}
		
		return mSections;
	}
	
	
	
}
