package demo.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestService {

    @Autowired
    TestMapper mapper;

}


