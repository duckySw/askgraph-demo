package cn.edu.pku.zyq.csv2ont.service;

import cn.edu.pku.zyq.common.mongodb.data.MongoConcept;
import cn.edu.pku.zyq.common.mongodb.data.MongoEdge;
import cn.edu.pku.zyq.common.mongodb.data.MongoInstance;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MongoTemplateService {

    @Resource
    private MongoTemplate mongoTemplate;

    public Object getCollectionNames() {
        // 执行获取集合名称列表
        return mongoTemplate.getCollectionNames();
    }

    /**
     * 检测集合【是否存在】
     *
     * @return 集合是否存在
     */
    public boolean collectionExists(String collectionName) {
        // 设置集合名称
        // 检测新的集合是否存在，返回检测结果
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * 删除【集合】
     *
     * @return 创建集合结果
     */
    public Object dropCollection(String collectionName) {
        if (!collectionExists(collectionName)) {
            return "集合不存在";
        }
        // 执行删除集合
        mongoTemplate.getCollection(collectionName).drop();
        // 检测新的集合是否存在，返回删除结果
        return !mongoTemplate.collectionExists(collectionName) ? "删除集合成功" : "删除集合失败";
    }

    //文档操作

    //保存或者插入文档
    public <T> void saveDocument(T newObject, String collectionName){
        mongoTemplate.save(newObject,collectionName);
    }

    //删除文档
    //抽象好像不可用
    public <T> void deleteDocumentByAccurate(String propertyName,T propertyValue,String collectionName){
//        System.out.println(propertyName);
//        System.out.println(propertyValue);
        Criteria criteria = Criteria.where(propertyName).is(propertyValue);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        String resultInfo = "成功删除 " + result.getDeletedCount() + " 条文档信息";
        System.out.println(resultInfo);
    }


    public void deleteDocumentById(String documentId,String collectionName){
        Criteria criteria = Criteria.where("_id").is(documentId);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        String resultInfo = "成功删除 " + result.getDeletedCount() + " 条文档信息";
        System.out.println(resultInfo);
    }

    public void deleteDocumentByclusterId(Long clusterId,String collectionName){
        Criteria criteria = Criteria.where("clusterId").is(clusterId);
        Query query = new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        String resultInfo = "成功删除 " + result.getDeletedCount() + " 条文档信息";
        System.out.println(resultInfo);
    }

    //删除单个并且返回
    public Object findAndRemoveByclusterId(Long clusterId,String collectionName){
        Criteria criteria = Criteria.where("clusterId").is(clusterId);
        Query query = new Query(criteria);
        Object result = mongoTemplate.findAndRemove(query, Object.class, collectionName);
        return result;
    }

    //删除全部并且返回
    public Object findAllAndRemoveByclusterId(Long clusterId,String collectionName){
        Criteria criteria = Criteria.where("clusterId").is(clusterId);
        Query query = new Query(criteria);
        Object result = mongoTemplate.findAllAndRemove(query, Object.class, collectionName);
        return result;
    }

    //方便使用的函数
    public <T extends Number> Criteria isCondition(String propertyName,T propertyValue){
        return Criteria.where(propertyName).is(propertyValue);
    }

    public <T extends Comparable<T>> Criteria betweenCondition(String propertyName,T lowerBound, T upperBound){
        assert(upperBound.compareTo(lowerBound)>0);
        return Criteria.where(propertyName).lt(upperBound).gt(lowerBound);
    }

    public  Criteria inCondition(String propertyName,List<?> propertyValueList){
        return Criteria.where(propertyName).in(propertyValueList);
    }

    public Criteria andCondition(Criteria condition1,Criteria condition2){
        return new Criteria().andOperator(condition1, condition2);
    }

    public Criteria orCondition(Criteria condition1,Criteria condition2){
        return new Criteria().orOperator(condition1, condition2);
    }

    //查询文档
    public List<Object> findAll(String collectionName) {
        // 执行查询集合中全部文档信息
        List<Object> documentList = mongoTemplate.findAll(Object.class, collectionName);
        // 输出结果
        return documentList;
    }

    public Object findById(String Id,String collectionName) {
        Object document = mongoTemplate.findById(Id,Object.class, collectionName);
        return document;
    }

    public MongoConcept findByNodeId(long nodeId, String collectionName) {
        Criteria criteria = isCondition("nodeId", (int) nodeId);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, MongoConcept.class, collectionName);
    }

    public List<MongoConcept> findByNodeName(String nodeName, String collectionName) {
        Criteria criteria = Criteria.where("name").is(nodeName);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, MongoConcept.class, collectionName);
    }

    public MongoInstance findInstanceByName(long conceptId, String instanceName, String collectionName) {
        Criteria criteria = andCondition(isCondition("concept", (int) conceptId), Criteria.where("name").is(instanceName));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, MongoInstance.class, collectionName);
    }

    public List<MongoInstance> findInstancesByPropVals(long conceptId, Map<String, String> propertyName2val, Map<String, String> colNameType, String collectionName) {
        Criteria criteria = isCondition("concept", (int) conceptId);
        for (String propertyName : propertyName2val.keySet()) {
            String value = propertyName2val.get(propertyName);
            String type = colNameType.get(propertyName);
            Object v = null;
            if (type.equals("Integer")) {
                v = Integer.valueOf(value);
            } else if (type.equals("Double")) {
                v = Double.valueOf(value);
            } else if (type.equals("Long")) {
                v = Long.valueOf(value);
            } else if (type.equals("String")) {
                v = value;
            }
//            else if (type.equals("Date")){ } 不会用时间来检索，不写
            criteria = andCondition(criteria, Criteria.where("properties."+propertyName).is(v));
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, MongoInstance.class, collectionName);
    }

    // 只返回 HAS_RELATION_CONCEPT 或 HAS_SUB_CONCEPT
    public MongoEdge findByEdgeId(long edgeId) {
        Criteria criteria = isCondition("edgeId", (int) edgeId);
        Query query = new Query(criteria);
        MongoEdge res = mongoTemplate.findOne(query, MongoEdge.class, "hasRelationConcept_edge");
        if(res != null) {
            return res;
        } else {
            return mongoTemplate.findOne(query, MongoEdge.class, "hasSubConcept_edge");
        }
    }

    public List<MongoEdge> findByEdgeName(String edgeName, String collectionName) {
        Criteria criteria = Criteria.where("name").is(edgeName);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, MongoEdge.class, collectionName);
    }

    public List<Object> findByClusterId(Long clusterId,String collectionName){
        Criteria criteria =isCondition("clusterId",clusterId);
        Query query = new Query(criteria);
        List<Object> documentList = mongoTemplate.find(query, Object.class, collectionName);
        return documentList;
    }
}
