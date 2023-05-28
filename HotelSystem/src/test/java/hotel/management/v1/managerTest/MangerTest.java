package hotel.management.v1.managerTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hotel.management.v1.manager.dao.ManagerDao;
import hotel.management.v1.manager.dto.ManagerDto;

@SpringBootTest
public class MangerTest {
    @Autowired
    private ManagerDao dao;
    
    @Test
    public void test() {
    }
}



