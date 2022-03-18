package cn.edu.pku.zyq.demo;

import cn.edu.pku.zyq.common.mongodb.data.MongoConcept;
import cn.edu.pku.zyq.csv2ont.Csv2ontApplication;
import cn.edu.pku.zyq.csv2ont.service.OntMappingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Csv2ontApplication.class)
class Csv2ontApplicationTests {

    @Autowired
    private OntMappingService ontMappingService;

    @Test
    void tc_getConceptId() {
        ontMappingService.getConcept();
    }

    @Test
    void tc_getPropertiesOfConcept() {
        long id = 24183;
        List<MongoConcept> list = ontMappingService.getPropertiesOfConcept(id);
        System.out.println("[info]: concept node id: " + id + ", it contains following properties:");
        for(MongoConcept m : list) {
            System.out.println("[info]: property node id: " + m.getNodeId());
        }
    }

}
