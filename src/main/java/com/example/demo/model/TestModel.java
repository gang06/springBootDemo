package com.example.demo.model;

import com.example.demo.vo.ItemVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author gang
 * @date 2021/9/6 16:25
 * @version 1.0
 */
@Data
public class TestModel implements Serializable {
    private static final long serialVersionUID = -1L;

    private List<ItemVO> data;


}
