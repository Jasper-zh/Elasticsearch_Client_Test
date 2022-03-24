package cn.yuornotes.es.client;

import cn.yuornotes.es.pojo.Product;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;

import java.io.IOException;

/**
 * @author hao
 * @date 2022/3/23 15:59
 */
public class SearchClient {
    public static void queryAll() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(b -> b.index("product").query(
                q -> q.matchAll(m -> m.queryName(""))
        ), Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void queryMatch() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(b -> b.index("product").query(
                q -> q.match(m -> m.field("name").query("佳能"))
        ), Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void queryTerm() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(b -> b.index("product").query(
                q -> q.term(m -> m.field("name").value("佳能"))
        ), Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void queryRange() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(b -> b.index("product").query(
                q -> q.range(m -> m.field("price").gte(JsonData.of(3000)))
        ), Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void queryFilter() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(
                b -> b.index("product")
                        .source(s -> s.filter(f -> f.includes("name")))
                        .query(q -> q.range(m -> m.field("price").gte(JsonData.of(3000)))
                        ),
                Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void querySort() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(
                b -> b.index("product")
                        .sort(s -> s.field(v -> v.field("price").order(SortOrder.Asc)))
                        .query(q -> q.matchAll(v -> v.queryName(""))
                        ),
                Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }
    public static void queryPage() throws IOException {
        SearchResponse<Product> search = EsClientUtil.getClient().search(
                b -> b.index("product")
                        .from(0)
                        .size(2)
                        .query(q -> q.matchAll(v -> v.queryName(""))
                        ),
                Product.class);
        EsClientUtil.closeClient();
        for(Hit<Product> obj : search.hits().hits()){
            System.out.println(obj.source());
        }
    }

}
