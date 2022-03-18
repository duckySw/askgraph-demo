package cn.edu.pku.zyq.common.mongodb.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MongoInstance {
    private Long nodeId; //节点ID（属性值），可以从40000开始+1

    private String name; //节点名称

    public String getName(){
        if(name==null) {
            return "";
        }
        return name;
    }

    private Long concept=-1L; //实例所属概念ID，如果没有，为-1

    private String types=""; //实例所属概念名称名称，如果没有，为""

    private Map<String,Object> properties = new HashMap<>();

    private String nodeLabel="Instance";  //"Instance"
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

    private String source="";

    public MongoInstance(){}

    public MongoInstance(String nodeName, String type, Long conceptId, Long id){
        nodeId=id;
        types=type;
        concept=conceptId;
        name=nodeName;
    }

    public MongoInstance(String nodeName, String type, Long conceptId, Long id, int trustedlevel){
        nodeId=id;
        types=type;
        concept=conceptId;
        name=nodeName;
        trustedLevel=trustedlevel;
    }

    public MongoInstance(String nodeName, String type, Long conceptId, Long id, Map<String,Object> newProperties){
        nodeId=id;
        types=type;
        concept=conceptId;
        name=nodeName;
        properties = newProperties;
    }

}
