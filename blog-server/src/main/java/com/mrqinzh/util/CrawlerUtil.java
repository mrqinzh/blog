package com.mrqinzh.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrawlerUtil {

    public static void run() {
        String url = "https://element.eleme.cn/#/zh-CN/component/icon";
        Map<String, String> cookies = new HashMap<>();
        cookies.put("_ga", "GA1.2.955844633.1627374890");
        cookies.put("_gid", "GA1.2.1503873209.1628476402");
        try {
            Document document = Jsoup.connect(url)
                    .cookies(cookies)
                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36")
                    .get();
            Element app = document.getElementById("app");
            System.out.println(app);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
    }

}
