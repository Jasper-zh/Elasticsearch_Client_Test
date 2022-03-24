package cn.yuornotes.es.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * @author hao
 * @date 2022/3/23 11:14
 */
public class EsClientUtil {
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "eA95WVi0HZarqp*LwoOa";
    private static CredentialsProvider credentialsProvider = null;
    private static RestClient restClient;
    private static ElasticsearchTransport transport;
    /*
     * elastic凭证
     */
    static {
        credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
    }

    /**
     * 获取ElasticsearchClient
     * @return
     */
    public static ElasticsearchClient getClient(){
        // 1.获取rest客户端，认证也在这部分解决
        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1",9201))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        restClient = builder.build();
        // 2.获取transport客户端
        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        // 3.获取 api 客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

    /**
     * 关闭客户端
     */
    public static void closeClient(){
        try{
            transport.close();
            restClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
