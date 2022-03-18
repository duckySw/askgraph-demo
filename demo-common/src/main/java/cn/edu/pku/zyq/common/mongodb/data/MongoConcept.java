package cn.edu.pku.zyq.common.mongodb.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MongoConcept {
    private Long nodeId; //节点ID（属性值），用来串联图数据库和文档数据库

    private String name; //节点名称

    private Long parent=-1L; //父节点ID，如果没有，为-1

    private String types=""; //父节点名称，如果没有，为""

    private String nodeLabel; //"Concept"/"Property"/"Relation"
    //30个城市
    private boolean judgeParent = false;//判断是否有孩子
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

    public MongoConcept() {}

    public MongoConcept(String thisName, Long id, Long parentId, String parentName, String label, boolean judge){
        name=thisName;
        nodeId=id;
        parent=parentId;
        types=parentName;
        nodeLabel=label;
        judgeParent=judge;
    }


    public Long getNodeId() {
        return nodeId;
    }
}
