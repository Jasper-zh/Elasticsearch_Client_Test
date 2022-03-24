package cn.yuornotes.es;

import java.io.IOException;
import cn.yuornotes.es.client.SearchClient;
import cn.yuornotes.es.pojo.Product;

/**
 * @author hao
 * @date 2022/3/18 17:07
 */
public class Main {

    public static void main(String[] args) throws IOException {
        SearchClient.queryPage();
    }

}
