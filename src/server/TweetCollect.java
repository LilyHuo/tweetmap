package server;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TweetCollect {
	
	
	static TwitterStream tweet_Collector ;   
	static Connection conn ;
	static ConfigurationBuilder cb;
	static StatusListener listener;
	
	
	public static void tweetget() throws Exception
		  {
		ArrayList<inform> datas = new ArrayList<inform>();
		 cb = new ConfigurationBuilder();
    	 cb.setDebugEnabled(true)
    	 .setOAuthConsumerKey("*")
         .setOAuthConsumerSecret("*")
         .setOAuthAccessToken("*")
         .setOAuthAccessTokenSecret("*");
    	 
       
		tweet_Collector = new TwitterStreamFactory(cb.build()).getInstance(); 
    	conn = DBcon.createDBConnection();
    	FilterQuery fq = new FilterQuery();    
        double[][] boundingBox= {{-180, -90}, {180, 90}};
        fq.locations(boundingBox);
        
    	//final String a = keyword;
        listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            	//if(status.getId()!=0 && status.getGeoLocation()!=null&&status.getLang().equalsIgnoreCase("en"))
            		if(status.getId()!=0 && status.getGeoLocation()!=null)
					{
	            		PreparedStatement preparedStatement = null; //An object that represents a precompiled SQL statement.
	            		try {
							preparedStatement = conn.prepareStatement("insert into TWEETER values(?,?,?,?,?)");
							preparedStatement.setString(1, Long.toString(status.getId()));
							preparedStatement.setString(2,status.getUser().getScreenName() );
							//preparedStatement.setString(3, a);
							preparedStatement.setDouble(4, status.getGeoLocation().getLatitude());
							preparedStatement.setDouble(5, status.getGeoLocation().getLongitude());
							preparedStatement.executeUpdate();
							System.out.println("Input successfully...");
							}	
						 catch (SQLException e) {e.printStackTrace();}
	            	}
            	       
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
             //   System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
               // System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        //FilterQuery q = new FilterQuery();
		//String[] keywordsArray = { keyword};
	    //q.track(keywordsArray);		
	    
        //tweet_Collector.addListener(listener);
        //tweet_Collector.filter(q);
       // tweet_Collector.sample();
        
        //tweet_Collector.shutdown();
        tweet_Collector.addListener(listener);
        tweet_Collector.filter(fq);   
     }

        
    }
