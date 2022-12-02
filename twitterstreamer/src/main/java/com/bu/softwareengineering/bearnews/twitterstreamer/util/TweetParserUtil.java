package com.bu.softwareengineering.bearnews.twitterstreamer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetParserUtil {

    public  List<String> extractUrls(String tweet)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        //String urlRegex = "\\\\b((https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(tweet);

        while (urlMatcher.find())
        {
            containedUrls.add(tweet.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        return containedUrls;
    }

    public List<String> extractHashTag(String tweet){
        String hashtagRegex = "^#\\w+|\\s#\\w+";
        Pattern hashtagPattern = Pattern.compile(hashtagRegex);
        List<String> hashtags = new ArrayList<>();
        Matcher hashTagMatcher = hashtagPattern.matcher(tweet);

        while(hashTagMatcher.find()){
            hashtags.add(hashTagMatcher.group().replace(" ", "").replace("#", ""));
        }
        return hashtags;

    }
}
