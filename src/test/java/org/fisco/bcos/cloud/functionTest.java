package org.fisco.bcos.cloud;

import net.bytebuddy.asm.Advice;
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
public class functionTest {

    @Autowired
    private function f;
    @Test
    public void test1() throws IOException {
        f.test();
    }
}