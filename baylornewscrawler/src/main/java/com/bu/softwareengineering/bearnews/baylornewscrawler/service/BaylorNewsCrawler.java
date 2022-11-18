package com.bu.softwareengineering.bearnews.baylornewscrawler.service;

import com.bu.softwareengineering.bearnews.baylornewscrawler.domain.BaylorNews;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaylorNewsCrawler {

    @Autowired
    BearNewsAPIKafkaProducer bearNewsAPIKafkaProducer;

    //@Scheduled(cron = "0/55 * * * * ?")
    @Scheduled(cron = "0 30 17 ? * *") // runs exactly at 5:30 PM everyday
    public List<BaylorNews> crawlDataFromBaylorNews() throws IOException {
        Document document = Jsoup.connect("https://www.baylor.edu/bn/news.php").get();
        Elements elements = document.getElementsByClass("newsWidget");
        List<BaylorNews> newsItems = new ArrayList<>();
        for(Element e : elements){
            for(int i=1; i<=e.childrenSize(); i++){
                Element newsElement = e.getElementsByClass("newsWidget-item-"+i).get(0);

                Element dateElements = newsElement.getElementsByClass("dateWidget-content").get(0);
                String newsDate = dateElements.text();


                Element titleElement = newsElement.select("a").first();
                String href = titleElement.attr("href");
                String title = titleElement.text();


                Element descriptionElements = newsElement.getElementsByClass("newsWidget-lead").get(0);
                String descriptionText = descriptionElements.text();

                String thumbnailUrl = "";
                Elements thumbnailElement = newsElement.getElementsByClass("newsWidget-thumb");
                for(Element thumbE : thumbnailElement){
                    thumbnailUrl = thumbE.select("img").attr("src");
                }
                BaylorNews baylorNews = new BaylorNews();
                baylorNews.setTitle(title);
                baylorNews.setDetailLink(href);
                baylorNews.setDescription(descriptionText);
                baylorNews.setDate(newsDate);
                baylorNews.setThumbnail(thumbnailUrl);
                newsItems.add(baylorNews);

            }

        }

        bearNewsAPIKafkaProducer.sendToBearNewsApiBackend(newsItems);
        return newsItems;
    }


}
