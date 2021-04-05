package org.fisco.bcos.cloud;

import org.fisco.bcos.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class identiyTest {

    @Autowired
    private identiy t;
    @Test
    public void display_test() throws IOException {
        t.display_MAU_lsit();
        t.display_CDC_lsit();
        t.display_VI_lsit();
        t.display_TE_lsit();
    }
}