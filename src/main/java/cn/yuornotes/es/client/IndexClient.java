package cn.yuornotes.es.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;

import java.io.IOException;

/**
 * @author hao
 * @date 2022/3/23 11:30
 */
public class IndexClient {

    public static void createIndex(String index) throws IOException {
        ElasticsearchClient client = EsClientUtil.getClient();
        // 通过client获取indexClient
        ElasticsearchIndicesClient indices = client.indices();
        // IndexClient通过create方法传入自定义CreateIndexRequest对象，完成操作
        indices.create(
                c -> c.index(index)
                        .mappings(
                                typeMapping -> typeMapping.properties("name"
                                        , property -> property.text(
                                                text -> text.index(true)
                                        )
                                )
                        )
        );
        EsClientUtil.closeClient();
    }

    public static void deleteIndex(String index) throws IOException {
        ElasticsearchClient client = EsClientUtil.getClient();
        // 通过client获取indexClient
        ElasticsearchIndicesClient indices = client.indices();
        // IndexClient通过create方法传入自定义CreateIndexRequest对象，完成操作
        indices.delete(d -> d.index(index));
        EsClientUtil.closeClient();
    }
}
