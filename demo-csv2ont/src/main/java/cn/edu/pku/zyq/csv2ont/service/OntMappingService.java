package cn.edu.pku.zyq.csv2ont.service;

import cn.edu.pku.zyq.common.mongodb.data.MongoConcept;

import cn.edu.pku.zyq.common.mongodb.service.ReMongoTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@PropertySource("classpath:application.yml")
public class OntMappingService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoTemplateService mongoTemplateService;
//    private ReMongoTemplateService mongoTemplateService;

    /**
     * 获取指定概念的所有属性
     * @param conceptId 概念节点的id
     * @return MongoConcept列表，存储所有属性节点
     */
    public List<MongoConcept> getPropertiesOfConcept(long conceptId) {
        System.out.println("[info]: conceptId: " + conceptId);

        Criteria criteria = mongoTemplateService.isCondition("fromId", (int) conceptId);
        Query query = new Query(criteria);
        query.fields().include("toId");
        List<Object> list = mongoTemplate.find(query, Object.class, "hasProperty_edge");

        System.out.println("[info]: OntMappingService: list.size:" + list.size());

        if(list.size() > 0) {
            List<Integer> propertyIdList = new ArrayList<>();
            for(Object o : list) {
                propertyIdList.add(((Map<String, Integer>) o).get("toId"));
            }

            criteria = mongoTemplateService.inCondition("nodeId", propertyIdList);
            query = new Query(criteria);
            return mongoTemplate.find(query, MongoConcept.class, "property_node");
        }
        return new ArrayList<>();
    }

    /**
     * 测试用
     * @param
     */
    public void getConcept() {
        Query query = new Query();
        query.fields().include("toId");
        List<Object> list = mongoTemplate.findAll(Object.class, "hasProperty_edge");
        System.out.println("[info]: OntMappingService: list.size: " + list.size());
    }
}
