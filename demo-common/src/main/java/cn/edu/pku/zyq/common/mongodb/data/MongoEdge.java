package cn.edu.pku.zyq.common.mongodb.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MongoEdge {

    private Long edgeId;

    private Long fromId;

    private Long toId;

    private Long relationId =-1L; //如果没有，则为"-1"

    public Long getRelationId() {
        return relationId;
    }

    public Long getEdgeId() {
        return edgeId;
    }

    private Long clusterId = -1L; //簇ID，如果没有，则为"-1"

    private String edgeLabel; //"HAS_SUB_CONCEPT" "HAS_PROPERTY" "HAS_RELATION_CONCEPT" "HAS_RELATION_INSTANCE"

    private String name="";

    private Map<String,Object> properties = new HashMap<>();

    //可信相关
    private Double trustedValue = .0; //可信值

    private Integer trustedLevel = 1; //可信等级

    private Double trustedReliability = .0; //可靠性

    private Double trustedCorrectness = .0; //正确性

    private Double trustedTimeliness = .0; //时效性

    private Double trustedSafety = .0; //安全性

    private Double trustedAccessibility = .0; //可访问性

    private Map<String, Object> evidences = new HashMap<>();

    private Map<String,Double> tagScore =new HashMap<>();

    private String source="";//记录概念的来源
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("relationId:- " + this.getRelationId() +", CLusterId:- " + this.getEdgeId());
        return str.toString();
    }

    public MongoEdge(){}

    public MongoEdge(Long id, Long fId, Long tId, Long cId, String label){
        edgeId=id;
        fromId=fId;
        toId=tId;
        clusterId=cId;
        edgeLabel=label;
        if(label.equals("HAS_SUB_CONCEPT")) {
            name="有子概念";
        } else if (label.equals("HAS_PROPERTY")) {
            name="有属性";
        } else if(label.equals("HAS_SUB_TAG")) {
            name="有子标签";
        } else if(label.equals("HAS_SUB_PROPERTY")) {
            name="有子属性";
        } else if(label.equals("HAS_SUB_RELATION")) {
            name="有子关系";
        } else if(label.equals("HAS_CONCEPT")) {
            name="有属性";
        }
    }

    public MongoEdge(Long id, Long fId, Long tId, Long cId, String label, String edgeName, Long rId){
        edgeId=id;
        fromId=fId;
        toId=tId;
        clusterId=cId;
        edgeLabel=label;
        name=edgeName;
        relationId=rId;
    }
}
