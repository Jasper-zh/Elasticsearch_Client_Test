package cn.yuornotes.es.client;

import cn.yuornotes.es.pojo.Product;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.JsonpSerializer;
import jakarta.json.stream.JsonGenerator;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author hao
 * @date 2022/3/23 11:32
 */
public class DocClient {
    /**
     * 添加文档
     * @throws IOException
     */
    public static void addDoc() throws IOException {
        EsClientUtil.getClient().index(
                indexRequestBuild -> indexRequestBuild.index("product").id("003").document(new Product("微单相机",6700))
        );
        EsClientUtil.closeClient();
    }

    /**
     * 获取文档
     * @return
     * @throws IOException
     */
    public static Product getDoc() throws IOException {
        GetResponse<Product> response = EsClientUtil.getClient().get(getRequestBuild -> getRequestBuild.index("product").id("001"), Product.class);
        EsClientUtil.closeClient();
        return response.source();
    }

    /**
     * 修改文档
     * @throws IOException
     */
    public static void updateDoc() throws IOException {
        EsClientUtil.getClient().update(
                updateRequestBuild -> updateRequestBuild.index("product").id("001").doc(new Product(2999))
        ,Product.class);
        EsClientUtil.closeClient();
    }

    /**
     * 删除文档
     * @throws IOException
     */
    public static void delDoc() throws IOException {
        EsClientUtil.getClient().delete(b -> b.index("product").id("001"));
        EsClientUtil.closeClient();
    }

    public static void bulkDoc() throws IOException {
        ArrayList<BulkOperation> list = new ArrayList<>();
        list.add(new BulkOperation.Builder().create(b -> b.id("004").document(new Product("冰箱", 2999))).build());
        list.add(new BulkOperation.Builder().create(b -> b.id("005").document(new Product("电视", 3299))).build());
        list.add(new BulkOperation.Builder().create(b -> b.id("006").document(new Product("投影仪", 3999))).build());
        list.add(new BulkOperation.Builder().delete(b -> b.id("001")).build());
        list.add(new BulkOperation.Builder().delete(b -> b.id("002")).build());
        boolean add = list.add(new BulkOperation.Builder().index(b -> b.id("003").document(new Product("佳能M6II", 6900))).build());
        EsClientUtil.getClient().bulk(b -> b.index("product").operations(list));
        EsClientUtil.closeClient();
    }
}
