package com.xujin.test;

import cn.hutool.core.util.StrUtil;
import com.xujin.Constants;
import com.xujin.pojo.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2021/10/13
 * @Author xujin
 * @TODO 数据解析
 * @Version 1.0
 */
public class CatData {
    public static void main(String[] args) {
        new CatData().catJD("码出高效").forEach(System.out::println);
    }

    public List<Product> catJD(String keyWords) {


        ArrayList<Product> products = new ArrayList<>();
        try {
            Document parse = Jsoup.parse(new URL(Constants.JD_URL + keyWords), 30000);
            Element element = parse.getElementById("J_goodsList");
            Elements elements = element.getElementsByTag("li");
            System.out.println(elements.size());
            for (Element el : elements) {
                String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
                String price = el.getElementsByTag("i").eq(0).text();
                Elements nameElements = el.getElementsByClass("p-name");
                String name = "";
                for (Element e : nameElements) {
                    name = e.getElementsByTag("em").eq(0).text();
                    if (StrUtil.isNotEmpty(name)) break;
                }
                Elements shopElements = el.getElementsByClass("p-shop");
                String shopName = "";
                for (Element e : shopElements) {
                    shopName = e.getElementsByTag("a").eq(0).text();
                    if (StrUtil.isNotEmpty(name)) break;
                }
                Product product = new Product().setProductImg(img).setProductPrice(price).setProductName(name).setShopName(shopName);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
